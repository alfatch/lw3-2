package com.amin.lw_03_01;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MyCursorAdapter extends SimpleCursorAdapter
{
	private int _layout;
	public MyCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to)
	{
		super(context, layout, c, from, to);
		_layout = layout;
	}
	@Override
	public View newView(Context _context, Cursor _cursor, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) _context.getSystemService(_context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(_layout, parent, false);
		return view;
	}

	@Override
	public void bindView(View view, Context _context, Cursor _cursor)
	{
		String firstName 	= _cursor.getString(_cursor.getColumnIndex(DbHelper.ColumnFirstName));
		String midleName 	= _cursor.getString(_cursor.getColumnIndex(DbHelper.ColumnMidleName));
		String lastName 	= _cursor.getString(_cursor.getColumnIndex(DbHelper.ColumnLastName));
		int rid 			= _cursor.getInt(_cursor.getColumnIndex(DbHelper.ColumnRid));
		String date 		= _cursor.getString(_cursor.getColumnIndex(DbHelper.ColumnDate));

		TextView ridId = (TextView) view.findViewById(R.id.ridId);
		ridId.setText("" + rid);

		TextView dateId = (TextView) view.findViewById(R.id.dateId);
		dateId.setText(date);

		TextView fio = (TextView) view.findViewById(R.id.firstNameId);
		fio.setText(lastName + " " + firstName + " " + midleName);
	}
}
