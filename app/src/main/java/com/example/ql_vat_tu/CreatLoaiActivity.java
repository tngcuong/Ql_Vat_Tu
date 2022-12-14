package com.example.ql_vat_tu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreatLoaiActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_loai);
        final Button btnInsert = (Button) findViewById(R.id.buttonInsert);
        final EditText txtName = (EditText) findViewById(R.id.editName);
        final Intent intent = getIntent();
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                if(txtName.getText()+""==""){
                    Toast.makeText(CreatLoaiActivity.this, "Thiếu dữ liệu", Toast.LENGTH_LONG).show();
                    return;
                }
                {
                    bundle.putString("tenloai", txtName.getText().toString());
                    intent.putExtra("DATA_LOAI", bundle);
                    setResult(MainActivity.SEND_DATA_FROM_LOAI_ACTIVITY, intent);
                    CreatLoaiActivity.this.finish();
                }
            }
        });
        final Button btnClear = (Button) findViewById(R.id.buttonClear);
        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                txtName.setText("");
                txtName.requestFocus();
            }
        });
        Bundle bundle = intent.getBundleExtra("DATA2");
        if (bundle != null && bundle.getInt("KEY") == 2) {
            String f2 = bundle.getString("getField2");
            String f3 = bundle.getString("getField3");
            txtName.setText(f2);
            btnInsert.setText("Sửa");
            this.setTitle("Sửa Loại Vật Tư");
        }
    }
}
