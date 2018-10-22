package be.pxl.student.t_rail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import be.pxl.student.t_rail.adapters.FavouritesAdapter;
import be.pxl.student.t_rail.domainClasses.ClickEvent;
import be.pxl.student.t_rail.domainClasses.StationCollection;
import be.pxl.student.t_rail.tasks.RoutePlannerHttpTask;

public class RoutePlanActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewFavourites;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<String> mDummyDataSetFavourites;

    private AutoCompleteTextView textViewDepartureStation;
    private AutoCompleteTextView textViewArrivalStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);

        textViewDepartureStation = (AutoCompleteTextView) findViewById(R.id.textViewFrom);
        textViewArrivalStation = (AutoCompleteTextView) findViewById(R.id.textViewTo);

        mRecyclerViewFavourites = (RecyclerView) findViewById(R.id.recyclerViewFavourites);

        //init test data
        ArrayList<String> stations = new ArrayList<String>();
        mDummyDataSetFavourites = new ArrayList<>();
        initTestComponents(stations,mDummyDataSetFavourites);

        //init textviews
        AutoCompleteTextView textViewFrom = (AutoCompleteTextView) findViewById(R.id.textViewFrom);
        AutoCompleteTextView textViewTo = (AutoCompleteTextView) findViewById(R.id.textViewTo);

       // ArrayAdapter<String> textViewAdapter = new ArrayAdapter(this,R.layout.autocomplete_stations,stations));
        ArrayAdapter<String> textViewAdapter = new ArrayAdapter(this,R.layout.autocomplete_stations,StationCollection.getStations());
        textViewFrom.setThreshold(1);
        textViewTo.setThreshold(1);

        textViewFrom.setAdapter(textViewAdapter);
        textViewTo.setAdapter(textViewAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewFavourites.setLayoutManager(mLayoutManager);

        mAdapter = new FavouritesAdapter(mDummyDataSetFavourites);
        mRecyclerViewFavourites.setAdapter(mAdapter);


        //TODO: implement dateTime picker
        //TODO: remove current system date and time after dateTime picker is implemented
        //TODO: implement proper check for input fields
        ClickEvent searchClick = new ClickEvent((view) ->{
           if(checkInputFields()){
                String currentTime = getCurrentSystemTime();
                String currentDate = getCurrentSystemDate();
               RoutePlannerHttpTask task = new RoutePlannerHttpTask(RoutePlanActivity.this,"Routes ophalen",true,RouteMasterDetailActivity.class);
               String url = String.format("connections/?from=%s&to=%s&format=json&lang=nl&time=%s&date=%s",textViewDepartureStation.getText(),textViewArrivalStation.getText(),currentTime,currentDate);
               task.execute(url);
           }

           else{
               Toast.makeText(this,"De stations zijn ongeldig",Toast.LENGTH_LONG).show();
           }
        });

        Button searchButton = (Button) findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(searchClick);
    }

    //TODO: add conditions for invalid input
    private boolean checkInputFields(){
        if(!textViewDepartureStation.getText().equals("") && !textViewArrivalStation.getText().equals("")){
            return true;
        }
        return false;
    }

    private void initTestComponents(ArrayList<String> stationsArray,List<String> dummyFavorites){
        dummyFavorites.add("Hasselt --> Aarschot");
        dummyFavorites.add("Aarschot --> Hasselt");
        dummyFavorites.add("Hasselt --> Kiewit");
        dummyFavorites.add("Kiewit --> Hasselt");

        stationsArray.add("Antwerpen");
        stationsArray.add("Leuven");
        stationsArray.add("Aarschot");
        stationsArray.add("Brussel");
        stationsArray.add("Hasselt");
    }

    private String getCurrentSystemTime(){
        Date time = Calendar.getInstance().getTime();
        Format formatter = new SimpleDateFormat("HHmm");
        return formatter.format(time);
    }

    private String getCurrentSystemDate(){
        Date date = Calendar.getInstance().getTime();
        Format formatter = new SimpleDateFormat("ddMMyy");
        return formatter.format(date);
    }
}
