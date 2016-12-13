package com.wangxuhui.navigationdrawer;

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


public class Adapter_bill extends ArrayAdapter{
        ArrayList<Data_bill> items;
        LayoutInflater inflater;

        public Adapter_bill(Context context, ArrayList<Data_bill> objects) {
            super(context, R.layout.list_item_bill, objects);
            items = objects;
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_bill, parent, false);
            }
            // get this legislator entry
            Data_bill this_item = items.get(position);
            //set bill id;
            TextView bill_id = (TextView)convertView.findViewById(R.id.bill_id);
            bill_id.setText(this_item.bill_id);
            //set title
            TextView title = (TextView)convertView.findViewById(R.id.bill_title);
            if (this_item.short_title != null)
                title.setText(this_item.short_title);
            else if (this_item.official_title != null)
                title.setText(this_item.official_title);
            else
                title.setText("None");

            TextView bill_date = (TextView)convertView.findViewById(R.id.bill_date);

            if (this_item.introduced_on != null)
            {
                Date d = new Date(this_item.introduced_on);
                bill_date.setText(d.toString());
            }
            else
                bill_date.setText("None");

            return convertView;
        }


}
