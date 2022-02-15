package com.example.yedidim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.Report;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapFragment extends Fragment {
    private GoogleMap gMap;
    private MapViewModel viewModel;
    private View view;
    private String username;        //TODO: need to be deleted because we will not save that in the future


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        viewModel.setReportID(MapFragmentArgs.fromBundle(getArguments()).getReportID());
        //TODO: need to be deleted because we will not save that in the future
        username = MapFragmentArgs.fromBundle(getArguments()).getUsername();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                if(viewModel.getReportID() == null) {
                    for(Report r : Model.getInstance().getAllReports().getValue())
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(r.getLatitude(), r.getLongitude())).title("Marker in " + r.getLocation())).setTag(r.getReportID());
                    //TODO: thy to set the camera on israel, not working to fix!
                    LatLng Israel = new LatLng(31.3555, 34.3565);
                    float zoomLevel = 5.0f;
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Israel, zoomLevel));
                }

                else{
                    Model.getInstance().getReportByID(viewModel.getReportID(), new Model.getReportByReportIDListener() {
                        @Override
                        public void onComplete(Report report) {
                            LatLng loc = new LatLng(report.getLatitude(), report.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(loc).title("Marker in " + report.getLocation())).setTag(report.getReportID());
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 10.0f));
                        }
                    });
                }

                gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Navigation.findNavController(view).navigate(MapFragmentDirections.actionMapFragmentToViewReportFragment(username, marker.getTag().toString()));
                    }
                });
            }
        });
        setHasOptionsMenu(true);
        return view;
    }
    public GoogleMap getMap(){return gMap;}

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_menu, menu);
        inflater.inflate(R.menu.my_profile_menu, menu);
        inflater.inflate(R.menu.my_reports_menu, menu);
        inflater.inflate(R.menu.log_out_menu, menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.addMenu_addReport:
//                //TODO: when we after the sign up maybe not allow to return there
//                MapFragmentDirections.ActionMapFragmentToAddingReportFragment action = MapFragmentDirections.actionMapFragmentToAddingReportFragment(username);
//                Navigation.findNavController(view).navigate(action);
//                return true;
//            case R.id.log_out_menu_LogOut:
//                //TODO
//                Navigation.findNavController(view).navigate(MapFragmentDirections.actionGlobalMainScreenFragment());
//                return true;
//            case R.id.myProfileMenu_myProfile:
//                Navigation.findNavController(view).navigate(MapFragmentDirections.actionGlobalMyProfileFragment(username));
//                return true;
//            case R.id.myReportsmenu_myReport:
//                Navigation.findNavController(view).navigate(MapFragmentDirections.actionGlobalMyReportsFragment(username));
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    LatLng sydney = new LatLng(31.7683, 35.2137);
//                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Jerusalem"));
//                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    LatLng ASH = new LatLng(31.8044, 34.6553);
//                googleMap.addMarker(new MarkerOptions().position(ASH).title("Marker in Ashdod"));
//                googleMap.moveCamera(CameraUpdateFactory.newLatLng(ASH));
}