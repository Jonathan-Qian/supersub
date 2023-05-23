package com.example.supersub.models;

import java.util.ArrayList;

public class PositionGroup extends Positions.Position {
    private ArrayList<Integer> positionIds;

    private PositionGroup(String name, String symbol, ArrayList<Integer> positionIds) {
        super(name, symbol);
        this.positionIds = positionIds;
    }
    private PositionGroup(String name, String symbol) {
        this(name, symbol, new ArrayList<Integer>());
    }


}