package com.ThinkAndGetIt.TestCases.CategoryManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetCategoryBySlug extends BaseTest {

    private String getValidCategorySlug() {
        Response response = Methods.GetMethod("/categories", "");

        List<String> slugs = response.path("data.slug");

        if (slugs != null) {
            for (String slug : slugs) {
                if (slug != null && !slug.trim().isEmpty()) {
                    return slug;
                }
            }
        }
        return "electronics";
    }

    @Test
    public void testGetSingleCategoryBySlugSuccessfully() {
        String validSlug = getValidCategorySlug();

        String path = "/categories/" + validSlug;
        Response response = Methods.GetMethod(path, "");

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));

        assertThat(response.path("message"), anyOf(equalTo("Success"), equalTo("Category details")));
        assertThat(response.path("data"), is(notNullValue()));

        Object dataSlugObj = response.path("data.slug");
        if (dataSlugObj instanceof List) {
            List<?> slugList = (List<?>) dataSlugObj;
            assertThat(slugList.get(0).toString(), equalTo(validSlug));
        } else if (dataSlugObj != null) {
            assertThat(dataSlugObj.toString(), equalTo(validSlug));
        }
    }

    @Test
    public void testGetCategoryByNonExistentSlug() {
        String path = "/categories/this-slug-does-not-exist-12345";
        Response response = Methods.GetMethod(path, "");

        assertThat(response.statusCode(), equalTo(404));
        assertThat(response.path("success"), equalTo(false));
        assertThat(response.path("message"), equalTo("Category not found"));
    }
}