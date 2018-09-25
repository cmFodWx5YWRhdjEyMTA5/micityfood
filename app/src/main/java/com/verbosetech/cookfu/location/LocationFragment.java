//package com.verbosetech.cookfu.location;
//
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.Toolbar;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.ui.PlaceSelectionListener;
//import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
//import com.verbosetech.cookfu.R;
//
//public class LocationFragment extends Fragment implements PlaceSelectionListener {
//
//    public LocationFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_location, container, false);
//        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().onBackPressed();
//            }
//        });
//
////        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
////        autocompleteFragment.setHint("Search other location..");
////        autocompleteFragment.setOnPlaceSelectedListener(this);
//
//        return view;
//    }
//
//    @Override
//    public void onPlaceSelected(Place place) {
//    }
//
//    @Override
//    public void onError(Status status) {
//
//    }
//}
