package org.esyz.lagc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sonu on 12-02-2017.
 */

public class SubscribeActivity extends Activity {
    private EditText edtEmailAdd, edtPhone, edtName;
    private Button btnSubmit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        initUi();
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
        txtMenu.setText("Subscribe");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//http://ec2-35-154-142-214.ap-south-1.compute.amazonaws.com/lagc/subscribe.php
        edtEmailAdd = (EditText) findViewById(R.id.edtEmailAdd);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtName = (EditText) findViewById(R.id.edtName);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtName.getText().toString().isEmpty()) {
                    edtName.setError("Please enter name");
                } else if (edtEmailAdd.getText().toString().isEmpty()) {
                    edtEmailAdd.setError("Please enter email address");
                } else if (edtPhone.getText().toString().isEmpty()) {
                    edtPhone.setError("Please enter phone number");
                } else if (!edtEmailAdd.getText().toString().matches(emailPattern)) {
                    edtEmailAdd.setError("Please enter valid email address");
                } else {
                    edtName.setError(null);
                    edtEmailAdd.setError(null);
                    edtPhone.setError(null);
                    subscribe();
                }
            }
        });
    }

    private void subscribe() {
        final ProgressDialog progressDialog = new ProgressDialog(SubscribeActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-35-154-142-214.ap-south-1.compute.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<JsonObject> call = apiCall.subscribe(edtName.getText().toString().trim(), edtEmailAdd.getText().toString().trim(), edtPhone.getText().toString().trim());
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
                  //  Toast.makeText(SubscribeActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    if (status) {
                       // Toast.makeText(SubscribeActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                    AlertDialog.Builder dialog=new AlertDialog.Builder(SubscribeActivity.this);
                    dialog.setMessage(jsonObject.getString("message"));
                    dialog.setTitle("Alert");
                    dialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                    edtName.setText("");
                    edtEmailAdd.setText("");
                    edtPhone.setText("");
                } catch (JSONException e) {
                    if (progressDialog.isShowing())
                        progressDialog.hide();
                     Toast.makeText(SubscribeActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    edtEmailAdd.setText("");
                    edtPhone.setText("");

                    System.out.println("Exception" + e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.hide();
                t.printStackTrace();
                edtName.setText("");
                edtEmailAdd.setText("");
                edtPhone.setText("");
            }
        });


    }
}
