package com.holland.demo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 每条数据单独处理，一条数据一个结果集
 *
 * @param <T> 数据类型
 * @param <R> 结果类型
 */
public class SingleRecursiveTask<T, R> extends RecursiveTask<List<BatchRes<R>>> {
    private final Collection<T> source;
    private final Function<T, BatchRes<R>> map;

    private static final int THRESHOLD = 10;

    public SingleRecursiveTask(Collection<T> source, Function<T, BatchRes<R>> map) {
        this.source = source;
        this.map = map;
    }

    @Override
    protected List<BatchRes<R>> compute() {
        if (source.size() > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubtasks())
                    .stream()
                    .map(ForkJoinTask::join)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        } else
            return processing(source);
    }

    private Collection<SingleRecursiveTask<T, R>> createSubtasks() {
        List<SingleRecursiveTask<T, R>> dividedTasks = new ArrayList<>();
        dividedTasks.add(new SingleRecursiveTask<>(source.stream().limit(source.size() / 2).collect(Collectors.toList()), map));
        dividedTasks.add(new SingleRecursiveTask<>(source.stream().skip(source.size() / 2).collect(Collectors.toList()), map));
        return dividedTasks;
    }

    private List<BatchRes<R>> processing(Collection<T> source) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(String.format("%s exec %s val in %s", Thread.currentThread(), source.size(), source.toString()));
        return source.stream()
                .map(map)
                .collect(Collectors.toList());
    }
}