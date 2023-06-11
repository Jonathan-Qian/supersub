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
import com.example.supersub.models.position.PositionGroup;
import com.example.supersub.models.position.Positions;
import com.example.supersub.ui.DrawerLocker;
import com.example.supersub.ui.adapters.PositionChipAdapter;

import java.util.Comparator;

public class AddPlayerFragment extends Fragment {
    private Button buttonCancel, buttonAddPlayer;
    private EditText etFirstName, etLastName, etJerseyNumber;
    private RecyclerView rvPositions;
    private View chipAddPosition;
    private PositionGroup positions;
    private PopupMenu menu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        return inflater.inflate(R.layout.fragment_add_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonCancel = view.findViewById(R.id.button_add_player_cancel);
        etFirstName = view.findViewById(R.id.et_add_first_name);
        etLastName = view.findViewById(R.id.et_add_last_name);
        etJerseyNumber = view.findViewById(R.id.et_add_jersey_number);
        rvPositions = view.findViewById(R.id.rv_add_positions);
        chipAddPosition = view.findViewById(R.id.chip_add_position);
        positions = new PositionGroup(Player.PLAYER_POSITION_GROUP_NAME, Player.PLAYER_POSITION_GROUP_SYMBOL);
        menu = new PopupMenu(getContext(), chipAddPosition);
        buttonAddPlayer = view.findViewById(R.id.button_add_player);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        AddPlayerFragmentDirections.actionAddPlayerFragmentToTeamMainFragment()
                );
            }
        });

        PositionChipAdapter adapter = new PositionChipAdapter(positions);
        rvPositions.setAdapter(adapter);
        //GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), (parent.getWidth() - R.dimen.default_layout_padding * 2) / chipAddPosition.getWidth());
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 6);
        rvPositions.setLayoutManager(layoutManager);

        for (int i = 0; i < Positions.length(); i++) {
            menu.getMenu().add(Menu.NONE, i, Menu.NONE, Positions.getPositionName(i));
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

        buttonAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etJerseyNumber.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), R.string.player_must_have_jersey_number, Toast.LENGTH_SHORT).show();
                }
                else if (Team.getCurrentTeam().indexOf(Integer.parseInt(etJerseyNumber.getText().toString())) != -1) {
                    Toast.makeText(view.getContext(), R.string.jersey_number_already_exists, Toast.LENGTH_SHORT).show();
                }
                else {
                    System.out.println(positions.toString());
                    Team.getCurrentTeam().getPlayers().add(new Player(
                            etFirstName.getText().toString(),
                            etLastName.getText().toString(),
                            new Integer(etJerseyNumber.getText().toString()),
                            positions
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
                            AddPlayerFragmentDirections.actionAddPlayerFragmentToTeamMainFragment()
                    );
                }
            }
        });
    }
}
