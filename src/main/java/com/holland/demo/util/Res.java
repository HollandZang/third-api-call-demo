package com.holland.demo.util;

public class Res<T> {
    boolean success;
    int failedCount;
    T data;

    public Res(boolean success, int failedCount, T data) {
        this.success = success;
        this.failedCount = failedCount;
        this.data = data;
    }

    public static <T> Res<T> success(T data) {
        return new Res<>(true, 0, data);

    }

    public static <T> Res<T> failed(T data) {
        return new Res<>(false, 1, data);
    }
}