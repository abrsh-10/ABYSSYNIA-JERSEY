package com.abyssiniajersey.jersey;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("user")
@Builder
public class NewJersey {
        private List<Size> size;
        private String initialYear;
        private String finalYear;
        private String club;
        private Kit kit;
        private List<String> imageUrl;
        private int quantity;
        private double initialPrice;
        private double currentPrice;
        private List<String> categories;
        private String description;

    }
