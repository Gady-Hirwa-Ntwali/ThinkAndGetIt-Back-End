package com.ThinkAndGetIt.ReusableMethods;

import com.ThinkAndGetIt.Base.BaseTest;
import io.restassured.response.Response;

import static com.ThinkAndGetIt.Routes.EndPoints.User;

public class GetUsers extends BaseTest {
    public static Response currentUser(String currentUser){
        return Methods.GetMethod(User, currentUser);
    }
}
