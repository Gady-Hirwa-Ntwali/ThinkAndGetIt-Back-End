package com.ThinkAndGetIt.TestCases.CartManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;

import com.ThinkAndGetIt.ReusableMethods.TestData;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static com.ThinkAndGetIt.ReusableMethods.TestData.token;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UpdateCartItem extends BaseTest {

    @Test
    public void updateCartSuccessfullyTest(){
        Response putResponse = Methods.updateCartItemQuantity(5);
        assertThat(putResponse.statusCode(), equalTo(200));
        assertThat(putResponse.path("message"), equalTo("Cart updated"));
    }

    @Test
    public void testUpdateCartItemWithNegativeQuantity() {

        Response putResponse = Methods.updateCartItemQuantity(-5);

        assertThat(putResponse.statusCode(), equalTo(400));
        assertThat(putResponse.path("success"), equalTo(false));
    }

    @Test
    public void testUpdateNonExistentCartItem() {
        String fakeCartItemId = "00000000-0000-0000-0000-000000000000";
        Map<String, Object> body = TestData.updateCartItem(3);

        Response putResponse = Methods.putMethod("/cart/items/" + fakeCartItemId, body, token);

        assertThat(putResponse.statusCode(), equalTo(404));
        assertThat(putResponse.path("message"), equalTo("Record not found"));
    }
}