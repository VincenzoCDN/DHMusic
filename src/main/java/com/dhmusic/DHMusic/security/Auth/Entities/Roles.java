package com.dhmusic.DHMusic.security.Auth.Entities;

public enum Roles {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_REGISTERED("ROLE_REGISTERED"),
    ROLE_ARTIST("ROLE_ARTIST"),
    ROLE_VIEWER("ROLE_VIEWWER");
    final String value;
    Roles(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return value;
    }

}
