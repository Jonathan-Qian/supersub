package com.example.supersub.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supersub.R;
import com.example.supersub.models.position.PositionGroup;

public class PositionChipAdapter extends RecyclerView.Adapter<PositionChipAdapter.ViewHolder> {
    private PositionGroup positions;
    private OnChipListener onChipListener;

    public PositionChipAdapter(PositionGroup positions, OnChipListener onChipListener) {
        this.positions = positions;
        this.onChipListener = onChipListener;
    }
    public PositionChipAdapter(PositionGroup positions) {
        this(positions, null);
    }

    public PositionGroup getPositions() {
        return positions;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvPosition;
        private OnChipListener onChipListener;

        public ViewHolder(@NonNull View itemView, OnChipListener onChipListener) {
            super(itemView);
            tvPosition = itemView.findViewById(R.id.tv_position_chip_position);
            this.onChipListener = onChipListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onChipListener.onChipClick(getAdapterPosition());
        }
    }

    public interface OnChipListener {
        void onChipClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chip_position, parent, false);
        PositionChipAdapter.ViewHolder viewHolder = new PositionChipAdapter.ViewHolder(view, onChipListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PositionChipAdapter.ViewHolder holder, int position) {
        holder.tvPosition.setText(positions.getPositionSymbol(position));
    }

    @Override
    public int getItemCount() {
        return positions.length();
    }

    public void addPosition(int id) {
        positions.add(id);
        notifyDataSetChanged();
    }

    public void removePosition(int index) {
        positions.remove(index);
        notifyDataSetChanged();
    }
}
