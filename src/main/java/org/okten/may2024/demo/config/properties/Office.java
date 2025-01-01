package org.okten.may2024.demo.config.properties;

import lombok.Data;

@Data
public class Office {

    private String name;

    private String contactNumber;

    private Address address;
}
