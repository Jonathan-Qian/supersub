package com.example.supersub.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.supersub.ui.fragments.TeamMembersFragment;
import com.example.supersub.ui.fragments.TeamStatisticsFragment;

// Adapter for the view pager in TeamMainFragment.
public class TeamViewPagerAdapter extends FragmentStateAdapter {
    public TeamViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    // Go to a fragment based on the tab selected.
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            // If it is the second tab, return the team statistics fragment.
            case 1:
                return new TeamStatisticsFragment();

            // If it is the first tab, return the team members fragment. Or if the cases do not cover the value of position, default to the team members fragment.
            default:
                return new TeamMembersFragment();
        }
    }

    // Return the number of fragments in this view pager.
    @Override
    public int getItemCount() {
        return 2;
    }
}
