package com.example.chapter3.homework;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {
    private static final long serialVersionUID = -6099312954099962806L;
    private String title;
    private String body;

    public Item(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public static ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("Person 1", "This is the first item"));
        items.add(new Item("Person 2", "This is the second item"));
        items.add(new Item("Person 3", "This is the third item"));
        items.add(new Item("Person 4", "This is the 4th item"));
        items.add(new Item("Person 5", "This is the 5th item"));
        items.add(new Item("Person 6", "This is the 6th item"));
        return items;
    }

    @Override
    public String toString() {
        return getTitle();
    }

}
