package com.holland.zdl.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 数据分批处理，一批数据一个结果集
 *
 * @param <T> 数据类型
 * @param <R> 结果类型
 */
public class BatchRecursiveTask<T, R> extends RecursiveTask<List<Res<R>>> {
    private final Collection<T> source;
    private final Function<Collection<T>, Res<R>> map;

    private static final int THRESHOLD = 10;

    public BatchRecursiveTask(Collection<T> source, Function<Collection<T>, Res<R>> map) {
        this.source = source;
        this.map = map;
    }

    @Override
    protected List<Res<R>> compute() {
        if (source.size() > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubtasks())
                    .stream()
                    .map(ForkJoinTask::join)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        } else {
            final Res<R> processing = processing(source);
            final List<Res<R>> res = new ArrayList<>();
            res.add(processing);
            return res;
        }
    }

    private Collection<BatchRecursiveTask<T, R>> createSubtasks() {
        List<BatchRecursiveTask<T, R>> dividedTasks = new ArrayList<>();
        dividedTasks.add(new BatchRecursiveTask<>(source.stream().limit(source.size() / 2).collect(Collectors.toList()), map));
        dividedTasks.add(new BatchRecursiveTask<>(source.stream().skip(source.size() / 2).collect(Collectors.toList()), map));
        return dividedTasks;
    }

    private Res<R> processing(Collection<T> source) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(String.format("%s exec %s val in %s", Thread.currentThread(), source.size(), source.toString()));
        return map.apply(source);
    }
}