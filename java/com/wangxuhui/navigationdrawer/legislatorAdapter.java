package com.wangxuhui.navigationdrawer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;
public class legislatorAdapter extends ArrayAdapter {
    ArrayList<Data_legislator> items;
    LayoutInflater inflater;

    public legislatorAdapter(Context context, ArrayList<Data_legislator> objects) {
        super(context, R.layout.list_item_legislator, objects);
        items = objects;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_legislator, parent, false);
        }
        // get this legislator entry
        Data_legislator this_item = items.get(position);
        //set Image
        ImageView thumbnail = (ImageView)convertView.findViewById(R.id.imageView);
        String imgUrl = "https://theunitedstates.io/images/congress/original/" + this_item.bioguide_id + ".jpg";
        Picasso.with(getContext()).load(imgUrl).into(thumbnail);
        //lastname, first_name
        TextView nameView = (TextView)convertView.findViewById(R.id.itemNameText);
        nameView.setText(this_item.last_name + ", " + this_item.first_name);
        //district, etc
        TextView bottomText = (TextView)convertView.findViewById(R.id.bottomText);
        bottomText.setText("(" + this_item.party + ")" + this_item.state_name + " - District " + this_item.district);
        return convertView;
    }
}
