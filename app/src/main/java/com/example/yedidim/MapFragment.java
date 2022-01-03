package com.example.yedidim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {
    private GoogleMap gMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                LatLng sydney = new LatLng(31.7683, 35.2137);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Jerusalem"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                LatLng ASH = new LatLng(31.8044, 34.6553);
                googleMap.addMarker(new MarkerOptions().position(ASH).title("Marker in Ashdod"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(ASH));

            }
        });
        return view;
    }
    public GoogleMap getMap(){return gMap;}

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.log_out_menu, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.log_out_menu_LogOut:
//                //TODO:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//        //        return true;
//    }
}