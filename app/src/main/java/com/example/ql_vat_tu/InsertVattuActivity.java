package com.example.ql_vat_tu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InsertVattuActivity extends Activity {
    InforData dataClick=null;
    SQLiteDatabase database=null;
    List<InforData> listVattu=null;
    List<InforData>listNhaCC=null;
    List<InforLoai> listLoai=null;
    InforData NhaCCData=null;
    InforLoai LoaiData=null;
    MySimpleArrayAdapter adapter=null;
    MySimpleArrayAdapter2 adapter2=null;
    String manhacc=null;
    String maloai=null;
    int index=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_vattu);

        Spinner spinner=(Spinner) findViewById(R.id.spinner1);
        Spinner spinner2=findViewById(R.id.spinner2);
        listLoai=new ArrayList<InforLoai>();
        listNhaCC=new ArrayList<InforData>();

        InforData d1=new InforData();
        d1.setField1("_");
        d1.setField2("Show All");
        d1.setField3("_");
        listNhaCC.add(d1);

        InforLoai d2=new InforLoai();
        d2.setField1("-");
        d2.setField2("Chọn loại");
        listLoai.add(d2);

        //Lệnh xử lý đưa dữ liệu là Nhà cc vào Spinner
        database=openOrCreateDatabase("mydata4.db", MODE_PRIVATE, null);
        if(database!=null)
        {
            Cursor cursor=database.query("tblNhaCC", null, null, null, null, null, null);
            cursor.moveToFirst();
            while(cursor.isAfterLast()==false)
            {
                InforData d=new InforData();
                d.setField1(cursor.getInt(0));
                d.setField2(cursor.getString(1));
                d.setField3(cursor.getString(2));
                listNhaCC.add(d);
                cursor.moveToNext();
            }
            cursor.close();
        }
        adapter=new MySimpleArrayAdapter(InsertVattuActivity.this, R.layout.my_layout_for_show_list_data,listNhaCC);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //Xử lý sự kiện chọn trong Spinner
        //chọn nhà cung cấp nào thì hiển thị toàn bộ vật tử của nhà cung cấp đó
        //Nếu chọn All thì hiển thị toàn bộ
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                if(arg2==0)
                {
                    //Hiển thị mọi vật tư  trong CSDL
                    NhaCCData=null;
                    EditText tvt=findViewById(R.id.editTenvt);
                    EditText sl=findViewById(R.id.editSoluong);
                    tvt.setText("");sl.setText("");
                    spinner2.setSelection(0);
                    loadAllListVattu();loadUI();

                }
                else
                {
                    //Hiển thị vật tư theo nhà cung cấp chọn trong Spinner (nếu chọn loại vật tư thì chỉ hiện loại vật tư của nhà cc đó)
                    NhaCCData=listNhaCC.get(arg2);
                    EditText tvt=findViewById(R.id.editTenvt);
                    EditText sl=findViewById(R.id.editSoluong);
                    tvt.setText("");sl.setText("");
                    loadListVattuByNhaCC(NhaCCData.getField1().toString(),maloai);loadUI();
                    manhacc=NhaCCData.getField1().toString();


                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                NhaCCData=null;
            }
        });

        //Lệnh xử lý đưa dữ liệu là Loại vật tư vào Spinner
        database=openOrCreateDatabase("mydata4.db", MODE_PRIVATE, null);
        if(database!=null)
        {
            Cursor cursor=database.query("tblLoai", null, null, null, null, null, null);
            cursor.moveToFirst();
            while(cursor.isAfterLast()==false)
            {
                InforLoai d5=new InforLoai();
                d5.setField1(cursor.getInt(0));
                d5.setField2(cursor.getString(1));
                listLoai.add(d5);
                cursor.moveToNext();
            }
            cursor.close();
        }
        adapter2=new MySimpleArrayAdapter2(InsertVattuActivity.this, R.layout.my_layout_for_show_list_data2,listLoai);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        //Xử lý sự kiện chọn trong Spinner
        //chọn loại nào thì hiển thị toàn bộ vật tư của loại đó

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                if(arg2==0)
                {
                    //Hiển thị mọi vật tư  trong CSDL
                    LoaiData=null;
                    EditText tvt=findViewById(R.id.editTenvt);
                    EditText sl=findViewById(R.id.editSoluong);
                    tvt.setText("");sl.setText("");
                    loadAllListVattu();loadUI();
                }
                else
                {
                    //Hiển thị vật tư theo nhà cung cấp chọn trong Spinner
                    LoaiData=listLoai.get(arg2);
                    EditText tvt=findViewById(R.id.editTenvt);
                    EditText sl=findViewById(R.id.editSoluong);
                    tvt.setText("");sl.setText("");
                    loadListVattuByNhaCC(manhacc,LoaiData.getField1().toString());
                    maloai=LoaiData.getField1().toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                LoaiData=null;
            }
        });

        //Lệnh xử lý thêm mới một vật tư theo nhà cung cấp đang chọn
        Button btnInsertVattu =(Button) findViewById(R.id.buttonInsertVT);
        btnInsertVattu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                  EditText txtTenvattu=(EditText) findViewById(R.id.editTenvt);
                  String x=txtTenvattu.getText()+"";
                  EditText txtSoluong = findViewById(R.id.editSoluong);
                if(NhaCCData==null)
                {
                    Toast.makeText(InsertVattuActivity.this, "Hãy chọn nhà cung cấp để thêm", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(LoaiData==null){
                    Toast.makeText(InsertVattuActivity.this, "Hãy chọn loại vật tư để thêm", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(txtTenvattu.getText()+""==""||txtSoluong.getText()+""==""){
                    Toast.makeText(InsertVattuActivity.this, "Thiếu dữ liệu", Toast.LENGTH_LONG).show();
                    return;
                }
                String sl=null;
                String id = null;
                String manhacc1=NhaCCData.getField1().toString();
                {
                    Cursor cur=database.query("tblVattu", null, "tenvattu=? and nhaccid=?", new String[]{x,manhacc1}, null, null, null);
                    cur.moveToFirst();
                    if(cur.moveToFirst()==false){
                        ContentValues values = new ContentValues();
                        values.put("tenvattu", txtTenvattu.getText().toString());
                        values.put("soluong", txtSoluong.getText() + "");
                        values.put("nhaccid", NhaCCData.getField1().toString());
                        values.put("loaiid",LoaiData.getField1().toString());
                        long bId = database.insert("tblVattu", null, values);
                        if (bId > 0) {
                            Toast.makeText(InsertVattuActivity.this, "Thêm vật tư thành công", Toast.LENGTH_LONG).show();
                            loadListVattuByNhaCC(NhaCCData.getField1().toString(),maloai);
                        } else {
                            Toast.makeText(InsertVattuActivity.this, "Thêm vật tư thất bại", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        listVattu = new ArrayList<InforData>();
                        while (cur.isAfterLast() == false) {
                            id = cur.getString(0);
                            sl = cur.getString(2);
                            cur.moveToNext();
                        }
                        cur.close();
                        ContentValues values = new ContentValues();
                        int soluong = Integer.parseInt(txtSoluong.getText() + "") + Integer.parseInt(sl);
                        values.put("tenvattu", txtTenvattu.getText().toString());
                        values.put("soluong", soluong);
                        values.put("nhaccid", NhaCCData.getField1().toString());
                        values.put("loaiid",LoaiData.getField1().toString());
                        long bId = database.update("tblVattu", values, "id=?", new String[]{id});
                        if (bId > 0) {
                            Toast.makeText(InsertVattuActivity.this, "Thêm vật tư thành công", Toast.LENGTH_LONG).show();
                            loadListVattuByNhaCC(NhaCCData.getField1().toString(),maloai);
                        } else {
                            Toast.makeText(InsertVattuActivity.this, "Thêm vật tư thất bại", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu_vattu, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.item1:
                doSuaVattu();
                break;
            case R.id.item2:
                doXoaVattu();
                break;
        }
        return super.onContextItemSelected(item);
    }
    public void doSuaVattu(){
        Toast.makeText(InsertVattuActivity.this,"View -->"+ dataClick.toString(), Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(InsertVattuActivity.this, UpdateVattuActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("KEY", 1);
        bundle.putString("getField1", dataClick.getField1().toString());
        bundle.putString("getField2", dataClick.getField2().toString());
        bundle.putString("getField3", dataClick.getField3().toString());
        intent.putExtra("DATA2", bundle);
        startActivityForResult(intent, MainActivity.OPEN_VATTU_DIALOG);
    }
    public void doXoaVattu(){
        Toast.makeText(InsertVattuActivity.this, "Edit-->"+dataClick.toString(), Toast.LENGTH_LONG).show();
        AlertDialog.Builder b=new AlertDialog.Builder(InsertVattuActivity.this);
        b.setTitle("Xóa Vật tư");
        b.setMessage("Xóa ["+dataClick.getField2() +" - "+dataClick.getField3() +"] ?");
        b.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                int n=database.delete("tblVattu", "id=?", new String[]{dataClick.getField1().toString()});
                if(n>0)
                {
                    Toast.makeText(InsertVattuActivity.this, "Xóa thành công", Toast.LENGTH_LONG).show();
                    listVattu.remove(index);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(InsertVattuActivity.this, "Xóa thất bại", Toast.LENGTH_LONG).show();
                }
            }
        });
        b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });
        b.show();
    }
    public void loadUI(){
        adapter=new MySimpleArrayAdapter(InsertVattuActivity.this, R.layout.my_layout_for_show_list_data, listVattu);
        ListView lv=(ListView) findViewById(R.id.listViewVattu);
        lv.setAdapter(adapter);
        lv.setAdapter(adapter);
        //xử lý lưu biến tạm khi nhấn long - click
        //phải dùng cái này để biết được trước đó đã chọn item nào
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //lưu vết lại đối tượng thứ i trong danh sách
                dataClick=listVattu.get(i);
                index=i;
                return false;
            }
        });
        registerForContextMenu(lv);

    }
    /*
     * Hàm hiển thị mọi vật tư trong CSDL
     */
    public void loadAllListVattu()
    {
        if(NhaCCData==null){

        }
        listVattu=new ArrayList<InforData>();
        InforData header=new InforData();
        header.setField1("Mã Vật tư");
        header.setField2("Tên Vật tư");
        header.setField3("Số lượng");
        listVattu.add(header);

        Cursor cur=database.query("tblVattu", null, null, null, null, null, null);
        cur.moveToFirst();
        while(cur.isAfterLast()==false)
        {
            InforData d=new InforData();
            d.setField1(cur.getInt(0));
            d.setField2(cur.getString(1));
            d.setField3(cur.getString(2));
            listVattu.add(d);
            cur.moveToNext();
        }
        cur.close();
        adapter=new MySimpleArrayAdapter(InsertVattuActivity.this, R.layout.my_layout_for_show_list_data, listVattu);
        ListView lv=(ListView) findViewById(R.id.listViewVattu);
        lv.setAdapter(adapter);
    }
    /**
     * hàm hiển thị vật tư theo nhà cung cấp
     * @param nhaccid
     */
    public void loadListVattuByNhaCC(String nhaccid,String loaiid)
    {

        listVattu=new ArrayList<InforData>();
        InforData header=new InforData();
        header.setField1("Mã Vật tư");
        header.setField2("Tên Vật tư");
        header.setField3("Số lượng");
        listVattu.add(header);
        if(NhaCCData==null){
            Cursor cur=database.query("tblVattu", null, "loaiid=? ", new String[]{loaiid}, null, null, null);
            cur.moveToFirst();
            while(cur.isAfterLast()==false)
            {
                InforData d=new InforData();
                d.setField1(cur.getInt(0));
                d.setField2(cur.getString(1));
                d.setField3(cur.getString(2));
                listVattu.add(d);
                cur.moveToNext();
            }
            cur.close();
            adapter=new MySimpleArrayAdapter(InsertVattuActivity.this, R.layout.my_layout_for_show_list_data, listVattu);
            ListView lv=(ListView) findViewById(R.id.listViewVattu);
            lv.setAdapter(adapter);
        }
        else if(LoaiData==null) {
            Cursor cur = database.query("tblVattu", null, "nhaccid=?", new String[]{nhaccid}, null, null, null);
            cur.moveToFirst();
            while (cur.isAfterLast() == false) {
                InforData d = new InforData();
                d.setField1(cur.getInt(0));
                d.setField2(cur.getString(1));
                d.setField3(cur.getString(2));
                listVattu.add(d);
                cur.moveToNext();
            }
            cur.close();
            adapter = new MySimpleArrayAdapter(InsertVattuActivity.this, R.layout.my_layout_for_show_list_data, listVattu);
            ListView lv = (ListView) findViewById(R.id.listViewVattu);
            lv.setAdapter(adapter);
        }
        else{
            Cursor cur=database.query("tblVattu", null, "nhaccid=? and loaiid=? ", new String[]{nhaccid,loaiid}, null, null, null);
            cur.moveToFirst();
            while(cur.isAfterLast()==false)
            {
                InforData d=new InforData();
                d.setField1(cur.getInt(0));
                d.setField2(cur.getString(1));
                d.setField3(cur.getString(2));
                listVattu.add(d);
                cur.moveToNext();
            }
            cur.close();
            adapter=new MySimpleArrayAdapter(InsertVattuActivity.this, R.layout.my_layout_for_show_list_data, listVattu);
            ListView lv=(ListView) findViewById(R.id.listViewVattu);
            lv.setAdapter(adapter);
        }
    }
    // lấy dữ liệu từ UpdateVattuActivity để sửa lại danh sách vật tư
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==MainActivity.SEND_DATA_FROM_VATTU_ACTIVITY)
        {
            Bundle bundle=data.getBundleExtra("DATA_VATTU");
            String f2=bundle.getString("tenvattu");
            String f3=bundle.getString("soluong");
            if(f2==""||f3==""){
                Toast.makeText(InsertVattuActivity.this, "Thiếu dữ liệu", Toast.LENGTH_LONG).show();
                return;
            }
                String f1 = dataClick.getField1().toString();
                ContentValues values = new ContentValues();
                values.put("tenvattu", f2);
                values.put("soluong", f3);
                if (database != null && bundle!=null) {
                    int n = database.update("tblVattu", values, "id=?", new String[]{f1});
                    if (n > 0) {
                        Toast.makeText(InsertVattuActivity.this, "Sửa thành công", Toast.LENGTH_LONG).show();
                        dataClick.setField2(f2);
                        dataClick.setField3(f3);
                        if (adapter != null)
                            adapter.notifyDataSetChanged();
                    }
                }
        }
    }

}
