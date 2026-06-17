package com.ThinkAndGetIt.TestCases.OrderPlacement;

import com.ThinkAndGetIt.Base.BaseTest;
import com.ThinkAndGetIt.ReusableMethods.Methods;
import com.ThinkAndGetIt.ReusableMethods.TestData;
import com.ThinkAndGetIt.Routes.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UploadPaymentProof extends BaseTest {

    private String authToken;
    private String dynamicOrderId;
    private File dummyProofFile;

    @BeforeClass
    public void setUp() {
        TestData.renewProductVariables();
        this.authToken = TestData.token;

        this.dummyProofFile = new File("src/test/resources/test_proof.png");

        if (!dummyProofFile.exists()) {
            dummyProofFile.getParentFile().mkdirs();
            try {
                java.nio.file.Files.write(dummyProofFile.toPath(), new byte[]{0});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Response response = Methods.GetMethod(EndPoints.Orders, authToken);
        if (response.statusCode() == 200 && response.path("data[0].id") != null) {
            this.dynamicOrderId = response.path("data[0].id");
        } else {
            this.dynamicOrderId = "00000000-0000-0000-0000-000000000000";
        }
    }

    @Test
    public void testUploadPaymentProofSuccessfully() {
        Response response = Methods.postMultipartMethod(
                EndPoints.getPaymentProofEndpoint(dynamicOrderId),
                "proof",
                dummyProofFile,
                authToken
        );

        assertThat(response.statusCode(), equalTo(200));

        if (response.statusCode() == 200) {
            assertThat(response.path("success"), equalTo(true));
        }
    }

    @Test
    public void testUploadPaymentProofWithoutAuthentication() {
        Response response = Methods.postMultipartMethod(
                EndPoints.getPaymentProofEndpoint(dynamicOrderId),
                "proof",
                dummyProofFile,
                ""
        );

        assertThat(response.statusCode(), anyOf(equalTo(401), equalTo(403)));
    }

    @Test
    public void testUploadPaymentProofWithWrongControlName() {
        Response response = Methods.postMultipartMethod(
                EndPoints.getPaymentProofEndpoint(dynamicOrderId),
                "invalidKeyName",
                dummyProofFile,
                authToken
        );

        assertThat(response.statusCode(), equalTo(400));
        assertThat(response.path("success"), equalTo(false));
    }

    @Test
    public void testUploadPaymentProofNotFound() {
        String nonExistentOrderId = "99999999-9999-9999-9999-999999999999";

        Response response = Methods.postMultipartMethod(
                EndPoints.getPaymentProofEndpoint(nonExistentOrderId),
                "proof",
                dummyProofFile,
                authToken
        );

        assertThat(response.statusCode(), anyOf(equalTo(404), equalTo(400)));
        assertThat(response.path("success"), equalTo(false));
    }
}