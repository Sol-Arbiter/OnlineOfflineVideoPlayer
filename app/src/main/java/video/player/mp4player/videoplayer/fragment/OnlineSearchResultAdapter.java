package video.player.mp4player.videoplayer.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import video.player.mp4player.videoplayer.R;
import video.player.mp4player.videoplayer.activity.OnlineVideoPlayer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;

public class OnlineSearchResultAdapter extends RecyclerView.Adapter<OnlineSearchResultAdapter.MyViewHolder> {
    private Activity mContext;
    private ArrayList<search_video_list> category_list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_chanul;
        public ImageView video_img;
        public LinearLayout lil_category_title;


        public MyViewHolder(View view) {
            super(view);
            video_img = (ImageView) view.findViewById(R.id.video_img);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_chanul = (TextView) view.findViewById(R.id.tv_chanul);
            lil_category_title = (LinearLayout) view.findViewById(R.id.lil_category_title);
        }
    }

    public OnlineSearchResultAdapter(final Activity mContext, ArrayList<search_video_list> category_list) {

        this.mContext = mContext;
        this.category_list = category_list;

        mInterstitialAdMob = showAdmobFullAd(mContext);
        this.mInterstitialAdMob.loadAd(new AdRequest.Builder()
                .addTestDevice("0E9048D9285193167D9674E79562F5DC")
                .addTestDevice("34D19C0181647FFA4D8F095F773F2154")
                .build());

    }


    private com.google.android.gms.ads.InterstitialAd mInterstitialAdMob;

    private com.google.android.gms.ads.InterstitialAd showAdmobFullAd(Context context) {
        com.google.android.gms.ads.InterstitialAd interstitialAd = new com.google.android.gms.ads.InterstitialAd(context);
        interstitialAd.setAdUnitId(context.getString(R.string.interstitial_ad_unit_id));
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


    @Override
    public OnlineSearchResultAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_layout, parent, false);

        return new OnlineSearchResultAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineSearchResultAdapter.MyViewHolder holder, int position) {
        final search_video_list search_video_list = category_list.get(position);
        RequestOptions options = new RequestOptions()
                .fitCenter();

        Glide.with(mContext)
                .load("http://img.youtube.com/vi/" + search_video_list.getMsvl_videoId() + "/0.jpg")
//                .apply(options)
                .into(holder.video_img);
        holder.lil_category_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OnlineVideoPlayer.class);
                intent.putExtra("video_id", search_video_list.getMsvl_videoId());
                mContext.startActivity(intent);
                showAdmobInterstitial();


            }
        });
        holder.tv_chanul.setText(search_video_list.getMsvl_chanal());
        holder.tv_title.setText(search_video_list.getMsvl_title());

    }

    @Override
    public int getItemCount() {
        return category_list.size();
    }

}

