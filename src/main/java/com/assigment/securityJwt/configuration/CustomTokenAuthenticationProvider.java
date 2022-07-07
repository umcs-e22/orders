package com.assigment.securityJwt.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class CustomTokenAuthenticationProvider implements AuthenticationProvider {

    @Value("${my.secretKey}")
    private String secret;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String customToken = (String) authentication.getPrincipal();
        return getValidationToken(customToken);
    }

    private Authentication getValidationToken(String customToken) {
        boolean isValid = customToken.equals(secret);
        if (isValid)
            return new PreAuthenticatedAuthenticationToken("AuthenticatedUser", "ROLE_ADMIN");
        else
            throw new AccessDeniedException("Invalid authetication token");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Lets use inbuilt token class for simplicity
        return PreAuthenticatedAuthenticationToken.class.equals(authentication);
    }

}
