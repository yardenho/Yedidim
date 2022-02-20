package com.example.yedidim;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.yedidim.Model.Model;

public class logInFragment extends Fragment {
    EditText usernameEt;
    EditText passwordEt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        usernameEt = view.findViewById(R.id.logIn_et_username);
        passwordEt = view.findViewById(R.id.logIn_et_password);
        Button logInBtn = view.findViewById(R.id.logIn_btn_logIn);
        Button cancelBtn = view.findViewById(R.id.logIn_btn_cancel);
        ProgressBar pb = view.findViewById(R.id.logIn_progressBar);
        pb.setVisibility(View.GONE);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                logInBtn.setEnabled(false);
                cancelBtn.setEnabled(false);
                if (checkDetails()) {
                    Model.getInstance().loginUser(usernameEt.getText().toString().trim(),
                            passwordEt.getText().toString().trim(), new Model.loginUserListener() {
                                @Override
                                public void onComplete(boolean success) {
                                    if(success) {
                                        @NonNull NavDirections action = logInFragmentDirections.actionLogInFragmentToReportsListFragment();
                                        Navigation.findNavController(v).navigate(action);
                                    }
                                    else {
                                        Toast.makeText(getActivity(), "failed to login, please check your credentials", Toast.LENGTH_LONG).show();
                                        pb.setVisibility(View.GONE);
                                        logInBtn.setEnabled(true);
                                        cancelBtn.setEnabled(true);
                                    }
                                }
                            });
                }
                else
                {
                    pb.setVisibility(View.GONE);
                    logInBtn.setEnabled(true);
                    cancelBtn.setEnabled(true);
                }
            }
        });
        return view;
    }

    private boolean checkDetails() {
        String email = usernameEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        if (email.isEmpty()) {
            usernameEt.setError("email is required");
            usernameEt.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            usernameEt.setError("Please provide valid email");
            usernameEt.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            passwordEt.setError("password is required");
            passwordEt.requestFocus();
            return false;
        }
        if (password.length() < 6) {
            passwordEt.setError("password should be at least 6 characters");
            passwordEt.requestFocus();
            return false;
        }
        return true;
    }
}