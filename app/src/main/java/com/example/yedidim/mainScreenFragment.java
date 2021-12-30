package com.example.yedidim;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class mainScreenFragment extends Fragment {

    public mainScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);

        Button signUpBtn = view.findViewById(R.id.mainScreen_btn_signUp);
        Button loginBtn = view.findViewById(R.id.mainScreen_btn_logIn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mainScreenFragmentDirections.
//                mainScreenFragmentDirections.ActionMainScreenFragmentToSignUpFragment action = mainScreenFragmentDirections.actionMainScreenFragmentToSignUpFragment();
                @NonNull NavDirections action = mainScreenFragmentDirections.actionMainScreenFragmentToSignUpFragment();

                Navigation.findNavController(v).navigate(action);
            }
        });
        
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @NonNull NavDirections action = mainScreenFragmentDirections.actionMainScreenFragmentToLogInFragment();
                Navigation.findNavController(v).navigate(action);
            }
        });

        return view;
    }
}