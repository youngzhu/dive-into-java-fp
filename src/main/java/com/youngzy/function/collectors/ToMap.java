package com.youngzy.function.collectors;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ToMap {

    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("Alice", 25),
                new Person("Bob", 30),
                new Person("Alice", 28),  // Duplicate key
                new Person("Charlie", 35)
        );

        // ToMap keeping first occurrence when keys are duplicated
        Map<String, Integer> nameToAge = people.stream()
                .collect(Collectors.toMap(
                        Person::getName,  // Key mapper
                        Person::getAge,   // Value mapper
                        // 没有这句会报错
                        // Duplicate key 25（数字完全没用，还有误导）
                        (first, second) -> first  // Keep first value when keys collide
                ));

        System.out.println(nameToAge);
        // Output: {Alice=25, Bob=30, Charlie=35}
    }

}

@Data
@AllArgsConstructor
class Person {
    private String name;
    private int age;
}