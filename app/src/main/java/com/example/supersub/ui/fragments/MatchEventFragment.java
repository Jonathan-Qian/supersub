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
    private int totalGoals, totalAssists, totalYellowCards, totalRedCards; // These will be used to keep track of the total goals, assists, etc. added to players in this match event.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Disable the drawer.
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);

        // Inflate the layout that corresponds to this fragment.
        return inflater.inflate(R.layout.fragment_match_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views.
        buttonCancel = view.findViewById(R.id.button_match_event_cancel);
        rvPlayers = view.findViewById(R.id.rv_match_event);
        fabComplete = view.findViewById(R.id.fab_complete_match_event);

        // Go back to the dashboard page when the cancel button is tapped.
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        MatchEventFragmentDirections.actionMatchEventFragmentToDashboardFragment()
                );
            }
        });
        //TODO: add an "are you sure you want to cancel this match event?" popup before canceling the match event

        // Set up the match event player card RecyclerView with the current team's players.
        MatchEventAdapter adapter = new MatchEventAdapter(Team.getCurrentTeam().getPlayers());
        rvPlayers.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        rvPlayers.setLayoutManager(layoutManager);
        rvPlayers.addItemDecoration(new VerticalSpaceItemDecoration(32)); // Add spacing between the items in the RecyclerView.
        // Make the floating action button disappear when scrolling down and appear when scrolling up.
        rvPlayers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            // When moving up (dy < 0), show the button. When moving down (dy > 0), hide the button.
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (dy < 0 && !fabComplete.isShown()) {
                    fabComplete.show();
                }
                else if (dy > 0 && fabComplete.isShown()) {
                    fabComplete.hide();
                }
            }

            // Implement OnScrollListener.onScrollStateChanged and let the parent class handle it.
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        // When the floating action button is tapped, set the players of the current team to the edited players in the adapter and go to the dashboard page.
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
