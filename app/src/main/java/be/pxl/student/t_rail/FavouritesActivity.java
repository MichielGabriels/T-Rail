package be.pxl.student.t_rail;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import be.pxl.student.t_rail.adapters.FavouritesAdapter;
import be.pxl.student.t_rail.domainClasses.ClickEvent;
import be.pxl.student.t_rail.domainClasses.Favourite;
import be.pxl.student.t_rail.domainClasses.StationCollection;

public class FavouritesActivity extends AppCompatActivity {

    private AutoCompleteTextView mTextViewFrom;
    private AutoCompleteTextView mTextViewTo;

    private DatabaseReference mDatabaseFavourites;

    private RecyclerView mRecyclerViewFavourites;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Favourite> mFavouriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        mRecyclerViewFavourites = (RecyclerView) findViewById(R.id.recyclerViewFavourites);
        mFavouriteList = new ArrayList<>();

        mDatabaseFavourites = FirebaseDatabase.getInstance().getReference("favourites"); // .getReference() --> get the reference of the root node of the json tree

        // Initialization of AutoCompleteTextViews
        mTextViewFrom = (AutoCompleteTextView) findViewById(R.id.textViewFrom);
        mTextViewTo = (AutoCompleteTextView) findViewById(R.id.textViewTo);

        ArrayAdapter<String> textViewAdapter = new ArrayAdapter(this, R.layout.autocomplete_stations, StationCollection.getStations());
        mTextViewFrom.setThreshold(1);
        mTextViewTo.setThreshold(1);

        mTextViewFrom.setAdapter(textViewAdapter);
        mTextViewTo.setAdapter(textViewAdapter);

        // Save a favourite route
        ClickEvent btnSaveFavouritesClick = new ClickEvent((view) -> {
            this.addFavourite();
        });

        Button saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(btnSaveFavouritesClick);

        // Delete a favourite route
        ClickEvent btnDeleteFavouritesClick = new ClickEvent((view) -> {
            this.deleteFavourite();
        });

        Button deleteButton = (Button) findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(btnDeleteFavouritesClick);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabaseFavourites.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mFavouriteList.clear();

                for (DataSnapshot favouriteSnapshot : dataSnapshot.getChildren()) {
                    Favourite favourite = favouriteSnapshot.getValue(Favourite.class);
                    mFavouriteList.add(favourite);
                }

                mLayoutManager = new LinearLayoutManager(getParent());
                mRecyclerViewFavourites.setLayoutManager(mLayoutManager);

                mAdapter = new FavouritesAdapter(mFavouriteList);
                mRecyclerViewFavourites.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FavouritesActivity.this, "Oeps! Er is iets misgelopen!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addFavourite() {
        String fromStation = mTextViewFrom.getText().toString().trim();
        String toStation = mTextViewTo.getText().toString().trim();

        if (this.validateStations(fromStation, toStation)) {

            String id = mDatabaseFavourites.push().getKey(); // create a unique string inside favourites, and use .getKey() to get the unique string

            // Generate a new favourite ...
            Favourite favourite = new Favourite(id, fromStation, toStation);

            // ... and store it under the newly created id
            mDatabaseFavourites.child(id).setValue(favourite);

            Toast.makeText(this, "Favoriet toegevoegd!", Toast.LENGTH_LONG).show();

            mTextViewFrom.setText("");
            mTextViewTo.setText("");
        } else {
            Toast.makeText(this, "Geen of ongeldige stations!", Toast.LENGTH_LONG).show();
        }
    }

    private void deleteFavourite() {
        String fromStation = mTextViewFrom.getText().toString().trim();
        String toStation = mTextViewTo.getText().toString().trim();

        if (this.validateStations(fromStation, toStation)) {

            mDatabaseFavourites.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    mFavouriteList.clear();

                    for (DataSnapshot favouriteSnapshot : dataSnapshot.getChildren()) {
                        Favourite favourite = favouriteSnapshot.getValue(Favourite.class);
                        if (favourite.getFromStation().equals(fromStation) && favourite.getToStation().equals(toStation)) {

                            favouriteSnapshot.getRef().setValue(null);

                            Toast.makeText(FavouritesActivity.this, "Favoriet verwijderd!", Toast.LENGTH_LONG).show();

                            mTextViewFrom.setText("");
                            mTextViewTo.setText("");

                            return;
                        }
                    }

                    Toast.makeText(FavouritesActivity.this, "Favoriet niet gevonden!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(FavouritesActivity.this, "Oeps! Er is iets misgelopen!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(this, "Geen of ongeldige stations!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateStations(String fromStation, String toStation) {
        if (!TextUtils.isEmpty(fromStation) && !TextUtils.isEmpty(toStation)) {
            ArrayList<String> stations = StationCollection.getStations();

            if (stations.contains(fromStation) || stations.contains(toStation)) {
                return true;
            }
        }

        return false;
    }
}
