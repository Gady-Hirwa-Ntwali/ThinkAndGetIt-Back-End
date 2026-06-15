package com.ThinkAndGetIt.TestCases.UserManagement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UploadUserAvatar extends BaseTest {

    private String authToken;
    private final String AVATAR_ENDPOINT = "/users/avatar";
    private final String CONTROL_NAME = "avatar";

    @BeforeClass
    public void setUpUserToken() {
        this.authToken = TestData.token;
    }

    @Test
    public void testUploadUserAvatarSuccessfully() {
        File validImageFile = new File("src/test/resources/test-avatar.png");

        if (!validImageFile.exists()) {
            throw new RuntimeException("Test artifact missing! Please add 'test-avatar.png' to src/test/resources/");
        }
        Response response = Methods.postMultipartMethod(AVATAR_ENDPOINT, CONTROL_NAME, validImageFile, authToken);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));
        assertThat(response.path("message"), equalTo("Avatar updated"));
        assertThat(response.path("data.id"), notNullValue());
    }

    @Test
    public void testUploadAvatarWithoutAuthentication() {
        File validImageFile = new File("src/test/resources/test-avatar.png");
        Response response = Methods.postMultipartMethod(AVATAR_ENDPOINT, CONTROL_NAME, validImageFile, "");
        assertThat(response.statusCode(), anyOf(equalTo(401), equalTo(403)));
    }

    @Test
    public void testUploadAvatarWithUnsupportedFileType() {
        File invalidFile = new File("src/test/resources/invalid-avatar.txt");
        try {
            invalidFile.getParentFile().mkdirs();
            invalidFile.createNewFile();
        } catch (Exception e) { /* no-op */ }

        Response response = Methods.postMultipartMethod(AVATAR_ENDPOINT, CONTROL_NAME, invalidFile, authToken);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("success"), equalTo(true));
        assertThat(response.path("message"), equalTo("Avatar updated"));
        assertThat(response.path("data.avatar"), nullValue());

        if (invalidFile.exists()) {
            invalidFile.delete();
        }
    }
}