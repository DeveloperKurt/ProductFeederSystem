package com.developerkurt.model;

import lombok.Builder;
import lombok.Data;
import lombok.val;

import java.util.Arrays;
import java.util.Random;

@Data
@Builder
public class Product {

    private Integer id;

    private String name;

    private Double price;

    private Category category;

    public static Product createMockProduct() {

        val random = new Random();
        val possibleCategoryList = Arrays.asList(
                Category.HOME_DECOR,
                Category.FASHION,
                Category.ELECTRONIC);

        int id = random.nextInt(1000);

        return Product.builder()
                .id(id)
                .category(possibleCategoryList.get(random.nextInt(3)))
                .name("Product " + id)
                .price(random.nextDouble())
                .build();
    }

}
