package com.funkyganesha.springbootCRUD.controller;

import com.funkyganesha.springbootCRUD.model.Car;
import com.funkyganesha.springbootCRUD.repository.CarRepository;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/v1")
@Slf4j
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping()
    @RequestMapping(
            value = "/{year}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<Flux<Car>> getCars(@PathVariable Integer year) {
        ResponseEntity<Flux<Car>> responseEntity;
        try {
            Preconditions.checkArgument(year != null);
            log.info("Finding car(s) manufactured in the year {}", year);
            responseEntity = ResponseEntity.ok(carRepository.findByYear(year));
        } catch (Throwable e) {
            responseEntity = ResponseEntity.badRequest().build();
        }
        return responseEntity;
    }

    @PostMapping
    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<Mono<Car>> save(@RequestBody Car car) {
        if (StringUtils.isEmpty(car.getId())) {
            car = Car
                    .builder()
                    .id(UUID.randomUUID().toString())
                    .year(car.getYear())
                    .companyName(car.getCompanyName())
                    .name(car.getName())
                    .build();
        }
        Mono<Car> mono = carRepository.save(car);
        ResponseEntity<Mono<Car>> responseEntity = ResponseEntity.ok(mono);
        return responseEntity;
    }

    @DeleteMapping(value = "/deleteAllCars")
    public ResponseEntity<Mono<Void>> deleteAll() {
        log.info("Deleting all cars");
        Mono<Void> mono = carRepository.deleteAll();
        ResponseEntity<Mono<Void>> responseEntity = ResponseEntity.ok(mono);
        return responseEntity;
    }
}
