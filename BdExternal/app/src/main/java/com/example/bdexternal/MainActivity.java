package com.example.bdexternal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity {
    public static Activity activity;
    DataBaseManager BD;
    TextView txtInfo;
    Button btnImport;
    Button btnExport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        btnImport = (Button) findViewById(R.id.btnImport);
        btnExport = (Button) findViewById(R.id.btnexport);
        activity = this;
        checkPermissionMethod();

        String state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)){
            File nuevaCarpeta = new File(Environment.getExternalStorageDirectory().toString()+"/Bases de datos integra/export/");
            if(!nuevaCarpeta.exists()){
                nuevaCarpeta.mkdirs();
            }
        }

        BD = new DataBaseManager(MainActivity.this);

        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermissionMethod()){
                    File dropbd = new File("/data/data/com.example.bdexternal/databases/example.db");
                    if(dropbd.exists()){
                        dropbd.delete();
                    }
                    File dropbdjournal = new File("/data/data/com.example.bdexternal/databases/example.db-journal");
                    if(dropbdjournal.exists()){
                        dropbdjournal.delete();
                    }

                    File dbfolder = new File("/data/data/com.example.bdexternal/databases/");
                    if(!dbfolder.exists()){
                        dbfolder.mkdir();
                    }
                    File dbfile = new File(Environment.getExternalStorageDirectory().toString() + "/Bases de datos integra/example.db/");
                    if(dbfile.exists()){
                        try {
                            if(BD.importDatabase( Environment.getExternalStorageDirectory().toString() + "/Bases de datos integra/example.db/")){
                                System.out.println("**********************exito al copiar la bd*********************");

                                System.out.println("**********************probando datos de la nueva db*********************");
        //los datos recolectados se enviaran a un textView en la vista
                                SQLiteDatabase db = BD.getWritableDatabase();
                                UsersQuery usersQuery = new UsersQuery();
                                UsersModel us = usersQuery.getUser(db);
                                txtInfo.setText(us.getUser());
                            }else{
                                System.out.println("****************************fail***************************");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(MainActivity.this,"El archivo de base de datos no existe en la ruta especificada",Toast.LENGTH_SHORT);
                    }
                }
            }
        });

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BD.exportDB();
            }
        });
    }
    private boolean checkPermissionMethod(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    0);
            return true;
        } else {
            return true;
        }
    }
}
