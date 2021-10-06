package video.player.mp4player.videoplayer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import video.player.mp4player.videoplayer.FolderClickListener;
import video.player.mp4player.videoplayer.R;
import video.player.mp4player.videoplayer.Utils.SavedPreferences;
import video.player.mp4player.videoplayer.Utils.CommonUtilities;
import video.player.mp4player.videoplayer.activity.MainActivity;
import video.player.mp4player.videoplayer.model.FolderDataModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.MyViewHolder> {
    private final ArrayList<FolderDataModel> folderList;
    private ArrayList<String> videoList;

    private ArrayList<FolderDataModel> arraylist = null;


    private final Activity activity;
    private Context mContext;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView folder_name, items, newTabText;
        RelativeLayout folder_item_parent;
        ImageView img_angle;

        MyViewHolder(View view) {
            super(view);
            newTabText = (TextView) view.findViewById(R.id.txt_newtab);
            folder_name = (TextView) view.findViewById(R.id.txt_folder_name);
            items = (TextView) view.findViewById(R.id.txt_folder_video_no);
            img_angle = (ImageView) view.findViewById(R.id.img_angle);
            folder_item_parent = (RelativeLayout) view.findViewById(R.id.folder_item_parent);
        }
    }


    public FolderAdapter(Activity activity, Context context, ArrayList<FolderDataModel> folder_list) {

        this.activity = activity;
        this.mContext = context;
        this.folderList = new ArrayList<FolderDataModel>();
        this.arraylist = folder_list;
        this.folderList.addAll(folder_list);
        preference = new SavedPreferences(context);

        getSortedArray();


    }

    private SavedPreferences preference;

    public void getSortedArray() {

        final int sortType = preference.getShort_list();
        Log.e("video_adepter_short", String.valueOf(sortType));

        Collections.sort(folderList, new Comparator<FolderDataModel>() {
            public int compare(FolderDataModel obj1, FolderDataModel obj2) {
                if (sortType == 0) {
                    return obj1.getFolder_name().compareToIgnoreCase(obj2.getFolder_name());
                } else if (sortType == 1) {
                    return obj2.getFolder_name().compareToIgnoreCase(obj1.getFolder_name());
                } else if (sortType == 4) {
                    try {
                        return Integer.valueOf(obj1.getItems()).compareTo(Integer.valueOf(obj2.getItems()));

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return 0;
                    }
                } else if (sortType == 5) {
                    try {
                        return Integer.valueOf(obj2.getItems()).compareTo(Integer.valueOf(obj1.getItems()));

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return 0;
                    }
                } else {
                    return obj1.getFolder_name().compareToIgnoreCase(obj2.getFolder_name());
                }

            }
        });
        notifyDataSetChanged();
    }

    @Override
    public FolderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_l, parent, false);

        return new FolderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FolderAdapter.MyViewHolder holder, final int position) {
        final FolderDataModel folder = folderList.get(position);
        SavedPreferences preference = new SavedPreferences(activity);
        videoList = new ArrayList<>();
        int count = 0;
        final MainActivity activity= (MainActivity) holder.folder_item_parent.getContext();

        videoList = CommonUtilities.getFolderMedia(mContext, folder.getFolder_name());

        for (int i = 0; i < videoList.size(); i++) {
            File f = new File(videoList.get(i));


            if (preference.getNewtab() != null) {
                for (int z = 0; z < preference.getNewtab().size(); z++) {

                    if (preference.getNewtab().get(z).equals(f.getPath())) {
                        count = count + 1;
                    }
                }
            }
        }
        if (count == videoList.size()) {
            folder.setNewtab(true);
        }

        if (folder.isNewtab()) {
            holder.newTabText.setVisibility(View.GONE);
        } else {
            holder.newTabText.setVisibility(View.VISIBLE);
        }


        holder.folder_name.setText(new File(folder.getFolder_name()).getName());
        holder.items.setText(String.valueOf(folder.getItems()) + " items");

        holder.folder_item_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                folderClickListener.onFolderClicked(folder);
            }
        });

    }
    FolderClickListener folderClickListener;
    public void setOnFolderClick(FolderClickListener folderClickListener){
        this.folderClickListener=folderClickListener;
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        folderList.clear();
        if (charText.length() == 0) {
            folderList.addAll(arraylist);
        } else {
            for (int i = 0; i < arraylist.size(); i++) {
                if (arraylist.get(i).getFolder_name().toLowerCase(Locale.getDefault()).contains(charText)) {
                    folderList.add(arraylist.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

}