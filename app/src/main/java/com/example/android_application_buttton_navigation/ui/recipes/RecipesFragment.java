package com.example.android_application_buttton_navigation.ui.recipes;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CpuUsageInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.android_application_buttton_navigation.DatabaseHelper;
import com.example.android_application_buttton_navigation.Ingredient;
import com.example.android_application_buttton_navigation.R;
import com.example.android_application_buttton_navigation.Recipe;

import java.io.IOException;
import java.util.ArrayList;

public class RecipesFragment extends Fragment implements View.OnClickListener {

    private RecipesViewModel recipesViewModel;

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recipesViewModel =
                ViewModelProviders.of(this).get(RecipesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recipes, container, false);
        final String LOG_TAG="myLogs";
        LinearLayout linLayout=(LinearLayout)root.findViewById(R.id.linLayout);
        LayoutInflater ltInflater=getLayoutInflater();


// Работа с БД:
        mDBHelper = new DatabaseHelper(getContext());
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        Cursor cursor = mDb.rawQuery("SELECT * FROM recipes", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            recipes.add(new Recipe(cursor.getString(1), cursor.getString(2),cursor.getInt(0)));
            cursor.moveToNext();
        }
        cursor.close();
//        заполняем дин. массив id ингредиентов в каждом рецепте
        Cursor cursor1 = mDb.rawQuery("SELECT * FROM dishes",null);
        cursor1.moveToFirst();
        while (!cursor1.isAfterLast()){
            int recipe_id = cursor1.getInt(1);
            int ingredient_id = cursor1.getInt(0);
            recipes.get(recipe_id-1).setIngrediets_id(ingredient_id);
            cursor1.moveToNext();
        }
        cursor1.close();
        // Конец работы с бд: динамический массив recipes состоит из классов recipe

        Log.d(LOG_TAG,"i= " + 1);

        for (int i = 0;i < recipes.size();i++) {
            final View item;
            item = ltInflater.inflate(R.layout.new_recipes_common, linLayout, false);
            item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            linLayout.addView(item);
            item.setOnClickListener(this);
        }
        return root;
    }

    @Override
    public void onClick(View v) {

    }
}