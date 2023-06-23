package com.example.supersub.models.position;

import java.util.ArrayList;
import java.util.Collections;

/*
 * PositionGroup(s) are positions that store the IDs (the index) of other positions in them.
 * The reason they don't just store Position objects is because the only Position objects (other than PositionGroup(s)) that should exist are the Position(s) in Positions.java.
 * This way, there won't be any duplicate Position(s) floating around and potentially causing confusion.
 * Basically, as well as storing a list of position references, this class acts as an interface between Positions (since many of Positions' methods are protected) and any other class that attempts to get info about a Position.
 * It is used to store a Player's positions and is also used to group Position(s) together in the Positions class (these include Forward, Midfield and Defense) so, for example, if a player plays all positions in defense, the user does not have to input every defense position for that player.
 */
public class PositionGroup extends Positions.Position {
    private ArrayList<Integer> positionIds;

    public PositionGroup(String name, String symbol, ArrayList<Integer> positionIds) {
        super(name, symbol);
        this.positionIds = positionIds;
        sort();
    }
    // This constructor allows new empty PositionGroup(s) to be made automatically rather than passing in a new ArrayList<Integer> into the above constructor.
    public PositionGroup(String name, String symbol) {
        this(name, symbol, new ArrayList<Integer>());
    }

    // Getters
    public String getPositionName(int index) {
        return Positions.getPositionName(positionIds.get(index));
    }

    public String getPositionSymbol(int index) {
        return Positions.getPositionSymbol(positionIds.get(index));
    }

    // This allows another class to get a position id from positionIds without accessing it directly
    public int getPositionId(int index) {
        return positionIds.get(index);
    }

    public int length() {
        return positionIds.size();
    }

    // Setters
    public void setPositionIds(ArrayList<Integer> positionIds) {
        this.positionIds = positionIds;
    }

    public void add(int id) {
        positionIds.add(new Integer(id));
    }

    public void remove(int index) {
        positionIds.remove(index);
    }

    // Used for debugging purposes
    @Override
    public String toString() {
        // A StringBuilder is used to avoid repeated concatenations
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

    // Gets the index of a position id in positionsIds
    public int indexOf(int id) {
        for (int i = 0; i < positionIds.size(); i++) {
            if (positionIds.get(i) == id) {
                return i;
            }
        }

        return -1;
    }

    // This is used to identify if this is a PositionGroup instead of a Position since instanceof PositionGroup returns true even when evaluating a Position
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

    /* This removes Position(s) that are part of a PositionGroup from the Positions class.
     * For example, if a player has both the Forward PositionGroup and the Striker Position, this method will remove the Striker Position from this PositionGroup since the Forward PositionGroup already contains the Striker Position
     */
    public void removeRedundant() {
        ArrayList<Integer> potentiallyRedundantPositionIds;

        // Iterating through all the position ids in this PositionGroup
        for (int i = 0; i < positionIds.size(); i++) {

            // Checking if the Position at i is a PositionGroup
            if (Positions.isGroup(i)) {
                // Getting that group's position ids
                potentiallyRedundantPositionIds = Positions.getGroupContents(i);

                // Iterating through the position ids from the PositionGroup at i
                for (int j = 0; j < potentiallyRedundantPositionIds.size(); j++) {
                    // Finding the index of the (position id at j in the PositionGroup at i) in this PositionGroup's positionIds
                    int redundantPositionIdIndex = indexOf(potentiallyRedundantPositionIds.get(j));

                    // If the (position id at j in the PositionGroup at i) exists in this PositionGroup's positionIds, remove it.
                    if (redundantPositionIdIndex > -1) {
                        positionIds.remove(redundantPositionIdIndex);
                        System.out.println("Redundant position removed");
                    }
                }
            }
        }
    }
}