package video.player.mp4player.videoplayer.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Visualizer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import video.player.mp4player.videoplayer.Equilizer.VisualizerView;
import video.player.mp4player.videoplayer.R;
import video.player.mp4player.videoplayer.Service.ServiceFloating;
import video.player.mp4player.videoplayer.Utils.SavedPreferences;
import video.player.mp4player.videoplayer.Utils.CommonUtilities;
import video.player.mp4player.videoplayer.floating_view.minivideomodel;
import video.player.mp4player.videoplayer.giraffeplayer.GiraffePlayer;
import video.player.mp4player.videoplayer.model.position_model;
import video.player.mp4player.videoplayer.model.VideoListModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

import tv.danmaku.ijk.media.player.misc.ITrackInfo;


public class PlayerActivity extends AppCompatActivity {

    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;
    GiraffePlayer player;
    private String video_path;
    private int lastIndex;
    private int position;
    private ArrayList<VideoListModel> arrayList;
    public boolean value = true;
    public static AppCompatActivity play_activity;
    int a;
    IntentFilter intentFilter;
    ConnectionReceiver receiver;

    public static boolean values = true;


    TextView app_video_currentTime, app_video_endTime;
    SeekBar app_video_seekBar;

    ImageView app_video_next;
    ImageView app_video_play;
    ImageView app_video_previous;
    ImageView app_video_resize;
    ImageView btn_toggle_ratio;
    ImageView img_volume;
    ImageView img_lock;
    ImageView img_unlock;
    ImageView img_equaliser;
    ImageView img_menu;
    public RelativeLayout xyz;
    RelativeLayout app_video_top_box;

    private static final float VISUALIZER_HEIGHT_DIP = 50f;
    private static final int PERMISSION = 1234;

    private Visualizer mVisualizer;
    private Equalizer mEqualizer;

    public LinearLayout visualLinearLayout;
    private LinearLayout mLinearLayout;
    public VisualizerView mVisualizerView;
    private SavedPreferences preference;
    File f;
    SeekBar seekBar1, seekBar2, seekBar3, seekBar4, seekBar5;
    int language;

    public static ArrayList<position_model> position_models_list;
    private boolean isresumed = false;
    private boolean impliment = false;

    TextView tv_subtitle;
    AlertDialog alertDialog1;

    LinearLayout lil_eq_textbox;
    FrameLayout frl_eq_seek1, frl_eq_seek2, frl_eq_seek3, frl_eq_seek4, frl_eq_seek5;

    ImageView img_mini;

    ImageView eq_app_video_finish;
    TextView eq_app_video_title;
    ImageView eq_img_equaliser;
    ImageView eq_img_menu;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preference = new SavedPreferences(PlayerActivity.this);
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
        setContentView(R.layout.giraffe_player);
        play_activity = PlayerActivity.this;
        Iconify.with(new FontAwesomeModule()).with(new EntypoModule());
        find_view_by_id();
        change_theem();

        preference = new SavedPreferences(PlayerActivity.this);
        xyz.setVisibility(View.GONE);

