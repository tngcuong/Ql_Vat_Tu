package com.example.ql_vat_tu;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ThemPN extends Activity {
    EditText tenLoai;
    Button troVe,themLoai;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thempn);
        tenLoai =(EditText) findViewById(R.id.eLoai);
        themLoai =(Button) findViewById(R.id.btnThem);
        final Intent intent = getIntent();
    }

    public void vtPN(View v){
        tenLoai.setText("");
    }

    public void TroVe(){
        Intent troVe = new Intent(this,QlPhieuNhap.class);
        Toast.makeText(this, "Trở lại ", Toast.LENGTH_SHORT).show();
        startActivity(troVe);
    }
    public void themPN(View v){
        if (tenLoai.getText()+""=="") {
            Toast.makeText(ThemPN.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }else {
            SQLiteDatabase db = openOrCreateDatabase("mydata4.db", MODE_PRIVATE, null);
            ContentValues temp = new ContentValues();
            temp.put("tenloai", tenLoai.getText() + "");
            String thongBao = "";
            if (db.insert("tblLoai", null, temp) == -1) {
                thongBao = "Thêm dữ liệu không thành công";
                TroVe();
            } else {
                thongBao = "Thêm dữ liệu  thành công";
                TroVe();
            }
            Toast.makeText(this, thongBao, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
