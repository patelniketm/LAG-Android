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
 * Created by Sonu on 12-03-2017.
 */

public class CommitteeAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<EventsListBean> list;

    public CommitteeAdapter(Context context, int resource, ArrayList<EventsListBean> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);
            holder = new CommitteeAdapter.Holder();

            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtDesignation = (TextView) convertView.findViewById(R.id.txtDesignation);
            holder.txtEmail = (TextView) convertView.findViewById(R.id.txtEmail);
            holder.imgPerson = (ImageView) convertView.findViewById(R.id.imgPerson);
            convertView.setTag(holder);
        } else {
            holder = (CommitteeAdapter.Holder) convertView.getTag();
        }
        holder.txtName.setText(list.get(position).getName());
        holder.txtDesignation.setText(list.get(position).getDesignation());
        holder.txtEmail.setText(list.get(position).getEmail());
        Picasso.with(context)
                .load(list.get(position).getPhoto_url())
                .placeholder(context.getResources().getDrawable(R.drawable.icon))
                .error(context.getResources().getDrawable(R.drawable.icon))
                .into(holder.imgPerson);

        return convertView;
    }

    public class Holder {
        TextView txtName, txtDesignation, txtEmail;
        ImageView imgPerson;
    }
}
