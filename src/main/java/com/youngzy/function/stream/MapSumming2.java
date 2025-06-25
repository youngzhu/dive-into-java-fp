package com.youngzy.function.stream;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author youngzy
 * @since 2025-06-25
 */
public class MapSumming2 {

    public static void main(String[] args) {
        List<Account2> accounts = ImmutableList.of(
                new Account2("zhangsan", "abc111", null, "100"),
                new Account2("zhangsan", "abc22", "1000", null),
                new Account2("zhangsan", "abc33", "2000", "20"),
                new Account2("Tom", "yyy", "100", "10")
        );

        Map<String, BigDecimal> sumByName = accounts.stream()
                .collect(Collectors.groupingBy(
                        Account2::getName,
                        Collectors.mapping(
                                account -> {
                                    BigDecimal a1 = account.getAmt1() != null
                                            ? new BigDecimal(account.getAmt1()) : BigDecimal.ZERO;
                                    BigDecimal a2 = account.getAmt2() != null
                                            ? new BigDecimal(account.getAmt2()) : BigDecimal.ZERO;
                                    return a1.add(a2);
                                },
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));

        sumByName.forEach((name, sum) ->
                System.out.println(name + ": " + sum));

    }
}

@Data
@AllArgsConstructor
class Account2 {
    private String name;
    private String accountNo;
    private String amt1;
    private String amt2;
}