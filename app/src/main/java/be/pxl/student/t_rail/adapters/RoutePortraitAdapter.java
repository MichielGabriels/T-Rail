package be.pxl.student.t_rail.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.pxl.student.t_rail.R;
import be.pxl.student.t_rail.domainClasses.Route;

public class RoutePortraitAdapter extends RecyclerView.Adapter<RoutePortraitAdapter.RoutePortraitViewHolder> {

    public List<Route> mRoutes;

    public static class RoutePortraitViewHolder extends RecyclerView.ViewHolder{

        public TextView txtStationFrom;
        public TextView txtStationTo;
        public TextView txtTimeDeparture;
        public TextView txtTimeArrival;
        public TextView txtDelayDeparture;
        public TextView txtDelayArrival;

        public RoutePortraitViewHolder(View view){
            super(view);
            txtStationFrom = (TextView) view.findViewById(R.id.textViewFrom);
            txtStationTo = (TextView) view.findViewById(R.id.textViewTo);
            txtTimeDeparture = (TextView) view.findViewById(R.id.routeListTimeDeparture);
            txtTimeArrival = (TextView) view.findViewById(R.id.routeListTimeArrival);
            txtDelayDeparture = (TextView) view.findViewById(R.id.routeListDelayDeparture);
            txtDelayArrival = (TextView) view.findViewById(R.id.routeListDelayArrival);
        }

    }

    public RoutePortraitAdapter(List<Route> routes){
        mRoutes = routes;
    }

    @NonNull
    @Override
    public RoutePortraitViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.routes_list_item,viewGroup,false);
        return new RoutePortraitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutePortraitViewHolder routePortraitViewHolder, int selectedIndex) {
        Route route  = mRoutes.get(selectedIndex);
        routePortraitViewHolder.txtStationFrom.setText(route.getStationDeparture());
        routePortraitViewHolder.txtStationTo.setText(route.getStationArrival());
        routePortraitViewHolder.txtTimeArrival.setText(route.getTimeArrival());
        routePortraitViewHolder.txtTimeDeparture.setText(route.getTimeDeparture());
        routePortraitViewHolder.txtDelayDeparture.setText(route.getDelayDeparture());
        routePortraitViewHolder.txtDelayArrival.setText(route.getDelayArrival());
    }

    @Override
    public int getItemCount() {
        return mRoutes.size();
    }
}
