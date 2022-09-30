package com.rana.neonfilesystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rana.nenofilesystem.R;
import com.rana.neonfilesystem.database.DBManager;
import com.rana.neonfilesystem.fileManager.FileManagerHelper;
import com.rana.neonfilesystem.models.ElementModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DBManager dbManager;
    private String rootPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         dbManager=new DBManager(this);

//
//        //https://www.digitalocean.com/community/tutorials/android-sqlite-database-example-tutorial
//        DBManager dbManager=new DBManager(this);
//        dbManager.open();
//        dbManager.insert("TestName1", "TestDesc1");
//       // dbManager.close();
//        Cursor cursor=dbManager.fetch();
//
//
//        if(cursor.moveToFirst()){
//            Log.e(TAG, cursor.getString(1));
//
//        }


    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(
                MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else
            return false;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(
                    MainActivity.this,
                    "Storage permission is requires, please allow from settings",
                    Toast.LENGTH_SHORT).show();
        } else
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    111);
    }

    public void scanFileManage(View view) {
        if(checkPermission()){
            //permission allowed
            rootPath = Environment.getExternalStorageDirectory().getPath();

            onScanFileManager(rootPath);


        }else {
            ///permission not allowed
            requestPermission();
        }
    }

    private void onScanFileManager(String path) {

        dbManager.open();
        FileManagerHelper managerHelper=new FileManagerHelper();

        File[] files = managerHelper.scan(path);
        if(null!=files){
            List<File> dirList=new ArrayList<>();
            for(File file:files){
                Log.e(TAG, ".............................................................");
                Log.e(TAG, "Root Path : "+path);
                Log.e(TAG, "file name : "+file.getName());
                Log.e(TAG, "file path : "+file.getPath());
                Log.e(TAG, "file absolute path : "+file.getAbsolutePath());
                Log.e(TAG, "file isDirectory : "+file.isDirectory());
                Log.e(TAG, "file isFile  : "+file.isFile());
                Log.e(TAG, "file getParent  : "+file.getParent());
                Log.e(TAG, "file getParentFile  : "+file.getParentFile());

                Log.e(TAG, ".............................................................");

                ElementModel model=new ElementModel();
                model.setElement(file.getName());

                //Check is parent or not
                // if yes then find out parent id and set parent id and save into database
                Log.e(TAG, "Root path "+rootPath);
                Log.e(TAG, "ParentFile "+file.getParentFile().toString());

                if(!rootPath.equals(file.getParentFile().toString())){
                    // find out parent id and set parent id and save into database
                    String[] splitString = file.getParentFile().toString().split("/");
                    String parentFileName=splitString[splitString.length-1];
                    Log.e(TAG, "Split String "+parentFileName);
                    //find out parentFileName id in sql
                    model.setParent_id(dbManager.getParentByElement(parentFileName));

                }
                if(file.isDirectory()){
                    model.setIs_dir(1);
                    dirList.add(file);
                }

                new InsertDataAsyncTask().execute(model, null, null);
                for(File dirFile:dirList){
                    onScanFileManager(dirFile.getAbsolutePath());
                }
        }
        }
    }

    private class InsertDataAsyncTask extends AsyncTask<ElementModel, Void, Void>{
        @Override
        protected Void doInBackground(ElementModel... elementModels) {
            dbManager.insert(elementModels[0]);
            return null;
        }
    }

}