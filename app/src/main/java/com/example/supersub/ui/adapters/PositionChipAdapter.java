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

    public PositionChipAdapter(PositionGroup positions) {
        this.positions = positions;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPosition = itemView.findViewById(R.id.tv_position_chip_position);
        }
    }

    @NonNull
    @Override
    public PositionChipAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chip_position, parent, false);
        PositionChipAdapter.ViewHolder viewHolder = new PositionChipAdapter.ViewHolder(view);
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
}
