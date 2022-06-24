package com.sirenanalytics.worldbank_common.util;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncryptionUtil implements PasswordEncoder
{
    private static final Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();

    @Override
    public String encode(CharSequence rawPassword)
    {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword)
    {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
