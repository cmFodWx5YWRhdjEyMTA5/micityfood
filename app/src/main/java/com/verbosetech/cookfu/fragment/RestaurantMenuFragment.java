package com.verbosetech.cookfu.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.verbosetech.cookfu.R;
import com.verbosetech.cookfu.adapter.RestaurantMenuAdapter;

public class RestaurantMenuFragment extends Fragment {
    private RecyclerView recyclerMenu;


    public RestaurantMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_menu, container, false);
        recyclerMenu = view.findViewById(R.id.recyclerMenu);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRestaurantMenu();
    }

    private void setupRestaurantMenu() {
        recyclerMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerMenu.setAdapter(new RestaurantMenuAdapter(getContext()));
    }
}
