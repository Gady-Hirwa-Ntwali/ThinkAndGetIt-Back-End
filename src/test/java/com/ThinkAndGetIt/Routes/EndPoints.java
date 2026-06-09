package com.ThinkAndGetIt.Routes;

import com.ThinkAndGetIt.Base.BaseTest;

public class EndPoints extends BaseTest {
    public static final String Register = "auth/register";
    public static final String Login = "auth/login";
    public static final String Forgot_Password = "auth/forgot-password";
    public static final String PasswordReset = "auth/reset-password";
    public static final String User = "auth/me";
    public static final String TokenRefresh = "/auth/refresh";
    public static final String VerifyEmail = "auth/verify-email";
    public static final String Products = "products";
    public static final String  Cart = "cart";
    public static final String AddToCart = Cart + "/items";
    public static  final String UpdateCart = Cart + "/items/" + properties.getProperty("productId");
}
