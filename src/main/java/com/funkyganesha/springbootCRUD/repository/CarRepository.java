package com.funkyganesha.springbootCRUD.repository;

import com.funkyganesha.springbootCRUD.model.Car;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CarRepository extends ReactiveMongoRepository<Car, String> {
    Flux<Car> findByYear(Integer year);
}
