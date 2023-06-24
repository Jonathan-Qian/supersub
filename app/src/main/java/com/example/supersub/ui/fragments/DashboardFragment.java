package com.example.supersub.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.supersub.R;
import com.example.supersub.models.Player;
import com.example.supersub.models.Team;
import com.example.supersub.ui.DrawerLocker;

public class DashboardFragment extends Fragment {
    private Button buttonManageTeam, buttonMatchEvent;
    private TextView tvTopScorer, tvTopAssister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Enable the drawer.
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);

        // Inflate the layout that corresponds to this fragment.
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views.
        buttonMatchEvent = view.findViewById(R.id.button_match_event);
        tvTopScorer = view.findViewById(R.id.tv_dashboard_top_scorer);
        tvTopAssister = view.findViewById(R.id.tv_dashboard_top_assister);
        buttonManageTeam = view.findViewById(R.id.button_manage_team);

        // Go to the match event page when the match event button is tapped.
        buttonMatchEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        DashboardFragmentDirections.actionDashboardFragmentToMatchEventFragment()
                );
            }
        });

        // Find the top scorer and set the topScorer TextView to a String with the player's number, name and goals.
        Player topScorer;

        // Set the TextView only if there are even players in the team.
        if ((topScorer = Team.getCurrentTeam().topScorer()) != null) {
            // Since many concatenations will need to be made otherwise, StringBuilder is essential.
            StringBuilder builder1 = new StringBuilder();

            builder1.append("#");
            builder1.append(topScorer.getJerseyNumber());
            builder1.append(" ");
            builder1.append(topScorer.getFirstName());
            builder1.append(" ");
            builder1.append(topScorer.getLastName());
            builder1.append(" (");
            builder1.append(topScorer.getGoals());
            builder1.append(")");
            tvTopScorer.setText(builder1.toString());
        }

        // Same as above with assists.
        Player topAssister;

        if ((topAssister = Team.getCurrentTeam().topAssister()) != null) {
            StringBuilder builder2 = new StringBuilder();

            builder2.append("#");
            builder2.append(topAssister.getJerseyNumber());
            builder2.append(" ");
            builder2.append(topAssister.getFirstName());
            builder2.append(" ");
            builder2.append(topAssister.getLastName());
            builder2.append(" (");
            builder2.append(topAssister.getAssists());
            builder2.append(")");
            tvTopAssister.setText(builder2.toString());
        }

        // Go to the team page when the match event button is tapped.
        buttonManageTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        DashboardFragmentDirections.actionDashboardFragmentToTeamMainFragment()
                );
            }
        });
    }
}
