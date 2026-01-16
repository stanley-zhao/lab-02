package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button add_button, confirm_button, delete_button;
    // Source - https://stackoverflow.com/a/19806319
    // Posted by Ruyonga Dan
    // Retrieved 2026-01-14, License - CC BY-SA 3.0
    LinearLayout input_linear_layout;
    boolean visibility = false;
    //Source - https://stackoverflow.com/a/34574187
    //Posted by Chandra Kumar, modified by community. See post 'Timeline' for change history
    //Retrieved 2026-01-14, License - CC BY-SA 4.0
    EditText user_input;
    String city_name;

    int lastSlectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cityList = findViewById(R.id.city_list);
        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        user_input = findViewById(R.id.edit_text);
        input_linear_layout =  findViewById(R.id.add_city_to_list_linear_layout);
        add_button = findViewById(R.id.add_button);
        delete_button = findViewById(R.id.delete_button);
        confirm_button = findViewById(R.id.confirm_button);

        add_button.setOnClickListener(v -> {
            if(!visibility){
                input_linear_layout.setVisibility(View.VISIBLE);
                visibility = true;
            }else{
                input_linear_layout.setVisibility(View.GONE);
                visibility = false;
            }
        });

        confirm_button.setOnClickListener(v -> {
            city_name = user_input.getText().toString();
            if(!city_name.isEmpty()) {
                dataList.add(city_name);
                // get idea from https://stackoverflow.com/a/4194320/22933385
                cityAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(this,"None Of Cities Added",Toast.LENGTH_SHORT).show();
            }
        });

        // Source - https://stackoverflow.com/a/20520795
    // Posted by Ashish Bindal
    // Retrieved 2026-01-14, License - CC BY-SA 3.0
    //set the selected item according after changing anther base adapter
    //To find last selected item position
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                lastSlectedPosition = position;
            }
        });

        delete_button.setOnClickListener(v->{
            if(lastSlectedPosition!=-1){
                dataList.remove(lastSlectedPosition);
                cityAdapter.notifyDataSetChanged();
                lastSlectedPosition = -1;
                Toast.makeText(this,"Deleted",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"None Of Cities Selected",Toast.LENGTH_SHORT).show();
            }
        });

    }
}