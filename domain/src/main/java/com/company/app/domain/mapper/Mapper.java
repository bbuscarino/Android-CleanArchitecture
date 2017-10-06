package com.company.app.domain.mapper;

import java.util.List;

public interface Mapper<T, U> {
    U mapTo(T t);

    List<U> mapTo(List<T> t);

    T mapFrom(U u);

    List<T> mapFrom(List<U> u);
}
