package kyo.learncoding.circleoflife;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONObject;

import java.io.Console;

import kyo.learncoding.circleoflife.model.RoleOfCircle;
import kyo.learncoding.circleoflife.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                Gson gson = new Gson();
                for (int i = 1; i <= 6; i++) {
                    String keyName = "role" + i;
                    int roleId = getResources().getIdentifier(keyName, "id", getPackageName());
                    String roleName = ((EditText) findViewById(roleId)).getText().toString();
                    int spinnerId = getResources().getIdentifier("spinner" + i, "id", getPackageName());

                    String currentPoint = ((Spinner) findViewById(spinnerId)).getSelectedItem().toString();
                    RoleOfCircle roleOfCircle = new RoleOfCircle();
                    roleOfCircle.RoleName = roleName;
                    roleOfCircle.CurrentPoint = currentPoint;
                    editor.putString(keyName, gson.toJson(roleOfCircle));
                    editor.apply();
                }
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }


}