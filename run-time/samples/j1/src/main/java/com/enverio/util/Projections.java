package com.enverio.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Projections {

    public static <T> Collection<T> in(Collection<T> c, Predicate<? super T> e) {
        List<T> r = new ArrayList<T>(c.size());
        for (T t : c) {
            if (e.evaluate(t)) {
                r.add(t);
            }
        }
        return r;
    }

    public static <T, R> Collection<R> transmute(Collection<T> c,
            Predicate<? super T> e, Transformer<? super T, R> m) {
        List<R> r = new ArrayList<R>(c.size());
        for (T t : c) {
            if (e.evaluate(t)) {
                r.add(m.transform(t));
            }
        }
        return r;
    }

}
