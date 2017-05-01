package bit.sikander.kickdown;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bit.sikander.kickdown.models.CarModel;
import bit.sikander.kickdown.models.UserCar;

/**
 * Created by Sikander on 2017-04-28.
 */

public class MyCarDetailsActivity extends AppCompatActivity {

    private static final String TAG = "My Car Details Activity";

    // Views and widgets on this activity
    private Toolbar toolbar;
    private ProgressDialog pDialog;
    private Spinner makeSpinner;
    private Spinner modelSpinner;
    private Button btnSubmit;
    private TextView txtCarDetails;
    private Button btnRedirect;

    // For populating spinners
    ArrayAdapter<String> model_adapter;
    ArrayAdapter<String> makes_adapter;
    List<String> makes;
    List<String> models;
    String make;

    // Firebase related
    private DatabaseReference dbRef;
    Query mQuery;

    // User auth related
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

            // JSON API GET related (Data source: National Highway Traffic Safety Administration (USA))
    StringBuilder final_url = new StringBuilder();
    String url = "https://vpic.nhtsa.dot.gov/api/vehicles/getmodelsformake/";
    String data_format = "?format=json";

    // Miscenalleous/un-used currently
    List<CarModel> model_list;
    private List<CarModel> rdataList;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car);

        mAuth = FirebaseAuth.getInstance();

        // Instantiate all views/objects/controls on this activity
        makeSpinner = (Spinner) findViewById(R.id.car_make_spinner);
        modelSpinner = (Spinner) findViewById(R.id.car_model_spinner);
        txtCarDetails = (TextView) findViewById(R.id.car_details_text);
        btnSubmit = (Button) findViewById(R.id.submit_button);
        btnRedirect = (Button) findViewById(R.id.btnRedirect);

        rdataList = new ArrayList<CarModel>();
        cd = new ConnectionDetector(this.getApplicationContext());
        // adapter = new CustomListAdapter(this, rdataList);
        // makeSpinner.setAdapter(adapter);


        checkInternetAndRetreiveData();


        makeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (makeSpinner.getSelectedItem().toString() != null) {
                    make = makeSpinner.getSelectedItem().toString();
                    retrieveModels(make);

                } else {
                    Toast.makeText(MyCarDetailsActivity.this, "Please select a car make first!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO: Code this later
            }
        });

        /// JSON Object request not executing while trying to fetch directly from API
        /// Debugged for 2 days, still no solution in sight
        /// TODO:  Find a solution to this

