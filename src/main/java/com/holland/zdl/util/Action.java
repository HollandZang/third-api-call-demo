package com.holland.zdl.util;

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
}