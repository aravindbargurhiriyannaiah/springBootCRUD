package com.funkyganesha.springbootCRUD.controller;

import com.funkyganesha.springbootCRUD.model.Car;
import com.funkyganesha.springbootCRUD.repository.CarRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CarRepository carRepository;

    @Before
    public void beforeEach() {
        webTestClient.delete().uri("/v1/deleteAllCars").exchange().expectStatus().isOk();

        Car car = build("Toyota", "Camry", 2009);
        webTestClient.post().uri("/v1/").body(BodyInserters.fromObject(car)).exchange().expectStatus().isOk();

        car = build("Nissan", "Altima", 2018);
        webTestClient.post().uri("/v1/").body(BodyInserters.fromObject(car)).exchange().expectStatus().isOk();

        car = build("Jeep", "Cherokee", 2016);
        webTestClient.post().uri("/v1/").body(BodyInserters.fromObject(car)).exchange().expectStatus().isOk();

        car = build("Honda", "Accord", 2006);
        webTestClient.post().uri("/v1/").body(BodyInserters.fromObject(car)).exchange().expectStatus().isOk();
    }

    @AfterEach
    public void afterEach() {
        webTestClient.delete().uri("/v1/deleteAllCars").exchange().expectStatus().isOk();
    }

    @Test
    public void test_sunnyScenario() {
        webTestClient.get()
                .uri("/v1/{year}", 2016)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.[0].companyName").isNotEmpty()
                .jsonPath("$.[0].companyName").isEqualTo("Jeep")
                .jsonPath("$.[0].name").isNotEmpty()
                .jsonPath("$.[0].name").isEqualTo("Cherokee");
    }

    @Test
    public void test_nullYear() {
        Integer year = null;
        webTestClient.get()
                .uri("/v1/{year}", year)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    private Car build(String companyName, String name, Integer year) {
        Car car = Car
                .builder()
                .id(UUID.randomUUID().toString())
                .companyName(companyName)
                .name(name)
                .year(year)
                .build();
        return car;
    }
}