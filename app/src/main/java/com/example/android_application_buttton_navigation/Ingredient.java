package com.example.android_application_buttton_navigation;

public class Ingredient {
    private String name;
    private int id;
    public Ingredient(String name, int id){
        this.name = name;
        this.id = id;
    }
    public String getName(){
        return name;
    }

    public int getId() {
        return id;
    }
}