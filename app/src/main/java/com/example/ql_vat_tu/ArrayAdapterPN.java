package com.example.ql_vat_tu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ArrayAdapterPN extends ArrayAdapter<IfLoai> {
    private Activity context;
    private int layout;
    private List<IfLoai> list;

    public ArrayAdapterPN(@NonNull Context context,  int textViewResourceId, @NonNull List<IfLoai> objects) {
        super(context,  textViewResourceId, objects);
        this.context=(Activity) context;
        this.layout=textViewResourceId;
        this.list=objects;
    }
    public View getView(int position, View convertView, ViewGroup
            parent) {
// TODO Auto-generated method stub
        LayoutInflater flater=context.getLayoutInflater();
        View row=flater.inflate(layout, parent,false);
        TextView txt1=(TextView) row.findViewById(R.id.id);
        TextView txt2=(TextView) row.findViewById(R.id.tenLoai);


        IfLoai data=list.get(position);
        txt1.setText(data.getId()==null?"":data.getId().toString());
        txt2.setText(data.getTenLoai()==null?"":data.getTenLoai().toString());

        if(position==0)
        {
            row.setBackgroundColor(Color.BLUE);
        }
        return row;
    }
}
