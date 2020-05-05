package com.amin.lw_03_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
	private Button _bListId;
	private Button _bAddId;
	private Button _bReplaceId;

	private Button 		_bInsertDataId;
	private Button 		_bCancelInserId;
	private EditText 	_teFirstName;
	private EditText 	_teMidleName;
	private EditText 	_teLastName;
	private TextView 	_twFirstName;
	private TextView 	_twMidleName;
	private TextView 	_twLastName;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_bListId 	= (Button) findViewById(R.id.bListId);
		_bAddId 	= (Button) findViewById(R.id.bAddId);
		_bReplaceId = (Button) findViewById(R.id.bReplaceId);
		_bInsertDataId = (Button) findViewById(R.id.bInsertDataId);
		_bCancelInserId= (Button) findViewById(R.id.bCancelInserId);

		_teFirstName	= (EditText) findViewById(R.id.teFirstNameId);
		_teMidleName	= (EditText) findViewById(R.id.teMidleNameId);
		_teLastName		= (EditText) findViewById(R.id.teLastNameId);
		_twFirstName	= (TextView) findViewById(R.id.twFirstNameId);
		_twMidleName	= (TextView) findViewById(R.id.twMidleNameId);
		_twLastName		= (TextView) findViewById(R.id.twLastNameId);
		_bReplaceId 	= (Button) findViewById(R.id.bReplaceId);

		_bListId.setOnClickListener(this);
		_bAddId.setOnClickListener(this);
		_bReplaceId.setOnClickListener(this);
		_bInsertDataId.setOnClickListener(this);
		_bCancelInserId.setOnClickListener(this);

		hideFio();
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.bListId:
				toList();
				break;
			case R.id.bAddId:
				showFio();
				break;
			case R.id.bInsertDataId:
				insertFio(	_teFirstName.getText().toString(),
							_teMidleName.getText().toString(),
							_teLastName.getText().toString());
				hideFio();
				break;
			case R.id.bCancelInserId:
				hideFio();
				break;
			case R.id.bReplaceId:
				updateLastRecord("Иванов","Иван","Иванович");
				toList();
				break;
		}
	}

	private void toList()
	{
		Intent intent = new Intent(MainActivity.this, ListActivity.class);
		startActivity(intent);
	}

	private void showFio()
	{
		_bAddId.setEnabled(false);
		_teFirstName.setVisibility(View.VISIBLE);
		_teMidleName.setVisibility(View.VISIBLE);
		_teLastName.setVisibility(View.VISIBLE);
		_twFirstName.setVisibility(View.VISIBLE);
		_twMidleName.setVisibility(View.VISIBLE);
		_twLastName.setVisibility(View.VISIBLE);
		_bInsertDataId.setVisibility(View.VISIBLE);
		_bCancelInserId.setVisibility(View.VISIBLE);
	}

	private void hideFio()
	{
		_bAddId.setEnabled(true);
		_teFirstName.setVisibility(View.INVISIBLE);
		_twFirstName.setVisibility(View.INVISIBLE);
		_teFirstName.setText("");
		_teMidleName.setVisibility(View.INVISIBLE);
		_twMidleName.setVisibility(View.INVISIBLE);
		_teMidleName.setText("");
		_teLastName.setVisibility(View.INVISIBLE);
		_twLastName.setVisibility(View.INVISIBLE);
		_teLastName.setText("");
		_bInsertDataId.setVisibility(View.INVISIBLE);
		_bCancelInserId.setVisibility(View.INVISIBLE);
	}

	private void insertFio(String firstName, String midleName, String lastName)
	{
		SQLiteDatabase db = App.Db.getWritableDatabase();
		try
		{
			ContentValues cv = new ContentValues();
			cv.put(DbHelper.ColumnFirstName, firstName);
			cv.put(DbHelper.ColumnMidleName, midleName);
			cv.put(DbHelper.ColumnLastName, lastName);
			cv.put(DbHelper.ColumnDate, App.GetCurrentTime());
			long rowID = db.insert(DbHelper.TableName, null, cv);

			App.Log("Добавлена запись id = " + rowID);
		}
		finally
		{
			db.close();
		}
	}

	private void updateLastRecord(String firstName, String midleName, String lastName)
	{
		SQLiteDatabase db = App.Db.getWritableDatabase();
		try
		{
			Cursor cursor = db.query(App.Db.TableName, new String[] {String.format("MAX(%s) as rid",App.Db.ColumnRid)},null, null, null, null, null);
			cursor.moveToFirst();

			int lastId = cursor.getInt(0);

			ContentValues values = new ContentValues();
			values.put(DbHelper.ColumnFirstName, firstName);
			values.put(DbHelper.ColumnMidleName, midleName);
			values.put(DbHelper.ColumnLastName, lastName);

			values.put(App.Db.ColumnDate, App.GetCurrentTime());
			String where = App.Db.ColumnRid + "=" + lastId;

			db.update(App.Db.TableName, values, where , null);
		}
		finally
		{
			db.close();
		}
	}
}
