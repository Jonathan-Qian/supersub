package com.example.supersub.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supersub.R;
import com.example.supersub.models.Player;
import com.example.supersub.models.Team;
import com.example.supersub.models.position.Positions;
import com.example.supersub.ui.DrawerLocker;
import com.example.supersub.ui.adapters.PositionChipAdapter;

import java.util.Comparator;

public class EditPlayerFragment extends Fragment implements PositionChipAdapter.OnChipListener {
    private int playerIndex;
    private Player player;
    private Button buttonCancel, buttonEditPlayer;
    private EditText etFirstName, etLastName, etJerseyNumber;
    private RecyclerView rvPositions;
    private View chipAddPosition;
    private PositionChipAdapter adapter;
    private PopupMenu menu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        return inflater.inflate(R.layout.fragment_edit_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerIndex = EditPlayerFragmentArgs.fromBundle(getArguments()).getPlayerIndex();
        player = Team.getCurrentTeam().getPlayers().get(playerIndex);
        buttonCancel = view.findViewById(R.id.button_edit_player_cancel);
        etFirstName = view.findViewById(R.id.et_edit_first_name);
        etLastName = view.findViewById(R.id.et_edit_last_name);
        etJerseyNumber = view.findViewById(R.id.et_edit_jersey_number);
        rvPositions = view.findViewById(R.id.rv_edit_positions);
        chipAddPosition = view.findViewById(R.id.chip_edit_position);
        menu = new PopupMenu(getContext(), chipAddPosition);
        buttonEditPlayer = view.findViewById(R.id.button_edit_player);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        EditPlayerFragmentDirections.actionEditPlayerFragmentToPlayerProfileFragment(playerIndex)
                );
            }
        });

        etFirstName.setText(player.getFirstName());
        etLastName.setText(player.getLastName());
        etJerseyNumber.setText(Integer.toString(player.getJerseyNumber()));

        adapter = new PositionChipAdapter(player.getPositions(), this);
        rvPositions.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 6);
        rvPositions.setLayoutManager(layoutManager);

        for (int i = 0; i < Positions.length(); i++) {
            if (adapter.getPositions().indexOf(Positions.indexOf(Positions.getPositionName(i))) == -1) {
                menu.getMenu().add(Menu.NONE, i, i, Positions.getPositionName(i));
            }
        }

        chipAddPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        adapter.addPosition(Positions.indexOf(menuItem.getTitle().toString()));
                        menu.getMenu().removeItem(menuItem.getItemId());
                        return true;
                    }
                });

                menu.show();
            }
        });

        buttonEditPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etJerseyNumber.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), R.string.player_must_have_jersey_number, Toast.LENGTH_SHORT).show();
                }
                else if (Team.getCurrentTeam().indexOf(Integer.parseInt(etJerseyNumber.getText().toString())) != -1
                && player.getJerseyNumber() != Integer.parseInt(etJerseyNumber.getText().toString())) {
                    Toast.makeText(view.getContext(), R.string.jersey_number_already_exists, Toast.LENGTH_SHORT).show();
                }
                else {
                    Team.getCurrentTeam().getPlayers().set(playerIndex, new Player(
                            etFirstName.getText().toString(),
                            etLastName.getText().toString(),
                            new Integer(etJerseyNumber.getText().toString()),
                            adapter.getPositions()
                    ));
                    Team.getCurrentTeam().getPlayers().sort(new Comparator<Player>() {
                        @Override
                        public int compare(Player player, Player t1) {
                            if (player.getJerseyNumber() == t1.getJerseyNumber())
                                return 0;

                            return player.getJerseyNumber() < t1.getJerseyNumber() ? -1 : 1;
                        }
                    });
                    Navigation.findNavController(view).navigate(
                            EditPlayerFragmentDirections.actionEditPlayerFragmentToPlayerProfileFragment(
                                    Team.getCurrentTeam().indexOf(new Integer(etJerseyNumber.getText().toString()))
                            )
                    );
                }
            }
        });
    }

    @Override
    public void onChipClick(int position) {
        int positionIndex = adapter.getPositions().getPositionId(position);
        menu.getMenu().add(Menu.NONE, positionIndex, positionIndex, Positions.getPositionName(positionIndex));
        adapter.removePosition(position);
    }
}
