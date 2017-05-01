package bit.sikander.kickdown;

import android.support.v7.app.AppCompatActivity;

public class ListDetailsActivity extends AppCompatActivity {
//    private ProgressDialog pDialog;
//    private static String TAG = ListDetailsActivity.class.getSimpleName();
//    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//    final String detailsURL = "https://maps.googleapis.com/maps/api/place/details/json?reference=" + Constants.dReferrence + "&key=" + Constants.API_KEY;
//    private Toolbar mToolbar;
////    final String detailsURL = "https://maps.googleapis.com/maps/api/place/details/json?reference=CoQBcgAAACNm5Xt30YN33owY0HD5gWDXmwRzRMBNwXI0o8N5DVL6El1x-2svpLK7htIzZah6iHKUO5NIxm509F578w1bz87XcfkD_vhIb1OGmS0EGhY2MPV_FbvbvWIamPB50caUp9nY3a_BIQOfe63JEdAHu03Eusjuf82Vpur6793vpC3sEhDuPWEdmwxP0xftsQGOY65aGhRgmaX7evnnaqQGjppFYQJNjqBdRg&key=AIzaSyBhjh1rMzVLUESCQI-jL8ZSiXsX649akD4";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_details);
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        listDetailsRequest();
//    }
//
//
//
//    private void listDetailsRequest() {
//
//        pDialog = ProgressDialog.show(this, "", "Loading..", false, false);
//
//        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                (Request.Method.GET, detailsURL, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
////                        tvResult.setText("Response: " + response.toString());
////                        String textResult = "";
//                        Log.d(TAG, detailsURL);
////                        pDialog.cancel();
//
//
//                        TextView cName = (TextView) findViewById(R.id.cName);
//                        TextView cAdd = (TextView) findViewById(R.id.cAddress);
//                        TextView cPhone = (TextView) findViewById(R.id.cPhone);
//                        TextView cWeb = (TextView) findViewById(R.id.cWeb);
//                        ImageView opencloseview = (ImageView) findViewById(R.id.openclose);
//                        final RatingBar detailsRating = (RatingBar) findViewById(R.id.detailsRating);
//                        try {
//                            JSONObject dt = response.getJSONObject("result");
//                            String name = dt.getString("name");
//                            Constants.ShareName=name;
//                            String formatted_address = dt.getString("formatted_address");
//                            Constants.ShareAddress=formatted_address;
//                            try {
//                                String formatted_phone_number = dt.getString("formatted_phone_number");
//                                cPhone.setText(formatted_phone_number);
//                            } catch (Exception e) {
//                                // TODO: handle exception
//                            }
//                            try {
//                                String website = dt.getString("website");
//                                cWeb.setText(website);
//                            } catch (Exception e) {
//                                // TODO: handle exception
//                            }
//
//
//                            final JSONObject resultGeo = dt.getJSONObject("geometry");
//
//                            final JSONObject location = resultGeo.getJSONObject("location");
//
//
//                            try {
//
//                                Constants.LAT = location.getString("lat");
//
//                                Constants.LNG = location.getString("lng");
//                                PrintLog.myLog("GEO", Constants.LAT);
//                            } catch (Exception e) {
//                                // TODO: handle exception
//                            }
//
//                            try {
//                                String rating = dt.getString("rating");
//
//                                Constants.rating = rating;
//                                Float count = Float.parseFloat(rating);
//                                detailsRating.setRating(count);
//                            } catch (Exception e) {
//                                // TODO: handle exception
//                            }
//
//                            try {
//                            JSONObject rOpen = dt.getJSONObject("opening_hours");
//                            try {
//                                String oc = rOpen.getString("open_now");
//
//
//                                if (oc.equals("false")) {
//
//                                    opencloseview.setImageResource(R.drawable.closed);
//
//                                } else {
//
//                                    opencloseview.setImageResource(R.drawable.open);
//
//                                }
//
//                            } catch (Exception e) {
//                                // TODO: handle exception
//                            }
//                            } catch (Exception e) {
//                                // TODO: handle exception
//                            }
//                            try {
////								getActionBar().setTitle("Custom Text");
//                                cName.setText(name);
////								cName.setTypeface(title);
//                            } catch (Exception e) {
//                                // TODO: handle exception
//                            }
//                            try {
////								getActionBar().setTitle("Custom Text");
//                                cAdd.setText(formatted_address);
////								cName.setTypeface(title);
//                            } catch (Exception e) {
//                                // TODO: handle exception
//                            }
//
//
//
//
//                            try {
////                                JSONArray photoArray = dt.getJSONArray("photos");
////
////
////                                for (int j = 0; j < photoArray.length(); j++) {
////
////                                    final JSONObject photoObject = photoArray.getJSONObject(j);
//                                try {
////                                        String photo_reference = photoObject.getString("photo_reference");
////                                        Constants.dReferrence = photo_reference;
//
//
//                                    String imgUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=480&photoreference="
//                                            + Constants.photoReferrence
//                                            + "&sensor=true&key=" + Constants.API_KEY;
//                                    PrintLog.myLog("LDAimgUrl:::", imgUrl);
//
//
//                                    if (imageLoader == null)
//                                        imageLoader = AppController.getInstance().getImageLoader();
//                                    NetworkImageView fImg = (NetworkImageView) findViewById(R.id.full_img);
//                                    try {
//                                        // thumbnail image
//                                        fImg.setImageUrl(imgUrl, imageLoader);
//                                        PrintLog.myLog("CLAdapter::", imgUrl);
//
//                                    } catch (Exception e) {
//                                        // TODO: handle exception
//                                    }
//
//
//                                } catch (Exception e) {
//                                    // TODO: handle exception
//                                }
//
//
//                            } catch (Exception e) {
//                                // TODO: handle exception
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(),
//                                    "Error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG).show();
//                        }
//
//                        pDialog.cancel();
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        VolleyLog.d(TAG, "Error: " + error.getMessage());
//                        Toast.makeText(getApplicationContext(),
//                                error.getMessage(), Toast.LENGTH_SHORT).show();
//                        pDialog.cancel();
//                    }
//                });
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(jsObjRequest);
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//            case R.id.sharethis:
//                // refresh
//                shareIt();
//
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//
//    /////////
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.share_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//
//    }
//
//
//    private void shareIt() {
//
//        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//        sharingIntent.setType("text/plain");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, Constants.ShareName);
//        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Constants.ShareName + "|" + Constants.ShareAddress+"- Easy CarMake Solution | https://play.google.com/store/apps/details?id=com.codeandcoder.easycar");
//        startActivity(Intent.createChooser(sharingIntent, "Share via"));
//
//    }
//
////    public void galleryBtn(View v) {
////        Log.e("DetailsURL::", detailsURL);
////
////        Intent next = new Intent(this, GalleryGridActivity.class);
////        next.putExtra("DetailsURL", detailsURL);
////        next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(next);
////
////    }
////    public void reviewsBtn(View v) {
////        Log.e("DetailsURL::", detailsURL);
////        Intent next = new Intent(this, ReviewsActivity.class);
////        next.putExtra("DetailsURL", detailsURL);
////        next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(next);
////
////    }
////    public void directionBtn(View v) {
////        Intent next = new Intent(this, DirectionActivity.class);
////        next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(next);
////
////    }
}
