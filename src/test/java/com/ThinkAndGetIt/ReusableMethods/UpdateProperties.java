package com.ThinkAndGetIt.ReusableMethods;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class UpdateProperties {
    public static void updatePropertiesFile(String token, String refreshToken) {
        String filePath = "src/test/resources/Config.Properties";
        Properties props = new Properties();

        try (FileInputStream in = new FileInputStream(filePath)) {
            props.load(in);
        } catch (IOException e) {
            System.err.println("Could not load properties file.");
        }

        props.setProperty("token", token);
        props.setProperty("refreshToken", refreshToken);

        try (FileOutputStream out = new FileOutputStream(filePath)) {
            props.store(out, "Updated via Automation Login Test Execution");
            System.out.println("Tokens successfully updated in Config.Properties!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updatevariantId(String variantId, String productId){
        String filePath = "src/test/resources/Config.Properties";
        Properties props = new Properties();

        try (FileInputStream in = new FileInputStream(filePath)) {
            props.load(in);
        } catch (IOException e) {
            System.err.println("Could not load properties file.");
        }

        props.setProperty("variantId", variantId);
        props.setProperty("productId", productId);

        try (FileOutputStream out = new FileOutputStream(filePath)) {
            props.store(out, "Updated via Automation Login Test Execution");
            System.out.println("variantId successfully updated in Config.Properties!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}