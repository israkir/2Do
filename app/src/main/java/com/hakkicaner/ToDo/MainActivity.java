package com.hakkicaner.ToDo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
        showAddButton();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    public void onSaveItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
        showAddButton();
    }

    public void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        items.remove(i);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
                        etNewItem.setText(items.get(i));
                        etNewItem.setSelection(etNewItem.getText().length());
                        items.remove(i);
                        itemsAdapter.notifyDataSetChanged();
                        showSaveButton();
                    }
                }
        );
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(toDoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(toDoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showSaveButton() {
        Button add = (Button) findViewById(R.id.btnAddItem);
        Button save = (Button) findViewById(R.id.btnSaveItem);
        add.setVisibility(View.GONE);
        save.setVisibility(View.VISIBLE);
    }

    private void showAddButton() {
        Button add = (Button) findViewById(R.id.btnAddItem);
        Button save = (Button) findViewById(R.id.btnSaveItem);
        save.setVisibility(View.GONE);
        add.setVisibility(View.VISIBLE);
    }
}
