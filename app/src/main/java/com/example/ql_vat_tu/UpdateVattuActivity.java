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

public class UpdateVattuActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_vattu);
        final Button btnInsert = (Button) findViewById(R.id.buttonInsertVT);
        final EditText txtName = (EditText) findViewById(R.id.editTenvt);
        final EditText txtSoluong = findViewById(R.id.editSoluong);
        final Intent intent = getIntent();
        btnInsert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                if(txtName.getText()+""==""||txtSoluong.getText()+""==""){
                    Toast.makeText(UpdateVattuActivity.this, "Thiếu dữ liệu", Toast.LENGTH_LONG).show();
                    txtName.requestFocus();
                    return;
                }
                else {
                    bundle.putString("tenvattu", txtName.getText().toString());
                    bundle.putString("soluong", txtSoluong.getText().toString());
                    intent.putExtra("DATA_VATTU", bundle);
                    setResult(MainActivity.SEND_DATA_FROM_VATTU_ACTIVITY, intent);
                    UpdateVattuActivity.this.finish();
                }
            }
        });
        final Button btn_xoaTrang = findViewById(R.id.buttonClear2);
        btn_xoaTrang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                txtName.setText("");
                txtSoluong.setText("");
                txtName.requestFocus();
            }
        });

        Bundle bundle = intent.getBundleExtra("DATA2");
        if (bundle != null && bundle.getInt("KEY") == 1) {
            String f2 = bundle.getString("getField2");
            String f3 = bundle.getString("getField3");
            txtName.setText(f2);
            txtSoluong.setText(f3);
            btnInsert.setText("Sửa");
            this.setTitle("Sửa Vật tư");
        }
    }


}
