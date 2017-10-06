package org.esyz.lagc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sonu on 12-02-2017.
 */

public class EventsActivity extends Activity {
    private ListView listEvents;
    private ArrayList<EventsListBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        initUi();
        getEventsList();
    }

    private void initUi() {
        //setDrawerLeftEdgeSize(MainActivity.this, drawerLayout, 0.7f);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setVisibility(View.VISIBLE);
        TextView txtMenu = (TextView) findViewById(R.id.txtMenu);
        txtMenu.setTextColor(getResources().getColor(R.color.white));
        txtMenu.setText("Events");
        listEvents = (ListView) findViewById(R.id.listEvents);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getEventsList() {
        final ProgressDialog progressDialog = new ProgressDialog(EventsActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.154.142.214/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<JsonObject> call = apiCall.latest_event_list();
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
                            EventsListBean bean = new EventsListBean();
                            bean.setMonth(jsonArray.getJSONObject(i).getString("Month"));
                            bean.setActivity(jsonArray.getJSONObject(i).getString("activity"));
                            bean.setDateVenu(jsonArray.getJSONObject(i).getString("DateVenu"));
                            bean.setContactPerson(jsonArray.getJSONObject(i).getString("ContactPerson"));
                            bean.setOverview(jsonArray.getJSONObject(i).getString("Overview"));
                            list.add(bean);
                        }
                    }

                    if (list != null && list.size() > 0) {
                        EventsAdapter adapter = new EventsAdapter(EventsActivity.this, R.layout.row_events, list);
                        listEvents.setAdapter(adapter);
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
