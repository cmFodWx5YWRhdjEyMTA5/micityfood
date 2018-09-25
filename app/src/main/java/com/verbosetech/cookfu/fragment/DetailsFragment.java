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
import com.verbosetech.cookfu.adapter.AddressAdapter;

public class DetailsFragment extends Fragment {
    private RecyclerView recyclerAddress;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        recyclerAddress = view.findViewById(R.id.recyclerAddress);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAddressRecycler();
    }

    private void setupAddressRecycler() {
        recyclerAddress.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAddress.setAdapter(new AddressAdapter(getContext()));
    }
}
