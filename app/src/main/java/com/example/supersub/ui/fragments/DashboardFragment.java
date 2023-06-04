package com.example.supersub.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supersub.R;
import com.example.supersub.models.Player;
import com.example.supersub.ui.DrawerLocker;
import com.example.supersub.ui.adapters.TeamCardAdapter;
import com.example.supersub.ui.VerticalSpaceItemDecoration;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_dashboard_team_card_players);

        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("John", "Smith", 1));
        players.add(new Player("John", "Doe", 2));
        players.add(new Player("Ryan", "Anderson", 3));
        players.add(new Player("Jayden", "Rye", 4));
        players.add(new Player("Joshua", "Few", 5));
        players.add(new Player("Caleb", "Richards", 6));
        players.add(new Player("John", "Smith", 1));
        players.add(new Player("John", "Doe", 2));
        players.add(new Player("Ryan", "Anderson", 3));
        players.add(new Player("Jayden", "Rye", 4));
        players.add(new Player("Joshua", "Few", 5));
        players.add(new Player("Caleb", "Richards", 6));
        players.add(new Player("John", "Smith", 1));
        players.add(new Player("John", "Doe", 2));
        players.add(new Player("Ryan", "Anderson", 3));
        players.add(new Player("Jayden", "Rye", 4));
        players.add(new Player("Joshua", "Few", 5));
        players.add(new Player("Caleb", "Richards", 6));
        TeamCardAdapter adapter = new TeamCardAdapter(players);
        //TODO: If players.size() == 0, show a TextView saying "No Players" instead of the RecyclerView

        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(16));
    }
}
