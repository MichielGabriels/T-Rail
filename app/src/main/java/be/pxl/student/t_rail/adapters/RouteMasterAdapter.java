package be.pxl.student.t_rail.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import be.pxl.student.t_rail.R;
import be.pxl.student.t_rail.domainClasses.Route;
import be.pxl.student.t_rail.events.ClickEvent;
import be.pxl.student.t_rail.events.LongClickEvent;

public class RouteMasterAdapter extends RecyclerView.Adapter<RouteMasterAdapter.RouteMasterViewHolder> {

    private List<Route> mRoutes;
    private ClickEvent mClickListener;
    private LongClickEvent mLongClickListener;

    public static class RouteMasterViewHolder extends RecyclerView.ViewHolder{

        public TextView txtStationFrom;
        public TextView txtStationTo;
        public TextView txtTimeDeparture;
        public TextView txtTimeArrival;
        public TextView txtDelayDeparture;
        public TextView txtDelayArrival;

        public RouteMasterViewHolder(View view, View.OnClickListener clickListener, LongClickEvent longClickEvent){
            super(view);
            txtStationFrom = (TextView) view.findViewById(R.id.routeListStation1);
            txtStationTo = (TextView) view.findViewById(R.id.routeListStation2);
            txtTimeDeparture = (TextView) view.findViewById(R.id.routeListTimeStation1);
            txtTimeArrival = (TextView) view.findViewById(R.id.routeListTimeStation2);
            txtDelayDeparture = (TextView) view.findViewById(R.id.routeListDelayDeparture);
            txtDelayArrival = (TextView) view.findViewById(R.id.routeListDelayArrival);
            view.setOnClickListener(clickListener);
            view.setOnLongClickListener(longClickEvent);
        }

    }

    public RouteMasterAdapter(List<Route> routes, ClickEvent clickEvent,LongClickEvent longClickEvent){
        mRoutes = routes;
        mClickListener = clickEvent;
        mLongClickListener = longClickEvent;
    }

    @Override
    public RouteMasterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.routes_master_list_item,viewGroup,false);

        return new RouteMasterViewHolder(view,mClickListener,mLongClickListener);
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
