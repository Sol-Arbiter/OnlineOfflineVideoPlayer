package video.player.mp4player.videoplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import video.player.mp4player.videoplayer.Adapter.FolderAdapter;
import video.player.mp4player.videoplayer.FolderClickListener;
import video.player.mp4player.videoplayer.R;
import video.player.mp4player.videoplayer.Utils.CommonUtilities;
import video.player.mp4player.videoplayer.activity.OfflineVideoFragment;
import video.player.mp4player.videoplayer.model.FolderDataModel;


public class FoldersFragment extends Fragment {
    private RecyclerView recyclerView;
    public static FolderAdapter adapter;
    private ArrayList<FolderDataModel> folder_list;
    private ArrayList<String> folder_list_t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_folders_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        folder_list = new ArrayList<>();
        folder_list_t = new ArrayList<>();
        folder_list_t = CommonUtilities.getAllMedia(getActivity());

        adapter = new FolderAdapter(getActivity(), getActivity(), CommonUtilities.getmediaperent(folder_list_t, folder_list)/*folder_list*/);

        adapter.setOnFolderClick(new FolderClickListener() {
            @Override
            public void onFolderClicked(FolderDataModel folder) {

                OfflineVideoFragment.tabLayout.setVisibility(View.GONE);
                FoldersVideoListFragment fragment = new FoldersVideoListFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.MainContainer, fragment);
                fragmentTransaction.addToBackStack(fragment.getClass().getName());
                fragmentTransaction.commit();

                Bundle arguments = new Bundle();
                arguments.putString("FolderPath", folder.getFolder_name());
                fragment.setArguments(arguments);
            }
        });

        setupRecycler();

        return v;
    }

    public FolderAdapter getAdapter() {
        return adapter;
    }


    public void setupRecycler() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}