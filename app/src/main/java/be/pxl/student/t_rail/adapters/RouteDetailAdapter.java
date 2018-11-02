package be.pxl.student.t_rail.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import be.pxl.student.t_rail.R;
import be.pxl.student.t_rail.events.LongClickEvent;
import be.pxl.student.t_rail.domainClasses.RouteDetail;

public class RouteDetailAdapter extends RecyclerView.Adapter<RouteDetailAdapter.RouteDetailViewHolder> {

    private List<RouteDetail> mRouteDetails;
    private LongClickEvent mLongClickEvent;

    public class RouteDetailViewHolder extends RecyclerView.ViewHolder {

        public TextView txtStation;
        public TextView txtTimeArrival;
        public TextView txtTimeDeparture;
        public TextView txtPlatform;
        public TextView txtDelayArrival;
        public TextView txtDelayDeparture;

        //TODO and add time2,delay1,delay2 to adapter
        public RouteDetailViewHolder(View view) {
            super(view);
            txtStation = (TextView) view.findViewById(R.id.textViewFrom);
            txtTimeArrival = (TextView) view.findViewById(R.id.routeDetailTimeArrival);
            txtTimeDeparture = (TextView) view.findViewById(R.id.routeDetailTimeDeparture);
            txtPlatform = (TextView) view.findViewById(R.id.routeDetailPlatform);
            txtDelayArrival = (TextView) view.findViewById(R.id.routeDetailDelayArrival);
            txtDelayDeparture = (TextView) view.findViewById(R.id.routeListDelayDeparture);

            view.setOnLongClickListener(mLongClickEvent);
        }
    }

    public RouteDetailAdapter(List<RouteDetail> routeDetails, LongClickEvent longClickEvent) {
        mRouteDetails = routeDetails;
        mLongClickEvent = longClickEvent;
    }

    @Override
    public RouteDetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.routes_detail_list_item, viewGroup, false);
        return new RouteDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RouteDetailViewHolder routeDetailViewHolder, int selectedIndex) {
        RouteDetail routeDetail = mRouteDetails.get(selectedIndex);
        routeDetailViewHolder.txtStation.setText(routeDetail.getStation1());
        routeDetailViewHolder.txtTimeArrival.setText(routeDetail.getTime1());
        //routeDetailViewHolder.txtTimeDeparture.setText(routeDetail.getTime2());
        routeDetailViewHolder.txtPlatform.setText(routeDetail.getPlatform1());

    }

    @Override
    public int getItemCount() {
        return mRouteDetails.size();
    }
}
