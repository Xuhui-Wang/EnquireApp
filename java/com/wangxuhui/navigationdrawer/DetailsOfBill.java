package com.wangxuhui.navigationdrawer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

public class DetailsOfBill extends AppCompatActivity {
    boolean isFavorite;
    Data_bill this_bill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_bill);
        MainActivity.setRowIdFromOutside(2);
        getSupportActionBar().setTitle("Bill Info");
        Gson gson = new Gson();
        Intent intent = getIntent();
        String bill_data = intent.getStringExtra("bills");
        this_bill = gson.fromJson(bill_data, Data_bill.class);
        isFavorite = MainActivity.containThisBill(this_bill);
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
                    MainActivity.deleteThisBill(this_bill);
                } else {
                    isFavorite = !isFavorite;
                    star_icon.setImageResource(R.drawable.star_full);
                    MainActivity.addThisBill(this_bill);
                }
            }
        });
        TextView bill_id = (TextView)findViewById(R.id.detail_bill_id);
        if (this_bill.bill_id != null)
            bill_id.setText(this_bill.bill_id);
        else
            bill_id.setText("None");

        TextView title = (TextView)findViewById(R.id.detail_title);
        if (this_bill.short_title != null)
            title.setText(this_bill.short_title);
        else if (this_bill.official_title != null)
            title.setText(this_bill.official_title);
        else
            title.setText("None");

        TextView bill_type = (TextView)findViewById(R.id.detail_bill_type);
        if (this_bill.bill_type != null)
            bill_type.setText(this_bill.bill_type);
        else
            bill_type.setText("None");

        TextView sponsor = (TextView)findViewById(R.id.detail_sponsor);
        if (this_bill.sponsor.first_name == null && this_bill.sponsor.last_name == null && this_bill.sponsor.title == null)
            sponsor.setText("None");
        else {
            String full_name = "";
            int num = 0;
            if (this_bill.sponsor.title != null)
            {
                num ++;
                full_name += this_bill.sponsor.title;
            }
            if (this_bill.sponsor.last_name != null)
            {
                if (num > 0)
                    full_name += ", ";
                full_name += this_bill.sponsor.last_name;
                num++;
            }
            if (this_bill.sponsor.first_name != null)
            {
                if (num > 0)
                    full_name += ", ";
                full_name += this_bill.sponsor.first_name;
                num++;
            }
            sponsor.setText(full_name);
        }

        TextView chamber = (TextView)findViewById(R.id.detail_chamber);
        if (this_bill.chamber != null)
            chamber.setText(this_bill.chamber.substring(0, 1).toUpperCase() + this_bill.chamber.substring(1));
        else
            chamber.setText("None");

        TextView status = (TextView)findViewById(R.id.detail_status);
        if (this_bill.history.active)
            status.setText("Active");
        else
            status.setText("New");

        TextView introduced_on = (TextView)findViewById(R.id.detail_introduced_on);
        if (this_bill.introduced_on != null)
            introduced_on.setText(new Date(this_bill.introduced_on).toString());
        else
            introduced_on.setText("None");

        TextView congress_url = (TextView)findViewById(R.id.detail_congress_url);
        if (this_bill.urls.congress != null)
            congress_url.setText(this_bill.urls.congress);
        else
            congress_url.setText("None");

        TextView version = (TextView)findViewById(R.id.detail_version);
        if (this_bill.last_version.version_name != null)
            version.setText(this_bill.last_version.version_name);
        else
            version.setText("None");

        TextView bill_url = (TextView)findViewById(R.id.detail_bill_url);
        if (this_bill.last_version.urls.pdf != null)
            bill_url.setText(this_bill.last_version.urls.pdf);
        else
            bill_url.setText("None");
    }
}
