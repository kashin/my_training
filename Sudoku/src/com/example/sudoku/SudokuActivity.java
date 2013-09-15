package com.example.sudoku;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;

public class SudokuActivity extends Activity {
	
	private SudokuAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sudoku);
		
		GridView field = (GridView) findViewById(R.id.gameField);
		
		mAdapter = new SudokuAdapter(this);
		field.setAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sudoku, menu);
		return true;
	}

}
