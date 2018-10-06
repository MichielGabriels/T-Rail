package be.pxl.student.t_rail.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import be.pxl.student.t_rail.R;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder> {

    private List<String> mDummyDatasetFavourites;

    // Provide a reference to the view for each data item
    public static class FavouritesViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public FavouritesViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.textViewListItem);
        }
    }

    public FavouritesAdapter(List<String> dummyDatasetFavourites) {
        mDummyDatasetFavourites = dummyDatasetFavourites;
    }

    // Create new views
    @Override
    public FavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourites_list_row, parent, false);

        return new FavouritesViewHolder(view);
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(FavouritesViewHolder favouritesViewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        favouritesViewHolder.mTextView.setText(mDummyDatasetFavourites.get(position));
    }

    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return mDummyDatasetFavourites.size();
    }
}
