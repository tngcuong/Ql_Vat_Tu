package com.example.ql_vat_tu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MySimpleArrayAdapter2 extends ArrayAdapter<InforLoai> {
    private Activity context;
    private int layout;
    private List<InforLoai> list;
    public MySimpleArrayAdapter2(Context context, int textViewResourceId, List<InforLoai> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        this.context=(Activity) context;
        this.layout=textViewResourceId;
        this.list=objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater flater=context.getLayoutInflater();
        View row=flater.inflate(layout, parent,false);
        TextView txt1=(TextView) row.findViewById(R.id.textView1);
        TextView txt2=(TextView) row.findViewById(R.id.textView2);

        InforLoai data=list.get(position);
        txt1.setText(data.getField1()==null?"":data.getField1().toString());
        txt2.setText(data.getField2()==null?"":data.getField2().toString());
        if(position==0)
        {
            row.setBackgroundColor(Color.BLUE);
        }
        return row;
    }
}
