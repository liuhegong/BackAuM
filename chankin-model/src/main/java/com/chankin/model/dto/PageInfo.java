package com.chankin.model.dto;

public class PageInfo {
    private int total;
    private Object rows;

    public PageInfo(int total, Object rows) {
        this.total = total;
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }
}
