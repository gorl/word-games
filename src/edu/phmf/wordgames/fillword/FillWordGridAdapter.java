package edu.phmf.wordgames.fillword;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;

public class FillWordGridAdapter extends BaseAdapter {
	private Context context;
	private int cols, rows;
	private Resources res;
	public FillWordGridAdapter(Context context, int cols, int rows){
		System.out.println("constructor");
		this.context = context;
		this.cols = cols;
		this.rows = rows;
		res = context.getResources();
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return cols * rows;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view;
		//корректно получаем вид
		if (convertView == null){
			view = new TextView(context);
		}
		else{
			view = (TextView)convertView;
		}
		Cell cell = Board.getInstance().getCellByPosition(position);
		if (cell.getLetter() == null){
			cell.setLetter(";;");
		}
		if(cell.getState() == State.CHECKED){
			//берем идентификатор из ресурсов
			view.setText(cell.getLetter().toUpperCase());	
			view.setBackgroundColor(Color.BLUE);
		} else {
			view.setText(cell.getLetter().toLowerCase());
			view.setBackgroundColor(Color.GREEN);
		}
		
		return view;
	}
	public View getView2(int position, View convertView, ViewGroup parent) {
		// артинка дл€ отображени€
		ImageView view;
		
		//корректно получаем вид
		if (convertView == null)
			view = new ImageView(context);
		else
			view = (ImageView)convertView;
		
		Cell cell = Board.getInstance().getCellByPosition(position);
		if(cell.getState() == State.CHECKED){
			//берем идентификатор из ресурсов
			Integer imgId = res.getIdentifier("test", "drawable", context.getPackageName());
			//ставим картинку по ид
			view.setImageResource(imgId);	
		} else {
			Integer imgId = res.getIdentifier("test2", "drawable", context.getPackageName());
			//ставим картинку по ид
			view.setImageResource(imgId);
		}
		
		
		return view;
		
	}

}
