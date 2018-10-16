package be.pxl.student.t_rail.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import be.pxl.student.t_rail.R;
import be.pxl.student.t_rail.domainClasses.Route;

public class RouteMasterAdapter extends RecyclerView.Adapter<RouteMasterAdapter.RouteMasterViewHolder> {

    public List<Route> mRoutes;

    public static class RouteMasterViewHolder extends RecyclerView.ViewHolder{

        public TextView txtStationFrom;
        public TextView txtStationTo;
        public TextView txtTimeDeparture;
        public TextView txtTimeArrival;
        public TextView txtDelayDeparture;
        public TextView txtDelayArrival;

        public RouteMasterViewHolder(View view){
            super(view);
            txtStationFrom = (TextView) view.findViewById(R.id.routeListStation1);
            txtStationTo = (TextView) view.findViewById(R.id.routeListStation2);
            txtTimeDeparture = (TextView) view.findViewById(R.id.routeListTimeStation2);
            txtTimeArrival = (TextView) view.findViewById(R.id.routeListTimeStation1);
            txtDelayDeparture = (TextView) view.findViewById(R.id.routeListPlatformStation1);
            txtDelayArrival = (TextView) view.findViewById(R.id.routeListDelayArrival);
        }

    }

    public RouteMasterAdapter(List<Route> routes){
        mRoutes = routes;
    }


    @Override
    public RouteMasterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.routes_list_item,viewGroup,false);
        return new RouteMasterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RouteMasterViewHolder routeMasterViewHolder, int selectedIndex) {
        Route route  = mRoutes.get(selectedIndex);
        routeMasterViewHolder.txtStationFrom.setText(route.getStationDeparture());
        routeMasterViewHolder.txtStationTo.setText(route.getStationArrival());
        routeMasterViewHolder.txtTimeArrival.setText(route.getTimeArrival());
        routeMasterViewHolder.txtTimeDeparture.setText(route.getTimeDeparture());
        routeMasterViewHolder.txtDelayDeparture.setText(route.getDelayDeparture());
        routeMasterViewHolder.txtDelayArrival.setText(route.getDelayArrival());
    }

    @Override
    public int getItemCount() {
        return mRoutes.size();
    }
}
