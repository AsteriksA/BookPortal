package com.gold.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

//    TODO: check this method
//    Analog web.xml
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{MapperConfig.class, WebSecurityConfig.class, PersistenceConfig.class};
    }

//    return class describes dispatcher servlet
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
