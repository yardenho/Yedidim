package com.example.yedidim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import android.content.Context;
import android.os.Bundle;
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

public class MapFragment extends Fragment {
    static final double ISRAELLATITUDE = 31.3555;
    static final double ISRAELLONGITUDE = 34.3565;
    static final float ISRAELZOOMLEVEL = 7.0f;
    static final float REPORTZOOMLEVEL = 11.0f;

    private GoogleMap gMap;
    private MapViewModel viewModel;
    private View view;

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

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                if(viewModel.getReportID() == null) {
                    for(Report r : Model.getInstance().getAllReports().getValue())
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(r.getLatitude(), r.getLongitude())).title("Marker in " + r.getLocation())).setTag(r.getReportID());
                    LatLng Israel = new LatLng(ISRAELLATITUDE, ISRAELLONGITUDE);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Israel, ISRAELZOOMLEVEL));
                }
                else{
                    Model.getInstance().getReportByID(viewModel.getReportID(), new Model.getReportByReportIDListener() {
                        @Override
                        public void onComplete(Report report) {
                            LatLng reportLocation = new LatLng(report.getLatitude(), report.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(reportLocation).title("Marker in " + report.getLocation())).setTag(report.getReportID());
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(reportLocation, REPORTZOOMLEVEL));
                        }
                    });
                }

                gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Navigation.findNavController(view).navigate(MapFragmentDirections.actionMapFragmentToViewReportFragment(marker.getTag().toString()));
                    }
                });
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_menu, menu);
        inflater.inflate(R.menu.my_profile_menu, menu);
        inflater.inflate(R.menu.my_reports_menu, menu);
        inflater.inflate(R.menu.log_out_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addMenu_addReport:
                @NonNull NavDirections action = MapFragmentDirections.actionMapFragmentToAddingReportFragment();
                Navigation.findNavController(view).navigate(action);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}