package com.ThinkAndGetIt.Routes;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.TestData;

import static com.ThinkAndGetIt.TestCases.OrderPlacement.GetSingleOrder.dynamicOrderId;

public class EndPoints extends BaseTest {
    public static TestData testData = new TestData();
    public static final String Register = "auth/register";
    public static final String Login = "auth/login";
    public static final String Forgot_Password = "auth/forgot-password";
    public static final String PasswordReset = "auth/reset-password";
    public static final String User = "auth/me";
    public static final String TokenRefresh = "/auth/refresh";
    public static final String VerifyEmail = "auth/verify-email";
    public static final String Products = "products";
    public static final String  Cart = "cart";
    public static final String AddToCart = Cart + "/items/";
    public static final String SaveForLater = AddToCart +testData.activeItemId + "/save-for-later/";
    public static final String DeleteItemInCart = AddToCart + testData.activeItemId;
    public static final String Coupon = "/cart/coupon";
    public static final String GET_ADDRESSES_ENDPOINT = "/users/addresses";
    public static final String CHANGE_PASSWORD_ENDPOINT = "/users/change-password";
    public static final String AVATAR_ENDPOINT = "/users/avatar";
    public static final String CONTROL_NAME = "avatar";
    public static final String Orders = "orders";
   public static final String SingleOrder = Orders + "/" + dynamicOrderId;
    public static final String AdminAllOrders = "orders/admin/all"; // Matches: /orders/admin/all

    public static String getCancelOrderEndpoint(String orderId) {
        return Orders + "/" + orderId + "/cancel";
    }

    public static String getSingleOrderEndpoint(String orderId) {
        return Orders + "/" + orderId;
    }


    public static String getReturnOrderEndpoint(String orderId) {
        return Orders + "/" + orderId + "/return";
    }

    public static String getPaymentProofEndpoint(String orderId) {
        return Orders + "/" + orderId + "/payment-proof";
    }

    public static String getAdminUpdateStatusEndpoint(String orderId) {
        return "orders/admin/" + orderId + "/status"; // Matches: /orders/admin/{id}/status
    }

}
