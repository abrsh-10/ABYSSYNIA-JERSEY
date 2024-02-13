package com.abyssiniajersey.jersey;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("jersey")
@Builder
public class Jersey {
    @Id
    private String id;
    private String name;
    private List<Size> size;
    private String year;
    private String club;
    private Kit kit;
    private List<String> imageUrl;
    private boolean inStock;
    private int quantity;
    private int rating;
    private double initialPrice;
    private double currentPrice;
    private String SKU;
    private List<String> categories;
    private String description;

}
