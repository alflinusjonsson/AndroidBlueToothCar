/*
 * Data class
 * Used for creating a ArrayList that is later used for saving commands sent by the car
 * 2019-03-21 Version 1.0
 */

package se.j.androidprojekt;

import java.util.ArrayList;

public class Data {

    public static ArrayList<logList> log = new ArrayList<>();

    public static class logList{
        public String title;
        public logList(String title) { this.title = title;  }
        @Override
        public String toString()  { return title; }

    }
}
