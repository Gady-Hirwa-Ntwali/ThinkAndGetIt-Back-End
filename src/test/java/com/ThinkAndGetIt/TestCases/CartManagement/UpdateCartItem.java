package com.ThinkAndGetIt.TestCases.CartManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.TestCases.Authorization.LoginTests;
import com.ThinkAndGetIt.TestCases.ProductManagement.CreateProduct;
import org.testng.annotations.Test;

import static com.ThinkAndGetIt.ReusableMethods.TestData.token;
import static com.ThinkAndGetIt.ReusableMethods.TestData.updateCartItem;
import static com.ThinkAndGetIt.Routes.EndPoints.UpdateCart;

public class UpdateCartItem extends BaseTest {
    @Test
    public void updateCartSuccessfully(){
        LoginTests.successfulLogin();
        CreateProduct.createProductSuccessfully();
        AddToCart.addToCartSuccessfully();
        Methods.putMethod(UpdateCart, updateCartItem(1),  token);
    }
}
