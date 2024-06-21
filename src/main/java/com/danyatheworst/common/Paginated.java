package com.danyatheworst.common;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Paginated<T> {
    public final long totalCount;
    public final List<T> result;
}
