package org.esyz.lagc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

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
 * Created by Sonu on 23-04-2017.
 */

public class GalleryActivity extends Activity {
    private ListView listView;
    private ArrayList<GalleryBean> list = new ArrayList();
    private Animator mCurrentAnimatorEffect;
    private int mShortAnimationDurationEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        listView = (ListView) findViewById(R.id.listView);
        imgBack.setVisibility(View.VISIBLE);
        TextView txtMenu = (TextView) findViewById(R.id.txtMenu);
        txtMenu.setTextColor(getResources().getColor(R.color.white));
        txtMenu.setText("Gallery");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getGallery();
        mShortAnimationDurationEffect = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView=(ImageView)findViewById(R.id.imgEvents);
                zoomImageFromThumb(imageView, list.get(position).getEventlink());
            }
        });
        // addGalleryData();

    }

/*
    private void addGalleryData() {
        GalleryBean galleryBean = new GalleryBean();
        galleryBean.setEventName("Bowling");
        ImageBean imageBean = new ImageBean();
        imageBean.setImgName("https://upload.wikimedia.org/wikipedia/commons/e/e6/Sport_current_event.png");
        imageBeanArrayList.add(imageBean);
        galleryBean.setList(imageBeanArrayList);
        list.add(galleryBean);


        ImageBean imageBean1 = new ImageBean();
        imageBean1.setImgName("https://upload.wikimedia.org/wikipedia/commons/thumb/1/1d/Futbol_current_event.png/1024px-Futbol_current_event.png");
        imageBeanArrayList.add(imageBean1);
        galleryBean1.setList(imageBeanArrayList);
        list.add(galleryBean1);

        GalleryBean galleryBean2 = new GalleryBean();
        ImageBean imageBean2 = new ImageBean();
        imageBean2.setImgName("https://upload.wikimedia.org/wikipedia/commons/4/45/Historic_event_marker_sport_English_Premier_Legue.png");
        imageBeanArrayList.add(imageBean2);
        galleryBean2.setList(imageBeanArrayList);
        list.add(galleryBean2);

        GalleryBean galleryBean3 = new GalleryBean();
        ImageBean imageBean3 = new ImageBean();
        imageBean3.setImgName("http://photos1.meetupstatic.com/photos/event/e/3/8/2/highres_281938242.jpeg");
        imageBeanArrayList.add(imageBean3);
        galleryBean3.setList(imageBeanArrayList);
        list.add(galleryBean3);

        galleryBean.setList(imageBeanArrayList);
        list.add(galleryBean);

    }
*/

    private void getGallery() {
        final ProgressDialog progressDialog = new ProgressDialog(GalleryActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        list.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.154.142.214")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<JsonObject> call = apiCall.getGallary();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Log.e("Gallery Response", "" + jsonObject.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("events");
                    if (jsonArray != null && jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String eventname = "";
                            eventname = jsonArray.getJSONObject(i).getString("event_name");

                            //  galleryBean.setEventName(jsonArray.getJSONObject(i).getString("event_name"));
                            JSONArray jsonArray1 = jsonArray.getJSONObject(i).getJSONArray("images");
                            if (jsonArray1 != null && jsonArray1.length() > 0) {

                                for (int j = 0; j < jsonArray1.length(); j++) {
                                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                                    GalleryBean galleryBean = new GalleryBean();
                                    Log.e("Image Name", "   " + jsonObject1.getString("scalar"));
                                    galleryBean.setEventlink(jsonObject1.getString("scalar"));
                                    galleryBean.setEventName(eventname);
                                    list.add(galleryBean);
                                }


                            }


                        }
                    }

                    NewGalleryAdapter galleryAdapter = new NewGalleryAdapter(GalleryActivity.this, R.layout.new_row_gallery, list);
                    listView.setAdapter(galleryAdapter);

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("GalleryThrowable", "" + t.toString());
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void zoomImageFromThumb(final View thumbView, String path) {
        if (mCurrentAnimatorEffect != null) {
            mCurrentAnimatorEffect.cancel();
        }

        final ImageView expandedImageView = (ImageView) findViewById(
                R.id.expanded_image);
        // expandedImageView.setImageResource(imageResId);
        Picasso.with(GalleryActivity.this)
                .load(path)
                .placeholder(GalleryActivity.this.getResources().getDrawable(R.drawable.icon))
                .error(GalleryActivity.this.getResources().getDrawable(R.drawable.icon))
                .into(expandedImageView);

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.listView)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDurationEffect);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimatorEffect = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimatorEffect = null;
            }
        });
        set.start();
        mCurrentAnimatorEffect = set;

        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimatorEffect != null) {
                    mCurrentAnimatorEffect.cancel();
                }

                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDurationEffect);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimatorEffect = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimatorEffect = null;
                    }
                });
                set.start();
                mCurrentAnimatorEffect = set;
            }
        });
    }
}

