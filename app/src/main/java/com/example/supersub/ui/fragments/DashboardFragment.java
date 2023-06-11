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
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonMatchEvent = view.findViewById(R.id.button_match_event);
        tvTopScorer = view.findViewById(R.id.tv_dashboard_top_scorer);
        tvTopAssister = view.findViewById(R.id.tv_dashboard_top_assister);
        buttonManageTeam = view.findViewById(R.id.button_manage_team);

        buttonMatchEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        DashboardFragmentDirections.actionDashboardFragmentToMatchEventFragment()
                );
            }
        });

        Player topScorer;

        if ((topScorer = Team.getCurrentTeam().topScorer()) != null) {
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
            builder2.append(topAssister.getGoals());
            builder2.append(")");
            tvTopAssister.setText(builder2.toString());
        }

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
