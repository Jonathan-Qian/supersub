package com.example.supersub.ui;

import com.example.supersub.models.Team;

// This interface is implemented in MainActivity so that the drawer updates to include the current team's information to appear in the drawer header
public interface DrawerHeaderSetter {
    public void updateDrawerHeader(Team team);
}