//        makeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (makeSpinner.getSelectedItem().toString() != null) {
//                    make = makeSpinner.getSelectedItem().toString();
//                    final_url.append(url);
//                    final_url.append(make);
//                    final_url.append(data_format);
//                    dataParsing(final_url.toString());
//                    model_adapter = new ArrayAdapter<String>(MyCarDetailsActivity.this, android.R.layout.simple_spinner_item, models);
//                    model_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    modelSpinner.setAdapter(model_adapter);
//                    model_adapter.notifyDataSetChanged();
//
//                } else {
//                    Toast.makeText(MyCarDetailsActivity.this, "Please select a car make first!", Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser = mAuth.getCurrentUser();
                if (currentUser != null){
                    String make_name = makeSpinner.getSelectedItem().toString();
                    String model_name = modelSpinner.getSelectedItem().toString();
                    dbRef = FirebaseDatabase.getInstance().getReference("user_cars");
                    UserCar userCar = new UserCar();
                    userCar.setMake_name(make_name);
                    userCar.setModel_name(model_name);
                    dbRef.child(currentUser.getUid()).setValue(userCar);
                    Toast.makeText(MyCarDetailsActivity.this, "Your car details have been saved to database!", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(MyCarDetailsActivity.this, "Please sign in to save your details.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(MyCarDetailsActivity.this, RegisterActivity.class);
                startActivity(registerActivity);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Get current user
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /*
             * Called during fetch from Vehicles Data API
             */
    private void dataParsing(String url){

        pDialog = new ProgressDialog(MyCarDetailsActivity.this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        String rbody = null;
        JSONObject obj = null;

        try {
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    hidePDialog();
                    try {
                        ///////////////////////////////////////////////////////////////////////
                        JSONArray arrResults = response.getJSONArray("Results");
                        for (int i = 0; i < arrResults.length(); i++) {

                            JSONObject obj = arrResults.getJSONObject(i);

                            models = new ArrayList<String>();

                            models.add(obj.getString("Model_Name"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        PrintLog.myLog("STATUS", "Parsing MODELS Finished");
                    }

                }

            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null) Log.e("Car Details Activity", error.getMessage());

                }
            });

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsObjRequest);

        } catch(Exception e) {
            Log.e("JsonObjectRequest", e.getMessage());

        }


    }

    public void onDestroy() {
        super.onDestroy();
        hidePDialog();

//        if (dbRef != null){
//            dbRef = null;
//        }

    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private void checkInternetAndRetreiveData(){

        isInternetPresent = cd.isConnectingToInternet();
        // check for Internet status
        if (isInternetPresent) {

            retrieveMakes();

        } else {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(MyCarDetailsActivity.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }

    }

    private void retrieveMakes(){

        dbRef = FirebaseDatabase.getInstance().getReference().child("cars").child("Results");
        mQuery = dbRef.limitToFirst(5);

        mQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Toast.makeText(MyCarDetailsActivity.this, "Count: " + snapshot.getChildrenCount(), Toast.LENGTH_LONG).show();

                pDialog = new ProgressDialog(MyCarDetailsActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();


                makes = new ArrayList<String>();

                for (DataSnapshot makeSnapshot: snapshot.getChildren()) {
                    String makeName = makeSnapshot.child("/Make_Name").getValue(String.class);
                    makes.add(makeName);
                }

                hidePDialog();

                makes_adapter = new ArrayAdapter<String>(MyCarDetailsActivity.this, android.R.layout.simple_spinner_item, makes);
                makes_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                makeSpinner.setAdapter(makes_adapter);
                makes_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void retrieveModels(String make_name){

        make_name = make_name.toLowerCase();

        dbRef = FirebaseDatabase.getInstance().getReference().child(make_name).child("Results");

        dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Toast.makeText(MyCarDetailsActivity.this, "Count: " + snapshot.getChildrenCount(), Toast.LENGTH_LONG).show();

                pDialog = new ProgressDialog(MyCarDetailsActivity.this);
                // Showing progress dialog before making http request
                pDialog.setMessage("Loading...");
                pDialog.show();


                models = new ArrayList<String>();

                for (DataSnapshot modelSnapshot: snapshot.getChildren()) {
                    String modelName = modelSnapshot.child("/Model_Name").getValue(String.class);
                    models.add(modelName);
                }

                hidePDialog();

                model_adapter = new ArrayAdapter<String>(MyCarDetailsActivity.this, android.R.layout.simple_spinner_item, models);
                model_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                modelSpinner.setAdapter(model_adapter);
                model_adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

    public void displayLoggedInUserCarDetais(){

        if (currentUser != null){

            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserCar current_user_in_db = snapshot.getValue(UserCar.class);
                        if (current_user_in_db.toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            makeSpinner.setSelection(makes_adapter.getPosition(current_user_in_db.getMake_name()));
                            modelSpinner.setSelection(model_adapter.getPosition((current_user_in_db.getMake_name())));
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "displayLoggedInUserDetails:onCancelled", databaseError.toException());

                }
            });
        }

    }

    private void updateUI(FirebaseUser user) {
        hidePDialog();
        if (user != null) {
            String email = user.getEmail();
            String username = user.getEmail().substring(0,(user.getEmail().indexOf('@')));
            txtCarDetails.setText("Welcome " + username + "! Please enter/update your car details below.");
            displayLoggedInUserCarDetais();
        } else {
            txtCarDetails.setText("You are not signed in. Please sign up or log in.");
        }
    }


}
