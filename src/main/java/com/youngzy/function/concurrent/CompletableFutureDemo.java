package com.youngzy.function.concurrent;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * CompletableFuture 示例
 *
 * 完成这样的功能：
 * 1. 创建10个异步任务，入参为1-10的数字，返回入参的1倍、10倍、100倍列表
 * 2. 异步执行所有任务
 * 3. 最终打印所有任务的执行结果
 *
 * @author youngzy
 * @since 2025-04-24
 */
public class CompletableFutureDemo {

    /**
     * 异步任务 - 计算数字的1倍、10倍和100倍
     * @param number 输入数字
     * @return 包含1倍、10倍和100倍的列表
     */
    private static List<Integer> calculateMultiples(int number) {
        // 模拟耗时操作
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("计算数字 " + number + " 的倍数...");

        return ImmutableList.of(
                number,
                number * 10,
                number * 100
        );
    }

    public static void main(String[] args) {
        // 任务创建：使用IntStream.rangeClosed(1, 10)创建1-10的数字流，为每个数字创建一个异步任务
        //
        //异步执行：使用CompletableFuture.supplyAsync()异步执行计算任务
        //
        //合并结果：使用CompletableFuture.allOf()等待所有任务完成
        //
        //结果收集：使用thenApply()在所有任务完成后收集结果
        //
        //结果打印：格式化打印每个任务的输入和计算结果

        // 1. 创建10个异步任务(1-10)
        List<CompletableFuture<List<Integer>>> futures = IntStream.rangeClosed(1, 10)
                .mapToObj(number ->
                        CompletableFuture.supplyAsync(() -> calculateMultiples(number))
                )
                .collect(Collectors.toList());

        // 2. 合并所有Future为一个，等待所有任务完成
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );

        // 3. 获取所有任务的结果
        CompletableFuture<List<List<Integer>>> combinedFuture = allFutures.thenApply(v ->
                futures.stream()
                        .map(CompletableFuture::join)  // 这里使用join而不是get，因为allOf已经确保完成
                        .collect(Collectors.toList())
        );

        // 4. 打印结果
        try {
            List<List<Integer>> allResults = combinedFuture.get();
            System.out.println("所有任务执行结果:");

            for (int i = 0; i < allResults.size(); i++) {
                List<Integer> result = allResults.get(i);
                System.out.printf("任务%d (输入=%d): 1倍=%d, 10倍=%d, 100倍=%d%n",
                        i + 1, i + 1, result.get(0), result.get(1), result.get(2));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
