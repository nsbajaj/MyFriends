package com.example.nitish.myfriends;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    int rows = 0;
    int min = 0;
    int max = 150;

    TextView idView;
    EditText studentNameText;

    int[] id;
    String[] name;
    String[] email;
    String[] phoneNumber;

	Student[] student = new Student[max];

    MyDBHandler myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentNameText = (EditText) findViewById(R.id.student_name);

        myDB = new MyDBHandler(this,null,null,1);

        Cursor cursor = myDB.getAllRows();
        getAllStudent(cursor);

        rows = cursor.getCount();
        setValue();
        transfer();

        listView();
    }

    private void getAllStudent(Cursor cursor) {
        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                Student temp = new Student();
                // Process the data:
                temp.setID(Integer.parseInt(cursor.getString(0)));
                temp.setName(cursor.getString(1));
                temp.setEmail(cursor.getString(2));
                temp.setPhoneNumber(cursor.getString(3));
		        if(min < max) {
                    student[min++] = temp;
                }
            } while(cursor.moveToNext());
        }
        // Close the cursor to avoid a resource leak.
        cursor.close();
    }
    private void setValue() {
        id = new int[rows];
        name = new String[rows];
        email = new String[rows];
        phoneNumber = new String[rows];
    }

    private void transfer(){
        for(int i = 0; i < min; i++){
            if(student[i].getID() != 0 && student[i].getName() != null && student[i].getEmail() != null && student[i].getPhoneNumber() != null) {
                id[i] = student[i].getID();
                name[i] = student[i].getName();
                email[i] = student[i].getEmail();
                phoneNumber[i] = student[i].getPhoneNumber();
            }
        }
    }
    private void listView() {
        /*
        1.) First Parameter is context
        2.) Second Parameter is the Layout, which this ArrayAdapter will use to bind the data from codeLearnChapters Array.
            Android comes pre bundled with some common layout which you can refer with android.R.layout. Here we are using - simple_list_item_1, which is just a simple text view.
        3.) Third parameter is the data, in this case the String Array.
        */
        String[] test = new String[min];
        for(int i = 0; i < min;  i++){
            test[i] = name[i];
        }
        ListView view = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, test);
        view.setAdapter(adapter);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String input = (String) parent.getItemAtPosition(position);
                for(int i = 0; i < min; i++){
                    if(input.equals(name[i])){
                        Intent intent = new Intent(MainActivity.this, ContactInfo.class);
                        String tempName = name[i];
                        String tempEmail = email[i];
                        String tempPhoneNumber = phoneNumber[i];
                        intent.putExtra("name",tempName);
                        intent.putExtra("email",tempEmail);
                        intent.putExtra("phone",tempPhoneNumber);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_about:
                toast("about");
                return true;
            case R.id.action_help:
                toast("help");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toast(String about) {
        if(about.equals("about")){
            Context context = getApplicationContext();
            CharSequence text = "This app shows information about contacts, such as their Name, Email & Phone Number";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        if(about.equals("help")){
            Context context = getApplicationContext();
            CharSequence text = "Click on one of the names to get a contact's information.";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

}
