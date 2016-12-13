package com.wangxuhui.navigationdrawer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class DetailsOfLegislator extends AppCompatActivity {
    private boolean isFavorite = false;
    private Data_legislator this_legi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This is for back button;
        MainActivity.setRowIdFromOutside(1);
        setContentView(R.layout.activity_details_of_legislator);
        getSupportActionBar().setTitle("Legislator Info");
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        //first get Intent data;
        Intent intent = getIntent();
        Gson gson = new Gson();
        String legi_data = intent.getStringExtra("legi");
        this_legi = gson.fromJson(legi_data, Data_legislator.class);
        isFavorite = MainActivity.containThis(this_legi);
        //define star_icon
        final ImageView star_icon = (ImageView)findViewById(R.id.star);
        //import favorite legislator data;
        if (isFavorite) {
            star_icon.setImageResource(R.drawable.star_full);
        }
        star_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavorite) {
                    isFavorite = !isFavorite;
                    star_icon.setImageResource(R.drawable.star_empty);
                    MainActivity.deleteThis(this_legi);
                } else {
                    isFavorite = !isFavorite;
                    star_icon.setImageResource(R.drawable.star_full);
                    MainActivity.addThis(this_legi);
                }
            }
        });
        ImageView fb_icon = (ImageView)findViewById(R.id.fb);
        fb_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (this_legi.facebook_id != null) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("http://www.facebook.com/" + this_legi.facebook_id));
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "this link does NOT exist!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageView tt_icon = (ImageView)findViewById(R.id.twitter);
        tt_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (this_legi.twitter_id != null) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("http://www.twitter.com/" + this_legi.twitter_id));
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "this link does NOT exist!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageView website_icon = (ImageView)findViewById(R.id.website);
        website_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (this_legi.website != null) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(this_legi.website));
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "this link does NOT exist!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        final ImageView photo_legi = (ImageView)findViewById(R.id.photo);
        if (this_legi.bioguide_id != null) {
            String imgUrl = "https://theunitedstates.io/images/congress/original/" + this_legi.bioguide_id + ".jpg";
                Picasso.with(getApplicationContext()).load(imgUrl).into(photo_legi);
        }
        ImageView party_image = (ImageView)findViewById(R.id.party_image);
        TextView party_text = (TextView)findViewById(R.id.party_text);
        if (this_legi.party.equals("R")) {
            party_image.setImageResource(R.drawable.r);
            party_text.setText("Republican");
        } else if(this_legi.party.equals("D")) {
            party_image.setImageResource(R.drawable.d);
            party_text.setText("Democrat");
        }
        int nameSize = 0;
        TextView legi_name = (TextView)findViewById(R.id.legi_name);
        String full_name = "";
        if (this_legi.title != null) {
            full_name += this_legi.title;
            nameSize++;
        }
        if (this_legi.last_name != null) {
            if (nameSize > 0)
                full_name += ", ";
            full_name += this_legi.last_name;
            nameSize++;
        }
        if (this_legi.first_name != null) {
            if (nameSize > 0)
                full_name += ", ";
            full_name += this_legi.first_name;
            nameSize++;
        }
        legi_name.setText(full_name);
        //set email
        TextView legi_email = (TextView)findViewById(R.id.legi_email);
        if (this_legi.oc_email != null)
            legi_email.setText(this_legi.oc_email);
        else
            legi_email.setText("None");
        //set chamber
        TextView legi_chamber = (TextView)findViewById(R.id.legi_chamber);
        String this_chamber = "None";
        if (this_legi.chamber != null) {
            this_chamber = this_legi.chamber;
            this_chamber = this_chamber.substring(0, 1).toUpperCase() + this_chamber.substring(1);
        }
        legi_chamber.setText(this_chamber);
        //set Contact
        TextView legi_phone = (TextView)findViewById(R.id.legi_phone);
        String this_phone = "None";
        if (this_legi.phone != null) {
            this_phone = this_legi.phone;
        }
        legi_phone.setText(this_phone);

        //start term
        TextView legi_start = (TextView)findViewById(R.id.legi_term_start);
        String this_term_start= "None";
        if (this_legi.term_start != null) {
            Date Date1 = new Date(this_legi.term_start);
            this_term_start = Date1.toString();
        }
        legi_start.setText(this_term_start);
        //end term
        TextView legi_end = (TextView)findViewById(R.id.legi_term_end);
        String this_term_end = "None";
        if (this_legi.term_end != null) {
            Date Date2 = new Date(this_legi.term_end);
            this_term_end = Date2.toString();
        }
        legi_end.setText(this_term_end);
        ProgressBar legi_term = (ProgressBar)findViewById(R.id.legi_term);
        TextView legi_term_text = (TextView)findViewById(R.id.legi_term_text);
        if (this_legi.term_start != null && this_legi.term_end != null) {
            Date Date1 = new Date(this_legi.term_start);
            Date Date2 = new Date(this_legi.term_end);
            Calendar calendar = Calendar.getInstance();
            Date nowDate = new Date(calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
            int progress = 100 * (nowDate.dateValue() - Date1.dateValue()) / (Date2.dateValue() - Date1.dateValue());
            legi_term.setProgress(progress);
            legi_term_text.setText(progress + "%");
        } else {
            legi_term.setProgress(0);
            legi_term_text.setText("None");
        }
        TextView legi_office = (TextView)findViewById(R.id.legi_office);
        String this_office = "None";
        if (this_legi.office != null)
            this_office = this_legi.office;
        legi_office.setText(this_office);
        TextView legi_state = (TextView)findViewById(R.id.legi_state);
        String this_state = "None";
        if (this_legi.state != null)
            this_state = this_legi.state;
        legi_state.setText(this_state);
        //set fax
        TextView legi_fax = (TextView)findViewById(R.id.legi_fax);
        String this_fax = "None";
        if (this_legi.fax != null)
            this_fax = this_legi.fax;
        legi_fax.setText(this_fax);
        //set birthday
        TextView legi_birthday = (TextView)findViewById(R.id.legi_birthday);
        String this_birthday = "None";
        if (this_legi.birthday != null)
        {
            Date birthday = new Date(this_legi.birthday);
            this_birthday = birthday.toString();
        }
        legi_birthday.setText(this_birthday);
    }
}
