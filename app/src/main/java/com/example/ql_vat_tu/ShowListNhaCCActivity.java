package com.example.ql_vat_tu;

import static com.example.ql_vat_tu.MainActivity.OPEN_NHACC_DIALOG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShowListNhaCCActivity extends Activity {
    int index=0;
    Bundle bundle1;
    Intent intent=null;
    ListView lv = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show_data);
        updateUI();
        Button btn=(Button) findViewById(R.id.buttonBack);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowListNhaCCActivity.this.finish();
            }
        });
    }
    List<InforData> list=new ArrayList<InforData>();
    InforData dataClick=null;
    SQLiteDatabase database=null;
    MySimpleArrayAdapter adapter=null;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu_nhacc, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.mnu_suanhacc:
                doSuaNhaCC();
                break;
            case R.id.mnu_xoanhacc:
                doXoaNhaCC();
                break;
        }
        return super.onContextItemSelected(item);
    }
    public void doSuaNhaCC() {
        Toast.makeText(ShowListNhaCCActivity.this,"View -->"+ dataClick.toString(), Toast.LENGTH_SHORT).show();
        intent=new Intent(ShowListNhaCCActivity.this, CreateNhaCCActivity.class);
        bundle1=new Bundle();
        bundle1.putInt("KEY", 1);
        bundle1.putString("getField1", dataClick.getField1().toString());
        bundle1.putString("getField2", dataClick.getField2().toString());
        bundle1.putString("getField3", dataClick.getField3().toString());
        intent.putExtra("DATA", bundle1);

        startActivityForResult(intent, OPEN_NHACC_DIALOG);
    }
    public void doXoaNhaCC(){

        Toast.makeText(ShowListNhaCCActivity.this, "Edit-->"+dataClick.toString(), Toast.LENGTH_LONG).show();
        AlertDialog.Builder b=new AlertDialog.Builder(ShowListNhaCCActivity.this);
        b.setTitle("Xóa Nhà Cung Cấp");
        b.setMessage("Xóa ["+dataClick.getField2() +" - "+dataClick.getField3() +"] ?");
        b.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                int n=database.delete("tblNhaCC", "id=?", new String[]{dataClick.getField1().toString()});
                if(n>0)
                {
                    Toast.makeText(ShowListNhaCCActivity.this, "Xóa thành công", Toast.LENGTH_LONG).show();
                    list.remove(index);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(ShowListNhaCCActivity.this, "Xóa thất bại", Toast.LENGTH_LONG).show();
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

    public void updateUI()
    {
        database=openOrCreateDatabase("mydata4.db", MODE_PRIVATE, null);
        if(database!=null)
        {
            Cursor cursor=database.query("tblNhaCC", null, null, null, null, null, null);
            startManagingCursor(cursor);
            InforData header=new InforData();

            header.setField1("Mã Nhà Cung Cấp");
            header.setField2("Tên Nhà Cung Cấp");
            header.setField3("Địa Chỉ");
            list.add(header);
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                InforData data=new InforData();
                data.setField1(cursor.getInt(0));
                data.setField2(cursor.getString(1));
                data.setField3(cursor.getString(2));
                list.add(data);
                cursor.moveToNext();
            }
            cursor.close();
            adapter=new MySimpleArrayAdapter(ShowListNhaCCActivity.this, R.layout.my_layout_for_show_list_data, list);
            lv= (ListView) findViewById(R.id.listViewShowData);
            lv.setAdapter(adapter);
            //xử lý lưu biến tạm khi nhấn long - click
            //phải dùng cái này để biết được trước đó đã chọn item nào
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //lưu vết lại đối tượng thứ i trong danh sách
                    dataClick=list.get(i);
                    index=i;
                    return false;
                }
            });
            //thực hiện menu context
            registerForContextMenu(lv);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==MainActivity.SEND_DATA_FROM_NHACC_ACTIVITY)
        {
            Bundle bundle=data.getBundleExtra("DATA_NHACC");
            String f2=bundle.getString("tennhacc");
            String f3=bundle.getString("diachi");

                String f1 = dataClick.getField1().toString();
                ContentValues values = new ContentValues();
                values.put("tennhacc", f2);
                values.put("diachi", f3);
                if (database != null) {
                    int n = database.update("tblNhaCC", values, "id=?", new String[]{f1});
                    if (n > 0) {
                        Toast.makeText(ShowListNhaCCActivity.this, "Sửa thành công", Toast.LENGTH_LONG).show();
                        dataClick.setField2(f2);
                        dataClick.setField3(f3);
                        if (adapter != null)
                            adapter.notifyDataSetChanged();
                    }
                }

        }
    }
}
