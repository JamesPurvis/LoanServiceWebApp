package me.jamespurvis.loanservicewebapp.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String dateOfBirth;

    private String state;

    private String city;

    private String streetAddress;

    private String zipCode;

    private String personalIdentifier;

    private double maxApprovedAmount;

}
