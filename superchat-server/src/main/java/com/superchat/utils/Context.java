package com.superchat.utils;

import com.superchat.Entity.User;

public class Context {

    static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Context.user = user;
    }
}
