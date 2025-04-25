package com.example.restful_api.dtos.global;

public class MetaPagination {
    private long total;
    private int currentPage;
    private int pageSize;
    private int pages;

    public MetaPagination() {
    }

    public MetaPagination(long total, int currentPage, int pageSize, int pages) {
        this.total = total;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pages = pages;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
