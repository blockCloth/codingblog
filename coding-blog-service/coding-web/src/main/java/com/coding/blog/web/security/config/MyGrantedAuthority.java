package com.coding.blog.web.security.config;

import org.springframework.security.core.GrantedAuthority;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MyGrantedAuthority implements GrantedAuthority {
    private String authority;

    public MyGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    @JsonProperty
    public String getAuthority() {
        return authority;
    }
}