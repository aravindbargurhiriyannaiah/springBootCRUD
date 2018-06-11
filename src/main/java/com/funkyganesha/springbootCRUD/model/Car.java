package com.funkyganesha.springbootCRUD.model;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "car")
@Builder
@Getter
public class Car {
    @Id
    private String id;

    private String companyName;

    private Integer year;

    private String name;

    @Tolerate
    public Car() {
    }
}
