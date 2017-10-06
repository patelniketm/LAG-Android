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

public class CommitteeMembersActivity extends Activity {
    private ListView listCommittee;
    private ArrayList<EventsListBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee);
        initUi();
        getEventsList();
    }

    private void initUi() {
        //setDrawerLeftEdgeSize(MainActivity.this, drawerLayout, 0.7f);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setSubtitle("");
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setVisibility(View.VISIBLE);
        TextView txtMenu = (TextView) findViewById(R.id.txtMenu);
        txtMenu.setTextColor(getResources().getColor(R.color.white));
        txtMenu.setText("Committee Members");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listCommittee = (ListView) findViewById(R.id.listCommittee);
    }

    private void getEventsList() {
        final ProgressDialog progressDialog = new ProgressDialog(CommitteeMembersActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.154.142.214/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<JsonObject> call = apiCall.getCommitte();
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
                            bean.setName(jsonArray.getJSONObject(i).getString("name"));
                            bean.setDesignation(jsonArray.getJSONObject(i).getString("designation"));
                            bean.setEmail(jsonArray.getJSONObject(i).getString("email"));
                            bean.setPhoto_url(jsonArray.getJSONObject(i).getString("photo_url"));
                            //photo_url
                            list.add(bean);
                        }
                    }
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println("Events List  " + list.get(i).getOverview());
                    }
                    if (list != null && list.size() > 0) {
                        CommitteeAdapter adapter = new CommitteeAdapter(CommitteeMembersActivity.this, R.layout.row_committee, list);
                        listCommittee.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    System.out.println("Exception" + e.toString());
                    if (progressDialog.isShowing())
                        progressDialog.hide();

                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.hide();
                t.printStackTrace();
            }
        });


    }

}
