package com.bmp.search.core.filter;

import com.bmp.search.core.Item;

import java.util.List;

/**
 * It is used to filter batch items.
 */
public interface BatchFilter {
    List<Item> filter(List<Item> items);
}
