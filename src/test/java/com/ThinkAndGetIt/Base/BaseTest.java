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
    public static RequestSpecification requestSpec;
    public static ResponseSpecification responseSpec;
    public static Properties properties = Utils.loadProperties("src/test/resources/config.properties");
    public static String email = properties.getProperty("email");

    public static void reloadProperties() {
        properties = Utils.loadProperties("src/test/resources/config.properties");
        email = properties.getProperty("email");
        System.out.println("Config.properties reloaded into Java memory!");
    }

    @BeforeClass
    public void setup() throws IOException {
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