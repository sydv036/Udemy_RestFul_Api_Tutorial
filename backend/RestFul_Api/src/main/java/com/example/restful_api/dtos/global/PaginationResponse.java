package com.example.restful_api.dtos.global;


public class PaginationResponse {
    private MetaPagination meta;
    private Object result;

    public PaginationResponse() {
    }

    public PaginationResponse(MetaPagination meta, Object result) {
        this.meta = meta;
        this.result = result;
    }

    public MetaPagination getMeta() {
        return meta;
    }

    public void setMeta(MetaPagination meta) {
        this.meta = meta;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
