package com.example.ql_vat_tu;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends Activity {

    Button btnQLLoai=null;
    Button btnInsertNhaCC=null;
    Button btnShowNhaCCList=null;
    Button btnInsertVattu=null;
    Button btnInsertLoai=null;
    Button btnShowLoaiList=null;
    public static final int OPEN_NHACC_DIALOG=1;
    public static final int SEND_DATA_FROM_NHACC_ACTIVITY=2;
    public static final int SEND_DATA_FROM_VATTU_ACTIVITY=3;
    public static final int OPEN_VATTU_DIALOG=4;
    public static final int SEND_DATA_FROM_LOAI_ACTIVITY=5;
    public static final int OPEN_LOAI_DIALOG=6;
    SQLiteDatabase database=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        btnInsertNhaCC=(Button) findViewById(R.id.btnInsertNhaCC);
        btnInsertNhaCC.setOnClickListener((View.OnClickListener) new MyEvent());
        btnShowNhaCCList=(Button) findViewById(R.id.buttonShowNhaCCList);
        btnShowNhaCCList.setOnClickListener((View.OnClickListener) new MyEvent());
//        btnInsertLoai=findViewById(R.id.buttonInsertLoai);
//        btnInsertLoai.setOnClickListener((View.OnClickListener) new MyEvent());
//        btnShowLoaiList=findViewById(R.id.buttonShowLoaiList);
//        btnShowLoaiList.setOnClickListener((View.OnClickListener)new MyEvent());
        btnInsertVattu=(Button) findViewById(R.id.buttonInsertVattu);
        btnInsertVattu.setOnClickListener((View.OnClickListener) new MyEvent());
        btnQLLoai=(Button) findViewById(R.id.btnQLLoai);
        btnQLLoai.setOnClickListener((View.OnClickListener) new MyEvent());
        getDatabase();
    }
    /**
     * hàm kiểm tra xem bảng có tồn tại trong CSDL hay chưa
     * @param database - cơ sở dữ liệu
     * @param tableName - tên bảng cần kiểm tra
     * @return trả về true nếu tồn tại
     */
    public boolean isTableExists(SQLiteDatabase database, String tableName) {
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
    /**
     * hàm tạo CSDL và các bảng liên quan
     * @return
     */
    public SQLiteDatabase getDatabase()
    {
        try
        {
            database=openOrCreateDatabase("mydata4.db", MODE_PRIVATE, null);
            if(database!=null)
            {
                if(isTableExists(database,"tblNhaCC"))
                    return database;
                database.setLocale(Locale.getDefault());
                database.setVersion(1);
                String sqlNhaCC="create table tblNhaCC ("
                        +"id integer primary key autoincrement,"
                        +"tennhacc text, "
                        +"diachi text)";
                database.execSQL(sqlNhaCC);
                String sqlLoai="create table tblLoai(" +
                        "id integer primary key autoincrement,"
                        +"tenloai text)";
                database.execSQL(sqlLoai);
                String sqlVattu="create table tblVattu ("
                        +"id integer primary key autoincrement,"
                        +"tenvattu text, "
                        +"soluong int,"
                        +"nhaccid integer not null constraint nhaccid references tblNhaCC(id) on delete cascade,"
                        +"loaiid integer not null constraint loaiid references tblLoai(id) on delete cascade)";
                database.execSQL(sqlVattu);
                //Cách tạo trigger khi nhập dữ liệu sai ràng buộc quan hệ
                String sqlTrigger="create trigger fk_insert_vattu before insert on tblVattu "
                        +" for each row "
                        +" begin "
                        +" 	select raise(rollback,'Thêm dữ liệu trên bảng tblVattu bị sai') "
                        +" 	where (select id from tblNhaCC where id=new.nhaccid) is null ;"
                        +" end;";
                database.execSQL(sqlTrigger);
                Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        return database;
    }
    public void createDatabaseAndTrigger()
    {
        if(database==null)
        {
            getDatabase();
            Toast.makeText(MainActivity.this, "Kết nối db thành công", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * hàm mở màn hình nhập Nhà cung cấp
     */
    public void showInsertNhaCCDialog()
    {
        Intent intent=new Intent(MainActivity.this, CreateNhaCCActivity.class);
        startActivityForResult(intent, OPEN_NHACC_DIALOG);
    }

    /**
     * hàm xem danh sách nhà cc dùng ListActivity
     */
    public void showNhaCCList2()
    {
        Intent intent=new Intent(MainActivity.this, ShowListNhaCCActivity.class);
        startActivity(intent);
    }
    public void showLoaiList(){
        Intent intent = new Intent(MainActivity.this,ShowListLoaiActivity.class);
        startActivity(intent);
    }
    public void showInsertLoaiDialog(){
        Intent intent=new Intent(MainActivity.this,CreatLoaiActivity.class);
        startActivityForResult(intent,OPEN_LOAI_DIALOG);
    }
    /**
     * hàm xử lý kết quả trả về
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==SEND_DATA_FROM_NHACC_ACTIVITY)
        {
            Bundle bundle= data.getBundleExtra("DATA_NHACC");

            String tennhacc=bundle.getString("tennhacc");
            String diachi=bundle.getString("diachi");

                ContentValues content = new ContentValues();
                content.put("tennhacc", tennhacc);
                content.put("diachi", diachi);
                if (database != null) {
                    long nhaccid = database.insert("tblNhaCC", null, content);
                    if (nhaccid == -1) {
                        Toast.makeText(MainActivity.this, nhaccid + " - " + tennhacc + " - " + diachi + " ==> Thêm thất bại", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, nhaccid + " - " + tennhacc + " - " + diachi + " ==> Thêm thành công!", Toast.LENGTH_LONG).show();
                    }
//

            }

        }
        if(resultCode==SEND_DATA_FROM_LOAI_ACTIVITY){
            Bundle bunble =data.getBundleExtra("DATA_LOAI");
            String tenloai=bunble.getString("tenloai");
            if(tenloai==""){
                Toast.makeText(MainActivity.this, "Thiếu dữ liệu", Toast.LENGTH_LONG).show();
                return;
            }
            ContentValues cv = new ContentValues();
            cv.put("tenloai",tenloai);
            if(database!=null){
                long loaiid=database.insert("tblLoai",null,cv);
                if(loaiid==-1){
                    Toast.makeText(MainActivity.this, loaiid + " - " + tenloai + " ==> Thêm thất bại", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this, loaiid + " - " + tenloai + " ==> Thêm thành công", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    /**
     * class xử lý sự kiện
     * @author drthanh
     *
     */
    private class MyEvent implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if(v.getId()==R.id.btnInsertNhaCC)
            {
                showInsertNhaCCDialog();
            }
            else if(v.getId()==R.id.buttonShowNhaCCList)
            {
                showNhaCCList2();
            }


            else if(v.getId()==R.id.buttonInsertVattu)
            {
                Intent intent=new Intent(MainActivity.this, InsertVattuActivity.class);
                startActivityForResult(intent,OPEN_VATTU_DIALOG);
            }
            else if(v.getId()==R.id.btnQLLoai){
                Intent intent=new Intent(MainActivity.this, QlPhieuNhap.class);
                startActivity(intent);
            }
        }

    }

}