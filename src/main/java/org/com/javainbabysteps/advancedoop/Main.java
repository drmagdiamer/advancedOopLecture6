package org.com.javainbabysteps.advancedoop;

import org.com.javainbabysteps.advancedoop.model.Department;
import org.com.javainbabysteps.advancedoop.model.Person;
import org.com.javainbabysteps.advancedoop.service.DepartmentRepo;
import org.com.javainbabysteps.advancedoop.service.PersonRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //demonstrating the Lombok features
        Person p1 = Person.builder()
                .name("Dr Magdi")
                .id("A123")
                .build();
        System.out.println(p1.getName());
        p1.setId("A101");

        System.out.println(p1);


        //Mono operations
        //if result is not returned by method or subscribed, the future will not be processed
        Mono<Person> monoPerson1 = getPerson("1");
        Mono<Float> result1 = monoPerson1.map(p -> {
            System.out.println("Salary is "+p.getSalary());
            return p.getSalary();
        });

        //To emulate sending the result to third party, we will block
        //Please note that blocking should never be used except for handling concurrency control issues
        System.out.println(monoPerson1.block());
        System.out.println(result1.block());

        //Example of Flux operations
        Flux<Person> flux1 =  PersonRepo.findByDepartmentId("E01");
        //converting from Flux<Person> to Mono<List<person>>
        //Note that collectList is not blocking
        Mono<List<Person>> monoOfList = flux1.collectList();
        Mono<Boolean> myResult = monoOfList.map(list -> {
            if (list.size() != 1)
                return false;
            else
                return true;
        });

        myResult.subscribe();
        //Blocking method. Should not be used
        Iterable<Person> iterable = flux1.toIterable();
        iterable.forEach(p -> System.out.println(p));


        //when you have to use the result of a first mono to obtain a mono or flux,
        // you will have Mono<Mono<..>> ot Mono<Flux<...>>
        Mono<Department> departmentMono = DepartmentRepo.findByName("IT");
        Mono<Mono<List<Person>>> map = departmentMono.map(d -> {
            String id = d.getId();
            return PersonRepo.findByDepartmentId(id).collectList();
        });


//                .map(Department::getId).zipWhen(id ->
//                PersonRepo.findByDepartmentId(id).collectList())

        //To prevent Mono<Mono..> and Mono<Flux<...> use zipWhen
        Mono<List<Person>> result2 = DepartmentRepo.findByName("IT").map(Department::getId).zipWhen(id ->
                        PersonRepo.findByDepartmentId(id).collectList())
                .map(tuple2 -> {
                    String id = tuple2.getT1();
                    List<Person> list = tuple2.getT2();
                    return list;
                });


        //To wait for the result of multiple Mono variable in the future (not blocking)
        // to take an action based on the result, use zip (can take up to 8 Mono variables
        Mono<List<Person>> finalResultMono = Mono.zip(result1, result2).map(
                tuple2 -> {
                    Float salary = tuple2.getT1();
                    List<Person> list = tuple2.getT2();
                    return list.stream().filter(p -> p.getSalary() > salary).toList();
                }
        );

        //Using block for demonstration only, should not be used in real program
        List<Person> blockedPerson = finalResultMono.block();
        System.out.println(blockedPerson);

        //showing how to use switch if empty
        Mono<Department> departmentMono2 = DepartmentRepo.findByName("AAA").switchIfEmpty(
                Mono.defer( () ->DepartmentRepo.findByName("aaa"))).switchIfEmpty(
                Mono.defer( () ->{
                    System.out.println("Inside the 2nd switch if empty");
                    return Mono.just(Department.builder()
                                    .id("AAA")
                                    .name("AAA")
                            .build());
                })
        );

        //if the result is not send back to a subscriber that consumes it, the future processing will not occure
        //You need subscribe fot hat code to be processed in this case
        departmentMono2.subscribe();

        //Error handling
        Mono<Department> departmentMono3 = DepartmentRepo.findByName("TEST").onErrorReturn(Department.builder()
                .id("TEST")
                .name("Error")
                .build());

        System.out.println(departmentMono3.block());

        Mono<Department> departmentMono4 = DepartmentRepo.findByName("TEST")
                //.doOnError() => Side-effect operator
                //.onErrorReturn() ==> Static value. Cannot call a method
                //.onErrorResume() ==> calls a method to return an alternative Flux.
                .doOnError(e -> System.out.println("\n\n\n******************\n"+e.getMessage()))
//                .onErrorReturn(Department.builder()
//                        .name("My Fixed Value")
//                        .build());
                .onErrorResume(e-> DepartmentRepo.findByName("IT"));


        departmentMono4.map(d -> {System.out.println(d); return d; }).subscribe();



    }

    private static Mono<Person> getPerson(String id) {
        return PersonRepo.findById(id);
    }
}