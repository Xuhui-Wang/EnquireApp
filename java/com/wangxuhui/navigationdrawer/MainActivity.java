package com.wangxuhui.navigationdrawer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    TextView header;
    //one TabLayout in this activity
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    LinearLayout side_index_bar;
    private static int row_id = 1;
    private static int col_id = 1;
    public boolean flag1 = false;
    public boolean flag2 = false;
    public boolean flag3 = false;
    public boolean flag4 = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public int getRowId() {
        return row_id;
    }
    //this is for handling cases of click on "favorite" entry;
    public static void setRowIdFromOutside(int i) {
        row_id = i;
        col_id = 1;
    }
    public void setRowId(int i) {
        row_id = i;
        col_id = 1;
        //set Toolbar title when row changes
        Toolbar t = (Toolbar) findViewById(R.id.toolbar);
        switch(i) {
            case 1:
                t.setTitle("Legislators");
                break;
            case 2:
                t.setTitle("Bills");
                break;
            case 3:
                t.setTitle("Committees");
                break;
            case 4:
                t.setTitle("Favorites");
                break;
            default:
        }
        update();
    }

    public int getColId() {
        return col_id;
    }

    public void setColId(int i) {
        col_id = i;
        update();
    }

    public static ArrayList<Data_legislator> legislators_by_state = new ArrayList<>();
    public static ArrayList<Data_legislator> legislators_senate = new ArrayList<>();
    public static ArrayList<Data_legislator> legislators_house = new ArrayList<>();
    public static ArrayList<Data_bill> active_bills = new ArrayList<>();
    public static ArrayList<Data_bill> new_bills = new ArrayList<>();
    public static ArrayList<Data_committee> com_senate = new ArrayList<>();
    public static ArrayList<Data_committee> com_house = new ArrayList<>();
    public static ArrayList<Data_committee> com_joint = new ArrayList<>();
    private static ArrayList<Data_legislator> favoriteLegislators = new ArrayList<>();
    private static ArrayList<Data_bill> favoriteBills = new ArrayList<>();
    public static ArrayList<Data_committee> favoriteComs = new ArrayList<>();

    Map<String, Integer> mapIndex;
    public void update() {
        Log.v("update", "row_id is " + row_id + "col id is " + col_id + "; flag: " + flag1);

        //set listView
        final ListView listView = (ListView) findViewById(R.id.myListView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)listView.getLayoutParams();
        side_index_bar = (LinearLayout)findViewById(R.id.side_index);
        ViewGroup.MarginLayoutParams params1 = (ViewGroup.MarginLayoutParams)side_index_bar.getLayoutParams();
        mapIndex = new HashMap<>();
        //this is for List rendering
        legislatorAdapter adapter_legislator;
        Adapter_bill adapter_bill;
        Adapter_com adapter_com;
        switch (row_id) {
            case 1:

                //make side_bar appear;
                side_index_bar.setVisibility(VISIBLE);
                params.rightMargin = 150;
                params1.width = 150;
                switch (col_id)
                {
                    /*This is when col_id == 1*/
                    case 1:
                        if (flag1) {
                            adapter_legislator = new legislatorAdapter(this, legislators_by_state);
                            listView.setAdapter(adapter_legislator);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Data_legislator thisLegislator = legislators_by_state.get(i);
                                    Intent intent = new Intent(view.getContext(), DetailsOfLegislator.class);
                                    //send this item.toString();
                                    Gson gson = new Gson();
                                    String stringOfThis = gson.toJson(thisLegislator);
                                    intent.putExtra("legi", stringOfThis);
                                    startActivity(intent);
                                }
                            });
                            //first remove all nodes in side_index_bar;
                            if (side_index_bar.getChildCount() > 0) {
                                side_index_bar.removeAllViewsInLayout();
                            }
                            //set side index bar;
                            String cur = "";
                            for (int i = 0; i < legislators_by_state.size(); i++)
                            {
                                if (!cur.equals(legislators_by_state.get(i).state_name.substring(0, 1))) {
                                    cur = legislators_by_state.get(i).state_name.substring(0, 1);
                                    mapIndex.put(cur, i);
                                    TextView tv = new TextView(this);
                                    tv.setText(cur);
                                    final String finalCur = cur;
                                    tv.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                         listView.setSelection(mapIndex.get(finalCur));
                                        }
                                    });
                                    tv.setGravity(0x01);
                                    tv.setTextSize(14);
                                    side_index_bar.addView(tv);
                                }
                            }
                        }
                     break;
                    case 2:
                        if (flag1) {
                            adapter_legislator = new legislatorAdapter(this, legislators_house);
                            listView.setAdapter(adapter_legislator);
                            //set OnClick
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Data_legislator thisLegislator = legislators_house.get(i);
                                    Intent intent = new Intent(view.getContext(), DetailsOfLegislator.class);
                                    //send this item.toString();
                                    Gson gson = new Gson();
                                    String stringOfThis = gson.toJson(thisLegislator);
                                    intent.putExtra("legi", stringOfThis);
                                    startActivity(intent);
                                }
                            });
                            //set index bar;
                            if (side_index_bar.getChildCount() > 0) {
                                side_index_bar.removeAllViewsInLayout();
                            }
                            String cur = "";
                            for (int i = 0; i < legislators_house.size(); i++) {
                                if (!cur.equals(legislators_house.get(i).last_name.substring(0, 1))) {
                                    cur = legislators_house.get(i).last_name.substring(0, 1);
                                    mapIndex.put(cur, i);
                                    TextView tv = new TextView(this);
                                    tv.setText(cur);
                                    final String finalCur = cur;
                                    tv.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            listView.setSelection(mapIndex.get(finalCur));
                                        }
                                    });
                                    tv.setGravity(0x01);
                                    tv.setTextSize(14);
                                    side_index_bar.addView(tv);
                                }
                            }
                        }
                     break;
                    case 3:
                        if (flag1) {
                            adapter_legislator = new legislatorAdapter(this, legislators_senate);
                            listView.setAdapter(adapter_legislator);
                            //set OnClick
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Data_legislator thisLegislator = legislators_senate.get(i);
                                    Intent intent = new Intent(view.getContext(), DetailsOfLegislator.class);
                                    //send this item.toString();
                                    Gson gson = new Gson();
                                    String stringOfThis = gson.toJson(thisLegislator);
                                    intent.putExtra("legi", stringOfThis);
                                    startActivity(intent);
                                }
                            });
                            //set index bar;
                            if (side_index_bar.getChildCount() > 0) {
                                side_index_bar.removeAllViewsInLayout();
                            }
                            String cur = "";
                            for (int i = 0; i < legislators_senate.size(); i++) {
                                if (!cur.equals(legislators_senate.get(i).last_name.substring(0, 1))) {
                                    cur = legislators_senate.get(i).last_name.substring(0, 1);
                                    mapIndex.put(cur, i);
                                    TextView tv = new TextView(this);
                                    tv.setText(cur);
                                    final String finalCur = cur;
                                    tv.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            listView.setSelection(mapIndex.get(finalCur));
                                        }
                                    });
                                    tv.setGravity(0x01);
                                    tv.setTextSize(14);
                                    side_index_bar.addView(tv);
                                }
                            }
                        }
                     break;
                    default:
                        Log.d("nothing", "happened");

                }
                break;
            /*This is when col_id == 2*/
            case 2:
                //make side_bar gone;
                side_index_bar.setVisibility(GONE);
                params.rightMargin = 0;
                if(flag2 && flag3) {
                    ArrayList<Data_bill> data_bills = new ArrayList<>();
                    if (col_id == 1) {
                        data_bills = active_bills;
                    } else {
                        data_bills = new_bills;
                    }
                    adapter_bill = new Adapter_bill(this, data_bills);

                    listView.setAdapter(adapter_bill);
                    //set OnClick
                    final ArrayList<Data_bill> finalData_bills = data_bills;
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Data_bill thisBill = finalData_bills.get(i);
                            Intent intent = new Intent(view.getContext(), DetailsOfBill.class);
                            //send this item.toString();
                            Gson gson = new Gson();
                            String stringOfThis = gson.toJson(thisBill);
                            intent.putExtra("bills", stringOfThis);
                            startActivity(intent);
                        }
                    });
                }
                break;
            case 3:
                //make side_bar gone;
                side_index_bar.setVisibility(GONE);
                params.rightMargin = 0;
                if (flag4) {
                    ArrayList<Data_committee> data_coms = new ArrayList<>();
                    if (col_id == 1)
                        data_coms = com_house;
                    else if (col_id == 2)
                        data_coms = com_senate;
                    else
                        data_coms = com_joint;
                    adapter_com = new Adapter_com(this, data_coms);
                    listView.setAdapter(adapter_com);
                    //set OnClick
                    final ArrayList<Data_committee> finalData_coms = data_coms;
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Data_committee thisCom = finalData_coms.get(i);
                            Intent intent = new Intent(view.getContext(), DetailOfCommittee.class);
                            //send this item.toString();
                            Gson gson = new Gson();
                            String stringOfThis = gson.toJson(thisCom);
                            intent.putExtra("coms", stringOfThis);
                            startActivity(intent);
                        }
                    });
                }

                break;
            case 4:

                //make side_bar gone;
                side_index_bar.setVisibility(GONE);
                params.rightMargin = 0;
                switch(col_id) {
                    case 1:
                        //make side_bar appear;
                        side_index_bar.setVisibility(VISIBLE);
                        params.rightMargin = 150;
                        params1.width = 150;
                        Collections.sort(favoriteLegislators, new Comparator_By_Last_Name());
                        adapter_legislator = new legislatorAdapter(this, favoriteLegislators);
                        listView.setAdapter(adapter_legislator);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Data_legislator thisLegislator = favoriteLegislators.get(i);
                                Intent intent = new Intent(view.getContext(), DetailsOfLegislator.class);
                                //send this item.toString();
                                Gson gson = new Gson();
                                String stringOfThis = gson.toJson(thisLegislator);
                                intent.putExtra("legi", stringOfThis);
                                startActivity(intent);
                            }
                        });
                        //first remove all nodes in side_index_bar;
                        if (side_index_bar.getChildCount() > 0) {
                            side_index_bar.removeAllViewsInLayout();
                        }
                        //set side index bar;
                        String cur = "";
                        for (int i = 0; i < favoriteLegislators.size(); i++)
                        {
                            if (!cur.equals(favoriteLegislators.get(i).last_name.substring(0, 1))) {
                                cur = favoriteLegislators.get(i).last_name.substring(0, 1);
                                mapIndex.put(cur, i);
                                TextView tv = new TextView(this);
                                tv.setText(cur);
                                final String finalCur = cur;
                                tv.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        listView.setSelection(mapIndex.get(finalCur));
                                    }
                                });
                                tv.setGravity(0x01);
                                tv.setTextSize(14);
                                side_index_bar.addView(tv);
                            }
                        }

                        break;
                    case 2:
                        Collections.sort(favoriteBills, new Comparator_by_introducedDate());
                        ArrayList<Data_bill> data_bills = favoriteBills;
                        adapter_bill = new Adapter_bill(this, data_bills);
                        listView.setAdapter(adapter_bill);
                        //set OnClick
                        final ArrayList<Data_bill> finalData_bills = data_bills;
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Data_bill thisBill = finalData_bills.get(i);
                                Intent intent = new Intent(view.getContext(), DetailsOfBill.class);
                                //send this item.toString();
                                Gson gson = new Gson();
                                String stringOfThis = gson.toJson(thisBill);
                                intent.putExtra("bills", stringOfThis);
                                startActivity(intent);
                            }
                        });
                        break;
                    case 3:
                        Collections.sort(favoriteComs, new Comparator_by_com_names());
                        ArrayList<Data_committee> data_coms = favoriteComs;
                        adapter_com = new Adapter_com(this, data_coms);
                        listView.setAdapter(adapter_com);
                        //set OnClick
                        final ArrayList<Data_committee> finalData_coms = data_coms;
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Data_committee thisCom = finalData_coms.get(i);
                                Intent intent = new Intent(view.getContext(), DetailOfCommittee.class);
                                //send this item.toString();
                                Gson gson = new Gson();
                                String stringOfThis = gson.toJson(thisCom);
                                intent.putExtra("coms", stringOfThis);
                                startActivity(intent);
                            }
                        });
                        break;
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Gson gson = new Gson();
        for (Data_legislator d : favoriteLegislators) {
            Log.v("this is ", gson.toJson(d));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        switch(row_id) {
            case 1:
                toolbar.setTitle("Legislators");
                break;
            case 2:
                toolbar.setTitle("Bills");
                break;
            case 3:
                toolbar.setTitle("Committees");
                break;
            case 4:
                toolbar.setTitle("Favorites");
                break;
            default:
        }
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //get the adapter of tab;
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        //set TabLayout
        //set tabs initially(or return from detail page)
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter.clear();
        switch(row_id) {
            case 1:
                viewPagerAdapter.addFragments(new col_1(), "BY STATES");
                viewPagerAdapter.addFragments(new col_2(), "HOUSE");
                viewPagerAdapter.addFragments(new col_3(), "SENATE");
                break;
            case 2:
                viewPagerAdapter.addFragments(new col_1(), "ACTIVE BILLS");
                viewPagerAdapter.addFragments(new col_2(), "NEW BILLS");
                break;
            case 3:
                viewPagerAdapter.addFragments(new col_1(), "HOUSE");
                viewPagerAdapter.addFragments(new col_2(), "SENATE");
                viewPagerAdapter.addFragments(new col_3(), "JOINT");
                break;
            case 4:
                viewPagerAdapter.addFragments(new col_1(), "LEGISLATORS");
                viewPagerAdapter.addFragments(new col_2(), "BILLS");
                viewPagerAdapter.addFragments(new col_3(), "COMMITTEES");
                break;
        }
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int num = tab.getPosition();
                setColId(num + 1);
//                Log.i("onSelected: ", "row: " + row_id + "/ col: " + col_id);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // retrieve data of legislators
        String url = "http://testwxh.pgcrkp2yzt.us-west-2.elasticbeanstalk.com/hw_congress/congress.php?id=1";
        new DownloadURLData().execute(url);
        //get data of bills
        String url1 = "http://testwxh.pgcrkp2yzt.us-west-2.elasticbeanstalk.com/hw_congress/congress.php?id=5";
        new DownloadURLData().execute(url1);

        String url2 = "http://testwxh.pgcrkp2yzt.us-west-2.elasticbeanstalk.com/hw_congress/congress.php?id=6";
        new DownloadURLData().execute(url2);
        //get committee data;
        String url3 = "http://testwxh.pgcrkp2yzt.us-west-2.elasticbeanstalk.com/hw_congress/congress.php?id=3";
        new DownloadURLData().execute(url3);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here
        int id = item.getItemId();
        Log.i("change horizontal : ", "id : " + id);
        //get the adapter of tab;
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        //set TabLayout
        //set tabs initially
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter.clear();
        if (id == R.id.nav_first_layout) {
            setRowId(1);
            viewPagerAdapter.addFragments(new col_1(), "BY STATES");
            viewPagerAdapter.addFragments(new col_2(), "HOUSE");
            viewPagerAdapter.addFragments(new col_3(), "SENATE");
        } else if (id == R.id.nav_second_layout) {
            setRowId(2);
            viewPagerAdapter.addFragments(new col_1(), "ACTIVE BILLS");
            viewPagerAdapter.addFragments(new col_2(), "NEW BILLS");
        } else if (id == R.id.nav_third_layout) {
            setRowId(3);
            viewPagerAdapter.addFragments(new col_1(), "HOUSE");
            viewPagerAdapter.addFragments(new col_2(), "SENATE");
            viewPagerAdapter.addFragments(new col_3(), "JOINT");
        } else if (id == R.id.nav_fourth_layout) {
            setRowId(4);
            viewPagerAdapter.addFragments(new col_1(), "LEGISLATORS");
            viewPagerAdapter.addFragments(new col_2(), "BILLS");
            viewPagerAdapter.addFragments(new col_3(), "COMMITTEES");
        } else if (id == R.id.nav_me) {
            startActivity(new Intent(getApplicationContext(), DetailsAboutMe.class));
        }
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int num = tab.getPosition();
                setColId(num + 1);
                Log.i("onSelected: ", "row: " + row_id + "/ col: " + col_id);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //return
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private class DownloadURLData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String content = "";
            if (urls.length > 0) {
//                Log.i("haha:", urls[0]);
                try {
                    URL url = new URL(urls[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    urlConnection.getInputStream()));

                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null)
                        response.append(inputLine);
                    in.close();
                    content = response.toString();
                    if (urls[0].equals("http://testwxh.pgcrkp2yzt.us-west-2.elasticbeanstalk.com/hw_congress/congress.php?id=1"))
                        content += "1";
                    else if (urls[0].equals("http://testwxh.pgcrkp2yzt.us-west-2.elasticbeanstalk.com/hw_congress/congress.php?id=5"))
                        content += "2";
                    else if (urls[0].equals("http://testwxh.pgcrkp2yzt.us-west-2.elasticbeanstalk.com/hw_congress/congress.php?id=6"))
                        content += "3";
                    else if (urls[0].equals("http://testwxh.pgcrkp2yzt.us-west-2.elasticbeanstalk.com/hw_congress/congress.php?id=3"))
                        content += "4";
                } catch (Exception e) {
                    Log.e("error: ", e.toString());
                }
            }
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            String string = s.substring(0, s.length() - 1);
            Gson gson = new Gson();
            switch (Character.getNumericValue(s.charAt(s.length() - 1))) {
                case 1:
                    legislators_by_state = new ArrayList<>();
                    legislators_senate = new ArrayList<>();
                    legislators_house = new ArrayList<>();
                    Legislators_data_map legi_data = gson.fromJson(string, Legislators_data_map.class);
                    legislators_by_state = legi_data.results;
//                    JSONHelper.exportLegisToJSON(legislators_by_state);
                    // sort legislators_by_state
                    Collections.sort(legislators_by_state, new Comparator_By_Last_Name());
                    Collections.sort(legislators_by_state, new Comparator_By_States());
                    legislators_house = new ArrayList<>();
                    legislators_senate = new ArrayList<>();
                    for (Data_legislator legi : legislators_by_state) {
                        if (legi.chamber.equals("house"))
                            legislators_house.add(legi);
                        else if (legi.chamber.equals("senate"))
                            legislators_senate.add(legi);
                    }
                    // sort legislators_senate && sort legislators_house
                    Collections.sort(legislators_house, new Comparator_By_Last_Name());
                    Collections.sort(legislators_senate, new Comparator_By_Last_Name());
                    flag1 = true;
//                    Log.i("length", legislators_by_state.size() + " / " + legislators_senate.size() + " / " + legislators_house.size());
                    update();
                    break;
                case 2:
                    active_bills = new ArrayList<>();
                    Bills_data_map bills_data_map = gson.fromJson(string, Bills_data_map.class);
                    active_bills = bills_data_map.results;
                    Collections.sort(active_bills, new Comparator_by_introducedDate());
                    flag2 = true;
                    update();
                    break;
                case 3:
                    new_bills = new ArrayList<>();
                    Bills_data_map bills_data_map1 = gson.fromJson(string, Bills_data_map.class);
                    new_bills = bills_data_map1.results;
                    Collections.sort(new_bills, new Comparator_by_introducedDate());
                    flag3 = true;
                    update();
                    break;
                case 4:
                    com_senate = new ArrayList<>();
                    com_house = new ArrayList<>();
                    com_joint = new ArrayList<>();
                    Coms_data_map coms_data_map = gson.fromJson(string, Coms_data_map.class);
                    ArrayList<Data_committee> coms = coms_data_map.results;
                    Collections.sort(coms, new Comparator_by_com_names());
                    for (Data_committee c : coms) {
                        if (c.committee_id.equals("HSFA16"))
                            Log.v("see this", gson.toJson(c));
                        if (c.chamber.equals("senate")) {
                            com_senate.add(c);
                        } else if (c.chamber.equals("house"))
                            com_house.add(c);
                        else
                            com_joint.add(c);
                    }
                    flag4 = true;
//                    Log.v("haha", com_house.size() + "/" + com_senate.size() + "/ " + com_joint);
                    update();
                    break;
                default:

            }
