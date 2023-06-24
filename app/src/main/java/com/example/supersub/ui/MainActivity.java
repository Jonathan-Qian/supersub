package com.example.supersub.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.supersub.NavigationDirections;
import com.example.supersub.R;
import com.example.supersub.models.Team;
import com.example.supersub.models.position.Positions;
import com.google.android.material.navigation.NavigationView;

/*
 * This app runs on this single activity. Fragments are used to contain the app's pages/screens.
 * It implements NavigationView.OnNavigationItemSelectedListener so that the drawer's menu items will do something when tapped, DrawerLocker to allow fragments to enable or disable the drawer and DrawerHeaderSetter to update the drawer when a new team is loaded in.
 * Android's navigation component is used for navigation. See the NavHostFragment, NavController and nav_graph.xml.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLocker, DrawerHeaderSetter {
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navView;
    private NavHostFragment navHostFragment; // Part of the navigation component. This is the container for fragments that are swapped out on navigate().
    private NavController navController; // Part of the navigation component. This is the thing that actually handles the navigation and is linked to a NavHost and nav graph.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Positions, drawer, toolbar and navigation components.
        Positions.init(getApplicationContext());
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close); // toggle has to do with the drawer. It is the hamburger menu button that toggles the drawer.
        navView = findViewById(R.id.nav_view);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        // Set up toolbar, navView, drawer and toggle.
        setSupportActionBar(toolbar);
        navView.setNavigationItemSelectedListener(this);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    // This tells the app what to do when a menu item in the drawer is tapped.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // If the dashboard item is tapped, go to the dashboard page.
            case R.id.nav_dashboard:
                navController.navigate(
                        NavigationDirections.actionGlobalDashboardFragment()
                );
                break;

            // If the team item is tapped, go to the team page.
            case R.id.nav_team:
                navController.navigate(
                        NavigationDirections.actionGlobalTeamMainFragment()
                );

            // If the exit team item is tapped, save the team, go to the team list page and show a toast message saying the team has been saved.
            case R.id.nav_exit_team:
                Team.getCurrentTeam().write(getApplicationContext());
                navController.navigate(
                        NavigationDirections.actionGlobalTeamListFragment()
                );
                Toast.makeText(MainActivity.this, R.string.team_saved, Toast.LENGTH_SHORT).show();
                break;

            // If the manual save item is tapped, save the team and show a toast message saying the team has been saved.
            case R.id.nav_manual_save:
                Team.getCurrentTeam().write(getApplicationContext());
                Toast.makeText(MainActivity.this, R.string.save_successful, Toast.LENGTH_SHORT).show();
        }

        // Close the drawer automatically after a menu item in the drawer is clicked.
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    // The next three methods are simply unmodified, overridden methods from AppCompatActivity and are needed for the drawer to work properly.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    // Close the drawer when the Android device's back button is pressed.
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    // This allows fragments to disable or enable the drawer (see comments in DrawerLocker for more).
    @Override
    public void setDrawerEnabled(boolean enabled) {
        // If enabled is true, set the drawer to unlocked. Otherwise, set the drawer to locked.
        drawer.setDrawerLockMode(enabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toggle.setDrawerIndicatorEnabled(enabled);
    }

    // Update the drawer's header with the current team's information.
    @Override
    public void updateDrawerHeader(Team team) {
        NavigationView navView = findViewById(R.id.nav_view);
        View header = navView.getHeaderView(0);

        // Initialize views.
        LinearLayout parent = header.findViewById(R.id.header_parent);
        TextView tvTeamName = header.findViewById(R.id.tv_header_team_name);
        TextView tvSeason = header.findViewById(R.id.tv_header_season);

        // Set the background colour of the header to the team colour and the team name and season to the current team's name and season.
        parent.setBackgroundColor(team.getColor());
        tvTeamName.setText(team.getName());
        tvSeason.setText(team.getSeason());
    }
}