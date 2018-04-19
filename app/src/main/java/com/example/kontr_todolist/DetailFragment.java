package com.example.kontr_todolist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    Button add_Button;
    EditText name_EditText;
    EditText title_EditText;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        add_Button = view.findViewById(R.id.fd_add_btn);
        name_EditText = view.findViewById(R.id.fd_name_et);
        title_EditText = view.findViewById(R.id.fd_title_et);

        String name = name_EditText.getText().toString();
        String title = title_EditText.getText().toString();

        add_Button.setOnClickListener(view1 -> {

        });


        return view;
    }


}

