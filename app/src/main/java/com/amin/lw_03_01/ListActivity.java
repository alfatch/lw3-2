package com.amin.lw_03_01;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity
{
	private SQLiteDatabase _db;
	private Cursor _cursor;
	private MyCursorAdapter _cursorAdapter;
	private ListView _userList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		_userList = (ListView)this.findViewById(R.id.listId);
		App.Log("ListActivity.onCreate");
	}

	@Override
	protected void onResume()
	{
		App.Log("ListActivity.onResume.Start");
		super.onResume();

		_db = App.Db.getReadableDatabase();
		_cursor =  _db.rawQuery(
				"select "
					+ DbHelper.ColumnRid + ","
					+ DbHelper.ColumnFirstName + ","
					+ DbHelper.ColumnMidleName + ","
					+ DbHelper.ColumnLastName + ","
					+ DbHelper.ColumnDate +
					" from " + DbHelper.TableName, null);
		int col = _cursor.getCount();
		App.Log("Всего " + col);
		String[] headers = new String[] {"_id", DbHelper.ColumnFirstName, DbHelper.ColumnMidleName, DbHelper.ColumnLastName, DbHelper.ColumnDate};

		_cursorAdapter = new MyCursorAdapter(this, R.layout.list_item,
				_cursor, headers, new int[]{R.id.ridId, R.id.firstNameId, R.id.midleNameId, R.id.lastNameId, R.id.dateId });

		_userList.setAdapter(_cursorAdapter);
		App.Log("ListActivity.onResume.End");
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		_db.close();
		_cursor.close();
		App.Log("ListActivity.onDestroy");
	}
}
