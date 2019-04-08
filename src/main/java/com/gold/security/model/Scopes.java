package com.gold.security.model;

public enum Scopes {
    REFRESH_TOKEN;
    
    public String authority() {
        return this.name();
    }
}
