package com.example.supersub.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supersub.R;
import com.example.supersub.models.TeamFacade;
import com.example.supersub.ui.DrawerLocker;
import com.example.supersub.ui.adapters.TeamListAdapter;
import com.example.supersub.ui.VerticalSpaceItemDecoration;

import java.util.ArrayList;

public class TeamListFragment extends Fragment implements TeamListAdapter.OnTeamListener {
    private RecyclerView recyclerView;
    private Button buttonNewTeam;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        return inflater.inflate(R.layout.fragment_team_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_team_list);
        buttonNewTeam = view.findViewById(R.id.button_new_team);

        ArrayList<TeamFacade> teamFacades = TeamFacade.readAll(view.getContext());
        //TODO: if teamFacades.size() == 0, display a layout prompting the user to create their first team
        TeamListAdapter adapter = new TeamListAdapter(teamFacades, this);

        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(32));

        buttonNewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        TeamListFragmentDirections.actionTeamListFragmentToCreateTeamFragment()
                );
            }
        });
    }

    @Override
    public void onTeamClick(int position) {
        Navigation.findNavController(getView()).navigate(
                TeamListFragmentDirections.actionTeamListFragmentToDashboardFragment()
        );
    }
}