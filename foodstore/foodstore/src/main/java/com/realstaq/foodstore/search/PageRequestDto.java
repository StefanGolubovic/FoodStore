package com.realstaq.foodstore.search;

public class PageRequestDto {

    private static final int DEFAULT_SIZE = 100;

    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size != 0 ? size : DEFAULT_SIZE;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int size;
}
