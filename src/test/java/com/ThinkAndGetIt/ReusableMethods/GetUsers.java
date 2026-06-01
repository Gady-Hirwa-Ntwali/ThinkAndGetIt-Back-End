package com.ThinkAndGetIt.ReusableMethods;

import com.ThinkAndGetIt.Base.BaseTest;

import static com.ThinkAndGetIt.Routes.EndPoints.User;

public class GetUsers extends BaseTest {
    public static void currentUser(String currentUser){
        Methods.GetMethod(User, currentUser);
    }
}
