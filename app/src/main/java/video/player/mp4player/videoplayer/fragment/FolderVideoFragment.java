package video.player.mp4player.videoplayer.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import video.player.mp4player.videoplayer.Adapter.AllVideoListAdapter;
import video.player.mp4player.videoplayer.R;
import video.player.mp4player.videoplayer.model.VideoListModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class FolderVideoFragment extends Fragment {

    private RecyclerView recyclerView;
    public static AllVideoListAdapter adapter;
    private ArrayList<VideoListModel> video_list;
    Context context;
    private Cursor videocursor;
    int count;
    private int video_column_index;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_f_video, container, false);
        context = getContext();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        video_list = new ArrayList<>();
        System.gc();
        String[] proj = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE, MediaStore.Video.Media.RESOLUTION};
        videocursor = getActivity().managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                proj, null, null, null);
        count = videocursor.getCount();

        for (int i = 0; i < count; i++) {
            video_column_index = videocursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA);
            videocursor.moveToPosition(i);
            String path =  videocursor.getString(video_column_index);

            video_column_index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
            videocursor.moveToPosition(i);
            String name= videocursor.getString(video_column_index);

            video_column_index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
            videocursor.moveToPosition(i);
            String duration= videocursor.getString(video_column_index);

            video_column_index = videocursor.getColumnIndex(MediaStore.Video.VideoColumns.RESOLUTION);
            videocursor.moveToPosition(i);
            String resolution =  videocursor.getString(video_column_index);

            video_column_index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
            videocursor.moveToPosition(i);
            int size = videocursor.getInt(video_column_index);


            File file = new File(path);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy  hh:mm a");
            Date lastModDate1 = new Date(file.lastModified());
            String date = format.format(lastModDate1);

            video_list.add(new VideoListModel(path,name, duration, resolution, size,  date));
        }

        adapter = new AllVideoListAdapter(getActivity(), video_list,true);
        recyclerView_code();


        return v;
    }

    public void recyclerView_code() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {
            adapter.notifyDataSetChanged();
        }
    }

}