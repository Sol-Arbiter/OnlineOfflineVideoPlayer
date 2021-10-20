package video.player.mp4player.videoplayer.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import video.player.mp4player.videoplayer.R;
import video.player.mp4player.videoplayer.Utils.SavedPreferences;

public class MainActivity extends AppCompatActivity {
    SavedPreferences preferenceObject;
    private BottomNavigationView bottomNavigationView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferenceObject = new SavedPreferences(this);
        if (preferenceObject.getTheem() == 1) {
            setTheme(R.style.AppTheme);
        } else if (preferenceObject.getTheem() == 2) {
            this.setTheme(R.style.AppTheme_2);
        } else if (preferenceObject.getTheem() == 3) {
            this.setTheme(R.style.AppTheme_3);
        } else if (preferenceObject.getTheem() == 4) {
            this.setTheme(R.style.AppTheme_4);
        } else if (preferenceObject.getTheem() == 5) {
            this.setTheme(R.style.AppTheme_5);
        } else if (preferenceObject.getTheem() == 6) {
            this.setTheme(R.style.AppTheme_6);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        loadFragment(new OfflineVideoFragment(), false);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.local:
                    fragment = new OfflineVideoFragment();
                    loadFragment(fragment, false);
                    return true;
                case R.id.online:
                    fragment = new OnlineVideoFragment();
                    loadFragment(fragment, true);
                    return true;
            }
            return false;
        }

        ;
    };


    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        if (addToBackStack)
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        if (fragment instanceof OnlineVideoFragment) {
            getSupportFragmentManager().popBackStack();
            Menu menu = bottomNavigationView.getMenu();
            menu.findItem(R.id.local).setChecked(true);
//            bottomNavigationView.findViewById(R.id.local).setSelected(true);
            return;
        }
        int fragmentStack = getSupportFragmentManager().getFragments().size();
        if (fragmentStack > 1) {
            getSupportFragmentManager().popBackStack();
            OfflineVideoFragment.tabLayout.setVisibility(View.VISIBLE);
            OfflineVideoFragment.setTitle(getString(R.string.name));

        } else {
            showExitDialog();
        }

    }

    public void showExitDialog() {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are You Sure You Want Exit?");

        // add the buttons
        builder.setPositiveButton("Rate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                launchMarket();
            }
        });
        builder.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}