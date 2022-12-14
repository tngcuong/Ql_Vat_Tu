package com.example.ql_vat_tu;

public class InforLoai {
    private Object field1;
    private Object field2;


    public Object getField1() {
        return field1;
    }
    public void setField1(Object field1) {
        this.field1 = field1;
    }
    public Object getField2() {
        return field2;
    }
    public void setField2(Object field2) {
        this.field2 = field2;
    }


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.field1 +" - " +this.field2;
    }
}
