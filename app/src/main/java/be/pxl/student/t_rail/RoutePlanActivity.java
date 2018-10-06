package be.pxl.student.t_rail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.pxl.student.t_rail.adapters.FavouritesAdapter;

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

        mDummyDataSetFavourites = new ArrayList<>();
        mDummyDataSetFavourites.add("Hasselt --> Aarschot");
        mDummyDataSetFavourites.add("Aarschot --> Hasselt");
        mDummyDataSetFavourites.add("Hasselt --> Kiewit");
        mDummyDataSetFavourites.add("Kiewit --> Hasselt");

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewFavourites.setLayoutManager(mLayoutManager);

        mAdapter = new FavouritesAdapter(mDummyDataSetFavourites);
        mRecyclerViewFavourites.setAdapter(mAdapter);
    }
}
