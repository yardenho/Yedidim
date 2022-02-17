package com.example.yedidim;

import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yedidim.Model.Model;

public class firstFragment extends Fragment {
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        v = view;
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Model.getInstance().getCurrentUser(new Model.getCurrentUserListener() {
            @Override
            public void onComplete(String userEmail) {
                if(userEmail == null)
                {
                    @NonNull NavDirections action = firstFragmentDirections.actionGlobalMainScreenFragment();
                    Navigation.findNavController(v).navigate(action);
                }
                else
                {
                    firstFragmentDirections.ActionFirstFragmentToReportsListFragment action = firstFragmentDirections.actionFirstFragmentToReportsListFragment(userEmail);
                    Navigation.findNavController(v).navigate(action);
                }
            }
        });
    }
}