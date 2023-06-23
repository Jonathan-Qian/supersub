package com.example.supersub.ui;

// Through the implementation of this interface in MainActivity, this allows any fragment to disable or enable the drawer
public interface DrawerLocker {
    public void setDrawerEnabled(boolean enabled);
}