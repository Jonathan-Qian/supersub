package com.example.supersub.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.supersub.ui.fragments.TeamMembersFragment;
import com.example.supersub.ui.fragments.TeamStatisticsFragment;

public class TeamViewPagerAdapter extends FragmentStateAdapter {
    public TeamViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TeamMembersFragment();

            case 1:
                return new TeamStatisticsFragment();

            default:
                return new TeamMembersFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
