package com.holland.demo.util;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Action {

    /**
     * @param action              执行动作
     * @param predicate           判断执行动作是否成功，成功就不会重试了。true：成功
     * @param interval            重试间隔，单位：毫秒，小于等于0则立即重试
     * @param retryTime           重试次数
     * @param retryAction         重试前执行的处理动作
     * @param whenTryActionDefeat 判断处理动作是否执行成功，执行成功才会重试，可空（必定重试）。true：成功
     * @param <T>                 执行动作的结果
     * @param <R>                 处理动作的结果
     * @return 返回最后一次执行动作的结果
     */
    public static <T, R> T retry(
            final Supplier<T> action
            , final Predicate<T> predicate
            , final long interval
            , final int retryTime
            , final Supplier<R> retryAction
            , final Predicate<R> whenTryActionDefeat
    ) {
        T t = action.get();
        if (retryTime > 0 && !predicate.test(t)) {
            final R r = retryAction.get();
            if (whenTryActionDefeat == null || whenTryActionDefeat.test(r)) {
                if (interval > 0) {
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                t = retry(action, predicate, interval, retryTime - 1, retryAction, whenTryActionDefeat);
            }
        }
        return t;
    }

    public static <T, R> BatchRes<R> singleMapReduce(
            Collection<T> source
            , Function<T, BatchRes<R>> map
            , BinaryOperator<BatchRes<R>> reduce
    ) {
        final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        final List<BatchRes<R>> r = forkJoinPool.invoke(new SingleRecursiveTask<>(source, map));

        return r.stream()
                .reduce(reduce)
                .orElse(new BatchRes<>(false, 0, null));
    }

    public static <T, R> BatchRes<R> batchMapReduce(
            Collection<T> source
            , Function<Collection<T>, BatchRes<R>> map
            , BinaryOperator<BatchRes<R>> reduce
    ) {
        final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        final List<BatchRes<R>> r = forkJoinPool.invoke(new BatchRecursiveTask<>(source, map));

        return r.stream()
                .reduce(reduce)
                .orElse(new BatchRes<>(false, 0, null));
    }
}