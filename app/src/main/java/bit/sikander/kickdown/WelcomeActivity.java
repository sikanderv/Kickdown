package bit.sikander.kickdown;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Sikander on 2017-03-19.
 */

public class WelcomeActivity extends AppCompatActivity {

    private Toolbar toolbar;

    ImageView img_my_car;
    FloatingActionButton fab_search;
    FloatingActionButton fab_signIn;
    FloatingActionButton fab_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setTitle("Welcome to Kickdown!");

        // My CarMake details
        img_my_car = (ImageView) findViewById(R.id.my_car);

        // Search nearby car repairs
        fab_search = (FloatingActionButton) findViewById(R.id.search);

        // Register user
        fab_signIn = (FloatingActionButton) findViewById(R.id.sign_in);

        // Register user
        fab_map = (FloatingActionButton) findViewById(R.id.map);

        //Picasso.with(this).load(R.mipmap.welcome_page).into(roundedImageView);

        fab_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listActivity = new Intent(WelcomeActivity.this, ListActivity.class);
                startActivity(listActivity);
            }
        });


        fab_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(registerActivity);
            }
        });

        img_my_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent carDetailsActivity = new Intent(WelcomeActivity.this, MyCarDetailsActivity.class);

                try {
                    startActivity(carDetailsActivity);
                }

                catch (Exception e){
                    Log.d("Image view setonclick", e.getMessage());
                }


            }
        });


    }


}
