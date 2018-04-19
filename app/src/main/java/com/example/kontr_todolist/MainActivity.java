package com.example.kontr_todolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.Callable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements ItemListFragment.Callback {

    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // добавляем стартовый фрагмент
        ItemListFragment myFragment = new ItemListFragment();
        fragmentTransaction.add(R.id.container, myFragment);
        fragmentTransaction.commit();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                String tag = DetailFragment.class.getSimpleName();
                DetailFragment detailFragment = new DetailFragment();
                transaction.replace(R.id.container, detailFragment, tag);
                transaction.addToBackStack(tag);
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
                transaction.commit();
            }
        });

        insertPerson().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(getApplicationContext(), "Вставка завершена", Toast.LENGTH_LONG).show();
                    }
                });
    }


    private io.reactivex.Observable<String> insertPerson() {
        return io.reactivex.Observable.fromCallable(new Callable<String>() {

            @Override
            public String call() throws Exception {
                DataBaseHelper databaseHelper = new DataBaseHelper(getApplicationContext());
                SQLiteDatabase database = databaseHelper.getWritableDatabase();

                try {
                    database.beginTransaction();

                    ItemList itemList = new ItemList();
                    itemList.setName("Rustam");
                    itemList.setTitle("+77021111111");

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DataBaseHelper.FULL_NAME_COLUMN, itemList.getName());
                    contentValues.put(DataBaseHelper.FULL_TITLE_COLUMN, itemList.getTitle());

                    database.insert(DataBaseHelper.LISTS_TABLE, null, contentValues);

                    /*database.update(DatabaseHelper.PEOPLE_TABLE, contentValues,
                            DatabaseHelper.ID_COLUMN + " LIKE ?",
                            new String[] {String.valueOf(1)});

                    database.delete(DatabaseHelper.PEOPLE_TABLE,
                            DatabaseHelper.ID_COLUMN + " LIKE ?",
                            new String[] {String.valueOf(1)});

                    Cursor cursor = database.query(
                            DatabaseHelper.PEOPLE_TABLE,
                            new String[]{DatabaseHelper.FULL_NAME_COLUMN, DatabaseHelper.EMAIL_COLUMN, DatabaseHelper.AGE_COLUMN},
                            " LIKE ?",
                            new String[]{String.valueOf(1)},
                            null,
                            null,
                            null //DataBaseHelper.AGE_COLUMN + " ASC"
                    );

                    cursor.moveToFirst();
                    while (cursor.moveToNext()) {
                        int ageColumnIndex = cursor.getColumnIndex(DatabaseHelper.AGE_COLUMN);
                        int age = cursor.getInt(ageColumnIndex);
                    }*/

//                    Cursor cursor = database.rawQuery("SELECT ALL FROM Lists", null,null);
                    Cursor cursor = database.query(
                            DataBaseHelper.LISTS_TABLE,
                            new String[]{DataBaseHelper.FULL_NAME_COLUMN, DataBaseHelper.FULL_TITLE_COLUMN},
                            " LIKE ?",
                            new String[]{String.valueOf(1)},
                            null,
                            null,
                            null //DataBaseHelper.AGE_COLUMN + " ASC"
                    );

                    database.setTransactionSuccessful();
                    //cursor.close();
                } catch (SQLException ex) {
                    Log.d(MainActivity.class.getSimpleName(), ex.getMessage());
                } finally {
                    database.endTransaction();
                }

                database.close();
                databaseHelper.close();

                return "";
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void changeFragmentClicked(View view, ItemList itemList) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelable("ItemList", itemList);
        detailFragment.setArguments(bundle);

        String tag = DetailFragment.class.getSimpleName();

        transaction.replace(R.id.container, detailFragment, tag);
        transaction.addToBackStack(tag);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        transaction.commit();
    }


}
