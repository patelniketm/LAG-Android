package org.esyz.lagc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sonu on 23-04-2017.
 */

public class NewGalleryAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private ArrayList<GalleryBean> list;
    private String eventName = "";

    public NewGalleryAdapter(Context context, int resource, ArrayList<GalleryBean> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Gallery galleryHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);

            galleryHolder = new Gallery();
            galleryHolder.txtEvents = (TextView) convertView.findViewById(R.id.txtEvents);

            galleryHolder.imgEvents = (ImageView) convertView.findViewById(R.id.imgEvents);
            convertView.setTag(galleryHolder);
        } else {
            galleryHolder = (Gallery) convertView.getTag();
            if (list != null && list.size() > 0) {
                galleryHolder.txtEvents.setText(list.get(position).getEventName());

                Picasso.with(context)
                        .load(list.get(position).getEventlink())
                        .placeholder(context.getResources().getDrawable(R.drawable.icon))
                        .error(context.getResources().getDrawable(R.drawable.icon))
                        .into(galleryHolder.imgEvents);

            }
        }
        return convertView;
    }

    public class Gallery {
        ImageView imgEvents;

        TextView txtEvents;
    }
}

