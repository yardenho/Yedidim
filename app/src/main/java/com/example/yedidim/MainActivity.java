package com.example.yedidim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yedidim.Model.Model;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private NavController navctrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavHostFragment nav_host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.base_navHost);
        navctrl = nav_host.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navctrl);


//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//        List<Address> addresses = null;
//        try {
//            addresses = geocoder.getFromLocation(33.4162, 35.8570, 1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String cityName = addresses.get(0).getAddressLine(0);
//        Log.d("TAG", "city:" + cityName);
//
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.base_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(!super.onOptionsItemSelected(item)) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    NavDestination myFragment = navctrl.getCurrentDestination();
                    if (myFragment.getId() == R.id.reportsListFragment) {
                        finish();
                        return true;
                    }
                    navctrl.navigateUp();
                    return true;
                case R.id.baseMenu_aboutUs:
                    navctrl.navigate(aboutUsFragmentDirections.actionGlobalAboutUsFragment());
                    return true;
                case R.id.log_out_menu_LogOut:
                    Model.getInstance().logOutUser(new Model.logOutUserListener() {
                        @Override
                        public void onComplete() {
                            navctrl.navigate(myReportsFragmentDirections.actionGlobalMainScreenFragment());
                        }
                    });
                    return true;
                case R.id.myProfileMenu_myProfile:
                    navctrl.navigate(MapFragmentDirections.actionGlobalMyProfileFragment());
                    return true;
                case R.id.myReportsmenu_myReport:
                    navctrl.navigate(MapFragmentDirections.actionGlobalMyReportsFragment());
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        return true;
    }
}