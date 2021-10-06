package video.player.mp4player.videoplayer.Equilizer.VideoPlayerGestures;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import video.player.mp4player.videoplayer.Equilizer.VisualizerView;
import video.player.mp4player.videoplayer.R;
import video.player.mp4player.videoplayer.Utils.SavedPreferences;
import video.player.mp4player.videoplayer.activity.MainActivity;
import video.player.mp4player.videoplayer.activity.OfflineVideoFragment;


public class EqualizerFragment extends Fragment {
    public static Visualizer mVisualizer;
    public static Equalizer mEqualizer;
    public VisualizerView mVisualizerView;
    private SavedPreferences preference;
    SeekBar seekBar1, seekBar2, seekBar3, seekBar4, seekBar5;
    private static final float VISUALIZER_HEIGHT_DIP = 50f;
    private static final int PERMISSION = 1234;
    private LinearLayout mLinearLayout;
    Spinner equalizerPresetSpinner;
    MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_equlizer, container, false);
        activity = (MainActivity) getActivity();
        preference = new SavedPreferences(activity);
        seekBar1 = (SeekBar) v.findViewById(R.id.seekBar1);
        seekBar2 = (SeekBar) v.findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar) v.findViewById(R.id.seekBar3);
        seekBar4 = (SeekBar) v.findViewById(R.id.seekBar4);
        seekBar5 = (SeekBar) v.findViewById(R.id.seekBar5);
        equalizerPresetSpinner = (Spinner) v.findViewById(R.id.spinner);
        mLinearLayout = (LinearLayout) v.findViewById(R.id.linearLayoutVisual);


        mEqualizer = new Equalizer(0, 0);
        mEqualizer.setEnabled(true);

        if (!hasAudioSettingsPermission(activity)) {
            requestAudioSettingsPermission(activity);
        }
        if (hasAudioSettingsPermission(activity)) {
            setupVisualizerFxAndUI();
        }
        spinner_array_add();
        setupEqualizerFxAndUI();
        mVisualizer.setEnabled(true);

        mVisualizerView.setVisualizerColor(new ColorDrawable(getResources().getColor(R.color.textColorPrimary)));

        return v;
    }

    private void spinner_array_add() {

    }

    private void equalizeSound() {

        ArrayList<String> equalizerPresetNames = new ArrayList<String>();
        ArrayAdapter<String> equalizerPresetSpinnerAdapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item,
                equalizerPresetNames);
        equalizerPresetSpinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

                if (position == 0) {
                    seekBar1.setProgress(1500);
                    seekBar2.setProgress(1500);
                    seekBar3.setProgress(1500);
                    seekBar4.setProgress(1500);
                    seekBar5.setProgress(1500);
                }
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
        TextView frequencyHeaderTextview = new TextView(activity);
        frequencyHeaderTextview.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        frequencyHeaderTextview.setGravity(Gravity.CENTER_HORIZONTAL);
        frequencyHeaderTextview
                .setText((mEqualizer.getCenterFreq((short) 0) / 1000) + " Hz");
        LinearLayout seekBarRowLayout = new LinearLayout(activity);
        seekBarRowLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView lowerEqualizerBandLevelTextview = new TextView(activity);
        lowerEqualizerBandLevelTextview.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        lowerEqualizerBandLevelTextview.setText((lowerEqualizerBandLevel / 100) + " dB");
        lowerEqualizerBandLevelTextview.setRotation(90);
        TextView upperEqualizerBandLevelTextview = new TextView(activity);
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

            }
        });


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
        mVisualizerView = new VisualizerView(activity);
        mVisualizerView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (VISUALIZER_HEIGHT_DIP * getResources().getDisplayMetrics().density)));
        mLinearLayout.addView(mVisualizerView);

        mVisualizer = new Visualizer(0);
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

    public static void requestAudioSettingsPermission(Activity activity) {

        String requiredPermission = Manifest.permission.RECORD_AUDIO;
        ActivityCompat.requestPermissions(activity, new String[]{requiredPermission}, PERMISSION);

    }

    public static boolean hasAudioSettingsPermission(Context context) {

        boolean hasPermission = (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED);
        return hasPermission;
    }


}
