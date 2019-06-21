package com.gold.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RestAccessService {

//    public boolean hasPermitState(Object userState) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserContext userContext = (UserContext) auth.getPrincipal();
//
//        return userContext.getStatePermission().toString().equals(userState);
//    }
//
//    public boolean hasPermitState() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserContext userContext = (UserContext) auth.getPrincipal();
//        return userContext.getStatePermission().toString().equals("ACTIVATED");
//    }
}
