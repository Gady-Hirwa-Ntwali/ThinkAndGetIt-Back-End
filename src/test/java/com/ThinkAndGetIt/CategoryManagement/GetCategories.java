package com.ThinkAndGetIt.CategoryManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetCategories extends BaseTest {

    @Test
    public void testGetAllCategoriesSuccessfully() {
        String path = "/categories";
        Response response = Methods.GetMethod(path, "");
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));
        assertThat(response.path("message"), equalTo("Success"));

        assertThat(response.path("data"), is(notNullValue()));
        assertThat(response.path("data"), instanceOf(java.util.List.class));
    }

    @Test
    public void testGetCategoriesWithInvalidMethod() {
        String path = "/categories";
        Response response = Methods.postMethod(path, new HashMap<>(), "");
        assertThat(response.statusCode(), anyOf(equalTo(401), equalTo(404)));
    }
}