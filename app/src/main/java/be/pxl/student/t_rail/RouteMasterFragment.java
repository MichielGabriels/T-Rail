package be.pxl.student.t_rail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import be.pxl.student.t_rail.adapters.RouteMasterAdapter;
import be.pxl.student.t_rail.dialogs.OptionsDialog;
import be.pxl.student.t_rail.domainClasses.RouteCollection;
import be.pxl.student.t_rail.domainClasses.RouteNotFoundException;
import be.pxl.student.t_rail.events.ClickEvent;
import be.pxl.student.t_rail.dialogs.ConnectionAlertDialog;
import be.pxl.student.t_rail.domainClasses.Route;
import be.pxl.student.t_rail.events.DialogClickEvent;
import be.pxl.student.t_rail.events.LongClickEvent;
import be.pxl.student.t_rail.services.ConnectionService;
import be.pxl.student.t_rail.services.NotificationService;

public class RouteMasterFragment extends Fragment {

    private RouteCollection mRoutes;
    private RouteMasterDetailActivity parentActivity;

    public RouteMasterFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle dataBundle = getArguments();
        View view = inflater.inflate(R.layout.fragment_route_master, container, false);
        parentActivity = (RouteMasterDetailActivity) getActivity();

        //orientation changed
        if(savedInstanceState != null){
            mRoutes = (RouteCollection) savedInstanceState.getSerializable("routes");
        }

        //first time fragment created
        else if(dataBundle != null){
            mRoutes = (RouteCollection) dataBundle.getSerializable("routes");
        }

        if(mRoutes != null){
          initializeData(view,mRoutes);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("routes",mRoutes);
    }


    //TODO: add notification service to dialogClickEvent
    private void initializeData(View view,RouteCollection routeCollection){
        LongClickEvent itemLongClick = new LongClickEvent((v) -> {
            DialogClickEvent dialogClickEvent = null;
            try{
               Route selectedRoute = getRouteFromClickedView(v);
                dialogClickEvent = new DialogClickEvent((dialog,which) ->{
                    Intent intent = new Intent(getContext(),NotificationService.class);
                    intent.putExtra("route",selectedRoute);
                    getActivity().startService(intent);
                    Toast.makeText(getContext(),"Route wordt gevolgd",Toast.LENGTH_SHORT).show();
                });
            }

            catch (RouteNotFoundException ex){
                System.out.println(ex.getMessage());
            }


            String[] dialogValues = new String[]{"Volg route"};
            if(dialogClickEvent != null){
                OptionsDialog dialog = new OptionsDialog(getActivity(),dialogValues,dialogClickEvent);
                dialog.show();
            }
        });
        ClickEvent itemClick = new ClickEvent((v) ->{
            search(v);
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewRouteMaster);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RouteMasterAdapter adapter = new RouteMasterAdapter(routeCollection.getRoutes(),itemClick,itemLongClick);
        recyclerView.setAdapter(adapter);
    }

    private void search(View v){
        DialogInterface.OnClickListener negativeEvent = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                search(v);
            }
        };
        DialogInterface.OnClickListener positiveEvent = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                parentActivity.finishAffinity();
            }
        };

        if(ConnectionService.hasActiveInternetConnection(parentActivity)){
            try{
                performSearch(v);
            }

            catch (Exception ex){
                System.out.println(ex.getMessage());
            }

        }

        else{
            new ConnectionAlertDialog(parentActivity,positiveEvent,negativeEvent);
        }
    }

    private void performSearch(View v) throws RouteNotFoundException{
        Route selectedRoute = getRouteFromClickedView(v);
        parentActivity.initializeDetailFragment(selectedRoute,getResources().getConfiguration().orientation);
    }

    private Route getRouteFromClickedView(View view) throws RouteNotFoundException,NullPointerException {
        TextView departureTime = (TextView) view.findViewById(R.id.routeListTimeStation1);

        if(mRoutes != null){
           Route selectedRoute = mRoutes.getRouteByDepartureTime(departureTime.getText().toString());
           return selectedRoute;
        }

        else{
            throw new NullPointerException("mRoutes is null");
        }
    }

}
