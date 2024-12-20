package com.example.to_do_list_app;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
    public static int theme=Color.WHITE;
    private  Calendar today=Calendar.getInstance();

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
        userName=db.retunUserName();



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

        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, monFrag).commit();

        shapeableImageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (R.id.settings == item.getItemId()) {
                    drawerLayout.closeDrawers();
                } else if (R.id.theme == item.getItemId()) {
                    showSubMenu(findViewById(R.id.theme));

                } else if (R.id.about == item.getItemId()) {
                    startActivity(new Intent(MainActivity.this, about_activity.class));
                    drawerLayout.closeDrawers();
                } else if (R.id.rate == item.getItemId()) {
                    String gitHubUrl="https://github.com/happyNicky/to-do-list-app-";
                    Intent intent= new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(gitHubUrl));
                    startActivity(intent);
                    drawerLayout.closeDrawers();
                }


                return true;
            }
        });
        System.out.println("before is selected ");
        if(db.isSelected())
        { Uri selectedImage = db.getImage(getApplicationContext());
            System.out.println("is selected is working ");
            shapeableImageView.setImageURI(selectedImage);
        }
        System.out.println("after is selected");
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
            intent.putExtra("theme",theme);
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
            db.insertOrUpdateImage(getApplicationContext(), selectedImage);
        }
    }

    private void showSubMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor, Gravity.END);
        popupMenu.getMenuInflater().inflate(R.menu.theme_sub_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                   if(item.getItemId()==R.id.drakTheme) {
                            theme=Color.BLACK;
                           findViewById(R.id.main).setBackgroundColor(theme);
                           searchEditText.setTextColor(theme);
                           searchEditText.setHintTextColor(Color.parseColor("#656161"));
                           TextView youshouldTxt=findViewById(R.id.youShouldTxt);
                           youshouldTxt.setTextColor(Color.WHITE);
                           burgerMenu.setBackgroundColor(theme);
                           burgerMenu.setColorFilter(Color.rgb(255,255,255));
                       findViewById(R.id.main_frame_layout).setBackgroundColor(theme);
                      monFrag.changeBackground(theme);
                           drawerLayout.closeDrawers();
                      }
                    else if(item.getItemId()== R.id.whiteTheme)
                    {      theme= Color.WHITE;
                        findViewById(R.id.main).setBackgroundColor(theme);
                        searchEditText.setTextColor(Color.BLACK);
                        searchEditText.setHintTextColor(Color.parseColor("#656161"));
                        TextView youshouldTxt=findViewById(R.id.youShouldTxt);
                        youshouldTxt.setTextColor(Color.BLACK);
                        burgerMenu.setBackgroundColor(theme);
                        burgerMenu.setColorFilter(Color.rgb(1,1,1));
                        findViewById(R.id.main_frame_layout).setBackgroundColor(theme);

                        monFrag.changeBackground(theme);
                        drawerLayout.closeDrawers();
                    }

                    return true;

                }
        });
        popupMenu.show();
    }



}
