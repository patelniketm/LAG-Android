package org.esyz.lagc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sonu on 16-04-2017.
 */

public class GalleryAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private ArrayList<GalleryBean> list;

    public GalleryAdapter(Context context, int resource, ArrayList<GalleryBean> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GalleryHolder galleryHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);

            galleryHolder = new GalleryHolder();
            galleryHolder.txtEvents = (TextView) convertView.findViewById(R.id.txtEvents);
            galleryHolder.ll_images = (LinearLayout) convertView.findViewById(R.id.ll_images);
            galleryHolder.imgOne = (ImageView) convertView.findViewById(R.id.imgOne);
            galleryHolder.imgTwo = (ImageView) convertView.findViewById(R.id.imgTwo);
            galleryHolder.imgThree = (ImageView) convertView.findViewById(R.id.imgThree);
            galleryHolder.imgFour = (ImageView) convertView.findViewById(R.id.imgFour);
            galleryHolder.imgFive = (ImageView) convertView.findViewById(R.id.imgFive);
            galleryHolder.imgSix = (ImageView) convertView.findViewById(R.id.imgSix);
            convertView.setTag(galleryHolder);
        } else {
            galleryHolder = (GalleryHolder) convertView.getTag();
            galleryHolder.txtEvents.setText(list.get(position).getEventName());
            if (list.get(position).getList() != null && list.get(position).getList().size() > 0) {
                Picasso.with(context)
                        .load(list.get(position).getList().get(0).getImgName())
                        .placeholder(context.getResources().getDrawable(R.drawable.icon))
                        .error(context.getResources().getDrawable(R.drawable.icon))
                        .into(galleryHolder.imgOne);
                Picasso.with(context)
                        .load(list.get(position).getList().get(1).getImgName())
                        .placeholder(context.getResources().getDrawable(R.drawable.icon))
                        .error(context.getResources().getDrawable(R.drawable.icon))
                        .into(galleryHolder.imgTwo);
                Picasso.with(context)
                        .load(list.get(position).getList().get(2).getImgName())
                        .placeholder(context.getResources().getDrawable(R.drawable.icon))
                        .error(context.getResources().getDrawable(R.drawable.icon))
                        .into(galleryHolder.imgThree);
                Picasso.with(context)
                        .load(list.get(position).getList().get(3).getImgName())
                        .placeholder(context.getResources().getDrawable(R.drawable.icon))
                        .error(context.getResources().getDrawable(R.drawable.icon))
                        .into(galleryHolder.imgFour);
                Picasso.with(context)
                        .load(list.get(position).getList().get(4).getImgName())
                        .placeholder(context.getResources().getDrawable(R.drawable.icon))
                        .error(context.getResources().getDrawable(R.drawable.icon))
                        .into(galleryHolder.imgFive);
                Picasso.with(context)
                        .load(list.get(position).getList().get(5).getImgName())
                        .placeholder(context.getResources().getDrawable(R.drawable.icon))
                        .error(context.getResources().getDrawable(R.drawable.icon))
                        .into(galleryHolder.imgSix);
            } else {
                // galleryHolder.ll_images.setVisibility(View.GONE);
            }

        }
        return convertView;
    }

    public class GalleryHolder {
        ImageView imgOne;
        ImageView imgTwo;
        ImageView imgThree;
        ImageView imgFour;
        ImageView imgFive;
        ImageView imgSix;
        LinearLayout ll_images;
        TextView txtEvents;
    }
}
