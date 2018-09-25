package com.verbosetech.cookfu.checkout;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import com.verbosetech.cookfu.R;

public class CheckoutAddressFragment extends Fragment implements View.OnClickListener {
    private RadioButton deliveryOptionRadio1, deliveryOptionRadio2, deliveryOptionRadio3;
    private AppCompatSpinner spinnerHours;

    public CheckoutAddressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout_address, container, false);
        deliveryOptionRadio1 = view.findViewById(R.id.deliveryOptionRadio1);
        deliveryOptionRadio2 = view.findViewById(R.id.deliveryOptionRadio2);
        deliveryOptionRadio3 = view.findViewById(R.id.deliveryOptionRadio3);
        spinnerHours = view.findViewById(R.id.spinnerHours);
        view.findViewById(R.id.deliveryOption1).setOnClickListener(this);
        view.findViewById(R.id.deliveryOption2).setOnClickListener(this);
        view.findViewById(R.id.deliveryOption3).setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupHoursSpinner();
    }

    private void setupHoursSpinner() {
        String[] spinnerArray = {"30 Mins", "1 Hour", "2 Hour", "2.5 Hour", "3 Hour"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHours.setAdapter(spinnerArrayAdapter);
    }

    @Override
    public void onClick(View v) {
        deliveryOptionRadio1.setChecked(false);
        deliveryOptionRadio2.setChecked(false);
        deliveryOptionRadio3.setChecked(false);
        switch (v.getId()) {
            case R.id.deliveryOption1:
                deliveryOptionRadio1.setChecked(true);
                break;
            case R.id.deliveryOption2:
                deliveryOptionRadio2.setChecked(true);
                break;
            case R.id.deliveryOption3:
                deliveryOptionRadio3.setChecked(true);
                break;
        }
    }

//    private void setupAddressRecycler() {
//        recyclerAddress.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerAddress.setAdapter(new AddressAdapter(getContext()));
//    }

}
