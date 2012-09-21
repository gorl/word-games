package edu.phmf.wordgames.fillword;

import java.io.IOException;

import edu.phmf.wordgames.R;
import edu.phmf.wordgames.dictionary.Dictionary;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Chronometer;
import android.widget.GridView;

public class FillWordActivity extends Activity {
	private GridView gridView;
	private FillWordGridAdapter gridAdapter;
	private Chronometer chronometer;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_word);
        
        //Создаем игровое поле
        gridView = (GridView)findViewById(R.id.field);
        gridView.setNumColumns(Board.COLUMNS);
        gridView.setVerticalSpacing(5);
        gridView.setHorizontalSpacing(5);

        gridView.setEnabled(true);
        
        //адаптер размера игрового поля
        gridAdapter = new FillWordGridAdapter(this, Board.COLUMNS, Board.ROWS);
        gridView.setAdapter(gridAdapter);

        //обработчик кликов по картинке
        gridView.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Board.getInstance().evaluateCell(position);
				gridAdapter.notifyDataSetChanged();
				System.out.println("getting");
				System.out.println(Dictionary.getInstance().getRandomWord(8));
				
			}
       	
        });
        
        chronometer = (Chronometer)findViewById(R.id.timeview);
        chronometer.start(); 
		Dictionary.initInstance(getResources());
		System.out.println("....creating board");
		Board.getInstance().getnerateField();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_fill_word, menu);
        return true;
    }
}
