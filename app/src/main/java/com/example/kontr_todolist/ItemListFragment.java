package com.example.kontr_todolist;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemListFragment extends Fragment {

    SQLiteDatabase sqLiteDatabase;
    DataBaseHelper dataBaseHelper;
    Cursor cursor;
    private ArrayList<ItemList> itemLists;
    private RecyclerView itemRecyclerView;
    private ItemListAdapter itemListAdapter;

    public ItemListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        itemRecyclerView = view.findViewById(R.id.fl_items_rv);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemLists = new ArrayList<>();


        //получение данных с базы
        dataBaseHelper = new DataBaseHelper(getContext());
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        cursor = dataBaseHelper.getInfo(sqLiteDatabase);
        if (cursor.moveToFirst()) {
            do {

                String name, title;
                name = cursor.getString(0);
                title = cursor.getString(1);
                ItemList item1 = new ItemList();
                item1.setName(name);
                item1.setTitle(title);
                itemLists.add(item1);
            } while (cursor.moveToNext());
        }

        itemRecyclerView.setAdapter(new ItemListAdapter(getContext(), itemLists, (MainActivity) getActivity()));
        itemListAdapter = (new ItemListAdapter(getContext(), itemLists, (MainActivity) getActivity()));
        itemListAdapter.notifyDataSetChanged();
        itemRecyclerView.setAdapter(itemListAdapter);

        return view;
    }

    private void init() {
        ItemList item1 = new ItemList();
        item1.setId(1);
        item1.setName("Meizu m3 Note");
        item1.setTitle("16 gb  ");

        ItemList item2 = new ItemList();
        item2.setId(2);
        item2.setName("iPhone X");
        item2.setTitle("256 gb  ");

        itemLists = new ArrayList<>();
        itemLists.add(item1);
        itemLists.add(item2);
    }


    public interface Callback {
        void changeFragmentClicked(View view, ItemList itemList);
    }
}
