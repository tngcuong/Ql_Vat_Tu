package com.example.ql_vat_tu;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class QlPhieuNhap extends AppCompatActivity {
    String id="",tenLoai="";
    String soLuong;
    List<IfLoai> listPN=new ArrayList<IfLoai>();
    SQLiteDatabase db=null;
    ArrayAdapterPN adapter=null;
    int index;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlphieunhap);
        FloatingActionButton btnBack= findViewById(R.id.btnBackQl);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                listPN.clear();
                Upload();
                Intent intent = new Intent(QlPhieuNhap.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Upload();
    }

    private void doSuaPN() {
        id = listPN.get(index).getId().toString();
        tenLoai = listPN.get(index).getTenLoai().toString();
        clickSuaPN();
        adapter.notifyDataSetChanged();
    }

    public void clickSuaPN(){
        Intent t = new Intent(this, SuaPN.class);
        Bundle b = new Bundle();
        b.putString("id",id);
        b.putString("tenLoai",tenLoai);
        t.putExtra("loai", b);
        startActivity(t);
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.mnu_sualoai:
                doSuaPN();

                break;
            case R.id.mnu_xoaloai:
                doXoaPN();
        }
        return super.onContextItemSelected(item);
    }

    private void doXoaPN() {
        final IfLoai data=listPN.get(index);
        final int pos=index;
//                    Toast.makeText(ShowListNhaCCActivity.this, "Edit-->"+data.toString(), Toast.LENGTH_LONG).show();
        AlertDialog.Builder b=new AlertDialog.Builder(QlPhieuNhap.this);
        b.setTitle("Xóa Loại");
        b.setMessage("Xóa ["+data.getTenLoai() +"] ?");
        b.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                int n=db.delete("tblLoai", "id=?", new String[]{data.getId().toString()});
                if(n>0)
                {
                    Toast.makeText(QlPhieuNhap.this, "Xóa thành công", Toast.LENGTH_LONG).show();
                    listPN.remove(pos);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(QlPhieuNhap.this, "Xóa thất bại", Toast.LENGTH_LONG).show();
                }
            }
        });
        b.setNegativeButton("Không", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        b.show();
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu_loai, menu);
    }

    public void Upload(){
        db = openOrCreateDatabase("mydata4.db",MODE_PRIVATE,null);
        if(db!=null){
            Cursor cursor=db.rawQuery("Select * from tblLoai",null);
            startManagingCursor(cursor);
            IfLoai headerPN = new IfLoai();
            headerPN.setId("Mã Loại");
            headerPN.setTenLoai("Tên Loại");
            listPN.add(headerPN);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                IfLoai data = new IfLoai();
                data.setId(cursor.getString(0));
                data.setTenLoai(cursor.getString(1));
                listPN.add(data);
                cursor.moveToNext();
            }
            cursor.close();
            adapter = new ArrayAdapterPN(QlPhieuNhap.this, R.layout.headpn,listPN);
            final ListView lview = findViewById(R.id.lvLoai);
            lview.setAdapter(adapter);
            lview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    index=i;
                    return false;
                }
            });
            registerForContextMenu(lview);
        }
    }

    public void Tim(){
        db = openOrCreateDatabase("mydata4.db",MODE_PRIVATE,null);
        if(db!=null){
           Cursor cursor=db.rawQuery("Select tenloai"
                   + "from loai "
                   + "where nhan_vien.chuc_vu=tluong.chuc_vu "
                   , null);
            startManagingCursor(cursor);
            IfLoai headerPN = new IfLoai();
            headerPN.setId("Mã Loại");
            headerPN.setTenLoai("Tên Loại");
            listPN.add(headerPN);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                IfLoai data = new IfLoai();
                data.setId(cursor.getString(0));
                data.setTenLoai(cursor.getString(1));
                listPN.add(data);
                cursor.moveToNext();
            }
            cursor.close();
            adapter = new ArrayAdapterPN(QlPhieuNhap.this, R.layout.headpn,listPN);
            final ListView lview = findViewById(R.id.lvLoai);
            lview.setAdapter(adapter);
            lview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    index=i;
                    return false;
                }
            });
            registerForContextMenu(lview);
        }
    }

    public void Click_Them(View v){
        Intent themVT = new Intent(this, ThemPN.class);
        startActivity(themVT);
    }
}


