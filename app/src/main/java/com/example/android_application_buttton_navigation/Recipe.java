package com.example.android_application_buttton_navigation;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class Recipe {
    private String name;
    private String description;
    private   int id;
    public ArrayList<Integer> ingrediet_id = new ArrayList<Integer>();
    public Recipe(String name, String description, int id){
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setIngrediets_id(int ingrediet_id) {
        this.ingrediet_id.add(ingrediet_id);
    }
}
