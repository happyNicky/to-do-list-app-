package com.example.to_do_list_app;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageButton burgerMenu;
    private RelativeLayout monday, tuesday, wednesday, friday, thursday, saterday, sunday;
    private View mondayView, tuesdayView, wednesdayView, thursdayView, fridayView, saterdayView, sundayView;
    private mondayFragment monFrag;

    private ImageButton goToNoteBtn;
    private static final int REQUEST_CODE_PERMISSION = 100;
    private static String day = "monday";
    private Bundle bundle;
    private dataBase db;
    private ArrayList<String> titles;
    private EditText searchEditText;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String userName;
    private TextView userNameGreating;
    private static final int PICK_IMAGE = 1;
    private ShapeableImageView shapeableImageView;

    public static String getDay() {
        return day;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean hasOpenedBefore = preferences.getBoolean("hasOpenedBefore", false);
        if (!hasOpenedBefore) { // Redirect to IntroActivity if the app is being opened for the first time
            Intent intent1 = new Intent(MainActivity.this,
                    welcome_page.class);
            startActivity(intent1);
            finish();
            return;
        }
        db = new dataBase(getApplicationContext());
        userName = db.retunUserName();


        userNameGreating = findViewById(R.id.userNameGriting);
        userNameGreating.setText("Hi," + userName);
        // instantiating layout files
        burgerMenu = findViewById(R.id.burgerMenu);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        friday = findViewById(R.id.friday);
        thursday = findViewById(R.id.thursday);
        saterday = findViewById(R.id.saterday);
        sunday = findViewById(R.id.sunday);
        goToNoteBtn = findViewById(R.id.goToNoteBtn);
        searchEditText = findViewById(R.id.search_edit_text);
        shapeableImageView = findViewById(R.id.userImage);
        navigationView = findViewById(R.id.nav_view);

        // instantiating view layout
        mondayView = findViewById(R.id.underLineMonday);
        tuesdayView = findViewById(R.id.underLineTuesday);
        wednesdayView = findViewById(R.id.underLineWednesday);
        thursdayView = findViewById(R.id.underLineThursday);
        fridayView = findViewById(R.id.underLineFriday);
        saterdayView = findViewById(R.id.underLineSaterday);
        sundayView = findViewById(R.id.underLineSunday);

        titles = db.returnTitles(day);
        // instantiating fragments
        monFrag = new mondayFragment();
        bundle = new Bundle();
        bundle.putStringArrayList(day, titles);
        bundle.putString("day", day);
        monFrag.setArguments(bundle);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        burgerMenu.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // trying code


        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, monFrag).commit();

        shapeableImageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (R.id.settings == item.getItemId()) {

                } else if (R.id.theme == item.getItemId()) {

                } else if (R.id.about == item.getItemId()) {
                    startActivity(new Intent(MainActivity.this, about_activity.class));
                } else if (R.id.rate == item.getItemId()) {

                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

          if(db.returnIsImageChoosen())
         shapeableImageView.setImageURI(db.getImage(getApplicationContext()));


        monday.setOnClickListener(v -> {
            day = "monday";
            titles = db.returnTitles(day);
            bundle.putStringArrayList(day, titles);
            bundle.putString("day", day);
            monFrag.setArguments(bundle);
            if (mondayView.getVisibility() == View.VISIBLE) {
                // do nothing
            } else {
                mondayView.setVisibility(View.VISIBLE);
                tuesdayView.setVisibility(View.GONE);
                wednesdayView.setVisibility(View.GONE);
                thursdayView.setVisibility(View.GONE);
                fridayView.setVisibility(View.GONE);
                saterdayView.setVisibility(View.GONE);
                sundayView.setVisibility(View.GONE);
            }
            monFrag.updateTitles(titles);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, monFrag).commit();
        });
        tuesday.setOnClickListener(v -> {
            day = "tuesday";
            titles = db.returnTitles(day);
            bundle.putStringArrayList(day, titles);
            bundle.putString("day", day);
            monFrag.setArguments(bundle);
            if (tuesdayView.getVisibility() == View.VISIBLE) {

            } else {
                tuesdayView.setVisibility(View.VISIBLE);
                mondayView.setVisibility(View.GONE);
                wednesdayView.setVisibility(View.GONE);
                thursdayView.setVisibility(View.GONE);
                fridayView.setVisibility(View.GONE);
                saterdayView.setVisibility(View.GONE);
                sundayView.setVisibility(View.GONE);
            }
            monFrag.updateTitles(titles);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, monFrag).commit();
        });
        wednesday.setOnClickListener(v -> {
            day = "wednesday";
            titles = db.returnTitles(day);
            bundle.putStringArrayList(day, titles);
            bundle.putString("day", day);
            monFrag.setArguments(bundle);
            if (wednesdayView.getVisibility() == View.VISIBLE) {

            } else {
                mondayView.setVisibility(View.GONE);
                tuesdayView.setVisibility(View.GONE);
                wednesdayView.setVisibility(View.VISIBLE);
                thursdayView.setVisibility(View.GONE);
                fridayView.setVisibility(View.GONE);
                saterdayView.setVisibility(View.GONE);
                sundayView.setVisibility(View.GONE);
            }
            monFrag.updateTitles(titles);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, monFrag).commit();

        });
        thursday.setOnClickListener(v -> {
            day = "thursday";
            titles = db.returnTitles(day);
            bundle.putStringArrayList(day, titles);
            bundle.putString("day", day);
            monFrag.setArguments(bundle);
            if (thursdayView.getVisibility() == View.VISIBLE) {

            } else {
                mondayView.setVisibility(View.GONE);
                tuesdayView.setVisibility(View.GONE);
                wednesdayView.setVisibility(View.GONE);
                thursdayView.setVisibility(View.VISIBLE);
                fridayView.setVisibility(View.GONE);
                saterdayView.setVisibility(View.GONE);
                sundayView.setVisibility(View.GONE);
            }
            monFrag.updateTitles(titles);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, monFrag).commit();
        });
        friday.setOnClickListener(v -> {
            day = "friday";
            titles = db.returnTitles(day);
            bundle.putStringArrayList(day, titles);
            bundle.putString("day", day);
            monFrag.setArguments(bundle);
            if (fridayView.getVisibility() == View.VISIBLE) {

            } else {
                mondayView.setVisibility(View.GONE);
                tuesdayView.setVisibility(View.GONE);
                wednesdayView.setVisibility(View.GONE);
                thursdayView.setVisibility(View.GONE);
                fridayView.setVisibility(View.VISIBLE);
                saterdayView.setVisibility(View.GONE);
                sundayView.setVisibility(View.GONE);
            }
            monFrag.updateTitles(titles);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, monFrag).commit();
        });
        saterday.setOnClickListener(v -> {
            day = "saterday";
            titles = db.returnTitles(day);
            bundle.putStringArrayList(day, titles);
            bundle.putString("day", day);
            monFrag.setArguments(bundle);
            if (saterdayView.getVisibility() == View.VISIBLE) {

            } else {
                mondayView.setVisibility(View.GONE);
                tuesdayView.setVisibility(View.GONE);
                wednesdayView.setVisibility(View.GONE);
                thursdayView.setVisibility(View.GONE);
                fridayView.setVisibility(View.GONE);
                saterdayView.setVisibility(View.VISIBLE);
                sundayView.setVisibility(View.GONE);
            }
            monFrag.updateTitles(titles);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, monFrag).commit();
        });
        sunday.setOnClickListener(v -> {
            day = "sunday";
            titles = db.returnTitles(day);
            bundle.putStringArrayList(day, titles);
            bundle.putString("day", day);
            monFrag.setArguments(bundle);
            if (sundayView.getVisibility() == View.VISIBLE) {

            } else {
                mondayView.setVisibility(View.GONE);
                tuesdayView.setVisibility(View.GONE);
                wednesdayView.setVisibility(View.GONE);
                thursdayView.setVisibility(View.GONE);
                fridayView.setVisibility(View.GONE);
                saterdayView.setVisibility(View.GONE);
                sundayView.setVisibility(View.VISIBLE);
            }
            monFrag.updateTitles(titles);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, monFrag).commit();
        });

        goToNoteBtn.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, addNote.class);
            intent.putExtra("day", day);
            intent.putExtra("titles", titles);
            startActivity(intent);
        });
        searchEditText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int drawableRight = 2;
                    Drawable drawable = searchEditText.getCompoundDrawables()[drawableRight];
                    if (drawable != null && event.getRawX() >= (searchEditText.getRight() - drawable.getBounds().width())) {
                        String searchInputTxt = searchEditText.getText().toString();
                        titles = db.searchResult(searchInputTxt, day);
                        System.out.println("inside search ");
                        for (int i = 0; i < titles.size(); i++) {
                            System.out.println(titles.get(i));
                        }
                        System.out.println("below search title ");
                        bundle.putStringArrayList(day, titles);
                        bundle.putString("day", day);
                        monFrag.setArguments(bundle);
                        monFrag.updateTitles(titles);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, monFrag).commit();
                        return true;
                    }
                }
                return false;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public boolean isVisible(View v) {
        return v.getVisibility() != View.GONE;
    }

    protected void onResume() {
        super.onResume();
        titles = db.returnTitles(day);
        bundle.putStringArrayList(day, titles);
        bundle.putString("day", day);
        monFrag.setArguments(bundle);
        monFrag.updateTitles(titles);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, monFrag).commit();
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            shapeableImageView.setImageURI(selectedImage);
            db.insertImage(getApplicationContext(), selectedImage);
        }
    }



    }
