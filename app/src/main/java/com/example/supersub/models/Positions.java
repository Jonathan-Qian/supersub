package com.example.supersub.models;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.HashMap;

public class Positions {
    private static HashMap<Integer, Positions.Position> positions;

    protected static class Position {
        private String name;
        private String symbol;

        public Position(String name, String symbol) {
            this.name = name;
            this.symbol = symbol;
        }
    }

    public static String getPositionName(Integer id) {
        return positions.get(id).name;
    }

    public static String getPositionSymbol(Integer id) {
        return positions.get(id).symbol;
    }

    private static HashMap<Integer, Positions.Position> read(Context context) throws XmlPullParserException, IOException {
        HashMap<Integer, Positions.Position> positions = new HashMap<>();

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(context.getAssets().open("positions.xml"), "UTF-8");
        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (parser.getName() == "group") {

                }
            }

            eventType = parser.next();
        }

        return positions;
    }

    public static void init(Context context) {
        try {
            read(context);
        }
        catch (XmlPullParserException e) {
            System.err.println(e);
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }
}
