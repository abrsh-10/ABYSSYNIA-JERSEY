package com.abyssiniajersey.user;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("user")
@Builder
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private UserType userType;
    private String password;
    private String email;
    private LocalDate joinedOn;
}
