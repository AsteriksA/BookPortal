package com.gold.security.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gold.security.exceptions.AuthMethodNotSupportedException;
import com.gold.security.exceptions.JwtExpiredTokenException;
import com.gold.util.ErrorCode;
import com.gold.util.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AjaxAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper mapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exp) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ErrorCode errorCode = ErrorCode.AUTHENTICATION;
        String message = "Authentication failed";
        Date date = new Date();

        if (exp instanceof BadCredentialsException) {
            message = "Invalid username or password";
        } else if (exp instanceof JwtExpiredTokenException) {
            message = "Token is expired";
            errorCode = ErrorCode.AUTHENTICATION;
        } else if (exp instanceof AuthMethodNotSupportedException){
            message = exp.getMessage();
        }
            mapper.writeValue(response.getWriter(), ErrorResponse.of(httpStatus,message,errorCode, date));
}
}
