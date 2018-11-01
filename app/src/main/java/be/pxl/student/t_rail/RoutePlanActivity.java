package be.pxl.student.t_rail;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import be.pxl.student.t_rail.adapters.FavouritesAdapter;
import be.pxl.student.t_rail.domainClasses.ClickEvent;
import be.pxl.student.t_rail.domainClasses.ConnectionAlertDialog;
import be.pxl.student.t_rail.services.ConnectionService;
import be.pxl.student.t_rail.domainClasses.StationCollection;
import be.pxl.student.t_rail.tasks.RoutePlannerHttpTask;

public class RoutePlanActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewFavourites;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<String> mDummyDataSetFavourites;

    private AutoCompleteTextView textViewDepartureStation;
    private AutoCompleteTextView textViewArrivalStation;

    private EditText mEditTextTime;
    private EditText mEditTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);

        //init test data
        mDummyDataSetFavourites = new ArrayList<>();
        initTestComponents(mDummyDataSetFavourites);

        initViewComponents();
    }

    private boolean checkInputFields(){
        if(textViewDepartureStation.getText().toString().trim().equals("") && textViewArrivalStation.getText().toString().trim().equals("")){
            return false;
        }

        ArrayList<String> stations = StationCollection.getStations();

        if (!stations.contains(textViewDepartureStation.getText().toString()) || !stations.contains(textViewArrivalStation.getText().toString())) {
            return false;
        }

        return true;
    }

    private void initTestComponents(List<String> dummyFavorites){
        dummyFavorites.add("Hasselt --> Aarschot");
        dummyFavorites.add("Aarschot --> Hasselt");
        dummyFavorites.add("Hasselt --> Kiewit");
        dummyFavorites.add("Kiewit --> Hasselt");
        dummyFavorites.add("Hasselt --> Aarschot");
        dummyFavorites.add("Aarschot --> Hasselt");
        dummyFavorites.add("Hasselt --> Kiewit");
        dummyFavorites.add("Kiewit --> Hasselt");
        dummyFavorites.add("Hasselt --> Aarschot");
        dummyFavorites.add("Aarschot --> Hasselt");
        dummyFavorites.add("Hasselt --> Kiewit");
        dummyFavorites.add("Kiewit --> Hasselt");
    }

    private String formatTime(String time){
        SimpleDateFormat formatterStringToDate = new SimpleDateFormat("HH:mm");
        formatterStringToDate.setLenient(false);

        Date formattedTime = null;
        try {
            formattedTime = formatterStringToDate.parse(time);
        } catch (ParseException ex) {
            Toast.makeText(this, "Ongeldig tijdstip!", Toast.LENGTH_LONG).show();
            return "";
        }

        Format formatter = new SimpleDateFormat("HHmm");

        return formatter.format(formattedTime);
    }

    private String formatDate(String date){
        SimpleDateFormat formatterStringToDate = new SimpleDateFormat("dd/MM/yy");
        formatterStringToDate.setLenient(false);

        Date formattedDate = null;
        try {
            formattedDate = formatterStringToDate.parse(date);
        } catch (ParseException ex) {
            Toast.makeText(this, "Ongeldige datum!", Toast.LENGTH_LONG).show();
            return "";
        }

        Format formatter = new SimpleDateFormat("ddMMyy");

        return formatter.format(formattedDate);
    }

    private void performSearch(){
        DialogInterface.OnClickListener negativeEvent = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                performSearch();
            }
        };
        DialogInterface.OnClickListener positiveEvent = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RoutePlanActivity.this.finishAffinity();
            }
        };

        if(ConnectionService.hasActiveInternetConnection(RoutePlanActivity.this)){
            searchRoute();
        }

        else{
            new ConnectionAlertDialog(RoutePlanActivity.this,positiveEvent,negativeEvent);
        }

    }

    private void searchRoute(){
        if(checkInputFields()) {
            String time = formatTime(mEditTextTime.getText().toString());
            if (time.equals("")) {
                return;
            }
            String date = formatDate(mEditTextDate.getText().toString());
            if (date.equals("")) {
                return;
            }
            RoutePlannerHttpTask task = new RoutePlannerHttpTask(RoutePlanActivity.this, true, RouteMasterDetailActivity.class);
            String url = String.format("connections/?from=%s&to=%s&format=json&lang=nl&time=%s&date=%s", textViewDepartureStation.getText(), textViewArrivalStation.getText(), time, date);
            task.execute(url,date);
        } else {
            Toast.makeText(this,"Geen of ongeldig(e) station(s)!",Toast.LENGTH_LONG).show();
        }
    }

    private void initViewComponents(){
        mRecyclerViewFavourites = (RecyclerView) findViewById(R.id.recyclerViewFavourites);

        //init textviews
        textViewDepartureStation = (AutoCompleteTextView) findViewById(R.id.textViewFrom);
        textViewArrivalStation = (AutoCompleteTextView) findViewById(R.id.textViewTo);
        AutoCompleteTextView textViewFrom = (AutoCompleteTextView) findViewById(R.id.textViewFrom);
        AutoCompleteTextView textViewTo = (AutoCompleteTextView) findViewById(R.id.textViewTo);
        mEditTextTime = (EditText) findViewById(R.id.editTextTime);
        mEditTextDate = (EditText) findViewById(R.id.editTextDate);

        //init adapters for suggestions
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
        ClickEvent searchClick = new ClickEvent((view) ->{
            performSearch();
        });

        Button searchButton = (Button) findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(searchClick);
    }
}
