package bit.sikander.kickdown;

/**
 * Created by Sikander on 2017-04-26.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import bit.sikander.kickdown.models.CarRepairGoogleMaps;


public class ListActivity extends AppCompatActivity {
    public Context con = this;
    // Log tag
    private static final String TAG = ListActivity.class.getSimpleName();


    final String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + Constants.LAT + "," + Constants.LNG + "&radius=" + Constants.RADIUS + "&type=" + Constants.TYPE + "&key=" + Constants.API_KEY;

    Boolean isInternetPresent = false;
    private ProgressDialog pDialog;
    private List<CarRepairGoogleMaps> rdataList;
    private ListView listView;
    private CustomListAdapter adapter;
    private ShareActionProvider myShareActionProvider;
    private Toolbar mToolbar;
    ConnectionDetector cd;
    SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quick_list);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("Car repair centres near you..");
        rdataList = new ArrayList<CarRepairGoogleMaps>();
        cd = new ConnectionDetector(this.getApplicationContext());
        listView = (ListView) findViewById(R.id.rrlist);
        adapter = new CustomListAdapter(this, rdataList);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        checkStatus();


    }

    void dataParsing(){

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
                        Log.e("UUUUURL", url);
                        hidePDialog();
                        try {
                            ///////////////////////////////////////////////////////////////////////
                            JSONArray arrProducts = response.getJSONArray("results");
                            for (int i = 0; i < arrProducts.length(); i++) {


                                JSONObject obj = arrProducts.getJSONObject(i);

                                CarRepairGoogleMaps rCarRepairList = new CarRepairGoogleMaps();

                                rCarRepairList.setName(obj.getString("name"));


                                try {
                                    rCarRepairList.setRating(obj.getString("rating"));
                                } catch (Exception e) {
                                    // TODO: handle exception
                                }

                                try {
                                    rCarRepairList.setReference(obj.getString("reference"));
                                } catch (Exception e) {
                                    // TODO: handle exception
                                }


                                rCarRepairList.setVicinity(obj.getString("vicinity"));


                                try {
                                    JSONArray photoArray = obj.getJSONArray("photos");


                                    for (int j = 0; j < photoArray.length(); j++) {

                                        final JSONObject photoObject = photoArray.getJSONObject(j);

                                        try {
                                            rCarRepairList.setPhotoReference(photoObject.getString("photo_reference"));

                                            Constants.photoReferrence = photoObject.getString("photo_reference");


                                            String imgUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=190&photoreference="
                                                    + Constants.photoReferrence
                                                    + "&sensor=true&key=" + Constants.API_KEY;

                                            rCarRepairList.setThumUrl(imgUrl);

                                        } catch (Exception e) {
                                            // TODO: handle exception
                                        }
                                    }


                                } catch (Exception e) {
                                    // TODO: handle exception
                                }


                                try {
                                    JSONObject rOpen = obj.getJSONObject("opening_hours");

                                    try {
                                        rCarRepairList.setOpenOrClose(rOpen.getString("open_now"));

                                    } catch (Exception e) {
                                        // TODO: handle exception
                                    }


                                } catch (Exception e) {
                                    // TODO: handle exception
                                }


////////////////////////////////////////////////////////////////////////////////////////////

                                rdataList.add(rCarRepairList);
                                adapter.add(rCarRepairList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            PrintLog.myLog("STATUS", "ParsingFinished");
                        }
                        adapter.notifyDataSetChanged();
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) Log.e("List Activity", error.getMessage());

                    }
                });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }


    void checkStatus(){

        isInternetPresent = cd.isConnectingToInternet();

        // check for Internet status
        if (isInternetPresent) {
            // Internet Connection is Present
            // make HTTP requests
//        showAlertDialog(getActivity(), "Internet Connection",
//                "You have internet connection", true);
            dataParsing();
        } else {
            // Internet connection is not present
            // Ask user to connect to Internet
//            myWebView.loadUrl("file:///android_asset/error.html");
            showAlertDialog(ListActivity.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }

    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutosearch, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);

        search = (SearchView) myActionMenuItem.getActionView();

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                adapter.filter(newText);
                return false;
            }
        });
        return true;
    }


    public class CustomListAdapter extends BaseAdapter {
        private Activity activity;
        private LayoutInflater inflater;
        private List<CarRepairGoogleMaps> rItems;
        private ArrayList<CarRepairGoogleMaps> arraylist;
//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        public CustomListAdapter(Activity activity, List<CarRepairGoogleMaps> items) {
            this.activity = activity;
            this.rItems = new ArrayList<>(items);
            this.arraylist = new ArrayList<>(items);
        }
        public void add(CarRepairGoogleMaps rm) {
            rItems.add(rm);
            arraylist.add(rm);
        }

        @Override
        public int getCount() {
            return rItems.size();

        }

        @Override
        public Object getItem(int location) {
            return rItems.get(location);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (inflater == null)
                inflater = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null)
                convertView = inflater.inflate(R.layout.list_row, null);

//            if (imageLoader == null)
//                imageLoader = AppController.getInstance().getImageLoader();
//            NetworkImageView thumbNail = (NetworkImageView) convertView
//                    .findViewById(R.id.thumbnail);


            TextView name = (TextView) convertView.findViewById(R.id.rowName);
            TextView address = (TextView) convertView.findViewById(R.id.rowAddress);
            ImageView opencloseview = (ImageView) convertView.findViewById(R.id.openclose);

            final RatingBar listRatings = (RatingBar) convertView.findViewById(R.id.ratingBarList);


            final CarRepairGoogleMaps m = rItems.get(position);


            String oc = m.getOpenOrClose();


            if (oc.equals("false")) {

                opencloseview.setImageResource(R.drawable.closed);

            } else {

                opencloseview.setImageResource(R.drawable.open);

            }


            try {
                String rating = m.getRating();


                Float count = Float.parseFloat(rating);

                listRatings.setRating(count);

            } catch (Exception e) {

            }

//            try {
//                // thumbnail image
//                thumbNail.setImageUrl(m.getThumUrl(), imageLoader);
//
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
            // name
//            name.setText(m.getShort_name());

            try {
                name.setText(m.getName());

            } catch (Exception e) {
                // TODO: handle exception
            }


            try {
                address.setText(m.getVicinity());

            } catch (Exception e) {
                // TODO: handle exception
            }


//      Onclick Set

            convertView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

//                    Log.e("Position", rItems.get(position).toString());
                    String name = m.getName();
                    String msg = m.getVicinity();

                    Constants.dReferrence = m.getReference();
                    Log.e("dReferrence", m.getReference());

                    try {
                        Constants.photoReferrence = m
                                .getPhotoReference().toString().trim();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
//                    final Intent iii = new Intent(con,
//                            ListDetailsActivity.class);
//                    iii.putExtra("POSITION", position);
//                    iii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(iii);

                }
            });

//        }
            return convertView;
        }

        // Filter Class
        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            rItems.clear();
            if (charText.length() == 0) {
                rItems.addAll(arraylist);
            } else {
                for (CarRepairGoogleMaps wp : arraylist) {
                    if (wp.getName().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        rItems.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

