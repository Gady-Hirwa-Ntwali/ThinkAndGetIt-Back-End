package com.ThinkAndGetIt.ReusableMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestData {

    public static String DynamicEmail = "gady_" + System.currentTimeMillis() + "@gmail.com";
    public static String Password = "dlksjflkdj@T2y";
    public static String FName = "Am not";
    public static String LName = "A Human";
    public static String Phone = "0782738589435";

    public static Map<String, Object> createProductPayload(String categoryId, String size, String color, String sku) {
        Map<String, Object> productBody = new HashMap<>();

        productBody.put("name", "sneakers " + System.currentTimeMillis());
        productBody.put("description", " sport shoes");
        productBody.put("price", 150);
        productBody.put("comparePrice", 200);
        productBody.put("categoryId", categoryId);

        List<String> tagsList = new ArrayList<>();
        tagsList.add("new-arrival");
        tagsList.add("trending");
        productBody.put("tags", tagsList);

        productBody.put("isFeatured", true);
        productBody.put("isFlashSale", true);
        productBody.put("flashSalePrice", 120);

        List<Map<String, Object>> variantsList = new ArrayList<>();
        Map<String, Object> singleVariant = new HashMap<>();
        singleVariant.put("size", size);
        singleVariant.put("color", color);
        singleVariant.put("colorHex", "#000000");
        singleVariant.put("sku", sku + "_" + System.currentTimeMillis());
        singleVariant.put("stock", 50);
        singleVariant.put("price", 150);

        variantsList.add(singleVariant);
        productBody.put("variants", variantsList);

        return productBody;
    }

    public static Map<String, Object> getFilteredProduct(int page, int limit, String category, String sort, boolean flash_sale){
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", 1);
        queryParams.put("limit", 5);
        queryParams.put("category", "electronics");
        queryParams.put("sort", "price_asc");
        queryParams.put("flash_sale", false);
        return queryParams;
    }

    public static Map<String, Object> updateProduct(String name, int price){
        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("name", name);
        updateBody.put("price", price);
        return updateBody;
    }
}