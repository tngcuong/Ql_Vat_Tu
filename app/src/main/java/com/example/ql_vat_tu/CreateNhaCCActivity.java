package com.example.ql_vat_tu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNhaCCActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_nhacc);
        final Button btnInsert = (Button) findViewById(R.id.buttonInsert);
        final EditText txtName = (EditText) findViewById(R.id.editName);
        final EditText txtDiachi = findViewById(R.id.editDiachi);
        final Intent intent = getIntent();
        btnInsert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                if(txtName.getText()+""==""||txtDiachi.getText()+""==""){
                    Toast.makeText(CreateNhaCCActivity.this, "Thiếu dữ liệu", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    bundle.putString("tennhacc", txtName.getText().toString());
                    bundle.putString("diachi", txtDiachi.getText().toString());
                    intent.putExtra("DATA_NHACC", bundle);
                    setResult(MainActivity.SEND_DATA_FROM_NHACC_ACTIVITY, intent);
                    CreateNhaCCActivity.this.finish();
                }
            }
        });
        final Button btnClear = (Button) findViewById(R.id.buttonClear);
        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                txtName.setText("");
                txtDiachi.setText("");
                txtName.requestFocus();
            }
        });
        Bundle bundle = intent.getBundleExtra("DATA");
        if (bundle != null && bundle.getInt("KEY") == 1) {
            String f2 = bundle.getString("getField2");
            String f3 = bundle.getString("getField3");
            txtName.setText(f2);
            txtDiachi.setText(f3);
            btnInsert.setText("Sửa");
            this.setTitle("Sửa Nhà Cung Cấp");
        }
    }
}
