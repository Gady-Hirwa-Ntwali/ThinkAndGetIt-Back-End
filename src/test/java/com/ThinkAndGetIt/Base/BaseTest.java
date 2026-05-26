package com.ThinkAndGetIt.Base;

import com.ThinkAndGetIt.utils.Utils;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;

    public static Properties properties;
    @BeforeClass
    public void setup() throws IOException {
        properties = Utils.loadProperties("src/test/resources/config.properties");

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(properties.getProperty("base.url"))
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectResponseTime(
                        org.hamcrest.Matchers.lessThan(5000L),
                        java.util.concurrent.TimeUnit.MILLISECONDS
                )
                .build();
    }
}
