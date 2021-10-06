package video.player.mp4player.videoplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import video.player.mp4player.videoplayer.R;
import com.google.android.gms.ads.AdRequest;



public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_scr);

        mInterstitialAdMob = showAdmobFullAd();
        this.mInterstitialAdMob.loadAd(new AdRequest.Builder()
                .build());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                showAdmobInterstitial();
            }
        },3000);

    }


    @Override
    protected void onPause() {

        super.onPause();
        finish();

    }

    private com.google.android.gms.ads.InterstitialAd mInterstitialAdMob;

    private com.google.android.gms.ads.InterstitialAd showAdmobFullAd() {
        com.google.android.gms.ads.InterstitialAd interstitialAd = new com.google.android.gms.ads.InterstitialAd(SplashScreen.this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAdMob.loadAd(new AdRequest.Builder()
                        .addTestDevice("0E9048D9285193167D9674E79562F5DC")
                        .addTestDevice("34D19C0181647FFA4D8F095F773F2154")
                        .build());
            }

            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdOpened() {
            }
        });
        return interstitialAd;
    }


    private void showAdmobInterstitial() {
        if (this.mInterstitialAdMob != null && this.mInterstitialAdMob.isLoaded()) {
            this.mInterstitialAdMob.show();
        }
    }


}