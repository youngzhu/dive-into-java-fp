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
public class MapSumming {

    public static void main(String[] args) {
        List<Account> accounts = ImmutableList.of(
                new Account("zhangsan", "abc111", null),
                new Account("zhangsan", "abc22", new BigDecimal("1000")),
                new Account("zhangsan", "abc33", new BigDecimal("2000")),
                new Account("Tom", "yyy", new BigDecimal("100"))
        );

        Map<String, BigDecimal> sumByName = accounts.stream()
                .filter(account -> account.getAmt() != null)  // filter out null amounts
                .collect(Collectors.groupingBy(
                        Account::getName,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Account::getAmt,
                                BigDecimal::add
                        )
                ));

        sumByName.forEach((name, sum) ->
                System.out.println(name + ": " + sum));

    }
}

@Data
@AllArgsConstructor
class Account {
    private String name;
    private String accountNo;
    private BigDecimal amt;
}
