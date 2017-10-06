package org.esyz.lagc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout drawerLayout;
    private DrawerArrowDrawable drawerArrow;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private InputMethodManager imm;
    private TextView txtComittee, txtEvents, txtSubscribe, txtGallary, txtAbout, txtLinks;

    private TextView txtEventName;
    private TextView txtTime;
    private TextView txtDate;
    private TextView txtPlace;
    private ImageView imgAbout, imgCommiittee, imgEvents, imgGallery, imgLinks, imgSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        drawerArrow = new DrawerArrowDrawable(this) {
            public boolean isLayoutRtl() {
                return false;
            }
        };

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }
        };


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        HomeEvents();

    }

    private void initUi() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_categorylayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        toolbar.setSubtitle("");
        TextView txtMenu = (TextView) findViewById(R.id.txtMenu);
        txtMenu.setTextColor(getResources().getColor(R.color.white));
        txtMenu.setText("LAGC");
        txtComittee = (TextView) findViewById(R.id.txtComittee);
        txtEvents = (TextView) findViewById(R.id.txtEvents);
        txtSubscribe = (TextView) findViewById(R.id.txtSubscribe);
        txtGallary = (TextView) findViewById(R.id.txtGallary);
        txtAbout = (TextView) findViewById(R.id.txtAbout);
        txtLinks = (TextView) findViewById(R.id.txtLinks);
        imgAbout = (ImageView) findViewById(R.id.imgAbout);
        imgAbout.setOnClickListener(this);
        imgCommiittee = (ImageView) findViewById(R.id.imgCommiittee);
        imgCommiittee.setOnClickListener(this);
        imgEvents = (ImageView) findViewById(R.id.imgEvents);
        imgEvents.setOnClickListener(this);
        imgGallery = (ImageView) findViewById(R.id.imgGallery);
        imgGallery.setOnClickListener(this);
        imgLinks = (ImageView) findViewById(R.id.imgLinks);
        imgLinks.setOnClickListener(this);
        imgSubscribe = (ImageView) findViewById(R.id.imgSubscribe);
        imgSubscribe.setOnClickListener(this);
        txtComittee.setOnClickListener(MainActivity.this);
        txtEvents.setOnClickListener(MainActivity.this);
        txtSubscribe.setOnClickListener(MainActivity.this);
        txtGallary.setOnClickListener(MainActivity.this);
        txtAbout.setOnClickListener(MainActivity.this);
        txtLinks.setOnClickListener(MainActivity.this);
        txtEventName = (TextView) findViewById(R.id.txtEventName);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtEventName = (TextView) findViewById(R.id.txtEventName);

    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.txtComittee:
                intent = new Intent(MainActivity.this, CommitteeMembersActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
                //  finish();
                break;
            case R.id.imgCommiittee:
                intent = new Intent(MainActivity.this, CommitteeMembersActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.txtEvents:
                intent = new Intent(MainActivity.this, EventsActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
                // finish();
                break;
            case R.id.imgEvents:
                intent = new Intent(MainActivity.this, EventsActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
                // finish();
                break;

            case R.id.txtSubscribe:
                intent = new Intent(MainActivity.this, SubscribeActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
                //  finish();
                break;
            case R.id.imgSubscribe:
                intent = new Intent(MainActivity.this, SubscribeActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
                //  finish();
                break;

            case R.id.txtGallary:
                intent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(intent);
               // finish();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.imgGallery:
                intent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(intent);
              //  finish();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case R.id.txtAbout:
                intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
                //  finish();
                break;
            case R.id.imgAbout:
                intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
                //  finish();
                break;

            case R.id.txtLinks:
                intent = new Intent(MainActivity.this, LinksAcivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
                // finish();
                break;
            case R.id.imgLinks:
                intent = new Intent(MainActivity.this, LinksAcivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
                // finish();
                break;

        }
    }

    private void HomeEvents() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.154.142.214/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<JsonObject> call = apiCall.latest_event();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (progressDialog.isShowing())
                    progressDialog.hide();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    System.out.println("Response------" + jsonObject.getString("status"));
                    Log.e("Response ", "" + jsonObject.getString("status"));
                    Boolean status = Boolean.valueOf(jsonObject.getString("status"));
                    if (status) {
                        JSONArray jsonArray = jsonObject.getJSONArray("events");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            txtEventName.setText(jsonArray.getJSONObject(i).getString("Activity"));
                            txtTime.setText(jsonArray.getJSONObject(i).getString("datetime1"));
                            txtDate.setText(jsonArray.getJSONObject(i).getString("venue"));
                        }
                    }
                } catch (JSONException e) {
                    if (progressDialog.isShowing())
                        progressDialog.hide();
                    System.out.println("Exception" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.hide();
            }
        });
    }
}
