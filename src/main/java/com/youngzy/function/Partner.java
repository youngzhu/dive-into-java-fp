package com.youngzy.function;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
有以下数据：
public class Partner {
    private String name;
    private String partner;
}
List list = List.of(new Partner("Tom", "Jerry"), new Partner("Zhang", "Li"));

用流的方式获得一个map，结果为：
{"Tom": "Jerry", "Jerry": "Tom", "Zhang": "Li", "Li": "Zhang"}
 */

/**
 * 双向映射关系
 */
@AllArgsConstructor
@Data
public class Partner {
    private String name;
    private String partner;

    public static void main(String[] args) {
        List<Partner> list = ImmutableList.of(
                new Partner("Tom", "Jerry"),
                new Partner("Zhang", "Li")
        );

        Map<String, String> map = list.stream()
                .flatMap(p -> Stream.of(
                        new AbstractMap.SimpleEntry<>(p.getName(), p.getPartner()),
                        new AbstractMap.SimpleEntry<>(p.getPartner(), p.getName())
                ))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));


        // Java 9+
//        Map<String, String> map = list.stream()
//                .flatMap(p -> Stream.of(
//                        Map.entry(p.getName(), p.getPartner()),
//                        Map.entry(p.getPartner(), p.getName())
//                ))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


        System.out.println(map); // 输出: {Tom=Jerry, Jerry=Tom, Zhang=Li, Li=Zhang}
    }
}
