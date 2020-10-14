package com.tinhuynh.ptfilemanager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.tinhuynh.ptfilemanager.adapter.ListAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView main_list;
    private TextView path1;
    private ListAdapter listAdapter;
    private String fileDirectory;
    private File directory;
    private File[] file_list;
    private String[] file_names;
    private int file_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if android version on running device is 6.0 or higher
        //apps run on android 6.0 or higher need to be granted permission
        //Check if the app has been granted WRITE EXTERNAL STORAGE permission
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        1001);
        }

        /**
         * Display the list of file in the Picture Directory
         */
        List<String> list = new ArrayList<>();
        path1 = findViewById(R.id.path1);

        fileDirectory = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        directory = new File(fileDirectory);
        file_list = directory.listFiles();
        path1.setText(fileDirectory.substring(fileDirectory.lastIndexOf('/')));
        file_count = file_list.length;


        ListAdapter listAdapter = new ListAdapter();
        main_list = findViewById(R.id.main_list);
        main_list.setAdapter(listAdapter);

        for (int i = 0; i < file_list.length; i++) {
            list.add(String.valueOf(file_list[i].getAbsolutePath()));
        }
        listAdapter.setFile_array(list);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }

    /**
     * The next five blocks will create a menu option and
     * assign actions on these options
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //find out which menu item was pressed
        switch (item.getItemId()) {
            case R.id.create_file_option:
                createFileDialog();
                return true;
            case R.id.help_option:
                help_option_menu();
                return true;
            case R.id.about_option:
                about_option_menu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createFileDialog() {
        //Create object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        //Set title
        builder.setTitle(getText(R.string.create_file));

        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.create_file_option_layout, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.show();

        Button save_button = view.findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button cancel_button = view.findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    File file = new File(Environment.DIRECTORY_PICTURES);

                    FileOutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(9);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {}

                dialog.dismiss();
            }
        });
    }

    private void help_option_menu() {

        startActivity(new Intent(this, Help.class));
    }

    private void about_option_menu() {

        startActivity(new Intent(this, About.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == 1001) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();

                //if the app get denied for permissions, it will be terminated
                //calling onDestroy method and exit
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
