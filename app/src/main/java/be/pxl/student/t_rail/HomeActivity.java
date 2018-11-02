package be.pxl.student.t_rail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import be.pxl.student.t_rail.events.ClickEvent;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ClickEvent btnPlanRouteClick =  new ClickEvent((view) -> {
            Intent intent = new Intent(this, RoutePlanActivity.class);
            startActivity(intent);
        });

        ClickEvent btnFavouritesClick = new ClickEvent((view) -> {
            Intent intent = new Intent(this, FavouritesActivity.class);
            startActivity(intent);
        });

        Button routeButton = (Button) findViewById(R.id.buttonRoute);
        routeButton.setOnClickListener(btnPlanRouteClick);

        Button favouritesButton = (Button) findViewById(R.id.buttonFavourites);
        favouritesButton.setOnClickListener(btnFavouritesClick);
    }
}
