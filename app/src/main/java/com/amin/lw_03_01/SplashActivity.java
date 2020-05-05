package com.amin.lw_03_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.Random;

public class SplashActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		if(savedInstanceState==null)
		{
			final SplashActivity host = this;
			MyTask task = new MyTask(new MyTask.Listener()
			{
				@Override
				public void onPreExecute()
				{
				}

				@Override
				public void doInBackground(Void... params)
				{
					String[][] array =
							{
									{"Косогоров","Кирил", "Сергеевич"},
									{"Алексеев","Никита", "Евгеньевич"},
									{"Баранников","Никита", "Сергеевич"},
									{"Копотов","Михаил", "Александрович"},
									{"Кошкин","Артем", "Андреевич"},
									{"Сахаров","Владимир", "Иванович"},
									{"Копташкина","Татьяна", "Александровна"}
							};

					SQLiteDatabase db = App.Db.getWritableDatabase();

					try
					{
						App.Log("Удаление всех студентов");
						db.execSQL(String.format("delete from %s", DbHelper.TableName));

						App.Log("Заполнение таблицы. strat");

						ContentValues cv = new ContentValues();
						int index = -1;
						for (int i = 0; i < 5; i++)
						{
							index 			= rnd(array.length - 1 - i);
							String[] item 	= get(array, index);

							cv.put(DbHelper.ColumnFirstName, item[0]);
							cv.put(DbHelper.ColumnMidleName, item[1]);
							cv.put(DbHelper.ColumnLastName, item[2]);
							cv.put(DbHelper.ColumnDate, App.GetCurrentTime());
							long rowID = db.insert(DbHelper.TableName, null, cv);

							App.Log("Добавлена запись id = " + rowID);
						}

						App.Log("Заполнение таблицы. end");
					}
					finally
					{
						db.close();
					}
				}

				@Override
				public void onPostExecute(Void result)
				{
					App.Log("Старт MainActivity.");
					Intent intent = new Intent(SplashActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				}

				private  int rnd(int max)
				{
					double r = Math.random();
					int random = (int) (Math.random() * max);
					return random;
				}

				private String[] get(String[][] array, int index)
				{
					int c = 0;
					for(int i=0; i< array.length; i++)
					{
						if (array[i] !=null)
						{
							if (c==index)
							{
								String[] res = array[i];
								array[i] = null;
								return  res;
							}
							c++;
						}
					}
					return null;
				}
			});
			task.execute();
		}
	}
}
