package be.pxl.student.t_rail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import be.pxl.student.t_rail.adapters.FavouritesAdapter;
import be.pxl.student.t_rail.domainClasses.ClickEvent;

public class RoutePlanActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewFavourites;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<String> mDummyDataSetFavourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);

        mRecyclerViewFavourites = (RecyclerView) findViewById(R.id.recyclerViewFavourites);

        //Test data
        ArrayList<String> stations = new ArrayList<String>();
        stations.add("Antwerpen");
        stations.add("Leuven");
        stations.add("Aarschot");
        stations.add("Brussel");
        stations.add("Hasselt");

        AutoCompleteTextView textViewFrom = (AutoCompleteTextView) findViewById(R.id.textViewStation1);
        AutoCompleteTextView textViewTo = (AutoCompleteTextView) findViewById(R.id.textViewTo);

        ArrayAdapter<String> textViewAdapter = new ArrayAdapter(this,R.layout.autocomplete_stations,stations);
        textViewFrom.setThreshold(1);
        textViewTo.setThreshold(1);

        textViewFrom.setAdapter(textViewAdapter);
        textViewTo.setAdapter(textViewAdapter);

        //test Data
        mDummyDataSetFavourites = new ArrayList<>();
        mDummyDataSetFavourites.add("Hasselt --> Aarschot");
        mDummyDataSetFavourites.add("Aarschot --> Hasselt");
        mDummyDataSetFavourites.add("Hasselt --> Kiewit");
        mDummyDataSetFavourites.add("Kiewit --> Hasselt");

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewFavourites.setLayoutManager(mLayoutManager);

        mAdapter = new FavouritesAdapter(mDummyDataSetFavourites);
        mRecyclerViewFavourites.setAdapter(mAdapter);

        //TODO:implement search functionality
        ClickEvent searchClick = new ClickEvent((view) ->{
           Intent intent = new Intent(this,RouteMasterActivity.class);
           startActivity(intent);
        });

        Button searchButton = (Button) findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(searchClick);
    }
}
