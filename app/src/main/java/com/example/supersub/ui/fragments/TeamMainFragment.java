package com.example.supersub.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.supersub.R;
import com.example.supersub.ui.DrawerLocker;
import com.example.supersub.ui.adapters.TeamViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/*
 * This fragment is not an ordinary fragment. This contains a view pager and tab layout that allows users to navigate other pages using a tabs.
 * Basically, this is a page that contains tabs and their corresponding fragments.
 */
public class TeamMainFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Enable the drawer for all fragments contained in this one.
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);

        // Inflate the layout that corresponds to this fragment.
        return inflater.inflate(R.layout.fragment_team_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views.
        tabLayout = view.findViewById(R.id.tab_layout_team);
        viewPager = view.findViewById(R.id.view_pager_team);

        /*
         * Much like RecyclerView's, ViewPager2(s) also need an adapter.
         * Set viewPager's adapter.
         */
        viewPager.setAdapter(new TeamViewPagerAdapter(this));

        // When a tab is tapped, used the view pager to go to the correct fragment.
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }
}
