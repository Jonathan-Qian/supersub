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
        // Disable the drawer.
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);

        // Inflate the layout that corresponds to this fragment.
        return inflater.inflate(R.layout.fragment_edit_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views and other variables.
        playerIndex = EditPlayerFragmentArgs.fromBundle(getArguments()).getPlayerIndex(); // This uses safe args to get the playerIndex. The playerIndex is necessary to determine which player the user is editing.
        player = Team.getCurrentTeam().getPlayers().get(playerIndex);
        buttonCancel = view.findViewById(R.id.button_edit_player_cancel);
        etFirstName = view.findViewById(R.id.et_edit_first_name);
        etLastName = view.findViewById(R.id.et_edit_last_name);
        etJerseyNumber = view.findViewById(R.id.et_edit_jersey_number);
        rvPositions = view.findViewById(R.id.rv_edit_positions);
        chipAddPosition = view.findViewById(R.id.chip_edit_position);
        menu = new PopupMenu(getContext(), chipAddPosition);
        buttonEditPlayer = view.findViewById(R.id.button_edit_player);

        /*
         * Go back to this player's player profile when the cancel button is tapped.
         * Send the playerIndex to the player profile fragment so it knows which player's information to display.
         */
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        EditPlayerFragmentDirections.actionEditPlayerFragmentToPlayerProfileFragment(playerIndex)
                );
            }
        });
        // TODO: Fix bug where edit player still applies changes when the cancel button is pressed.

        // Set the text of the text fields to already contain the current name and jerseyNumber of the player.
        etFirstName.setText(player.getFirstName());
        etLastName.setText(player.getLastName());
        etJerseyNumber.setText(Integer.toString(player.getJerseyNumber()));

        // Set up the position chip RecyclerView and pass in the player's current positions.
        adapter = new PositionChipAdapter(player.getPositions(), this);
        rvPositions.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 6);
        rvPositions.setLayoutManager(layoutManager);

        // Iterate though Positions.
        for (int i = 0; i < Positions.length(); i++) {
            // Add position to the add positions popup menu (shows all positions that aren't already added to this player) only if this player doesn't already have it.
            if (adapter.getPositions().indexOf(Positions.indexOf(Positions.getPositionName(i))) == -1) {
                menu.getMenu().add(Menu.NONE, i, i, Positions.getPositionName(i));
            }
        }

        // When the add position button is tapped, show the popup menu.
        chipAddPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When a popup menu item is tapped, add the position to the PositionGroup in the adapter and remove that position from the popup menu.
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

        // Set the Player when the edit player button is clicked.
        buttonEditPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If there jersey number field is empty, prompt the user to enter a jersey number using a toast message.
                if (etJerseyNumber.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), R.string.player_must_have_jersey_number, Toast.LENGTH_SHORT).show();
                }
                // Check if another player in the current team already has the jersey number in the jersey number field. If yes, then prompt the user to choose another number.
                else if (Team.getCurrentTeam().indexOfPlayer(Integer.parseInt(etJerseyNumber.getText().toString())) != -1
                && player.getJerseyNumber() != Integer.parseInt(etJerseyNumber.getText().toString())) {
                    Toast.makeText(view.getContext(), R.string.jersey_number_already_exists, Toast.LENGTH_SHORT).show();
                }
                // If the inputs are valid, set the player with the new values.
                else {
                    Team.getCurrentTeam().getPlayers().set(playerIndex, new Player(
                            etFirstName.getText().toString(),
                            etLastName.getText().toString(),
                            new Integer(etJerseyNumber.getText().toString()),
                            adapter.getPositions()
                    ));
                    // Sort the team's players by jersey number after editing this player using a comparator to maintain an order.
                    Team.getCurrentTeam().getPlayers().sort(new Comparator<Player>() {
                        // Return 0 if the players being compared have the same jersey number, return 1 if player's jersey number is larger and return -1 if t1's jersey number is larger.
                        @Override
                        public int compare(Player player, Player t1) {
                            if (player.getJerseyNumber() == t1.getJerseyNumber())
                                return 0;

                            return player.getJerseyNumber() < t1.getJerseyNumber() ? -1 : 1;
                        }
                    });
                    // Go back to this player's player profile. I cannot pass in playerIndex because the jersey number could have changed which would change the order and therefore bring the user to the wrong player profile.
                    Navigation.findNavController(view).navigate(
                            EditPlayerFragmentDirections.actionEditPlayerFragmentToPlayerProfileFragment(
                                    Team.getCurrentTeam().indexOfPlayer(new Integer(etJerseyNumber.getText().toString()))
                            )
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
        menu.getMenu().add(Menu.NONE, positionIndex, positionIndex, Positions.getPositionName(positionIndex));
        // Remove the position from the PositionGroup in the RecyclerView adapter.
        adapter.removePosition(position);
    }
}
