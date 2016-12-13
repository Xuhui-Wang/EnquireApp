package com.wangxuhui.navigationdrawer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class Adapter_com extends ArrayAdapter{
    ArrayList<Data_committee> items;
    LayoutInflater inflater;

    public Adapter_com(Context context, ArrayList<Data_committee> objects) {
        super(context, R.layout.list_item_committee, objects);
        items = objects;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_committee, parent, false);
        }
        // get this committee entry
        Data_committee this_item = items.get(position);
        //set committee id;
        TextView comm_id = (TextView)convertView.findViewById(R.id.com_id);
        comm_id.setText(this_item.committee_id);
        //set title
        TextView title = (TextView)convertView.findViewById(R.id.com_title);
        if (this_item.name != null)
            title.setText(this_item.name);
        else
            title.setText("None");

        TextView chamber = (TextView)convertView.findViewById(R.id.com_date);
        if (this_item.chamber != null)
        {
            String s = this_item.chamber;
            chamber.setText(s.substring(0, 1).toUpperCase() + s.substring(1));
        }
        else
            chamber.setText("None");

        return convertView;
    }

}
