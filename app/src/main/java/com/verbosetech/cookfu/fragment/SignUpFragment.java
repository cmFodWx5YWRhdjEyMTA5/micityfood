package com.verbosetech.cookfu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.verbosetech.cookfu.R;
import com.verbosetech.cookfu.interactor.AuthInnerInteractor;

public class SignUpFragment extends Fragment {
    private AuthInnerInteractor innerInteractor;


    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(AuthInnerInteractor innerInteractor) {
        SignUpFragment fragment = new SignUpFragment();
        fragment.innerInteractor = innerInteractor;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }
}
