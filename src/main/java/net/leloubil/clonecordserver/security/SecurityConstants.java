package net.leloubil.clonecordserver.security;

public class SecurityConstants {

    public static final String SECRET = "motdepassehahaha";
    public static final long EXPIRATION_TIME = 30 * 60 * 60 * 1000; // 30 sec
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/auth/register";

}
