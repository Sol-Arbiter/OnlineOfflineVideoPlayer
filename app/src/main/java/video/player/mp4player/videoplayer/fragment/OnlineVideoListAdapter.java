package video.player.mp4player.videoplayer.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import video.player.mp4player.videoplayer.R;
import video.player.mp4player.videoplayer.activity.OnlineVideoPlayer;


public class OnlineVideoListAdapter extends XRecyclerView.Adapter<OnlineVideoListAdapter.MyViewHolder> {
    private Activity mContext;
    private ArrayList<OnlineVideoListModel> videoList;

    public class MyViewHolder extends XRecyclerView.ViewHolder {
        public TextView tv_title, tv_duration;
        public ImageView video_img;
        public LinearLayout videoLayout;

        public MyViewHolder(View view) {
            super(view);
            video_img = (ImageView) view.findViewById(R.id.video_img);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_duration = (TextView) view.findViewById(R.id.tv_duration);
            videoLayout = (LinearLayout) view.findViewById(R.id.lil_category_title);
        }
    }

    public OnlineVideoListAdapter(final Activity mContext) {
        this.mContext = mContext;
        this.videoList = new ArrayList<>();

    }


    private String getDuration(String duration) {
        String s1 = duration.replace("PT", "");
        String t = "";
        String h = "", m = "", s = "";
//        try {
        if (s1.contains("H") && s1.contains("M") && s1.contains("S")) {
            h = s1.split("H")[0];
            m = s1.split("H")[1].split("M")[0];
            s = s1.split("H")[1].split("M")[1].split("S")[0];
            t = h + ":" + m + ":" + s;
        } else if (s1.contains("H") && s1.contains("M")) {
            h = s1.split("H")[0];
            m = s1.split("H")[1].split("M")[0];
        } else if (s1.contains("H") && s1.contains("S")) {
            h = s1.split("H")[0];
            m = "00";
            s = s1.split("H")[1].split("S")[0];
            t = h + ":" + m + ":" + s;
        } else if (s1.contains("M") && s1.contains("S")) {
            m = s1.split("M")[0];
            s = s1.split("M")[1].split("S")[0];
            t = m + ":" + s;
        } else if (s1.contains("H")) {
            h = s1.split("H")[0];
            m = "00";
            s = "00";
            t = h + ":" + m + ":" + s;
        } else if (s1.contains("M")) {
            m = s1.split("M")[0];
            s = "00";
            t =  m + ":" + s;
        } else if (s1.contains("S")) {
            s = s1.split("S")[0] + "Sec";
            t = s;
        }

            /*
            if (s1.contains("H")) {
                String h = s1.split("H")[0];
                String s = "00", m = "00";
                if (s1.contains("M"))
                    m = s1.split("H")[1].split("M")[0];
                if (s1.contains("S"))
                    s = s1.split("H")[1].split("M")[1].replace("S", "");
                t = h.concat(":").concat(m).concat(":").concat(s);
            } else if (s1.contains("M")) {
                String m = s1.split("M")[0];
                String s = "00";
                if (s1.contains("S"))
                    s = s1.split("M")[1].replace("S", "");
                t = t.concat(m).concat(":").concat(s);
            } else
                t = t.concat(s1.replace("S", "sec"));
        } finally {
//            t = t.concat(s1.replace("S", "sec"));
        }*/



        return t;
    }


    public void addVideo(OnlineVideoListModel video) {
        videoList.add(videoList.size() == 0 ? 0 : videoList.size(), video);
        notifyItemInserted(videoList.size() == 0 ? 0 : videoList.size());
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ol_home, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final OnlineVideoListModel video = videoList.get(position);
//        new LongOperation(holder.tv_title, holder.video_img, video.getVideoId()).execute(video.getVideoId());
        Glide.with(mContext)
                .load("http://img.youtube.com/vi/" + video.getVideoId() + "/0.jpg")
                .apply(new RequestOptions()
                        .centerCrop())
                .into(holder.video_img);
        holder.tv_title.setText(video.getVideoTitle());
        holder.tv_duration.setText(getDuration(video.getDuration()));

        holder.videoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OnlineVideoPlayer.class);
                intent.putExtra("video_id", video.getVideoId());
                intent.putExtra("title", video.getVideoTitle());
                intent.putExtra("description", video.getVideoDetails());
                intent.putExtra("views", video.getViewsCount());
                intent.putExtra("date", video.getUploadDate());
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

/*    private class LongOperation extends AsyncTask<String, Void, String> {
        ImageView video_img;
        TextView tv_title;
        String str_id;
        final String[] title = {null};

        public LongOperation(TextView tv_title, ImageView video_img, String str_id) {
            this.video_img = video_img;
            this.tv_title = tv_title;
            this.str_id = str_id;
        }

        @Override
        protected String doInBackground(String... params) {
            String youtubeVideoId = params[0];
            try {
                if (youtubeVideoId != null) {
                    InputStream is = new URL("http://www.youtube.com/oembed?url=https://www.youtube.com/watch?v=" + youtubeVideoId + "&format=json").openStream();
                    Log.e("is", String.valueOf(is));
                    title[0] = new JSONObject(IOUtils.toString(is)).getString("title");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return title[0];
        }

        @Override
        protected void onPostExecute(String result) {
            tv_title.setText(title[0]);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }*/
}