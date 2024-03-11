package org.com.javainbabysteps.advancedoop.service;

import org.com.javainbabysteps.advancedoop.model.Department;
import org.com.javainbabysteps.advancedoop.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepo {
    public static Mono<Person> findById(String id){
        return Mono.just(
                Person.builder()
                        .name("Person "+id)
                        .id(id)
                        .salary(3500)
                        .departmentId("E01")
                        .build()
        );
    }

    public static Flux<Person> findAll(){
        return Flux.just(
                Person.builder()
                        .name("Person 01")
                        .id("01")
                        .salary(5000)
                        .departmentId("E01")
                        .build(),
                Person.builder()
                        .name("Person 02")
                        .id("02")
                        .salary(4000)
                        .departmentId("E01")
                        .build(),
                Person.builder()
                        .name("Person 03")
                        .id("03")
                        .salary(3000)
                        .departmentId("E01")
                        .build(),
                Person.builder()
                        .name("Person 04")
                        .id("04")
                        .salary(2500)
                        .departmentId("E01")
                        .build(),
                Person.builder()
                        .name("Person 05")
                        .id("05")
                        .salary(2500)
                        .departmentId("E01")
                        .build(),
                Person.builder()
                        .name("Person 06")
                        .id("06")
                        .salary(7000)
                        .departmentId("M02")
                        .build(),
                Person.builder()
                        .name("Person 07")
                        .id("07")
                        .salary(8000)
                        .departmentId("M02")
                        .build(),
                Person.builder()
                        .name("Person 08")
                        .id("08")
                        .salary(2000)
                        .departmentId("M02")
                        .build(),
                Person.builder()
                        .name("Person 09")
                        .id("09")
                        .salary(1000)
                        .departmentId("M02")
                        .build(),
                Person.builder()
                        .name("Person 10")
                        .id("10")
                        .salary(1000)
                        .departmentId("M02")
                        .build()

        );
    }

    public static Flux<Person> findByDepartmentId(String departmentId){
        switch(departmentId){
            case "E01":
                return Flux.just(
                        Person.builder()
                                .name("Person 01")
                                .id("01")
                                .salary(5000)
                                .departmentId("E01")
                                .build(),
                        Person.builder()
                                .name("Person 02")
                                .id("02")
                                .salary(4000)
                                .departmentId("E01")
                                .build(),
                        Person.builder()
                                .name("Person 03")
                                .id("03")
                                .salary(3000)
                                .departmentId("E01")
                                .build(),
                        Person.builder()
                                .name("Person 04")
                                .id("04")
                                .salary(2500)
                                .departmentId("E01")
                                .build(),
                        Person.builder()
                                .name("Person 05")
                                .id("05")
                                .salary(2500)
                                .departmentId("E01")
                                .build()
                );
            case "M02":
                return Flux.just(
                        Person.builder()
                                .name("Person 06")
                                .id("06")
                                .salary(7000)
                                .departmentId("M02")
                                .build(),
                        Person.builder()
                                .name("Person 07")
                                .id("07")
                                .salary(8000)
                                .departmentId("M02")
                                .build(),
                        Person.builder()
                                .name("Person 08")
                                .id("08")
                                .salary(2000)
                                .departmentId("M02")
                                .build(),
                        Person.builder()
                                .name("Person 09")
                                .id("09")
                                .salary(1000)
                                .departmentId("M02")
                                .build(),
                        Person.builder()
                                .name("Person 10")
                                .id("10")
                                .salary(1000)
                                .departmentId("M02")
                                .build()

                );
            default:
                return Flux.empty();
        }

    }


}
