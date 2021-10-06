package video.player.mp4player.videoplayer.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import video.player.mp4player.videoplayer.R;
import video.player.mp4player.videoplayer.Utils.SavedPreferences;

public class setting extends AppCompatActivity {

    SavedPreferences preference;
    private static final String[] paths = {"Theme 1: Corel", "Theme 2: Cyan"};
    LinearLayout ol_action_bar;
    TextView tv_title;
    private ImageView back;
    private LinearLayout thim1;
    private LinearLayout thim2;
    private LinearLayout thim3;
    private LinearLayout thim4;
    private LinearLayout thim5;
    private LinearLayout thim6;

    private TextView tv_tf;
    private TextView tv_tf_2;
    private TextView tv_tf_3;
    private TextView tv_tf_4;
    private TextView tv_tf_5;
    private TextView tv_tf_6;

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preference = new SavedPreferences(setting.this);
        if (preference.getTheem() == 1) {
            setTheme(R.style.AppTheme);
        } else if (preference.getTheem() == 2) {
            this.setTheme(R.style.AppTheme_2);
        } else if (preference.getTheem() == 3) {
            this.setTheme(R.style.AppTheme_3);
        } else if (preference.getTheem() == 4) {
            this.setTheme(R.style.AppTheme_4);
        } else if (preference.getTheem() == 5) {
            this.setTheme(R.style.AppTheme_5);
        } else if (preference.getTheem() == 6) {
            this.setTheme(R.style.AppTheme_6);
        } else {

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_title = (TextView) findViewById(R.id.tv_title);
        ol_action_bar = (LinearLayout) findViewById(R.id.ol_action_bar);
        thim1 = (LinearLayout) findViewById(R.id.thim1);
        thim2 = (LinearLayout) findViewById(R.id.thim2);
        thim3 = (LinearLayout) findViewById(R.id.thim3);
        thim4 = (LinearLayout) findViewById(R.id.thim4);
        thim5 = (LinearLayout) findViewById(R.id.thim5);
        thim6 = (LinearLayout) findViewById(R.id.thim6);
        tv_tf = (TextView) findViewById(R.id.tv_tf);
        tv_tf_2 = (TextView) findViewById(R.id.tv_tf_2);
        tv_tf_3 = (TextView) findViewById(R.id.tv_tf_3);
        tv_tf_4 = (TextView) findViewById(R.id.tv_tf_4);
        tv_tf_5 = (TextView) findViewById(R.id.tv_tf_5);
        tv_tf_6 = (TextView) findViewById(R.id.tv_tf_6);
        change_theem();


        thim1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.setTheem(1);
                startActivity(new Intent(setting.this,setting.class));
            }
        });
        thim2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.setTheem(2);
                startActivity(new Intent(setting.this,setting.class));
            }
        });
        thim3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.setTheem(3);
                startActivity(new Intent(setting.this,setting.class));
            }
        });
        thim4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.setTheem(4);
                startActivity(new Intent(setting.this,setting.class));
            }
        });
        thim5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.setTheem(5);
                startActivity(new Intent(setting.this,setting.class));
            }
        });
        thim6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.setTheem(6);
                startActivity(new Intent(setting.this,setting.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(setting.this, OfflineVideoFragment.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void change_theem() {
        if (preference.getTheem() == 1) {
            this.setTheme(R.style.AppTheme);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            ol_action_bar.setBackgroundResource(R.color.colorPrimary);
        } else if (preference.getTheem() == 2) {
            this.setTheme(R.style.AppTheme_2);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_2));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_2));
            ol_action_bar.setBackgroundResource(R.color.colorPrimary_2);
        } else if (preference.getTheem() == 3) {
            this.setTheme(R.style.AppTheme_3);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_3));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_3));
            ol_action_bar.setBackgroundResource(R.color.colorPrimary_3);
        } else if (preference.getTheem() == 4) {
            this.setTheme(R.style.AppTheme_4);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_4));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_4));
            ol_action_bar.setBackgroundResource(R.color.colorPrimary_4);
        } else if (preference.getTheem() == 5) {
            this.setTheme(R.style.AppTheme_5);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_5));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_5));
            ol_action_bar.setBackgroundResource(R.color.colorPrimary_5);
        } else if (preference.getTheem() == 6) {
            this.setTheme(R.style.AppTheme_6);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_6));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_6));
            ol_action_bar.setBackgroundResource(R.color.colorPrimary_6);
        } else {

        }


    }


}