        fetch_arraylist();
        play_video_resume();
        onComplete_video();
        app_video_next_previous_code();
        lock_code();
        rotation_code();
        volume_code();
        equaliser_code();
        language();
        subtitle_code();
        miniscr_code();
    }

    private void find_view_by_id() {
        app_video_next = (ImageView) findViewById(R.id.app_video_next);
        app_video_play = (ImageView) findViewById(R.id.app_video_play);
        app_video_previous = (ImageView) findViewById(R.id.app_video_previous);
        app_video_resize = (ImageView) findViewById(R.id.app_video_resize);
        btn_toggle_ratio = (ImageView) findViewById(R.id.btn_toggle_ratio);
        img_volume = (ImageView) findViewById(R.id.img_volume);
        img_lock = (ImageView) findViewById(R.id.img_lock);
        img_unlock = (ImageView) findViewById(R.id.img_unlock);
        img_equaliser = (ImageView) findViewById(R.id.img_equaliser);
        img_menu = (ImageView) findViewById(R.id.img_menu);
        xyz = (RelativeLayout) findViewById(R.id.xyz);
        app_video_top_box = (RelativeLayout) findViewById(R.id.app_video_top_box);
        visualLinearLayout = (LinearLayout) findViewById(R.id.visualLinearLayout);
        app_video_currentTime = (TextView) findViewById(R.id.app_video_currentTime);
        app_video_endTime = (TextView) findViewById(R.id.app_video_endTime);
        app_video_seekBar = (SeekBar) findViewById(R.id.app_video_seekBar);
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        seekBar4 = (SeekBar) findViewById(R.id.seekBar4);
        seekBar5 = (SeekBar) findViewById(R.id.seekBar5);
        app_video_next = (ImageView) findViewById(R.id.app_video_next);
        app_video_previous = (ImageView) findViewById(R.id.app_video_previous);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayoutVisual);

        lil_eq_textbox = (LinearLayout) findViewById(R.id.lil_eq_textbox);
        frl_eq_seek1 = (FrameLayout) findViewById(R.id.frl_eq_seek1);
        frl_eq_seek2 = (FrameLayout) findViewById(R.id.frl_eq_seek2);
        frl_eq_seek3 = (FrameLayout) findViewById(R.id.frl_eq_seek3);
        frl_eq_seek4 = (FrameLayout) findViewById(R.id.frl_eq_seek4);
        frl_eq_seek5 = (FrameLayout) findViewById(R.id.frl_eq_seek5);
        img_mini = (ImageView) findViewById(R.id.img_mini);
        eq_app_video_finish = (ImageView) findViewById(R.id.eq_app_video_finish);
        eq_app_video_title = (TextView) findViewById(R.id.eq_app_video_title);
        eq_img_equaliser = (ImageView) findViewById(R.id.eq_img_equaliser);
        eq_img_menu = (ImageView) findViewById(R.id.eq_img_menu);



    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void change_theem() {
        preference = new SavedPreferences(PlayerActivity.this);
        if (preference.getTheem() == 1) {
            this.setTheme(R.style.AppTheme);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            lil_eq_textbox.setBackgroundResource(R.color.speener_back);
            frl_eq_seek1.setBackgroundResource(R.color.speener_back);
            frl_eq_seek2.setBackgroundResource(R.color.speener_back);
            frl_eq_seek3.setBackgroundResource(R.color.speener_back);
            frl_eq_seek4.setBackgroundResource(R.color.speener_back);
            frl_eq_seek5.setBackgroundResource(R.color.speener_back);
        } else if (preference.getTheem() == 2) {
            this.setTheme(R.style.AppTheme_2);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_2));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_2));
            lil_eq_textbox.setBackgroundResource(R.color.speener_back_2);
            frl_eq_seek1.setBackgroundResource(R.color.speener_back_2);
            frl_eq_seek2.setBackgroundResource(R.color.speener_back_2);
            frl_eq_seek3.setBackgroundResource(R.color.speener_back_2);
            frl_eq_seek4.setBackgroundResource(R.color.speener_back_2);
            frl_eq_seek5.setBackgroundResource(R.color.speener_back_2);
        } else if (preference.getTheem() == 3) {
            this.setTheme(R.style.AppTheme_3);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_3));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_3));
            lil_eq_textbox.setBackgroundResource(R.color.speener_back_3);
            frl_eq_seek1.setBackgroundResource(R.color.speener_back_3);
            frl_eq_seek2.setBackgroundResource(R.color.speener_back_3);
            frl_eq_seek3.setBackgroundResource(R.color.speener_back_3);
            frl_eq_seek4.setBackgroundResource(R.color.speener_back_3);
            frl_eq_seek5.setBackgroundResource(R.color.speener_back_3);
        } else if (preference.getTheem() == 4) {
            this.setTheme(R.style.AppTheme_4);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_4));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_4));
            lil_eq_textbox.setBackgroundResource(R.color.speener_back_4);
            frl_eq_seek1.setBackgroundResource(R.color.speener_back_4);
            frl_eq_seek2.setBackgroundResource(R.color.speener_back_4);
            frl_eq_seek3.setBackgroundResource(R.color.speener_back_4);
            frl_eq_seek4.setBackgroundResource(R.color.speener_back_4);
            frl_eq_seek5.setBackgroundResource(R.color.speener_back_4);
        } else if (preference.getTheem() == 5) {
            this.setTheme(R.style.AppTheme_5);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_5));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_5));
            lil_eq_textbox.setBackgroundResource(R.color.speener_back_5);
            frl_eq_seek1.setBackgroundResource(R.color.speener_back_5);
            frl_eq_seek2.setBackgroundResource(R.color.speener_back_5);
            frl_eq_seek3.setBackgroundResource(R.color.speener_back_5);
            frl_eq_seek4.setBackgroundResource(R.color.speener_back_5);
            frl_eq_seek5.setBackgroundResource(R.color.speener_back_5);
        } else if (preference.getTheem() == 6) {
            this.setTheme(R.style.AppTheme_6);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_6));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_6));
            lil_eq_textbox.setBackgroundResource(R.color.speener_back_6);
            frl_eq_seek1.setBackgroundResource(R.color.speener_back_6);
            frl_eq_seek2.setBackgroundResource(R.color.speener_back_6);
            frl_eq_seek3.setBackgroundResource(R.color.speener_back_6);
            frl_eq_seek4.setBackgroundResource(R.color.speener_back_6);
            frl_eq_seek5.setBackgroundResource(R.color.speener_back_6);
        } else {

        }


    }

    private void fetch_arraylist() {


        Bundle extras = getIntent().getExtras();
        arrayList = CommonUtilities.FinalVideoList;
        lastIndex = arrayList.size() - 1;
        position = extras.getInt("position");
        video_path = arrayList.get(position).getPath();
        f = new File(video_path);
        position_models_list = new ArrayList<>();
    }

    private void play_video_resume() {
        player = new GiraffePlayer(this);
        player.setTitle(f.getName());

        SharedPreferences sharedPrefslike = getApplicationContext().getSharedPreferences("bb", MODE_PRIVATE);
        Gson gsonlike = new Gson();
        final String jsonlike = sharedPrefslike.getString("video_resume_key", null);
        Type typelike = new TypeToken<ArrayList<position_model>>() {
        }.getType();
        position_models_list = gsonlike.fromJson(jsonlike, typelike);

        if (position_models_list != null) {
            for (int j = 0; j < position_models_list.size(); j++) {
                if (position_models_list.get(j).getVideo_path().equals(f.getPath())) {
                    isresumed = true;
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(PlayerActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(PlayerActivity.this);
                    }
                    final int finalJ = j;
                    builder.setCancelable(false);
                    builder.setTitle("Resume")
                            .setMessage("Are you sure you want to Resume this video")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    player.play(video_path);
                                    player.function_onResume(position_models_list.get(finalJ).getVideo_positipn());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    player.play(video_path);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    break;
                }
            }
        }

        if (!isresumed) {
            player.play(video_path);
        }

    }

    public void onComplete_video() {
        player.onComplete(new Runnable() {
            @Override
            public void run() {
                position = position + 1;
                if (position > lastIndex) {
                    position = 0;
                    VideoListModel value = arrayList.get(position);
                    video_path = value.getPath();
                    f = new File(video_path);
                    player.play(video_path);
                    player.setTitle(f.getName());

                } else {
                    VideoListModel value = arrayList.get(position);
                    video_path = value.getPath();
                    f = new File(video_path);
                    player.play(video_path);
                    player.setTitle(f.getName());
                }

            }
        }).onError(new GiraffePlayer.OnErrorListener() {
            @Override
            public void onError(int what, int extra) {
                Toast.makeText(getApplicationContext(), "video play error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void app_video_next_previous_code() {

        app_video_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = position + 1;
                if (position > lastIndex) {
                    position = 0;
                    VideoListModel value = arrayList.get(position);
                    video_path = value.getPath();
                    f = new File(video_path);
                    player.play(video_path);
                    player.setTitle(f.getName());

                } else {
                    VideoListModel value = arrayList.get(position);
                    video_path = value.getPath();
                    f = new File(video_path);
                    player.play(video_path);
                    player.setTitle(f.getName());
                }
            }
        });

        app_video_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = position - 1;
                if (position < 0) {
                    position = lastIndex;
                    VideoListModel value = arrayList.get(position);
                    video_path = value.getPath();
                    f = new File(video_path);
                    player.play(video_path);
                    player.setTitle(f.getName());
                } else {
                    VideoListModel value = arrayList.get(position);
                    video_path = value.getPath();
                    f = new File(video_path);
                    player.play(video_path);
                    player.setTitle(f.getName());
                }
            }
        });
    }

    private void lock_code() {
        xyz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (img_unlock.getVisibility() == View.VISIBLE) {
                    img_unlock.setVisibility(View.GONE);
                } else {
                    img_unlock.setVisibility(View.VISIBLE);
                }
            }
        });

        img_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = false;
                xyz.setVisibility(View.VISIBLE);
                app_video_next.setVisibility(View.GONE);
                app_video_play.setVisibility(View.GONE);
                app_video_previous.setVisibility(View.GONE);
                app_video_resize.setVisibility(View.GONE);
                btn_toggle_ratio.setVisibility(View.GONE);
                img_volume.setVisibility(View.GONE);
                app_video_currentTime.setVisibility(View.GONE);
                app_video_endTime.setVisibility(View.GONE);
                app_video_seekBar.setVisibility(View.GONE);
                app_video_top_box.setVisibility(View.GONE);
                img_lock.setVisibility(View.GONE);
                img_mini.setVisibility(View.GONE);
                img_unlock.setVisibility(View.VISIBLE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

            }
        });
        img_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xyz.setVisibility(View.GONE);
                app_video_next.setVisibility(View.VISIBLE);
                app_video_play.setVisibility(View.VISIBLE);
                app_video_previous.setVisibility(View.VISIBLE);
                app_video_resize.setVisibility(View.VISIBLE);
                btn_toggle_ratio.setVisibility(View.VISIBLE);
                img_volume.setVisibility(View.VISIBLE);
                app_video_currentTime.setVisibility(View.VISIBLE);
                app_video_endTime.setVisibility(View.VISIBLE);
                app_video_seekBar.setVisibility(View.VISIBLE);
                app_video_top_box.setVisibility(View.VISIBLE);
                img_mini.setVisibility(View.VISIBLE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
                img_lock.setVisibility(View.VISIBLE);
                img_unlock.setVisibility(View.GONE);
            }
        });

        btn_toggle_ratio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.toggleAspectRatio();
            }
        });
    }

    private void rotation_code() {

        app_video_resize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }

        });
    }

    private void volume_code() {
        img_volume.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                if (audioManager.isStreamMute(AudioManager.STREAM_MUSIC)) {
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    img_volume.setImageResource(R.drawable.ic_volume_up_white_36dp);
                } else {
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    img_volume.setImageResource(R.drawable.ic_volume_off_white_36dp);
                }

            }
        });
    }

    private void equaliser_code() {

        img_equaliser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                visualLinearLayout.setVisibility(View.VISIBLE);
                visualLinearLayout.isClickable();
            }
        });
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        eq_app_video_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visualLinearLayout.setVisibility(View.GONE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
            }
        });

        eq_img_equaliser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visualLinearLayout.setVisibility(View.GONE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
            }
        });
        eq_img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language_code();
            }
        });

        mEqualizer = new Equalizer(0, player.getAudioSessionID());
        mEqualizer.setEnabled(true);

        if (!hasAudioSettingsPermission(this)) {
            requestAudioSettingsPermission(this);
        }
        if (hasAudioSettingsPermission(this)) {
            setupVisualizerFxAndUI();
        }
        setupEqualizerFxAndUI();
        mVisualizer.setEnabled(true);

        mVisualizerView.setVisualizerColor(new ColorDrawable(getResources().getColor(R.color.textColorPrimary)));

    }

    private void equalizeSound() {

        ArrayList<String> equalizerPresetNames = new ArrayList<String>();
        ArrayAdapter<String> equalizerPresetSpinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                equalizerPresetNames);
        equalizerPresetSpinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner equalizerPresetSpinner = (Spinner) findViewById(R.id.spinner);
        for (short i = 0; i < mEqualizer.getNumberOfPresets(); i++) {
            equalizerPresetNames.add(mEqualizer.getPresetName(i));
        }
        equalizerPresetNames.add("Custom");

        equalizerPresetSpinner.setAdapter(equalizerPresetSpinnerAdapter);

        int current = preference.getposition();
        equalizerPresetSpinner.setSelection(current);
        equalizerPresetSpinner.setOnItemSelectedListener(new AdapterView
                .OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    mEqualizer.usePreset((short) (position - 1));
                }
                short numberFrequencyBands = mEqualizer.getNumberOfBands();
                final short lowerEqualizerBandLevel = mEqualizer.getBandLevelRange()[0];

                seekBar1.setProgress(mEqualizer.getBandLevel((short) 0) - lowerEqualizerBandLevel);
                seekBar2.setProgress(mEqualizer.getBandLevel((short) 1) - lowerEqualizerBandLevel);
                seekBar3.setProgress(mEqualizer.getBandLevel((short) 2) - lowerEqualizerBandLevel);
                seekBar4.setProgress(mEqualizer.getBandLevel((short) 3) - lowerEqualizerBandLevel);
                seekBar5.setProgress(mEqualizer.getBandLevel((short) 4) - lowerEqualizerBandLevel);

                preference.setposition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void setupEqualizerFxAndUI() {

        short numberFrequencyBands = mEqualizer.getNumberOfBands();

        final short lowerEqualizerBandLevel = mEqualizer.getBandLevelRange()[0];
        final short upperEqualizerBandLevel = mEqualizer.getBandLevelRange()[1];
        TextView frequencyHeaderTextview = new TextView(this);
        frequencyHeaderTextview.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        frequencyHeaderTextview.setGravity(Gravity.CENTER_HORIZONTAL);
        frequencyHeaderTextview
                .setText((mEqualizer.getCenterFreq((short) 0) / 1000) + " Hz");
        LinearLayout seekBarRowLayout = new LinearLayout(this);
        seekBarRowLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView lowerEqualizerBandLevelTextview = new TextView(this);
        lowerEqualizerBandLevelTextview.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        lowerEqualizerBandLevelTextview.setText((lowerEqualizerBandLevel / 100) + " dB");
        lowerEqualizerBandLevelTextview.setRotation(90);
        TextView upperEqualizerBandLevelTextview = new TextView(this);
        upperEqualizerBandLevelTextview.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        upperEqualizerBandLevelTextview.setText((upperEqualizerBandLevel / 100) + " dB");
        upperEqualizerBandLevelTextview.setRotation(90);



        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT - 60,
                120);
        layoutParams.weight = 1;

        ColorDrawable seekbg;
        seekbg = new ColorDrawable(getResources().getColor(R.color.colorPrimary));
        seekbg.setAlpha(90);

        seekBar1.setMax(upperEqualizerBandLevel - lowerEqualizerBandLevel);
        seekBar2.setMax(upperEqualizerBandLevel - lowerEqualizerBandLevel);
        seekBar3.setMax(upperEqualizerBandLevel - lowerEqualizerBandLevel);
        seekBar4.setMax(upperEqualizerBandLevel - lowerEqualizerBandLevel);
        seekBar5.setMax(upperEqualizerBandLevel - lowerEqualizerBandLevel);

        int progressBar1 = preference.getSeekValue(0, 1500);
        int progressBar2 = preference.getSeekValue(1, 1500);
        int progressBar3 = preference.getSeekValue(2, 1500);
        int progressBar4 = preference.getSeekValue(3, 1500);
        int progressBar5 = preference.getSeekValue(4, 1500);

        if (progressBar1 != 1500 && progressBar2 != 1500 && progressBar3 != 1500 && progressBar4 != 1500 && progressBar5 != 1500) {
            seekBar1.setProgress(progressBar1);
            seekBar2.setProgress(progressBar2);
            seekBar3.setProgress(progressBar3);
            seekBar4.setProgress(progressBar4);
            seekBar5.setProgress(progressBar5);
            mEqualizer.setBandLevel((short) 0,
                    (short) (progressBar1 + lowerEqualizerBandLevel));
            mEqualizer.setBandLevel((short) 1,
                    (short) (progressBar2 + lowerEqualizerBandLevel));
            mEqualizer.setBandLevel((short) 2,
                    (short) (progressBar3 + lowerEqualizerBandLevel));
            mEqualizer.setBandLevel((short) 3,
                    (short) (progressBar4 + lowerEqualizerBandLevel));
            mEqualizer.setBandLevel((short) 4,
                    (short) (progressBar5 + lowerEqualizerBandLevel));

        } else {
            seekBar1.setProgress(mEqualizer.getBandLevel((short) 0));
            seekBar2.setProgress(mEqualizer.getBandLevel((short) 1));
            seekBar3.setProgress(mEqualizer.getBandLevel((short) 2));
            seekBar4.setProgress(mEqualizer.getBandLevel((short) 3));
            seekBar5.setProgress(mEqualizer.getBandLevel((short) 4));
            mEqualizer.setBandLevel((short) 0,
                    (short) (progressBar1 + lowerEqualizerBandLevel));
            mEqualizer.setBandLevel((short) 1,
                    (short) (progressBar2 + lowerEqualizerBandLevel));
            mEqualizer.setBandLevel((short) 2,
                    (short) (progressBar3 + lowerEqualizerBandLevel));
            mEqualizer.setBandLevel((short) 3,
                    (short) (progressBar4 + lowerEqualizerBandLevel));
            mEqualizer.setBandLevel((short) 4,
                    (short) (progressBar5 + lowerEqualizerBandLevel));


        }

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                mEqualizer.setBandLevel((short) 0,
                        (short) (progress + lowerEqualizerBandLevel));

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                preference.setSeekValue(0, seekBar.getProgress());
                preference.setposition(0);
            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                mEqualizer.setBandLevel((short) 1,
                        (short) (progress + lowerEqualizerBandLevel));

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                preference.setSeekValue(1, seekBar.getProgress());
                preference.setposition(1);
            }
        });
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                mEqualizer.setBandLevel((short) 2,
                        (short) (progress + lowerEqualizerBandLevel));

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                preference.setSeekValue(2, seekBar.getProgress());
                preference.setposition(2);
            }
        });
        seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                mEqualizer.setBandLevel((short) 3,
                        (short) (progress + lowerEqualizerBandLevel));

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                preference.setSeekValue(3, seekBar.getProgress());
                preference.setposition(3);
            }
        });
        seekBar5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                mEqualizer.setBandLevel((short) 4,
                        (short) (progress + lowerEqualizerBandLevel));

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                preference.setSeekValue(4, seekBar.getProgress());
                preference.setposition(4);
            }
        });


        IconDrawable equalizer = new IconDrawable(this, FontAwesomeIcons.fa_minus_square).colorRes(R.color.colorAccent);
        equalizer.actionBarSize();
        LinearLayout.LayoutParams seekBarLayout = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        seekBarLayout.weight = 1;
        seekBarLayout.setMargins(5, 0, 5, 0);
        seekBarRowLayout.setLayoutParams(seekBarLayout);

        equalizeSound();
    }

    private void setupVisualizerFxAndUI() {
        mVisualizerView = new VisualizerView(this);
        mVisualizerView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (VISUALIZER_HEIGHT_DIP * getResources().getDisplayMetrics().density)));
        mLinearLayout.addView(mVisualizerView);

        mVisualizer = new Visualizer(player.getAudioSessionID());
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);

        mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes,
                                              int samplingRate) {
                mVisualizerView.updateVisualizer(bytes);
            }

            public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
            }
        }, Visualizer.getMaxCaptureRate() / 2, true, false);
    }

    private void language() {
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language_code();
            }
        });

    }

    private void language_code() {
        final ITrackInfo[] trackInfo = player.videoView.ijkMediaPlayer.getTrackInfo();
        final ArrayList<String> day_radio_mid = new ArrayList<String>();

        language = player.videoView.ijkMediaPlayer.getSelectedTrack(2);

        if (trackInfo != null) {
            for (int i = 0; i < trackInfo.length; i++) {
                if (trackInfo[i].getTrackType() == 2) {
                    day_radio_mid.add(trackInfo[i].getLanguage());
                }
            }
        }
        final CharSequence[] day_radio = new CharSequence[day_radio_mid.size()];
        if (trackInfo != null) {
            for (int i = 0; i < day_radio_mid.size(); i++) {
                day_radio[i] = day_radio_mid.get(i);
            }
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(PlayerActivity.this);
        builder.setTitle("Select Your Choice");

        builder.setSingleChoiceItems(day_radio, a, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                a = item;
                for (int i = 0; i < trackInfo.length; i++) {
                    if (trackInfo[i].getLanguage() == day_radio[item]) {
                        if (language != i) {
                            language = i;
                            preference.setLanguage(i);
                            player.videoView.ijkMediaPlayer.selectTrack(i);
                            player.function_onResume(player.getCurrentPosition());
                        }

                    }
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }

    private void subtitle_code() {

    }

    private void miniscr_code() {
        receiver = new ConnectionReceiver();
        intentFilter = new IntentFilter("com.journaldev.broadcastreceiver.SOME_ACTION");

        img_mini.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkDrawOverlayPermission();
                } else {
                    startMiniScreen();
                }

            }
        });
    }

    public void startMiniScreen() {
        preference = new SavedPreferences(PlayerActivity.this);
        preference.setVideo_path(f.getPath());
        preference.setVideo_position(player.getCurrentPosition());
        preference.setActuly_video_position(position);

        startService(new Intent(PlayerActivity.this, ServiceFloating.class));


        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    public static void requestAudioSettingsPermission(Activity activity) {

        String requiredPermission = Manifest.permission.RECORD_AUDIO;
        ActivityCompat.requestPermissions(activity, new String[]{requiredPermission}, PERMISSION);

    }

    public static boolean hasAudioSettingsPermission(Context context) {

        boolean hasPermission = (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED);
        return hasPermission;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "permission denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onRestart() {

        stopService(new Intent(PlayerActivity.this, ServiceFloating.class));
        super.onRestart();
    }

    @Override
    protected void onResume() {
        if (player != null) {
            player.onResume();
        }
        registerReceiver(receiver, intentFilter);
        super.onResume();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    protected void onPause() {
        if (player != null) {
            player.onPause();
        }

        if (position_models_list != null) {

            for (int z = 0; z < position_models_list.size(); z++) {
                Log.e("position_models_list", position_models_list.get(z).getVideo_path());
                if (position_models_list.get(z).getVideo_path().equals(f.getPath())) {

                    impliment = true;
                    position_models_list.get(z).setVideo_positipn(player.getCurrentPosition());

                    SharedPreferences wishPref = getApplicationContext().getSharedPreferences("bb", Context.MODE_PRIVATE);
                    SharedPreferences.Editor wishedit = wishPref.edit();
                    Gson gson = new Gson();
                    String json13 = gson.toJson(position_models_list);
                    wishedit.putString("video_resume_key", json13);
                    wishedit.commit();

                    break;
                }
            }
        }
        if (!impliment) {
            position_model ui = new position_model();
            ui.setVideo_path(f.getPath());
            ui.setVideo_positipn(player.getCurrentPosition());
            if (position_models_list == null) {
                position_models_list = new ArrayList<>();
            }
            position_models_list.add(ui);

            SharedPreferences wishPref = getApplicationContext().getSharedPreferences("bb", Context.MODE_PRIVATE);
            SharedPreferences.Editor wishedit = wishPref.edit();
            Gson gson = new Gson();
            String json13 = gson.toJson(position_models_list);
            wishedit.putString("video_resume_key", json13);
            wishedit.commit();
        }

        if (isFinishing()) {
            mVisualizer.release();
            mEqualizer.release();
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {

        setResult(RESULT_OK);
        if (visualLinearLayout.getVisibility() == View.VISIBLE) {
            visualLinearLayout.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
        } else {
            mVisualizer.release();
            mEqualizer.release();
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (player != null) {
            player.onDestroy();
        }
        if (isFinishing()) {
            mVisualizer.release();
            mEqualizer.release();
        }
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public class ConnectionReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String s = minivideomodel.mini_video_path;
            if (s != null) {
                player = new GiraffePlayer(PlayerActivity.this);
                player.setTitle(f.getName());
                player.play(s);
                player.function_onResume(minivideomodel.mini_video_positipn);
                minivideomodel.mini_video_path = null;
                minivideomodel.mini_video_positipn = 0;
                preference = new SavedPreferences(PlayerActivity.this);
                position = preference.getActuly_video_position();

            } else if (player != null) {
                player.onResume();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkDrawOverlayPermission() {
        Log.v("App", "Package Name: " + getApplicationContext().getPackageName());


        if (!Settings.canDrawOverlays(PlayerActivity.this)) {
            Log.v("App", "Requesting Permission" + Settings.canDrawOverlays(PlayerActivity.this));

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getApplicationContext().getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        } else {
            Log.v("App", "We already have permission for it.");
            startMiniScreen();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("App", "OnActivity Result.");


        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    startMiniScreen();
                }
            }
        }

    }
}