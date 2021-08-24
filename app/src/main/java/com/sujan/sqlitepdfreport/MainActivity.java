package com.sujan.sqlitepdfreport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText name,number,qty1,qty2;
    Spinner spinner1,spinner2;
    Button saveprint,oldprint;

    //array for spinner
    String[] itemlist;
    int[] itemprice;

    //array adapder
    ArrayAdapter<String> adapter;

    //db object
    MyHelper myHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        objectAssignment();
        callOnClickListner();
    }

    private void callOnClickListner() {
        saveprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cusname = name.getText().toString();
                String pnumber = number.getText().toString();
                String item_1 = spinner1.getSelectedItem().toString();
                int itemQty1 = Integer.parseInt(String.valueOf(qty1.getText()));
                int itemamount1 = itemQty1 * itemprice[spinner1.getSelectedItemPosition()];
                String item_2 = spinner2.getSelectedItem().toString();
                int itemQty2 = Integer.parseInt(String.valueOf(qty2.getText()));
                int itemamount2 = itemQty2 * itemprice[spinner2.getSelectedItemPosition()];

                Date date = new Date();

                //to insert data to the table
                myHelper.insert(cusname,pnumber,date.getTime(),item_1,itemQty1,itemamount1,item_2,itemQty2,itemamount2);
                Toast.makeText(getApplicationContext(),"Details saved successfully!",Toast.LENGTH_LONG).show();

                //fetch the trip number from db
                Cursor cursor = sqLiteDatabase.rawQuery("select * from myTable",null);
                cursor.move(cursor.getCount());

                try {
                    new PrintPDF(cursor.getInt(0),cusname,pnumber,date.getTime(),item_1,itemQty1,itemamount1,item_2,itemQty2,itemamount2).getPDF();
                    Toast.makeText(MainActivity.this,"PDF created",Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void objectAssignment() {
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        qty1 = findViewById(R.id.qty1);
        qty2 = findViewById(R.id.qty2);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        saveprint = findViewById(R.id.saveprint_btn);
        oldprint = findViewById(R.id.printold_btn);

        //to assign item list
        itemlist = new String[]{"Bus","Car","Van","Three_weeler","Bike"};
        itemprice = new int[]{1000,2500,450,3000,500};

        //to add items into spinner we need to use array  adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,itemlist);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        myHelper = new MyHelper(MainActivity.this);
        sqLiteDatabase = myHelper.getWritableDatabase();


    }
}