package com.example.yedidim;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.yedidim.Model.Model;

public class firstFragment extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_first, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Model.getInstance().getCurrentUser(new Model.getCurrentUserListener() {
            @Override
            public void onComplete(String userEmail) {
                @NonNull NavDirections action;
                if(userEmail == null)
                    action = firstFragmentDirections.actionGlobalMainScreenFragment();
                else
                    action = firstFragmentDirections.actionFirstFragmentToReportsListFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }
}