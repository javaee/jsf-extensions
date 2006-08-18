package com.enverio.util;

public interface Transformer<T,R> {
    public R transform(T item);
}
