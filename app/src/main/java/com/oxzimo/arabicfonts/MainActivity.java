package com.oxzimo.arabicfonts;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;


public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {
    ArrayList<HashMap<String, String>> fontsList;
    TextView txtHelloWorld;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayShowTitleEnabled(false);

        txtHelloWorld = (TextView) findViewById(R.id.txt_hello_world);

        fontsList = new ArrayList<HashMap<String, String>>();


        readFonts(this);

        //ArrayAdapter<Pair<String, String>> aAdpt = new ArrayAdapter<Pair<String, String>>(this, android.R.layout.simple_list_item_1, android.R.id.text1, fontsList);
        SimpleAdapter adapter = new SimpleAdapter(this, fontsList, android.R.layout.simple_list_item_1,
                new String[]{"name"}, new int[]{android.R.id.text1});
        actionBar.setListNavigationCallbacks(adapter, this);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        CalligraphyUtils.applyFontToTextView(this, txtHelloWorld, fontsList.get(position).get("value"));
        return true;
    }

    public void readFonts(Context context) {
        AssetManager am = context.getAssets();
        try {
            String[] fonts = am.list("fonts");
            for (String font : fonts) {
                HashMap<String, String> map = new HashMap<String, String>();
                String name = font.replace(".ttf", "");
                String value = "fonts/".concat(font);
                map.put("name", name);
                map.put("value", value);
                fontsList.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
