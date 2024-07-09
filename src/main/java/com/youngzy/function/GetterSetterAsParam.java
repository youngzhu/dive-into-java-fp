package com.youngzy.function;

import com.youngzy.function.datetime.DateTimes;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author youngzy
 * @since 2024-07-09
 */
public class GetterSetterAsParam {

    public static <T, R> void getterAsParam(T t, Function<T, R> getter) {
        String format = "get value: %s";
        System.out.println(String.format(format, getter.apply(t)));
    }

    public static <T, V> void setterAsParam(T t, V value, BiConsumer<T, V> setter) {
        setter.accept(t, value);
    }

    public static void main(String[] args) {
        Person person = new Person("ZhangSan", DateTimes.newDate(2000, 1, 1));

        // Output:
        // get value: ZhangSan
        // get value: Sat Jan 01 00:00:00 CST 2000
        getterAsParam(person, Person::getName);
        getterAsParam(person, Person::getBirthday);

        setterAsParam(person, "LiSi", Person::setName);
        setterAsParam(person, DateTimes.newDate(2024, 1, 1), Person::setBirthday);
        // Output:
        // LiSi, Mon Jan 01 00:00:00 CST 2024
        System.out.println(person.getName() + ", " + person.getBirthday());
    }

    @Data
    @AllArgsConstructor
    static class Person {
        private String name;
        private Date birthday;
    }
}
