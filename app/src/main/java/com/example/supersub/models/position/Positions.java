package com.example.supersub.models.position;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;

/*
 * This class contains a global list of all the available Position(s) that can be added to a player. It is nothing more than a static list so there is no constructor.
 * However, classes should use PositionGroup(s) as an intermediary to access the information from the Position(s) in Positions most of the time instead of directly accessing them using this class.
 */
public class Positions {
    private static ArrayList<Position> positions = new ArrayList<>();

    /*
     * This class cannot be accessed anywhere other than this package so that classes outside of the position package cannot create Position(s).
     * This way, there won't be any duplicate Position(s) floating around and the only Position(s) with the exception of PositionGroup(s) that exist are the ones in Positions.
     */
    protected static class Position {
        private String name;
        private String symbol;

        public Position(String name, String symbol) {
            this.name = name;
            this.symbol = symbol;
        }

        // Getters
        public String getName() {
            return name;
        }

        public String getSymbol() {
            return symbol;
        }

        // Used to identify this object as not a PositionGroup. This is overridden by PositionGroup to return true instead.
        public boolean isGroup() {
            return false;
        }

        @Override
        public String toString() {
            return "Position with name: " + this.name + "; with symbol: " + this.symbol;
        }
    }

    /*
     * Getters and toString from the Position class are called from Positions since Position is protected.
     * This also allows this class to provide the attributes of a Position without returning a Position.
     */
    public static String getPositionName(int index) {
        return positions.get(index).name;
    }

    public static String getPositionSymbol(int index) {
        return positions.get(index).symbol;
    }

    public static boolean isGroup(int index) {
        return positions.get(index).isGroup();
    }

    protected static String positionToString(int index) {
        return positions.get(index).toString();
    }

    // Returns the contents of a PositionGroup in positions at index
    public static ArrayList<Integer> getGroupContents(int index) {
        ArrayList<Integer> groupContents = new ArrayList<>();

        // If the Position at index is not a PositionGroup, this method will return an empty list
        if (isGroup(index)) {
            // After ensuring the Position at index is a PositionGroup, the Position needs to be cast to a PositionGroup to use getPositionIds() since the method is only available for PositionGroup(s)
            groupContents = ((PositionGroup) positions.get(index)).getPositionIds();
        }
        else {
            System.out.println("Position at " + index + " is not a PositionGroup");
        }

        return groupContents;
    }

    // Returns the index of a Position in positions based on its name
    public static int indexOf(String name) {
        // Doing a linear search to look for a Position with a matching name
        for (int i = 0; i < positions.size(); i++) {
            if (name.equals(getPositionName(i))) {
                return i;
            }
        }

        return -1;
    }

    /*
     * Read Position(s) from the positions.xml file in the asset directory that stores all the positions available in this app.
     * The default positions are stored in a xml file because I did not want to hard code the positions in a class.
     * That way, the app's code does not need to be updated if more positions are added. Only the positions.xml file needs to be replaced.
     * The Context parameter is necessary for opening the positions.xml file in the assets directory.
     * This method throws XmlPullParserException and IOException because of the use of XMLPullParser.
     * It uses XMLPullParser to read the xml file.
     */
    private static ArrayList<Positions.Position> read(Context context) throws XmlPullParserException, IOException {
        ArrayList<Positions.Position> positions = new ArrayList<>();

        // Create and set up a XMLPullParser by getting the XMLPullParserFactory and creating a XMLPullParser.
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(context.getAssets().open("positions.xml"), "UTF-8");
        int eventType = parser.getEventType();

        PositionGroup currentGroup = null;

        while (eventType != XmlPullParser.END_DOCUMENT) { // Continue until the file ends.
            if (eventType == XmlPullParser.START_TAG) {
                // If the start tag is a group tag, create a new PositionGroup with the attributes in the tag.
                if (parser.getName().equals("group")) {
                    currentGroup = new PositionGroup(parser.getAttributeValue(null, "name"),
                            parser.getAttributeValue(null, "symbol"));
                }
                // If the start tag is a Position tag, create a new Position with the attributes in the tag.
                else if (parser.getName().equals("position")) {
                    positions.add(new Position(parser.getAttributeValue(null, "name"),
                            parser.getAttributeValue(null, "symbol")));

                    // If the current Position is within a group and if the current PositionGroup exists, add it to the current PositionGroup.
                    if (parser.getDepth() > 2 && currentGroup != null) {
                        currentGroup.add(positions.size() - 1);
                    }
                }
            }
            else if (eventType == XmlPullParser.END_TAG) {
                // Once the group ends, the current PositionGroup can finally be added to positions since there will no longer be any more Position(s) within this PositionGroup.
                if (parser.getName().equals("group")) {
                    positions.add(currentGroup);
                    currentGroup = null;
                }
            }

            // Move to the next line (similar to bufferedReader.readLine() for BufferedReader).
            eventType = parser.next();
        }

        return positions;
    }

    // Return the length of Positions' positions list
    public static int length() {
        return positions.size();
    }

    // Since this class does not have a Constructor and read() is private, read() needs to be called from this method and this method needs to be called in the OnCreate() callback in MainActivity to ensure this list is initialized.
    public static void init(Context context) { // Context must be passed in because read() has a Context as its parameter.
        try {
            positions = read(context);
        }
        catch (XmlPullParserException e) { // This method also conveniently catches the errors thrown by read().
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Calls toString() on every Position in positions.
    public static String positionsToString() {
        // Since potentially dozens of concatenations will need to be made to add the toString of every Position, StringBuilder is essential.
        StringBuilder builder = new StringBuilder("Available positions and position groups include: ");

        // Iterate through positions and calling the toString() of the Position at i.
        for (int i = 0; i < positions.size(); i++) {
           builder.append(positionToString(i));
           builder.append(", ");
        }

        return builder.toString();
    }
}
