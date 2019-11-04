package com.example.android_application_buttton_navigation.ui.ingredients;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.android_application_buttton_navigation.DatabaseHelper;
import com.example.android_application_buttton_navigation.Ingredient;
import com.example.android_application_buttton_navigation.MainActivity;
import com.example.android_application_buttton_navigation.R;

import java.io.IOException;
import java.util.ArrayList;

public class IngredientsFragment extends Fragment implements View.OnClickListener {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    private IngredientsViewModel ingredientsViewModel;
    final String LOG_TAG="myLogs";
    Button btnChecked;
    ListView lvMain;
    TextView textView;
    public ArrayList<Ingredient> ingredient = new ArrayList<Ingredient>();
    public ArrayList<String> name = new ArrayList<String>();

//    String[] names={"лучок","плов","баклажан","помидор"};


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ingredientsViewModel =
                ViewModelProviders.of(this).get(IngredientsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ingredients, container, false);
        lvMain=root.findViewById(R.id.lvMain);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        btnChecked=root.findViewById(R.id.btnChecked);
        btnChecked.setOnClickListener(this);

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
        Cursor cursor = mDb.rawQuery("SELECT * FROM ingredients", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ingredient.add(new Ingredient(cursor.getString(1), cursor.getInt(0)));
            name.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        // Конец работы с бд: массив names содержит все ингредиенты из бд

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_multiple_choice, name);

        lvMain.setAdapter(adapter);


        return root;
    }

    public void onClick(View v){
        Log.d(LOG_TAG,"выбранные ингридиенты: ");
        SparseBooleanArray sbArray=lvMain.getCheckedItemPositions();
        for (int i=0;i<sbArray.size();i++){
            int key=sbArray.keyAt(i);
            if (sbArray.get(key)){
//                Log.d(LOG_TAG,names[key]);

            }
        }
    }
}