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

public class AddPlayerFragment extends Fragment implements PositionChipAdapter.OnChipListener {
    private Button buttonCancel, buttonAddPlayer;
    private EditText etFirstName, etLastName, etJerseyNumber;
    private RecyclerView rvPositions;
    private View chipAddPosition;
    private PositionChipAdapter adapter;
    private PopupMenu popupMenu; // This menu will popup when adding positions to the new player and display all the available positions.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Disable the drawer.
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);

        // Inflate the layout that corresponds to this fragment.
        return inflater.inflate(R.layout.fragment_add_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initializing views and other objects.
        buttonCancel = view.findViewById(R.id.button_add_player_cancel);
        etFirstName = view.findViewById(R.id.et_add_first_name);
        etLastName = view.findViewById(R.id.et_add_last_name);
        etJerseyNumber = view.findViewById(R.id.et_add_jersey_number);
        rvPositions = view.findViewById(R.id.rv_add_positions);
        chipAddPosition = view.findViewById(R.id.chip_add_position);
        popupMenu = new PopupMenu(getContext(), chipAddPosition);
        buttonAddPlayer = view.findViewById(R.id.button_add_player);

        // Go back to the team page when the cancel button is tapped.
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        AddPlayerFragmentDirections.actionAddPlayerFragmentToTeamMainFragment()
                );
            }
        });

        // Setting up adapter.
        adapter = new PositionChipAdapter(new PositionGroup(Player.PLAYER_POSITION_GROUP_NAME, Player.PLAYER_POSITION_GROUP_SYMBOL), this);
        rvPositions.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 6); // rvPositions uses a grid layout with 6 columns.
        rvPositions.setLayoutManager(layoutManager);

        // Add positions to the popup menu.
        for (int i = 0; i < Positions.length(); i++) {
            popupMenu.getMenu().add(Menu.NONE, i, i, Positions.getPositionName(i));
        }

        // When the add position button is tapped, show the popup menu.
        chipAddPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When a popup menu item is tapped, add the position to the PositionGroup in the adapter and remove that position from the popup menu.
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        adapter.addPosition(Positions.indexOf(menuItem.getTitle().toString()));
                        popupMenu.getMenu().removeItem(menuItem.getItemId());
                        return true;
                    }
                });

                popupMenu.show();
            }
        });

        // When the add player button is tapped, add the new player to the current team if possible.
        buttonAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If there jersey number field is empty, prompt the user to enter a jersey number using a toast message.
                if (etJerseyNumber.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), R.string.player_must_have_jersey_number, Toast.LENGTH_SHORT).show();
                }
                // Check if another player in the current team already has the jersey number in the jersey number field. If yes, then prompt the user to choose another number.
                else if (Team.getCurrentTeam().indexOfPlayer(Integer.parseInt(etJerseyNumber.getText().toString())) != -1) {
                    Toast.makeText(view.getContext(), R.string.jersey_number_already_exists, Toast.LENGTH_SHORT).show();
                }
                // If the inputs are valid, add the new player to the team.
                else {
                    Team.getCurrentTeam().getPlayers().add(new Player(
                            etFirstName.getText().toString(),
                            etLastName.getText().toString(),
                            new Integer(etJerseyNumber.getText().toString()),
                            adapter.getPositions()
                    ));
                    // Sort the team's players by jersey number after adding a new one using a comparator to maintain an order.
                    Team.getCurrentTeam().getPlayers().sort(new Comparator<Player>() {
                        // Return 0 if the players being compared have the same jersey number, return 1 if player's jersey number is larger and return -1 if t1's jersey number is larger.
                        @Override
                        public int compare(Player player, Player t1) {
                            if (player.getJerseyNumber() == t1.getJerseyNumber())
                                return 0;

                            return player.getJerseyNumber() < t1.getJerseyNumber() ? -1 : 1;
                        }
                    });
                    // Return to the team page after successfully creating a new player.
                    Navigation.findNavController(view).navigate(
                            AddPlayerFragmentDirections.actionAddPlayerFragmentToTeamMainFragment()
                    );
                }
            }
        });
    }

    // Allow the user to remove a position from the player when they click its chip and add it back to the popup menu as an available position again.
    @Override
    public void onChipClick(int position) {
        // Get the index of the Position in Positions corresponding to the chip at position.
        int positionIndex = adapter.getPositions().getPositionId(position);
        // Add the position back to the popup menu in the correct order.
        popupMenu.getMenu().add(Menu.NONE, positionIndex, positionIndex, Positions.getPositionName(positionIndex));
        // Remove the position from the PositionGroup in the RecyclerView adapter.
        adapter.removePosition(position);
    }
}
