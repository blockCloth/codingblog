package com.coding.blog.common.util;

import org.springframework.context.ApplicationEvent;

public class ReloadSecurityEvent extends ApplicationEvent {
    public ReloadSecurityEvent(Object source) {
        super(source);
    }
}