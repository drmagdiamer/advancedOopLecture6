package org.com.javainbabysteps.advancedoop.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Person {
    private String id;
    private String name;
    private float salary;
    private String departmentId;

}
