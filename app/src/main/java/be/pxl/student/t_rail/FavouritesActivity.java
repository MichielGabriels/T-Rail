package be.pxl.student.t_rail;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.pxl.student.t_rail.adapters.FavouritesAdapter;
import be.pxl.student.t_rail.events.ClickEvent;
import be.pxl.student.t_rail.domainClasses.StationCollection;
import be.pxl.student.t_rail.helpers.DatabaseHelper;

public class FavouritesActivity extends AppCompatActivity {

    private AutoCompleteTextView mTextViewFrom;
    private AutoCompleteTextView mTextViewTo;

    private RecyclerView mRecyclerViewFavourites;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<String> mFavouriteList;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        mDatabaseHelper = new DatabaseHelper(this);
        mFavouriteList = new ArrayList<>();

        mRecyclerViewFavourites = (RecyclerView) findViewById(R.id.recyclerViewFavourites);

        // Initialization of AutoCompleteTextViews
        mTextViewFrom = (AutoCompleteTextView) findViewById(R.id.textViewFrom);
        mTextViewTo = (AutoCompleteTextView) findViewById(R.id.textViewTo);

        ArrayAdapter<String> textViewAdapter = new ArrayAdapter(this, R.layout.autocomplete_stations, StationCollection.getStations(false));
        mTextViewFrom.setThreshold(1);
        mTextViewTo.setThreshold(1);

        mTextViewFrom.setAdapter(textViewAdapter);
        mTextViewTo.setAdapter(textViewAdapter);

        // Save a favourite route
        ClickEvent btnSaveFavouritesClick = new ClickEvent((view) -> {
            String stationFrom = mTextViewFrom.getText().toString();
            String stationTo = mTextViewTo.getText().toString();

            if (this.validateStations(stationFrom, stationTo)) {
                this.addFavourite(stationFrom, stationTo);
            } else {
                Toast.makeText(this, "Geen of ongeldige stations!", Toast.LENGTH_LONG).show();
            }
        });

        Button saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(btnSaveFavouritesClick);

        // Delete a favourite route
        ClickEvent btnDeleteFavouritesClick = new ClickEvent((view) -> {
            String stationFrom = mTextViewFrom.getText().toString();
            String stationTo = mTextViewTo.getText().toString();

            for (String favourite : mFavouriteList) {
                if (favourite.contains(stationFrom + " --> " + stationTo)) {
                    mDatabaseHelper.deleteData(stationFrom, stationTo);
                    this.populateFavouritesRecyclerView();
                    Toast.makeText(this, "Favoriet verwijderd!", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            Toast.makeText(this, "Favoriet niet gevonden!", Toast.LENGTH_LONG).show();
        });

        Button deleteButton = (Button) findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(btnDeleteFavouritesClick);

        this.populateFavouritesRecyclerView();
    }

    private void addFavourite(String fromStation, String toStation) {
        boolean insertData = mDatabaseHelper.addData(fromStation, toStation);

        if (insertData) {
            Toast.makeText(this, "Favoriet toegevoegd!", Toast.LENGTH_LONG).show();
            this.populateFavouritesRecyclerView();
        } else {
            Toast.makeText(this, "Er is iets misgelopen!", Toast.LENGTH_LONG).show();
        }
    }

    private void populateFavouritesRecyclerView() {
        Cursor data = mDatabaseHelper.getData();
        mFavouriteList = new ArrayList<>();
        while (data.moveToNext()) {
            mFavouriteList.add(data.getString(1) + " --> " + data.getString(2));
        }

        mLayoutManager = new LinearLayoutManager(getParent());
        mRecyclerViewFavourites.setLayoutManager(mLayoutManager);

        ClickEvent itemClick = new ClickEvent((view) ->{
            // Do nothing
        });

        mAdapter = new FavouritesAdapter(mFavouriteList, itemClick);
        mRecyclerViewFavourites.setAdapter(mAdapter);
    }

    private boolean validateStations(String fromStation, String toStation) {
        if (!TextUtils.isEmpty(fromStation) && !TextUtils.isEmpty(toStation)) {
            ArrayList<String> stations = StationCollection.getStations(true);

            if (stations.contains(fromStation.toLowerCase()) || stations.contains(toStation.toLowerCase())) {
                return true;
            }
        }

        return false;
    }
}
