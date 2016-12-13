package com.wangxuhui.navigationdrawer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

public class DetailOfCommittee extends AppCompatActivity {
    boolean isFavorite;
    Data_committee this_com;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_of_committee);
        MainActivity.setRowIdFromOutside(3);
        getSupportActionBar().setTitle("Committees");
        //parse Gson
        Gson gson = new Gson();
        Intent intent = getIntent();
        String com_data = intent.getStringExtra("coms");
        this_com = gson.fromJson(com_data, Data_committee.class);
        isFavorite = MainActivity.containThisCommittee(this_com);
        //define star_icon        //set favorite star
        final ImageView star_icon = (ImageView)findViewById(R.id.star);
        //import favorite bill data;
        if (isFavorite) {
            star_icon.setImageResource(R.drawable.star_full);
        }
        star_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavorite) {
                    isFavorite = !isFavorite;
                    star_icon.setImageResource(R.drawable.star_empty);
                    MainActivity.deleteThisCommittee(this_com);
                } else {
                    isFavorite = !isFavorite;
                    star_icon.setImageResource(R.drawable.star_full);
                    MainActivity.addThisCommittee(this_com);
                }
            }
        });

        TextView com_id = (TextView)findViewById(R.id.com_id);
        if(this_com.committee_id != null)
            com_id.setText(this_com.committee_id);
        else
            com_id.setText("None");

        TextView name = (TextView)findViewById(R.id.name);
        if(this_com.name != null)
            name.setText(this_com.name);
        else
            name.setText("None");

        ImageView chamber_image = (ImageView)findViewById(R.id.chamber_image);
        TextView chamber = (TextView)findViewById(R.id.chamber);
        if(this_com.chamber != null)
        {
            if (!this_com.chamber.equals("house"))
                chamber_image.setImageResource(R.drawable.ic_s);
            chamber.setText(this_com.chamber.substring(0, 1).toUpperCase() + this_com.chamber.substring(1));
        }
        else
            chamber.setText("None");

        TextView parent = (TextView)findViewById(R.id.parent);
        if(this_com.parent_committee_id != null)
            parent.setText(this_com.parent_committee_id);
        else
            parent.setText("None");

        TextView contact = (TextView)findViewById(R.id.contact);
        if(this_com.phone != null)
            contact.setText(this_com.phone);
        else
            contact.setText("None");

        TextView office = (TextView)findViewById(R.id.office);
        if(this_com.office != null)
            office.setText(this_com.office);
        else
            office.setText("None");
    }
}
