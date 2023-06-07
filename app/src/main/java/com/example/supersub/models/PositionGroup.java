package com.example.supersub.models;

import java.util.ArrayList;

public class PositionGroup extends Positions.Position {
    private ArrayList<Integer> positionIds;

    public PositionGroup(String name, String symbol, ArrayList<Integer> positionIds) {
        super(name, symbol);
        this.positionIds = positionIds;
        //TODO: Add a validate function to remove duplicate positions
    }
    public PositionGroup(String name, String symbol) {
        this(name, symbol, new ArrayList<Integer>());
    }

    public void add(int id) {
        positionIds.add(new Integer(id));
    }

    public String getPositionName(int index) {
        return Positions.getPositionName(positionIds.get(index));
    }

    public String getPositionSymbol(int index) {
        return Positions.getPositionSymbol(positionIds.get(index));
    }

    public int getPositionId(int index) {
        return positionIds.get(index);
    }

    public int length() {
        return positionIds.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Position group with name: ");
        builder.append(this.getName());
        builder.append("; with symbol: ");
        builder.append(this.getSymbol());
        builder.append("; has positions: {");

        for (int i = 0; i < positionIds.size(); i++) {
            builder.append(Positions.positionToString(positionIds.get(i)));
            builder.append(", ");
        }

        builder.append("}");

        return builder.toString();
    }
}