//            Log.v("legi: ", s);

        }
    }

    public static boolean containThis (Data_legislator item) {
        for (Data_legislator d : favoriteLegislators) {
            if (d.bioguide_id.equals(item.bioguide_id))
                return true;
        }
        return false;
    }
    public static boolean containThisBill (Data_bill item) {
        for (Data_bill b : favoriteBills) {
            if (b.bill_id.equals(item.bill_id))
                return true;
        }
        return false;
    }
    public static boolean containThisCommittee (Data_committee item) {
        for (Data_committee b : favoriteComs) {
            if (b.committee_id.equals(item.committee_id))
                return true;
        }
        return false;
    }
    public static void deleteThis (Data_legislator item) {
        for (int i = favoriteLegislators.size() - 1; i >= 0; i--) {
            if (favoriteLegislators.get(i).bioguide_id.equals(item.bioguide_id))
                favoriteLegislators.remove(i);
        }
    }
    public static void deleteThisBill(Data_bill item) {
        for (int i = favoriteBills.size() - 1; i >= 0; i--) {
            if (favoriteBills.get(i).bill_id.equals(item.bill_id))
                favoriteBills.remove(i);
        }
    }
    public static void deleteThisCommittee(Data_committee item) {
        for (int i = favoriteComs.size() - 1; i >= 0; i--) {
            if (favoriteComs.get(i).committee_id.equals(item.committee_id))
                favoriteComs.remove(i);
        }
    }
    public static void addThis (Data_legislator item) {
        favoriteLegislators.add(item);
    }
    public static void addThisBill(Data_bill item) { favoriteBills.add(item);}
    public static void addThisCommittee(Data_committee item) { favoriteComs.add(item);}
}
