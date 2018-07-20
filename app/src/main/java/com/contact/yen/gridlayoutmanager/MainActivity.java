package com.contact.yen.gridlayoutmanager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 100;
    public static final String PATH = Environment.getExternalStorageDirectory().getPath().toString();
    public static final String DOTPNG = ".png";
    public static final String DOTJPG = ".jpg";
    public static final String DOTJPEG = ".jpeg";
    public static final int NUM_COLUMN = 2;

    private List<File> mListImage;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
        init();

    }

    private void init() {

        mRecyclerView = findViewById(R.id.recycler_view);
        mListImage = new ArrayList<>();
        adapter = new RecyclerViewAdapter(MainActivity.this, mListImage);
        GridLayoutManager manager = new GridLayoutManager(this, NUM_COLUMN);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                initData();
            }else{
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }
    }

    private void initData() {
        getfile(new File(PATH));
        adapter.notifyDataSetChanged();
    }
    public void getfile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    getfile(listFile[i]);
                } else {
                    if (listFile[i].getName().endsWith(DOTPNG)
                            || listFile[i].getName().endsWith(DOTJPG)
                            || listFile[i].getName().endsWith(DOTJPEG)
                            ) {
                        mListImage.add(listFile[i]);
                    }
                }
            }
        }
    }
}

