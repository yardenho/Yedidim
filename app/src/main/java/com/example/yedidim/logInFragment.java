package com.example.yedidim;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.User;

public class logInFragment extends Fragment {
    EditText usernameEt;
    EditText passwordEt;
    TextView usernameError;
    TextView passwordError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        usernameEt = view.findViewById(R.id.logIn_et_username);
        passwordEt = view.findViewById(R.id.logIn_et_password);
        Button logInBtn = view.findViewById(R.id.logIn_btn_logIn);
        usernameError = view.findViewById(R.id.logIn_tv_usernameError);
        passwordError = view.findViewById(R.id.logIn_tv_passwordError);
        usernameError.setVisibility(View.GONE);
        passwordError.setVisibility(View.GONE);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().getUserByUserName(usernameEt.getText().toString(), new Model.getUserByUserNameListener() {
                    @Override
                    public void onComplete(User user) {
                        if(user == null){
                            passwordError.setVisibility(View.GONE);
                            usernameError.setVisibility(View.VISIBLE);
                        }
                        else{
                            if(user.getPassword().equals(passwordEt.getText().toString())){
                                logInFragmentDirections.ActionLogInFragmentToReportsListFragment action = logInFragmentDirections.actionLogInFragmentToReportsListFragment(usernameEt.getText().toString());
                                Navigation.findNavController(v).navigate(action);
                            }
                            else{
                                passwordError.setVisibility(View.VISIBLE);
                                usernameError.setVisibility(View.GONE);

                            }

                        }
                    }
                });
            }
        });



        return view;
    }
}