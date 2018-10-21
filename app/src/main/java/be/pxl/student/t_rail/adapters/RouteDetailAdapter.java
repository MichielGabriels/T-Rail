package be.pxl.student.t_rail.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import be.pxl.student.t_rail.R;
import be.pxl.student.t_rail.domainClasses.RouteDetail;

public class RouteDetailAdapter extends RecyclerView.Adapter<RouteDetailAdapter.RouteDetailViewHolder> {

    private List<RouteDetail> mRouteDetails;

    public class RouteDetailViewHolder extends RecyclerView.ViewHolder {

        public TextView txtStation1;
        public TextView txtStation2;
        public TextView txtTime1;
        public TextView txtTime2;
        public TextView txtPlatform1;
        public TextView txtPlatform2;

        public RouteDetailViewHolder(View view) {
            super(view);

            txtStation1 = (TextView) view.findViewById(R.id.textViewFrom);
            txtStation2 = (TextView) view.findViewById(R.id.textViewStation2);
            txtTime1 = (TextView) view.findViewById(R.id.routeListTimeStation1);
            txtTime2 = (TextView) view.findViewById(R.id.routeListTimeStation2);
            txtPlatform1 = (TextView) view.findViewById(R.id.routeListPlatformStation1);
            txtPlatform2 = (TextView) view.findViewById(R.id.routeListPlatformStation2);
        }
    }

    public RouteDetailAdapter(List<RouteDetail> routeDetails) {
        mRouteDetails = routeDetails;
    }

    @Override
    public RouteDetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.routes_detail_list_item, viewGroup, false);
        return new RouteDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RouteDetailViewHolder routeDetailViewHolder, int selectedIndex) {
        RouteDetail routeDetail = mRouteDetails.get(selectedIndex);
        routeDetailViewHolder.txtStation1.setText(routeDetail.getStation1());
        routeDetailViewHolder.txtStation2.setText(routeDetail.getStation2());
        routeDetailViewHolder.txtTime1.setText(routeDetail.getTime1());
        routeDetailViewHolder.txtTime2.setText(routeDetail.getTime2());
        routeDetailViewHolder.txtPlatform1.setText(routeDetail.getPlatform1());
        routeDetailViewHolder.txtPlatform2.setText(routeDetail.getPlatform2());
    }

    @Override
    public int getItemCount() {
        return mRouteDetails.size();
    }
}
