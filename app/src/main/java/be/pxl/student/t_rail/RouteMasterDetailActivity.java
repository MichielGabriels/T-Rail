package be.pxl.student.t_rail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import be.pxl.student.t_rail.adapters.RouteMasterAdapter;
import be.pxl.student.t_rail.domainClasses.Route;

public class RouteMasterDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_master);

        String connections = getIntent().getStringExtra("connections");

        Bundle dataBundle = new Bundle();
        dataBundle.putString("connections",connections);

        RouteMasterFragment masterFragment = new RouteMasterFragment();
        masterFragment.setArguments(dataBundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentMaster,masterFragment);
        transaction.commit();
    }
}
