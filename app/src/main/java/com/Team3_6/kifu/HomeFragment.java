package com.Team3_6.kifu;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    Button mapButton;


    //initialise category list
    List<Category> lstCategory;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment

        mapButton = rootview.findViewById(R.id.mapButton);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MapActivity.class);

                startActivity(intent);

            }
        });


        // add all the categories
        lstCategory = new ArrayList<Category>();
        lstCategory.add(new Category("Books & Stationery", R.drawable.stationery));
        lstCategory.add(new Category("Clothes & Accessories", R.drawable.clothes2));
        lstCategory.add(new Category("Electronics", R.drawable.electronics2));
        lstCategory.add(new Category("Furniture", R.drawable.furniture2));
        lstCategory.add(new Category("Gardening", R.drawable.garderning2));
        lstCategory.add(new Category("Home Appliances", R.drawable.homeappliance));
        lstCategory.add(new Category("Toys & Games", R.drawable.toy));
        lstCategory.add(new Category("Others", R.drawable.misc));

        RecyclerView MenuRecyclerView = rootview.findViewById(R.id.menu_recycler_view);
        MenuAdapter menuAdapter = new MenuAdapter(getActivity(), lstCategory);
        MenuRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        MenuRecyclerView.setAdapter(menuAdapter);

        return rootview;

    }

}
