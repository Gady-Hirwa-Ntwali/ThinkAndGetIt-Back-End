package com.ThinkAndGetIt.ReusableMethods;

import java.util.HashMap;

public class Payload {

    public static HashMap payload(String email, String password, String fName, String lName, String phone){
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", password);
        payload.put("firstName", fName);
        payload.put("lastName", lName);
        payload.put("phone", phone);
        return payload;
    }
}
