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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supersub.R;
import com.example.supersub.models.Player;
import com.example.supersub.models.Team;
import com.example.supersub.ui.DrawerLocker;
import com.example.supersub.ui.adapters.PositionChipAdapter;

public class PlayerProfileFragment extends Fragment {
    private Player player;
    private TextView tvJerseyNumber, tvName, tvGoals, tvAssists, tvYellowCards, tvRedCards;
    private RecyclerView rvPositions;
    private Button buttonBack, buttonEdit, buttonRemove;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);
        return inflater.inflate(R.layout.fragment_player_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        player = Team.getCurrentTeam().getPlayers().get(PlayerProfileFragmentArgs.fromBundle(getArguments()).getPlayerIndex());
        tvJerseyNumber = view.findViewById(R.id.tv_player_profile_player_number);
        tvName = view.findViewById(R.id.tv_player_profile_player_name);
        tvGoals= view.findViewById(R.id.tv_player_profile_goals);
        tvAssists = view.findViewById(R.id.tv_player_profile_assists);
        tvYellowCards = view.findViewById(R.id.tv_player_profile_yellow_cards);
        tvRedCards = view.findViewById(R.id.tv_player_profile_red_cards);
        tvJerseyNumber = view.findViewById(R.id.tv_player_profile_player_number);
        rvPositions = view.findViewById(R.id.rv_player_profile_positions);
        buttonBack = view.findViewById(R.id.button_player_profile_back);
        buttonEdit = view.findViewById(R.id.button_player_profile_edit_player);
        buttonRemove = view.findViewById(R.id.button_player_profile_remove_player);

        tvJerseyNumber.setText(Integer.toString(player.getJerseyNumber()));
        tvName.setText(player.getFirstName() + "\n" + player.getLastName());
        tvGoals.setText(Integer.toString(player.getGoals()));
        tvAssists.setText(Integer.toString(player.getAssists()));
        tvYellowCards.setText(Integer.toString(player.getYellowCards()));
        tvRedCards.setText(Integer.toString(player.getRedCards()));

        PositionChipAdapter adapter = new PositionChipAdapter(player.getPositions());
        rvPositions.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 6);
        rvPositions.setLayoutManager(layoutManager);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        PlayerProfileFragmentDirections.actionPlayerProfileFragmentToTeamMainFragment()
                );
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Team.getCurrentTeam().getPlayers().remove(player);
                Navigation.findNavController(view).navigate(
                        PlayerProfileFragmentDirections.actionPlayerProfileFragmentToTeamMainFragment()
                );
            }
        });
    }
}
