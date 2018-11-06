package be.pxl.student.t_rail.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import be.pxl.student.t_rail.R;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder> {

    private List<String> mFavouritesList;

    private View.OnClickListener mOnClickListener;

    // Provide a reference to the view for each data item
    public static class FavouritesViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public FavouritesViewHolder(View view, View.OnClickListener onClickListener) {
            super(view);
            mTextView = view.findViewById(R.id.textViewListItem);
            view.setOnClickListener(onClickListener);
        }
    }

    public FavouritesAdapter(List<String> favourites, View.OnClickListener clickEvent) {
        mFavouritesList = favourites;
        mOnClickListener = clickEvent;
    }

    // Create new views
    @Override
    public FavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourites_list_row, parent, false);

        return new FavouritesViewHolder(view, mOnClickListener);
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(FavouritesViewHolder favouritesViewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        favouritesViewHolder.mTextView.setText(mFavouritesList.get(position));
    }

    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return mFavouritesList.size();
    }
}
