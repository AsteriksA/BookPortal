package com.gold.config;

import com.gold.service.impl.RestAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;

//@Configuration
//@ComponentScan("com.gold.security2")
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    /*private final  UserDetailsService userDetailsService;
    private final RestAccessService accessService;
    private final ApplicationContext context;

//    public MethodSecurityConfig(UserDetailsServiceImpl userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
         final DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
//        final MethodSecurityExpressionHandler expressionHandler = new CustomMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new CustomPermissionEvaluator(userDetailsService, accessService));
        expressionHandler.setApplicationContext(context);
        return expressionHandler;

//        @Bean
//        public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
//            DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
//            handler.setPermissionEvaluator(permissionEvaluator);
//            return handler;
//        }
    }*/
}
