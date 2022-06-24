package com.sirenanalytics.worldbank_auth.service.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sirenanalytics.worldbank_auth.config.AppProperties;
import com.sirenanalytics.worldbank_auth.model.entity.FailedLogin;
import com.sirenanalytics.worldbank_auth.model.simple.AuthRequest;
import com.sirenanalytics.worldbank_auth.model.simple.AuthResponse;
import com.sirenanalytics.worldbank_auth.model.simple.RefreshRequest;
import com.sirenanalytics.worldbank_auth.model.simple.ResetRequest;
import com.sirenanalytics.worldbank_auth.repository.FailedLoginRepository;
import com.sirenanalytics.worldbank_auth.service.AuthService;
import com.sirenanalytics.worldbank_auth.util.SharedSecretUtil;
import com.sirenanalytics.worldbank_common.model.entity.LogResetPassword;
import com.sirenanalytics.worldbank_common.model.entity.Session;
import com.sirenanalytics.worldbank_common.model.entity.User;
import com.sirenanalytics.worldbank_common.repository.CommonLogResetPasswordRepository;
import com.sirenanalytics.worldbank_common.repository.CommonSessionRepository;
import com.sirenanalytics.worldbank_common.repository.CommonUserRepository;
import com.sirenanalytics.worldbank_common.util.ContextUtil;
import com.sirenanalytics.worldbank_common.util.EncryptionUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService
{
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Resource
    CommonUserRepository commonUserRepository;

    @Resource
    CommonSessionRepository commonSessionRepository;

    @Resource
    FailedLoginRepository failedLoginRepository;

    @Resource
    CommonLogResetPasswordRepository commonLogResetPasswordRepository;

    @Resource
    AppProperties appProperties;

    @Override
    @Transactional
    public AuthResponse validateUser(AuthRequest authRequest)
    {
        AuthResponse authResponse = new AuthResponse();

        validateInputParams(authRequest, authResponse);
        if (Objects.nonNull(authResponse.getHttpStatus()))
            return authResponse;

        User user = getUserByUsername(authRequest.getUsername(), authResponse);
        if (Objects.isNull(user))
            return authResponse;

        Optional<FailedLogin> optionalFailedLogin = failedLoginRepository.findByUserId(user.getId());
        FailedLogin failedLogin = optionalFailedLogin.isEmpty() ?
                new FailedLogin(user.getId(), 0) : optionalFailedLogin.get();

        processConsecutiveFailedLoginDelay(authRequest, authResponse, failedLogin);
        if (Objects.nonNull(authResponse.getHttpStatus()))
            return authResponse;

        if (!isPasswordValid(user, authRequest))
        {
            processFailedLogin(user, authResponse, failedLogin);
            return authResponse;
        }

        if (isAccountLocked(user, authResponse))
            return authResponse;

        /*----------------------------------------------------------+
        |	User validates.  Build valid auth response, create a    |
        |   new session record in the db (and purge any existing    |
        |   ones, and delete any consecutive failed login attempts. |
        +----------------------------------------------------------*/
        buildAuthResponseAndCreateSession(user, authResponse, 0);
        failedLoginRepository.deleteExistingByUserId(user.getId());
        return authResponse;

    }

    private void validateInputParams(AuthRequest authRequest, AuthResponse authResponse)
    {
		/*----------------------------------------------------------+
		|	First validate that we have a username and password to	|
		|	work with.  If we don't, there's no point in proceeding	|
		+----------------------------------------------------------*/
        if (Objects.isNull(authRequest.getUsername()) || Objects.isNull(authRequest.getPassword()))
        {
            authResponse.getResponseMap().put("error", "invalid_request");
            authResponse.getResponseMap().put("error_description", "username and/or password missing");
            authResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
        }
    }

    private User getUserByUsername(String username, AuthResponse authResponse)
    {
        /*----------------------------------------------------------+
		|	Look for a user with this username.  If one is not		|
		| 	found, flag with invalid credentials and return.    	|
		+----------------------------------------------------------*/
        Optional<User> optionalUser = commonUserRepository.findByUsername(username);
        if (optionalUser.isEmpty())
        {
            /*----------------------------------------------------------+
            |	We didn't find the username. Invalidate and return.		|
            +----------------------------------------------------------*/
            authResponse.getResponseMap().put("error", "invalid_grant");
            authResponse.getResponseMap().put("error_description", "Invalid user credentials");
            authResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);

            return null;
        }
        /*----------------------------------------------------------+
        |   User found.  Return for the next step.                  |
        +----------------------------------------------------------*/
        return optionalUser.get();
    }

    private void processConsecutiveFailedLoginDelay(AuthRequest authRequest, AuthResponse authResponse, FailedLogin failedLogin)
    {
        /*----------------------------------------------------------+
        |   We give the user 3 chances to login successfully.       |
        |   After that, delay logins by 5 minutes for each failed   |
        |   login, up to a max of one hour.  Subtract only 2 to     |
        |   account for 0 being a spot.                             |
        +----------------------------------------------------------*/
        int delayInMinutes = (failedLogin.getConsecutiveFailCount() - 2) * 5;
        if (delayInMinutes <= 0)
            return;

        /*----------------------------------------------------------+
        |   Get the time of the first failed login and add the      |
        |   delayInMinutes we're supposed to wait.  If now is after |
        |   the required delay, they may proceed.  If not, return   |
        |   an error indicating how much longer until they may try  |
        |   again.                                                  |
        +----------------------------------------------------------*/
        LocalDateTime dateTimeUseCanTryAgain = failedLogin.getUpdateTimestamp().plusMinutes(delayInMinutes);
        Duration remainingTimeUntilNextTry = Duration.between(LocalDateTime.now(), dateTimeUseCanTryAgain);

        //cap at 1 hour
        if (remainingTimeUntilNextTry.toMinutes() > 60)
            remainingTimeUntilNextTry = Duration.ofMinutes(60);

        //a negative numbers means the requisite delay time has passed
        if (remainingTimeUntilNextTry.isNegative())
            return;

        authResponse.getResponseMap().put("error", "account_locked");
        authResponse.getResponseMap().put("error_description", "Try again in: " +
                remainingTimeUntilNextTry.toMinutesPart() + " minute(s), " +
                remainingTimeUntilNextTry.toSecondsPart() + " second(s)");
        authResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
    }

    private boolean isPasswordValid(User user, AuthRequest authRequest)
    {
        /*----------------------------------------------------------+
        |	Confirm the submitted and encrypted passwords match     |
        +----------------------------------------------------------*/
        return new EncryptionUtil().matches(authRequest.getPassword(), user.getPassword());
    }

    private void processFailedLogin(User user, AuthResponse authResponse, FailedLogin failedLogin)
    {
        /*----------------------------------------------------------+
        |   The passwords don't match.  Increment the failedLogin   |
        |   and save.  We track consecutive failed logins.          |
        +----------------------------------------------------------*/
        failedLogin.incrementConsecutiveFailedLoginCount();
        failedLogin.setUpdateTimestamp(LocalDateTime.now());
        failedLoginRepository.save(failedLogin);

        authResponse.getResponseMap().put("error", "invalid_grant");
        authResponse.getResponseMap().put("error_description", "Invalid user credentials");
        authResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
    }

    private boolean isAccountLocked(User user, AuthResponse authResponse)
    {
        if (!user.isEnabled())
        {
            authResponse.getResponseMap().put("error", "account_locked");
            authResponse.getResponseMap().put("error_description", "Account Locked.  Please contact an administrator");
            authResponse.setHttpStatus(HttpStatus.FORBIDDEN);

            return true;
        }
        return false;
    }

    private void buildAuthResponseAndCreateSession(User user, AuthResponse authResponse, int currentConsecutiveRefreshes)
    {
        String accessToken = generateAccessToken(user, authResponse);
        String refreshToken = generateRefreshToken(authResponse);
        authResponse.getResponseMap().put("id", String.valueOf(user.getId()));
        authResponse.setHttpStatus(HttpStatus.OK);

        Session session = new Session();
        session.setUsername(user.getUsername());
        session.setConsecutiveRefreshes(currentConsecutiveRefreshes);
        session.setAccessToken(accessToken);
        session.setRefreshToken(refreshToken);
        session.setCreationTime(LocalDateTime.now());

        commonSessionRepository.deleteExistingSessionsForUser(user.getUsername());
        commonSessionRepository.save(session);
    }

    private String generateAccessToken(User user, AuthResponse authResponse)
    {
        try
        {
            /*------------------------------------------------------------------+
            |   Get the accessTokenLifeSpan from the app settings. Add this     |
            |   to "now" to get the token expiration.  We need a java.util.Date |
            |   as well as longs representing seconds from epoch.               |
            +------------------------------------------------------------------*/
            Duration accessTokenLifeSpan = Duration.ofMinutes(appProperties.getAccessTokenLifespanInMinutes());
            Instant tokenExpirationInstant = LocalDateTime.now().plus(accessTokenLifeSpan)
                    .atZone(ZoneId.systemDefault()).toInstant();

            Date tokenExpirationDate = Date.from(tokenExpirationInstant);
            long secondsFromEpochToTokenExpiration = tokenExpirationInstant.getEpochSecond();
            long secondsFromEpochToNow = Instant.now().getEpochSecond();

            // Prepare JWT with claims set
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer("moj")
                    .expirationTime(tokenExpirationDate)
                    .claim("exp", secondsFromEpochToTokenExpiration)
                    .claim("iat", secondsFromEpochToNow)
                    .claim("nbf", secondsFromEpochToNow)
                    //TODO: This will change with each env
                    .claim("iss", "MOJ")
                    .claim("aud", "MOJ")
                    .claim("authorities", user.getAuthorities())
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

            // Apply the HMAC protection
            signedJWT.sign(SharedSecretUtil.getJWSSigner());

			/*----------------------------------------------------------------------+
			|	Serialize the signedJWT.  This will compact it to something like	|
			|	eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJpbXBhY3QiLCJzdWIiOiJBTlRPSU5FIiwi	|
			|	ZXhwIjoxNjA4Njg3NjU4fQ.lutKA9eFiPrgeqz_2jHEuEza-6hnNNY3YotuOvtv9rc	|
			+----------------------------------------------------------------------*/
            String serializedJWT = signedJWT.serialize();

            //Build the response map
            authResponse.getResponseMap().put("access_token", serializedJWT);
            authResponse.getResponseMap().put("expires_in", String.valueOf(accessTokenLifeSpan.getSeconds()));
            authResponse.getResponseMap().put("token_type", "bearer");

            return serializedJWT;
        }
        catch (JOSEException e)
        {
            log.error("JOSEException", e);
        }
        return null;
    }

    private String generateRefreshToken(AuthResponse authResponse)
    {
        /*------------------------------------------------------------------------------------------+
        |   Generate a refresh token, which is simply a 128 byte random string, and put it in the   |
        |   response.  We also return it so it can be used for the user's session.                  |
        +------------------------------------------------------------------------------------------*/
        String refreshTokenString = RandomStringUtils.randomAlphanumeric(128);
        authResponse.getResponseMap().put("refresh_token", refreshTokenString);
        return refreshTokenString;
    }

    @Override
    @Transactional
    public AuthResponse refreshUserToken(RefreshRequest refreshRequest)
    {
        AuthResponse authResponse = new AuthResponse();
        /*----------------------------------------------------------+
        |   Make sure we get a valid username and refresh token     |
        +----------------------------------------------------------*/
        validateRefreshRequest(refreshRequest, authResponse);
        if (Objects.nonNull(authResponse.getHttpStatus()))
            return authResponse;

        /*----------------------------------------------------------+
        |   Make sure we already have a session to refresh          |
        +----------------------------------------------------------*/
        Optional<Session> optionalSession = commonSessionRepository.findByRefreshToken(refreshRequest.getRefreshToken());
        if (optionalSession.isEmpty())
        {
            authResponse.getResponseMap().put("error", "invalid_grant");
            authResponse.getResponseMap().put("error_description", "Invalid refresh token");
            authResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);

            return authResponse;
        }

        /*----------------------------------------------------------+
        |   Make sure the refresh token (not access) expiration     |
        |   is before now                                           |
        +----------------------------------------------------------*/
        Session session = optionalSession.get();
        LocalDateTime refreshTokenExpiration = session.getCreationTime()
                .plusMinutes(appProperties.getRefreshTokenLifespanInMinutes());

        if (refreshTokenExpiration.isBefore(LocalDateTime.now()))
        {
            authResponse.getResponseMap().put("error", "invalid_grant");
            authResponse.getResponseMap().put("error_description", "Expired refresh token");
            authResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
            commonSessionRepository.deleteByRefreshToken(refreshRequest.getRefreshToken());

            return authResponse;
        }

        /*----------------------------------------------------------+
        |   We allow a limited number of refreshes before we force  |
        |   the user to re-authenticate.                            |
        +----------------------------------------------------------*/
        session.incrementConsecutiveRefreshes();
        if (session.getConsecutiveRefreshes() > appProperties.getMaxConsecutiveRefreshesAllowed())
        {
            authResponse.getResponseMap().put("error", "refresh_limit");
            authResponse.getResponseMap().put("error_description", "Refresh limit reached.  Please log in again.");
            authResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
            commonSessionRepository.deleteByRefreshToken(refreshRequest.getRefreshToken());

            return authResponse;
        }

        /*----------------------------------------------------------+
        |   Do a sanity check to ensure the user exists             |
        +----------------------------------------------------------*/
        Optional<User> optionalUser = commonUserRepository.findByUsername(refreshRequest.getUsername());
        if (optionalUser.isEmpty())
        {
            authResponse.getResponseMap().put("error", "invalid_grant");
            authResponse.getResponseMap().put("error_description", "Invalid user credentials");
            authResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);

            return authResponse;
        }

        /*----------------------------------------------------------+
        |   Make sure the user's account is still enabled           |
        +----------------------------------------------------------*/
        User user = optionalUser.get();
        if (!user.isEnabled())
        {
            authResponse.getResponseMap().put("error", "account_locked");
            authResponse.getResponseMap().put("error_description", "Account Locked.  Please contact an administrator");
            authResponse.setHttpStatus(HttpStatus.FORBIDDEN);

            return authResponse;
        }

        /*----------------------------------------------------------+
        |   We're good.  Delete the current session and generate    |
        |   a new access and refresh token on a new session         |
        +----------------------------------------------------------*/
        commonSessionRepository.deleteByRefreshToken(refreshRequest.getRefreshToken());
        buildAuthResponseAndCreateSession(optionalUser.get(), authResponse, session.getConsecutiveRefreshes());

        return authResponse;
    }

    private void validateRefreshRequest(RefreshRequest refreshRequest, AuthResponse authResponse)
    {
        if (Objects.isNull(refreshRequest.getUsername()) || Objects.isNull(refreshRequest.getRefreshToken()))
        {
            authResponse.getResponseMap().put("error", "invalid_request");
            authResponse.getResponseMap().put("error_description", "Missing parameter: username or refresh token");
            authResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @Transactional
    public void logUserOut()
    {
        /*----------------------------------------------------------+
        |   Simply remove the record from the session table         |
        +----------------------------------------------------------*/
        String accessToken = ContextUtil.getCurrentAccessToken();
        if (Objects.nonNull(accessToken))
            commonSessionRepository.deleteByAccessToken(accessToken);
    }

    @Override
    @Transactional
    public AuthResponse resetPassword(ResetRequest resetRequest)
    {
        AuthResponse authResponse = new AuthResponse();
        validateResetRequest(resetRequest, authResponse);
        if (Objects.nonNull(authResponse.getHttpStatus()))
            return authResponse;

        /*----------------------------------------------------------+
        |   If we don't find the user, fail attempt                 |
        +----------------------------------------------------------*/
        User user = getUserByUsername(resetRequest.getUsername(), authResponse);
        if (Objects.nonNull(authResponse.getHttpStatus()))
            return authResponse;

        assert user != null;    //user should never be null from here on

        /*----------------------------------------------------------+
        |   If we don't find the key, fail attempt                  |
        +----------------------------------------------------------*/
        Optional<LogResetPassword> optionalLogResetPassword = commonLogResetPasswordRepository.findByKey(resetRequest.getResetKey());
        if (optionalLogResetPassword.isEmpty())
        {
            authResponse.getResponseMap().put("error", "invalid_grant");
            authResponse.getResponseMap().put("error_description", "Invalid user credentials");
            authResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
            return authResponse;
        }

        /*----------------------------------------------------------+
        |   The reset request user id must match the username that  |
        |   was passed in.  In addition, the status needs to be "P" |
        |   (pending) and this record expires after 1 hour from     |
        |   creation.  If either are false, fail this reset.        |
        +----------------------------------------------------------*/
        LogResetPassword logResetPassword = optionalLogResetPassword.get();
        if (!user.getId().equals(logResetPassword.getUserId()) ||
                !"P".equalsIgnoreCase(logResetPassword.getStatus()) ||
                logResetPassword.getCreateTimestamp().plusHours(1).isBefore(LocalDateTime.now()))
        {
            authResponse.getResponseMap().put("error", "invalid_grant");
            authResponse.getResponseMap().put("error_description", "This key is stale");
            authResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
            return authResponse;
        }

        /*----------------------------------------------------------+
        |	All good.  Invalidate reset key so it can't be used     |
        |   again.  Force the user to change their password on the  |
        |   next request.  Return valid credentials.                |
        +----------------------------------------------------------*/
        logResetPassword.setStatus("C");
        commonLogResetPasswordRepository.save(logResetPassword);
        user.setChangePasswordRequired(true);
        commonUserRepository.save(user);
        buildAuthResponseAndCreateSession(user, authResponse, 0);

        return authResponse;
    }

    private void validateResetRequest(ResetRequest resetRequest, AuthResponse authResponse)
    {
        if (Objects.isNull(resetRequest.getUsername()) || Objects.isNull(resetRequest.getResetKey()))
        {
            authResponse.getResponseMap().put("error", "invalid_request");
            authResponse.getResponseMap().put("error_description", "Missing parameter: username or reset key");
            authResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
        }
    }
}
