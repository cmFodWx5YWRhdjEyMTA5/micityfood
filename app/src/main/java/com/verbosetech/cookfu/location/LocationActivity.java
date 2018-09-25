package com.verbosetech.cookfu.location;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.verbosetech.cookfu.R;
import com.verbosetech.cookfu.activity.MainActivity;

public class LocationActivity extends AppCompatActivity {
    final String FRAG_TAG_SEARCH_LOCATION = "fragSearchLocation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        ImageView background = findViewById(R.id.background);
        ImageView splashLogo = findViewById(R.id.splashLogo);
        Glide.with(this).load(R.drawable.background).into(background);
        Glide.with(this).load(R.drawable.chef_logo).into(splashLogo);

        findViewById(R.id.locationNew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                loadFragment(new LocationFragment());
            }
        });
        findViewById(R.id.locationSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.bottom_up, R.anim.bottom_down, R.anim.bottom_up, R.anim.bottom_down)
                .add(R.id.mainFrame, fragment, FRAG_TAG_SEARCH_LOCATION)
                .addToBackStack(FRAG_TAG_SEARCH_LOCATION)
                .commit();
    }
}
