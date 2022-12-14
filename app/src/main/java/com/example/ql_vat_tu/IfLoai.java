package com.example.ql_vat_tu;

public class IfLoai {
    private Object id;
    private Object tenLoai;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(Object tenLoai) {
        this.tenLoai = tenLoai;
    }

    @Override
    public String toString() {
        return "IfLoai{" +
                "id=" + id +
                ", tenLoai=" + tenLoai +
                '}';
    }
}
