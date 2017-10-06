package org.esyz.lagc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sonu on 10-03-2017.
 */

public class EventsAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<EventsListBean> list;

    public EventsAdapter(Context context, int resource, ArrayList<EventsListBean> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);
            viewHolder = new Holder();

            viewHolder.txtMonth = (TextView) convertView.findViewById(R.id.txtMonth);
            viewHolder.txtActivity = (TextView) convertView.findViewById(R.id.txtActivity);
            viewHolder.txtDateVenu = (TextView) convertView.findViewById(R.id.txtDateVenu);
            viewHolder.txtContactPerson = (TextView) convertView.findViewById(R.id.txtContactPerson);
            viewHolder.txtOverview = (TextView) convertView.findViewById(R.id.txtOverview);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }
        viewHolder.txtMonth.setText(list.get(position).getMonth());
        viewHolder.txtActivity.setText(list.get(position).getActivity());
        viewHolder.txtDateVenu.setText(list.get(position).getDateVenu());
        viewHolder.txtContactPerson.setText(list.get(position).getContactPerson());
        viewHolder.txtOverview.setText(list.get(position).getOverview());

        return convertView;
    }

    public class Holder {
        TextView txtMonth, txtActivity, txtDateVenu, txtContactPerson, txtOverview;

    }
}
