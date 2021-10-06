package video.player.mp4player.videoplayer.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import video.player.mp4player.videoplayer.Adapter.AllVideoListAdapter;
import video.player.mp4player.videoplayer.R;
import video.player.mp4player.videoplayer.activity.OfflineVideoFragment;
import video.player.mp4player.videoplayer.model.VideoListModel;


public class FoldersVideoListFragment extends Fragment {
    private RecyclerView recyclerView;
    public static AllVideoListAdapter adapter;
    private ArrayList<VideoListModel> video_list;
    private String folderPath;
    private Cursor videoCursor;
    private int videoColumnIndex;

    public AllVideoListAdapter getAdapter() {
        return adapter;
    }


    int count;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_folder_video_list, container, false);

        folderPath = getArguments().getString("FolderPath");
        OfflineVideoFragment.setTitle(folderPath);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        video_list = new ArrayList<>();
        System.gc();
        String[] proj = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE, MediaStore.Video.Media.RESOLUTION};
        videoCursor = getActivity().managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                proj, null, null, null);
        count = videoCursor.getCount();


        for (int i = 0; i < count; i++) {
            videoColumnIndex = videoCursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA);
            videoCursor.moveToPosition(i);
            String path = videoCursor.getString(videoColumnIndex);
            File f = new File(path);
            File f1 = new File(f.getParent());

            if (f1.getPath().equals(folderPath)) {

                videoColumnIndex = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                videoCursor.moveToPosition(i);
                String name = videoCursor.getString(videoColumnIndex);

                videoColumnIndex = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
                videoCursor.moveToPosition(i);
                String duration = videoCursor.getString(videoColumnIndex);

                videoColumnIndex = videoCursor.getColumnIndex(MediaStore.Video.VideoColumns.RESOLUTION);
                videoCursor.moveToPosition(i);
                String resolution = videoCursor.getString(videoColumnIndex);

                videoColumnIndex = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
                videoCursor.moveToPosition(i);
                int size = videoCursor.getInt(videoColumnIndex);

                videoColumnIndex = videoCursor.getColumnIndex(MediaStore.Video.VideoColumns.DATE_MODIFIED);
                videoCursor.moveToPosition(i);
                String date_fetch = videoCursor.getString(videoColumnIndex);


                File file = new File(path);
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy  hh:mm a");
                Date lastModDate1 = new Date(file.lastModified());
                String date = format.format(lastModDate1);

                video_list.add(new VideoListModel(path, name, duration, resolution, size, date));

            }
        }

        adapter = new AllVideoListAdapter(getActivity(), video_list, false);
        recyclerView_code();
        return v;
    }

    public void recyclerView_code() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


}
