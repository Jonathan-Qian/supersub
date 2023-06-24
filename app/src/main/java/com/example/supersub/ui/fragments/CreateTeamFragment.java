package com.example.supersub.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.supersub.R;
import com.example.supersub.models.TeamFacade;
import com.example.supersub.ui.DrawerLocker;

public class CreateTeamFragment extends Fragment {
    private EditText etTeamName, etSeason, etDescription, etColorR, etColorG, etColorB;
    private Button buttonCancel, buttonAddTeam;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Disable the drawer.
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);

        // Inflate the layout that corresponds to this fragment.
        return inflater.inflate(R.layout.fragment_create_team, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initializing views.
        buttonCancel = view.findViewById(R.id.button_create_team_cancel);
        etTeamName = view.findViewById(R.id.et_team_name);
        etSeason = view.findViewById(R.id.et_season);
        etDescription = view.findViewById(R.id.et_description);
        etColorR = view.findViewById(R.id.et_color_r);
        etColorG = view.findViewById(R.id.et_color_g);
        etColorB = view.findViewById(R.id.et_color_b);
        buttonAddTeam = view.findViewById(R.id.button_add_team);

        // Go back to the team list page when the cancel button is tapped.
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        CreateTeamFragmentDirections.actionCreateTeamFragmentToTeamListFragment()
                );
            }
        });

        // Add and write a new team based on the values in the text fields.
        buttonAddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If the team name is not empty, continue. Otherwise, show a toast message prompting the user to enter the team name field.
                if (!etTeamName.getText().toString().equals("")) {
                    // Write the new TeamFacade.
                    TeamFacade teamFacade = new TeamFacade(
                            etTeamName.getText().toString(),
                            etSeason.getText().toString(),
                            etDescription.getText().toString(),
                            Color.rgb(
                                    Integer.parseInt(etColorR.getText().toString()),
                                    Integer.parseInt(etColorG.getText().toString()),
                                    Integer.parseInt(etColorB.getText().toString())
                            )
                    );
                    //TODO: Add proper colour picker so the app doesn't crash if you don't enter all colour text fields
                    //TODO: Colour picker should start at a random colour so if the user chooses not to set the team colour, the teams will still have different colours

                    teamFacade.write(view.getContext());

                    // Return to the team list page.
                    Navigation.findNavController(view).navigate(
                            CreateTeamFragmentDirections.actionCreateTeamFragmentToTeamListFragment()
                    );
                }
                else {
                    Toast.makeText(view.getContext(), R.string.enter_all_required_fields, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
