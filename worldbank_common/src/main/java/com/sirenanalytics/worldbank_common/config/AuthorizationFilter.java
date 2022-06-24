package com.sirenanalytics.worldbank_common.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.sirenanalytics.worldbank_common.model.entity.Session;
import com.sirenanalytics.worldbank_common.model.entity.User;
import com.sirenanalytics.worldbank_common.model.entity.XSharedSecret;
import com.sirenanalytics.worldbank_common.repository.CommonSessionRepository;
import com.sirenanalytics.worldbank_common.service.CommonSecretsAndKeysService;
import com.sirenanalytics.worldbank_common.service.CommonUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorizationFilter extends OncePerRequestFilter
{
    private static final Logger log = LoggerFactory.getLogger(AuthorizationFilter.class);
    private static final List<MACVerifier> macVerifierList = new ArrayList<>();

    @Resource
    private CommonSecretsAndKeysService commonSharedSecretService;

    @Resource
    private CommonUserService commonUserService;

    @Resource
    private CommonSessionRepository commonSessionRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        log.info("==>> Begin AuthorizationFilter");
        /*------------------------------------------------------+
        |   Get the Authorization header, if there is one.      |
        |   This is the accessToken.                            |
        +------------------------------------------------------*/
        String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null)
        {
            log.info("requestTokenHeader != null");
            //Remove 'Bearer' and a possible space delimiting the rest of the token (Case Insensitive)
            requestTokenHeader = requestTokenHeader.replaceFirst("^(?i)bearer ?", "");

            /*------------------------------------------------------+
            |	Try to parse for a JWSObject first.  If we don't    |
            |   get that, don't bother with anything else.          |
            +------------------------------------------------------*/
            JWSObject jwsObject = parseRequestTokenHeaderForJWSObject(requestTokenHeader);
            if (jwsObject != null)
            {
                log.info("jwsObject != null");
                /*------------------------------------------------------+
                |	Get the claims of this token.  If the token         |
                |   validates we can trust its claims.                  |
                +------------------------------------------------------*/
                JWTClaimsSet jwtClaimsSet = parseRequestTokenHeaderForJWTClaimsSet(requestTokenHeader);
                log.info("jwtClaimsSet != null: {}", jwtClaimsSet != null);
                if (jwtClaimsSet != null && validateToken(jwsObject) && validateJwtClaims(jwtClaimsSet))
                {
                    /*----------------------------------------------+
                    |   The jwtClaimsSet subject is the username    |
                    +----------------------------------------------*/
                    String username = jwtClaimsSet.getSubject();

                    /*--------------------------------------------------------------+
                    |	This lookup occurs on every request.  It's fast, but this	|
                    |	would be a great candidate for caching, like REDIS			|
                    +--------------------------------------------------------------*/
                    Optional<User> optionalUser = commonUserService.findByUsername(username);
                    if (optionalUser.isPresent())
                    {
                        User user = optionalUser.get();
                        Optional<Session> optionalSession = commonSessionRepository.findByAccessToken(requestTokenHeader);
                        if (user.isEnabled() && optionalSession.isPresent())
                        {
                            /*------------------------------------------------------+
                            |   If the user is still enabled and there is a valid   |
                            |   session present, set the user info into the context |
                            |   so the app can access it, if needed.				|
                            +------------------------------------------------------*/
                            UsernamePasswordAuthenticationToken authToken
                                    = new UsernamePasswordAuthenticationToken(user, requestTokenHeader, user.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    }
                }
            }
        }
        log.info("==>> End AuthorizationFilter");
        filterChain.doFilter(request, response);
    }

    private JWSObject parseRequestTokenHeaderForJWSObject(String requestTokenHeader)
    {
		/*------------------------------------------------------+
		|	Convert the String into a JWSObject, if possible,	|
		|	and return.  If not, return null					|
		+------------------------------------------------------*/
        try
        {
            return JWSObject.parse(requestTokenHeader);
        }
        catch (Exception e)
        {
            log.error("Unable to parse requestTokenHeader to JWSObject: {}", requestTokenHeader, e);
        }
        return null;
    }

    private JWTClaimsSet parseRequestTokenHeaderForJWTClaimsSet(String requestTokenHeader)
    {
		/*------------------------------------------------------+
		|	Parse the jwt claimSet from the token string, if 	|
		|	possible, and return.  If not, return null	    	|
		+------------------------------------------------------*/
        try
        {
            return JWTParser.parse(requestTokenHeader).getJWTClaimsSet();
        }
        catch (Exception e)
        {
            log.error("Unable to parse requestTokenHeader to JWT: {}", requestTokenHeader, e);
        }
        return null;
    }

    private boolean validateToken(JWSObject jwsObject)
    {
		/*------------------------------------------------------+
		|	Attempt to verify this token's signature with the	|
		|	shared secret key.  Most of the time it will be		|
		|	the first item in the macVerifierList.				|
		|	On occasion, it will be the next one because we		|
		|	are rotating the keys periodically.  So the token	|
		|	may have been generated with the previous key.		|
		+------------------------------------------------------*/
        if (iterateOverMacVerifierList(jwsObject))
            return true;

		/*------------------------------------------------------+
		|	The auth server may have just generated a new		|
		|	shared secret, or this may be the first run since	|
		|	the service was started.  Reload the list from the	|
		|	database, which is acting as a keyvault and try		|
		|	one more time to be sure.							|
		+------------------------------------------------------*/
        try
        {
            List<XSharedSecret> xSharedSecretList = commonSharedSecretService.findAllXSharedSecretOrderByCreateDateTimeDesc();
            List<MACVerifier> updatedMacVerifierList = new ArrayList<>();
            for (XSharedSecret xSharedSecret : xSharedSecretList)
                updatedMacVerifierList.add(new MACVerifier(xSharedSecret.getKey()));

            macVerifierList.clear();
            macVerifierList.addAll(updatedMacVerifierList);
        }
        catch (JOSEException e)
        {
            log.error("Unable to create MACVerifier from given key", e);
        }

		/*------------------------------------------------------+
		|	After refreshing the MACVerifier list, if any keys	|
		|	validate, return true.  Otherwise return false.		|
		+------------------------------------------------------*/
        return iterateOverMacVerifierList(jwsObject);
    }

    private boolean iterateOverMacVerifierList(JWSObject jwsObject)
    {
        try
        {
            for (MACVerifier macVerifier : macVerifierList)
            {
                if (jwsObject.verify(macVerifier))
                {
                    log.info("jwsObject verified!");
                    return true;
                }
            }
        }
        catch (Exception e)
        {
            log.error("Unable to verify jwsObject", e);
        }
        return false;
    }

    private boolean validateJwtClaims(JWTClaimsSet jwtClaimsSet)
    {
        /*------------------------------------------------------+
		|   By the time we get here, we should have established |
		|   that the token is trustworthy.  Now we need to      |
		|   validate the claims.  The most important one is the |
		|   expiration.                                         |
        +------------------------------------------------------*/
        Date claimedExpirationDate = jwtClaimsSet.getExpirationTime();

        /*------------------------------------------------------+
		|   java.util.Date is legacy, but that's what Nimbus    |
		|   uses.  If expiration is before now, fail validation  |
        +------------------------------------------------------*/
        boolean claimsAreValid = claimedExpirationDate.after(new Date());
        log.info("claimsAreValid: {}", claimsAreValid);
        return claimsAreValid;

        /*------------------------------------------------------+
		|   Add additional validation as needed.                |
		+------------------------------------------------------*/
    }
}
