package com.holland.zdl.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Action {

    public static <T, R> T retry(
            final Supplier<T> action
            , final Predicate<T> predicate
            , final int retryTime
            , final Supplier<R> retryAction
            , final Predicate<R> whenTryActionDefeat
    ) {
        T t = action.get();
        if (retryTime > 0 && !predicate.test(t)) {
            final R r = retryAction.get();
            if (whenTryActionDefeat == null || whenTryActionDefeat.test(r))
                t = retry(action, predicate, retryTime - 1, retryAction, whenTryActionDefeat);
        }
        return t;
    }

    public static <T, R> Res<R> singleMapReduce(
            Collection<T> source
            , Function<T, Res<R>> map
            , BinaryOperator<Res<R>> reduce
    ) {
        final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        final List<Res<R>> r = forkJoinPool.invoke(new SingleRecursiveTask<>(source, map));

        return r.stream()
                .reduce(reduce)
                .orElse(new Res<>(false, 0, null));
    }

    public static <T, R> Res<R> batchMapReduce(
            Collection<T> source
            , Function<Collection<T>, Res<R>> map
            , BinaryOperator<Res<R>> reduce
    ) {
        final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        final List<Res<R>> r = forkJoinPool.invoke(new BatchRecursiveTask<>(source, map));

        return r.stream()
                .reduce(reduce)
                .orElse(new Res<>(false, 0, null));
    }

    public static void main(String[] args) {
        final long l = System.currentTimeMillis();
        final List<Integer> source = new ArrayList<>();
        for (int i = 0; i < 1000; i++)
            source.add(i);

        final Res<List<String>> singleMapReduce = singleMapReduce(source
                , integer -> {
                    if (integer % 2 == 0) {
                        final List<String> data = new ArrayList<>();
                        data.add(integer + "a");
                        return Res.success(data);
                    } else
                        return Res.failed(new ArrayList<>());
                }
                , (res, res2) -> {
                    res.success &= res2.success;
                    res.failedCount += res2.failedCount;
                    res.data.addAll(res2.data);
                    return res;
                });

        final long l1 = System.currentTimeMillis();
        System.out.println(l1 - l);

        final Res<List<String>> batchMapReduce = batchMapReduce(source
                , collection -> {
                    if (collection.size() % 2 == 0) {
                        final List<String> data = new ArrayList<>();
                        data.add("这批数据完成了这么多个：" + collection.size());
                        return Res.success(data);
                    } else {
                        return Res.failed(new ArrayList<>());
                    }
                }
                , (res, res2) -> {
                    res.success &= res2.success;
                    res.failedCount += res2.failedCount;
                    res.data.addAll(res2.data);
                    return res;
                });

        System.out.println(System.currentTimeMillis() - l1);
    }
}