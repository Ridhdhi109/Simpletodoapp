package com.codepath.starter.simpletodoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {
	
	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	private EditText etNewItems;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		lvItems = (ListView) findViewById(R.id.lvItems);
		etNewItems = (EditText) findViewById(R.id.etNewItems);
		readItems();
		todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
		lvItems.setAdapter(todoAdapter);
		setupListViewListener();		
		
	}
	
	private void setupListViewListener() {
		lvItems.setOnItemClickListener (new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View item, int pos,long id) {
				todoItems.remove(pos);
				todoAdapter.notifyDataSetChanged();
				writeItems();
			}
			
		});
		
	}

	public void onAddedItem(View v){
		String itemText = etNewItems.getText().toString();
		todoAdapter.add(itemText);
		etNewItems.setText("");
		writeItems();
		
	}
	
	
	private void readItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try{
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		}
		catch(IOException e){
			todoItems = new ArrayList<String>();		
		}
	}
	
	private void writeItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try{
			FileUtils.writeLines(todoFile, todoItems);
		}
		catch(IOException e){
			e.printStackTrace();		
		}
	}
	
}
