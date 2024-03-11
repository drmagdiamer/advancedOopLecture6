package org.com.javainbabysteps.advancedoop.service;

import org.com.javainbabysteps.advancedoop.model.Department;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DepartmentRepo {
    public static Mono<Department> findById(String id){
        switch(id){
            case "E01":
                return Mono.just(
                        Department.builder()
                                .name("IT")
                                .id("E01")
                                .build());
            case "M02":
                return Mono.just(
                        Department.builder()
                                .name("Marketing")
                                .id("M02")
                                .build());
            default:
                return Mono.empty();
        }


    }

    public static Mono<Department> findByName(String id){
        switch(id){
            case "IT":
                return Mono.just(
                        Department.builder()
                                .name("IT")
                                .id("E01")
                                .build());
            case "Marketing":
                return Mono.just(
                        Department.builder()
                                .name("Marketing")
                                .id("M02")
                                .build());
            case "TEST":
                return Mono.error(new RuntimeException("Testing Exception Handling"));
            default:
                return Mono.empty();
        }


    }

    public static Flux<Department> findAll() {
        return Flux.just(
                Department.builder()
                        .name("IT")
                        .id("E01")
                        .build(),
                Department.builder()
                        .name("Marketing")
                        .id("M02")
                        .build()
        );
    }
}
