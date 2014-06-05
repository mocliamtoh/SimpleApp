package com.android.simpleapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class SimpleActivity extends Activity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    final static String btnLabel="Add";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        items.add("This comes first");
        items.add("Hello World!");
        setupListViewListener();
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");

        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e){
            items = new ArrayList<String>();
            e.printStackTrace();
        }
    }

    private void saveItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");

        try{
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long row) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                saveItems();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.simple, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addTodoItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        itemsAdapter.add(etNewItem.getText().toString());
        etNewItem.setText("");

        saveItems();
    }

}
