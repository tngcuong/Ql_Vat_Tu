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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SuaPN extends AppCompatActivity {
    EditText  txtTenLoai;
    String txtId;
    Button Sua,XoaTrang;
    FloatingActionButton TroLai;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suapn);
        txtTenLoai = findViewById(R.id.eLoai);
        Sua = findViewById(R.id.btnSua);
        XoaTrang = findViewById(R.id.btnXoaTrangSua);
        TroLai=findViewById(R.id.btnTroLaiSua);

        Intent intent=getIntent();
        Bundle b = intent.getBundleExtra("loai");
        txtTenLoai.setText(b.getString("tenLoai"));
        txtId=(b.getString("id"));
        XoaTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTenLoai.setText("");

            }
        });
        Sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtTenLoai.getText()+""==""){
                    Toast.makeText(SuaPN.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }else {
                    SQLiteDatabase db = openOrCreateDatabase("mydata4.db", MODE_PRIVATE, null);
                    ContentValues value = new ContentValues();
                    value.put("tenLoai", txtTenLoai.getText() + "");
                    value.put("id", txtId + "");
                    db.update("tblLoai", value, "id=?", new String[]{txtId});
                    Intent intent = new Intent(SuaPN.this, QlPhieuNhap.class);
                    startActivity(intent);
                }
            }
        });
    }
}
