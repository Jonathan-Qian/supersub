package com.example.supersub.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supersub.R;
import com.example.supersub.models.position.PositionGroup;

// RecyclerView adapter that displays position chips.
public class PositionChipAdapter extends RecyclerView.Adapter<PositionChipAdapter.ViewHolder> {
    // This PositionGroup contains all the positions that will be displayed in the RecyclerView this adapter is used in.
    private PositionGroup positions;
    // An custom listener that is intended to be passed in from whatever fragment that uses this adapter and will be relayed to all the ViewHolder(s) in this adapter (See comments at the OnChipListener interface below).
    private OnChipListener onChipListener;
    public PositionChipAdapter(PositionGroup positions, OnChipListener onChipListener) {
        this.positions = positions;
        this.onChipListener = onChipListener;
    }
    // This constructor allows this adapter to not have an OnChipListener so that the fragment class that this adapter will be used in does not have to implement OnChipListener.
    public PositionChipAdapter(PositionGroup positions) {
        this(positions, null);
    }

    // Getters.
    public PositionGroup getPositions() {
        return positions;
    }

    /*
     * The ViewHolder is the container for an item in the RecyclerView similar to how fragments are containers for a page.
     * It implements View.OnClickListener so that it can set its onClickListener to itself.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvPosition;
        private OnChipListener onChipListener;

        public ViewHolder(@NonNull View itemView, OnChipListener onChipListener) {
            super(itemView);

            // Initialize TextView and OnChipListener.
            tvPosition = itemView.findViewById(R.id.tv_position_chip_position);
            this.onChipListener = onChipListener;

            itemView.setOnClickListener(this);
        }

        // When this ViewHolder is clicked, call the implementation of onChipClick() from the OnChipListener that is passed into this ViewHolder.
        @Override
        public void onClick(View view) {
            onChipListener.onChipClick(getAdapterPosition());
        }
    }

    // This interface along with its set up in this class and the ViewHolder class allows a fragment to define custom behaviour for when an item in the RecyclerView attached to this adapter is clicked.
    public interface OnChipListener {
        void onChipClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate match event card and pass it into a new ViewHolder along with onChipListener (see comments at the declaration of onChipListener above).
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chip_position, parent, false);
        PositionChipAdapter.ViewHolder viewHolder = new PositionChipAdapter.ViewHolder(view, onChipListener);
        return viewHolder;
    }

    // This is where the content of each view in each ViewHolder in the RecyclerView is defined.
    @Override
    public void onBindViewHolder(@NonNull PositionChipAdapter.ViewHolder holder, int position) {
        // Set the position chip text to display the symbol of its corresponding Position.
        holder.tvPosition.setText(positions.getPositionSymbol(position));
    }

    // Return the length of positions.
    @Override
    public int getItemCount() {
        return positions.length();
    }

    // Add a position from this adapter's positions list and inform the adapter of the change.
    public void addPosition(int id) {
        positions.add(id);
        notifyDataSetChanged();
    }

    // Remove a position from this adapter's positions list and inform the adapter of the change.
    public void removePosition(int index) {
        positions.remove(index);
        notifyDataSetChanged();
    }
}
