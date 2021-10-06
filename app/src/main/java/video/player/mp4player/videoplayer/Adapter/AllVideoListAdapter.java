package video.player.mp4player.videoplayer.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import video.player.mp4player.videoplayer.R;
import video.player.mp4player.videoplayer.Utils.CommonUtilities;
import video.player.mp4player.videoplayer.Utils.SavedPreferences;
import video.player.mp4player.videoplayer.activity.PlayerActivity;
import video.player.mp4player.videoplayer.model.VideoListModel;


public class AllVideoListAdapter extends RecyclerView.Adapter<AllVideoListAdapter.MyViewHolder> {
    private final boolean ad;
    private Activity mContext;
    private ArrayList<VideoListModel> albumList;
    private ArrayList<VideoListModel> arraylist = null;
    SavedPreferences preference;
    private boolean impliments = false;
    MediaPlayer mp;

    private String height;
    private String width;
    private long timeInMillisec;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_video_name, txt_video_time, txt_video_size, lastModDate, space, txt_newtab;
        public ImageView img_video;
        public ImageView img_video_menu;
        public LinearLayout path;

        public MyViewHolder(View view) {
            super(view);
            txt_newtab = (TextView) view.findViewById(R.id.txt_newtab);
            txt_video_size = (TextView) view.findViewById(R.id.txt_video_size);
            txt_video_time = (TextView) view.findViewById(R.id.txt_video_time);
            txt_video_name = (TextView) view.findViewById(R.id.txt_video_name);
            lastModDate = (TextView) view.findViewById(R.id.lastModDate);
            space = (TextView) view.findViewById(R.id.space);
            img_video = (ImageView) view.findViewById(R.id.img_video);
            img_video_menu = (ImageView) view.findViewById(R.id.img_video_menu);
            path = (LinearLayout) view.findViewById(R.id.path);
        }
    }


    public AllVideoListAdapter(final Activity mContext, ArrayList<VideoListModel> video_List, boolean ad) {


        this.ad = ad;
        this.mContext = mContext;
        this.albumList = new ArrayList<VideoListModel>();
        this.arraylist = video_List;
        this.albumList.addAll(video_List);

        preference = new SavedPreferences(mContext);

        getSortedArray();
        admobInterstitialAd();

    }

    boolean isInters;

    private void showAdmobInterstitial() {
        isInters = true;
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (isInters) {
                    mInterstitialAd.show();
                }
                super.onAdLoaded();
            }

            @Override
            public void onAdClosed() {
                isInters = false;
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                super.onAdClosed();
            }
        });
    }

    private InterstitialAd mInterstitialAd;

    @SuppressLint("StaticFieldLeak")
    private void admobInterstitialAd() {
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId(mContext.getString(R.string.interstitial_ad_unit_id));
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mInterstitialAd.loadAd(new AdRequest.Builder()
                                .addTestDevice("0E9048D9285193167D9674E79562F5DC")
                                .addTestDevice("34D19C0181647FFA4D8F095F773F2154")
                                .build());
                    }
                });

                return null;
            }
        }.execute();

    }

    public void getSortedArray() {

        final int sortType = preference.getShort_list();
        Log.e("video_adepter_short", String.valueOf(sortType));

        Collections.sort(albumList, new Comparator<VideoListModel>() {
            public int compare(VideoListModel obj1, VideoListModel obj2) {
                if (sortType == 0) {
                    return obj1.getTxt_video_name().compareToIgnoreCase(obj2.getTxt_video_name());
                } else if (sortType == 1) {
                    return obj2.getTxt_video_name().compareToIgnoreCase(obj1.getTxt_video_name());
                } else if (sortType == 2) {
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy  hh:mm a");
                    try {
                        return format.parse(obj1.getLastModDate()).compareTo(format.parse(obj2.getLastModDate()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return 0;
                    }
                } else if (sortType == 3) {
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy  hh:mm a");
                    try {
                        return format.parse(obj2.getLastModDate()).compareTo(format.parse(obj1.getLastModDate()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return 0;
                    }
                } else if (sortType == 4) {
                    try {
                        return Integer.valueOf(obj1.getSpace()).compareTo(Integer.valueOf(obj2.getSpace()));

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return 0;
                    }
                } else if (sortType == 5) {
                    try {
                        return Integer.valueOf(obj2.getSpace()).compareTo(Integer.valueOf(obj1.getSpace()));

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return 0;
                    }
                } else {
                    return obj1.getTxt_video_name().compareToIgnoreCase(obj2.getTxt_video_name());
                }

            }
        });
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_l, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final VideoListModel video_l = albumList.get(position);
        SavedPreferences preference = new SavedPreferences(mContext);

        final File f = new File(video_l.getPath());
        if (preference.getNewtab() != null) {
            for (int z = 0; z < preference.getNewtab().size(); z++) {
                if (preference.getNewtab().get(z).equals(f.getPath())) {
                    video_l.setNewtab(true);
                }
            }
        }
        if (video_l.isNewtab()) {
            holder.txt_newtab.setVisibility(View.GONE);
        } else {
            holder.txt_newtab.setVisibility(View.VISIBLE);
        }

        Uri uri = Uri.fromFile(new File(video_l.getPath()));
        Glide.with(mContext)
                .load(uri)
                .thumbnail(0.1f)
                .apply(new RequestOptions().override(100, 100))
                .into(holder.img_video);


        if (video_l.getTxt_video_name() != null) {
            holder.txt_video_name.setText(video_l.getTxt_video_name());
        }

        if (video_l.getTxt_video_size() != null) {
            long timeInMillisecs = Long.parseLong(video_l.getTxt_video_size());
            holder.txt_video_time.setText(CommonUtilities.milliSecondsToTimer(timeInMillisecs));
        }

        if (video_l.getMp_resolution() != null) {
            holder.txt_video_size.setText(video_l.getMp_resolution());
        }

        if (video_l.getSpace() != 0) {

            long sizeInBytes = video_l.getSpace();
            long sizeInMb = sizeInBytes / (1024 * 1024);
            int space = (int) sizeInMb;

            holder.space.setText(String.valueOf(space));
        }

        if (video_l.getLastModDate() != null) {
            holder.lastModDate.setText(video_l.getLastModDate());
        }


        holder.path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SavedPreferences preference = new SavedPreferences(mContext);
                if (preference.getNewtab() != null) {

                    for (int z = 0; z < preference.getNewtab().size(); z++) {
                        if (preference.getNewtab().equals(f.getPath())) {
                            impliments = true;
                            break;
                        }
                    }
                }
                if (!impliments) {
                    ArrayList<String> stringArrayList = preference.getNewtab();
                    if (stringArrayList == null) {
                        stringArrayList = new ArrayList<>();
                    }
                    stringArrayList.add(f.getPath());
                    preference.setNewtab(stringArrayList);
                }

                notifyDataSetChanged();
                Intent intent = new Intent(mContext, PlayerActivity.class);
                CommonUtilities.FinalVideoList = albumList;
                intent.putExtra("position", position);
                mContext.startActivityForResult(intent, 1001);

                showAdmobInterstitial();

            }
        });

        holder.img_video_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.img_video_menu, f.getPath());
            }
        });

    }

    private void showPopupMenu(View view, String path) {

        String Video_path = path;
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_video, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(Video_path));
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private final String Path;
        File f_file;

        public MyMenuItemClickListener(String Path) {
            this.Path = Path;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_details:
                    f_file = new File(Path);

                    long sizeInBytes = f_file.length();
                    long sizeInMb = sizeInBytes / (1024 * 1024);
                    float space = sizeInMb;


                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy  hh:mm a");
                    Date lastModDate1 = new Date(f_file.lastModified());
                    String date = format.format(lastModDate1);
                    MediaPlayer mp = MediaPlayer.create(mContext, Uri.parse(Path));

                    String millis = String.valueOf(mp.getDuration());
                    timeInMillisec = Long.parseLong(millis);
                    String height = String.valueOf(mp.getVideoHeight());
                    String width = String.valueOf(mp.getVideoWidth());

                    Dialog customDialog;
                    LayoutInflater inflater = (LayoutInflater) mContext.getLayoutInflater();
                    View customView = inflater.inflate(R.layout.custom_dialog, null);

                    customDialog = new Dialog(mContext, R.style.MyAlertDialogTheme);
                    customDialog.setContentView(customView);

                    TextView file = (TextView) customView.findViewById(R.id.file);
                    TextView Location = (TextView) customView.findViewById(R.id.Location);
                    TextView Size = (TextView) customView.findViewById(R.id.Size);
                    TextView Date = (TextView) customView.findViewById(R.id.Date);
                    TextView Format = (TextView) customView.findViewById(R.id.Format);
                    TextView Resolution = (TextView) customView.findViewById(R.id.Resolution);
                    TextView Length = (TextView) customView.findViewById(R.id.Length);
                    TextView Finished = (TextView) customView.findViewById(R.id.Finished);
                    TextView Last_position = (TextView) customView.findViewById(R.id.Last_position);
                    file.setText(f_file.getName());
                    Location.setText(f_file.getParent());
                    Size.setText(String.valueOf(space));
                    Date.setText(date);
                    Format.setText(f_file.getName());
                    Resolution.setText(height + "*" + width);
                    Length.setText(CommonUtilities.milliSecondsToTimer(timeInMillisec));
                    customDialog.show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        albumList.clear();
        if (charText.length() == 0) {
            albumList.addAll(arraylist);
        } else {
            for (int i = 0; i < arraylist.size(); i++) {

                if (arraylist.get(i).getTxt_video_name().toLowerCase(Locale.getDefault()).contains(charText)) {
                    albumList.add(arraylist.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }


}