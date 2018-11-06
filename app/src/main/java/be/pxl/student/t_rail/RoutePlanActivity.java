package be.pxl.student.t_rail;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import be.pxl.student.t_rail.adapters.FavouritesAdapter;
import be.pxl.student.t_rail.events.ClickEvent;
import be.pxl.student.t_rail.dialogs.ConnectionAlertDialog;
import be.pxl.student.t_rail.helpers.DatabaseHelper;
import be.pxl.student.t_rail.services.ConnectionService;
import be.pxl.student.t_rail.domainClasses.StationCollection;
import be.pxl.student.t_rail.services.TimeService;
import be.pxl.student.t_rail.tasks.RoutePlannerHttpTask;

public class RoutePlanActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewFavourites;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private AutoCompleteTextView textViewFrom;
    private AutoCompleteTextView textViewTo;

    private EditText mEditTextTime;
    private EditText mEditTextDate;

    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);
        mDatabaseHelper = new DatabaseHelper(this);
        initViewComponents();
    }

    private boolean checkInputFields(){
        if(textViewFrom.getText().toString().trim().equals("") && textViewTo.getText().toString().trim().equals("")){
            return false;
        }

        ArrayList<String> stations = StationCollection.getStations(true);



        if (!stations.contains(textViewFrom.getText().toString().toLowerCase()) || !stations.contains(textViewTo.getText().toString().toLowerCase())) {
            return false;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.populateFavouritesRecyclerView();
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
            String url = String.format("connections/?from=%s&to=%s&format=json&lang=nl&time=%s&date=%s", textViewFrom.getText(), textViewTo.getText(), time, date);
            task.execute(url,date);
        } else {
            Toast.makeText(this,"Geen of ongeldig(e) station(s)!",Toast.LENGTH_LONG).show();
        }
    }

    private void insertFavourites(View view) {
        TextView textViewListItem = (TextView) findViewById(R.id.textViewListItem);

        String[] favouriteRouteStations = textViewListItem.getText().toString().trim().split("-->");

        textViewFrom.setText(favouriteRouteStations[0]);
        textViewTo.setText(favouriteRouteStations[1]);
    }

    private void initViewComponents(){
        mRecyclerViewFavourites = (RecyclerView) findViewById(R.id.recyclerViewFavourites);

        //init textviews
        textViewFrom = (AutoCompleteTextView) findViewById(R.id.textViewFrom);
        textViewTo = (AutoCompleteTextView) findViewById(R.id.textViewTo);
        AutoCompleteTextView textViewFrom = (AutoCompleteTextView) findViewById(R.id.textViewFrom);
        AutoCompleteTextView textViewTo = (AutoCompleteTextView) findViewById(R.id.textViewTo);
        mEditTextTime = (EditText) findViewById(R.id.editTextTime);
        mEditTextDate = (EditText) findViewById(R.id.editTextDate);

        //init adapters for suggestions
        ArrayAdapter<String> textViewAdapter = new ArrayAdapter(this,R.layout.autocomplete_stations,StationCollection.getStations(false));
        textViewFrom.setThreshold(1);
        textViewTo.setThreshold(1);

        textViewFrom.setAdapter(textViewAdapter);
        textViewTo.setAdapter(textViewAdapter);

        //TODO: implement dateTime picker
        //TODO: remove current system date and time after dateTime picker is implemented
        ClickEvent searchClick = new ClickEvent((view) ->{
            performSearch();
        });
        ClickEvent timeAndDateNowClick = new ClickEvent((view)->{
            String timeNow = TimeService.getCurrentSystemTime(new SimpleDateFormat("HH:mm"));
            String dateNow = TimeService.getCurrentSystemDate(new SimpleDateFormat("dd/MM/yy"));
            mEditTextDate.setText(dateNow);
            mEditTextTime.setText(timeNow);
        });

        Button searchButton = (Button) findViewById(R.id.buttonSearch);
        Button dateTimeNowButton = (Button) findViewById(R.id.buttonDateTimeNow);

        searchButton.setOnClickListener(searchClick);
        dateTimeNowButton.setOnClickListener(timeAndDateNowClick);
    }

    private void populateFavouritesRecyclerView() {
        Cursor data = mDatabaseHelper.getData();
        List<String> favouriteList = new ArrayList<>();
        while (data.moveToNext()) {
            favouriteList.add(data.getString(1) + " --> " + data.getString(2));
        }

        mLayoutManager = new LinearLayoutManager(getParent());
        mRecyclerViewFavourites.setLayoutManager(mLayoutManager);

        ClickEvent itemClick = new ClickEvent((view) ->{
            // Do nothing
        });

        mAdapter = new FavouritesAdapter(favouriteList, itemClick);
        mRecyclerViewFavourites.setAdapter(mAdapter);
    }
}
