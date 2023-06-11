package com.example.supersub.models.position;

import java.util.ArrayList;
import java.util.Collections;

public class PositionGroup extends Positions.Position {
    private ArrayList<Integer> positionIds;

    public PositionGroup(String name, String symbol, ArrayList<Integer> positionIds) {
        super(name, symbol);
        this.positionIds = positionIds;
        sort();
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

    private int indexOf(int id) {
        for (int i = 0; i < positionIds.size(); i++) {
            if (positionIds.get(i) == id) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean isGroup() {
        return true;
    }

    protected ArrayList<Integer> getPositionIds() {
        return positionIds;
    }

    public void sort() {
        Collections.sort(positionIds);
    }

    public void removeRedundant() {
        ArrayList<Integer> potentiallyRedundantPositionIds;

        for (int i = 0; i < positionIds.size(); i++) {
            System.out.println("Doing something");
            if (Positions.isGroup(i)) {
                potentiallyRedundantPositionIds = Positions.getGroupContents(i);

                for (int j = 0; j < potentiallyRedundantPositionIds.size(); j++) {
                    int redundantPositionIdIndex = indexOf(potentiallyRedundantPositionIds.get(j));
                    System.out.println("index");

                    if (redundantPositionIdIndex > -1) {
                        positionIds.remove(redundantPositionIdIndex);
                        System.out.println("redundant removed");
                    }
                }
            }
        }

        System.out.println("test");
    }
}