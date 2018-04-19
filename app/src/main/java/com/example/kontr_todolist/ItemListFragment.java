package com.example.kontr_todolist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemListFragment extends Fragment {

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

        init();

        itemRecyclerView.setAdapter(new ItemListAdapter(getContext(), itemLists, (MainActivity) getActivity()));

        itemListAdapter = (new ItemListAdapter(getContext(), itemLists, (MainActivity) getActivity()));
        itemListAdapter.notifyDataSetChanged();
        itemRecyclerView.setAdapter(itemListAdapter);

        return view;
    }

    private void init() {
        ItemList item1 = new ItemList();
        item1.setId(UUID.randomUUID());
        item1.setName("Meizu m3 Note");
        item1.setTitle("16 gb  ");

        ItemList item2 = new ItemList();
        item2.setId(UUID.randomUUID());
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
