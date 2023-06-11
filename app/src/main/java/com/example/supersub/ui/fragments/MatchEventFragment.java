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
import com.example.supersub.models.Team;
import com.example.supersub.ui.DrawerLocker;
import com.example.supersub.ui.VerticalSpaceItemDecoration;
import com.example.supersub.ui.adapters.MatchEventAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MatchEventFragment extends Fragment {
    private Button buttonCancel;
    private RecyclerView rvPlayers;
    private FloatingActionButton fabComplete;
    private int totalGoals, totalAssists, totalYellowCards, totalRedCards;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        return inflater.inflate(R.layout.fragment_match_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonCancel = view.findViewById(R.id.button_match_event_cancel);
        rvPlayers = view.findViewById(R.id.rv_match_event);
        fabComplete = view.findViewById(R.id.fab_complete_match_event);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        MatchEventFragmentDirections.actionMatchEventFragmentToDashboardFragment()
                );
            }
        });
        //TODO: add an "are you sure you want to cancel this match event?" popup before canceling the match event

        MatchEventAdapter adapter = new MatchEventAdapter(Team.getCurrentTeam().getPlayers());
        rvPlayers.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        rvPlayers.setLayoutManager(layoutManager);
        rvPlayers.addItemDecoration(new VerticalSpaceItemDecoration(32));

        // the following code is from Stack Overflow and makes the floating action button disappear when scrolling
        rvPlayers.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (dy > 0 || dy < 0 && fabComplete.isShown())
                {
                    fabComplete.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    fabComplete.show();
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        fabComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Team.getCurrentTeam().setPlayers(adapter.getPlayers());
                Navigation.findNavController(view).navigate(
                        MatchEventFragmentDirections.actionMatchEventFragmentToDashboardFragment()
                );
            }
        });
        //TODO: add an "are you sure you want to finish this match event" popup before finishing the match event
    }
}
