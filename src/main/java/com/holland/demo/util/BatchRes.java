package com.holland.demo.util;

public class BatchRes<T> {
    boolean success;
    int failedCount;
    T data;

    public BatchRes(boolean success, int failedCount, T data) {
        this.success = success;
        this.failedCount = failedCount;
        this.data = data;
    }

    public static <T> BatchRes<T> success(T data) {
        return new BatchRes<>(true, 0, data);

    }

    public static <T> BatchRes<T> failed(T data) {
        return new BatchRes<>(false, 1, data);
    }
}