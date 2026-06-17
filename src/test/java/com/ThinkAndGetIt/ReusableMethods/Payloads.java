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
        payload.put("currentPassword", currentPassword);
        payload.put("newPassword", newPassword);
        return payload;
    }


    public static HashMap<String, Object> addAddressPayload(String label, String firstName, String lastName, String phone, String street, String city, String state, String country, String postalCode, boolean isDefault) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("label", label);
        payload.put("firstName", firstName);
        payload.put("lastName", lastName);
        payload.put("phone", phone);
        payload.put("street", street);
        payload.put("city", city);
        payload.put("state", state);
        payload.put("country", country);
        payload.put("postalCode", postalCode);
        payload.put("isDefault", isDefault);
        return payload;
    }

    public static HashMap<String, Object> placeOrderPayload(String addressId, String paymentMethod, String notes, int shippingFee) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("addressId", addressId);
        payload.put("paymentMethod", paymentMethod);
        payload.put("notes", notes);
        payload.put("shippingFee", shippingFee);
        return payload;
    }

    public static HashMap<String, Object> returnOrderPayload(String reason) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("reason", reason); // Matches "reason" request body requirement
        return payload;
    }

    public static HashMap<String, Object> updateOrderStatusPayload(String status, String message, String trackingNumber) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("status", status); // Matches "status" field
        payload.put("message", message); // Matches "message" field
        payload.put("trackingNumber", trackingNumber); // Matches "trackingNumber" field
        return payload;
    }

}
