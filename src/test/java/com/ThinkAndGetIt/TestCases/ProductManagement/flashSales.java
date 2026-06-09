package com.ThinkAndGetIt.TestCases.ProductManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class flashSales extends BaseTest {
    @Test
    public void getFlashSalesProductsSuccessTest() {
        Response response = Methods.GetMethod(EndPoints.Products + "/flash-sales");

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));
        List<Boolean> flashSaleFlags = response.path("data.isFlashSale");
        assertThat("Every product returned must have isFlashSale set to true",
                flashSaleFlags, everyItem(equalTo(true)));


    }
    @Test
    public void getFlashSalesProductsInvalidAPi() {
        Response response = Methods.GetMethod(EndPoints.Products + "/flashin");

        boolean success = response.path("success");
        String message = response.path("message");
        assertThat(response.statusCode(), equalTo(404));
        assertThat(success, equalTo(false));
        assertThat(message, equalTo("Product not found"));
    }
}
