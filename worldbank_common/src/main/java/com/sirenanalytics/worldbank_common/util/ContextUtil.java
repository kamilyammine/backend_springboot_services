package com.sirenanalytics.worldbank_common.util;

import com.sirenanalytics.worldbank_common.model.entity.User;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

public class ContextUtil
{
    public static Long getCurrentUserId()
    {
        /*------------------------------------------------------------------+
        |   There should always be a User in the security context.  But, if |
        |   for some reason there is not, simply catch the exception and    |
        |   put an entry of -1 for the change user.                         |
        +------------------------------------------------------------------*/
        try
        {
            return ((User) getContext().getAuthentication().getPrincipal()).getId();
        }
        catch (NullPointerException | ClassCastException e)
        {
            return -1L;
        }
    }

    public static String getCurrentUsername()
    {
        try
        {
            return ((User) getContext().getAuthentication().getPrincipal()).getUsername();
        }
        catch (NullPointerException | ClassCastException e)
        {
            return null;
        }
    }

    public static String getCurrentAccessToken()
    {
        try
        {
            return (String) getContext().getAuthentication().getCredentials();
        }
        catch (NullPointerException | ClassCastException e)
        {
            return null;
        }
    }

    public static User getCurrentUser()
    {
        try
        {
            return ((User) getContext().getAuthentication().getPrincipal());
        }
        catch (NullPointerException | ClassCastException e)
        {
            return null;
        }
    }
}
