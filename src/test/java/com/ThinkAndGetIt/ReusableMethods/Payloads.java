package com.ThinkAndGetIt.ReusableMethods;

import java.util.HashMap;

public class Payloads {

    public static HashMap payload(String email, String password, String fName, String lName, String phone){
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", password);
        payload.put("firstName", fName);
        payload.put("lastName", lName);
        payload.put("phone", phone);
        return payload;
    }

    public static HashMap<String, Object> changePasswordPayload(String currentPassword, String newPassword) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("currentPassword", currentPassword); // Matches "currentPassword" in documentation
        payload.put("newPassword", newPassword);         // Matches "newPassword" in documentation
        return payload;
    }

}
