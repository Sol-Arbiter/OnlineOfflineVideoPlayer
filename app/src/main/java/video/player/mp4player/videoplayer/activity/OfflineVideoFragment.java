package video.player.mp4player.videoplayer.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import video.player.mp4player.videoplayer.Adapter.DrawerItemCustomAdapter;
import video.player.mp4player.videoplayer.Equilizer.VideoPlayerGestures.EqualizerFragment;
import video.player.mp4player.videoplayer.R;
import video.player.mp4player.videoplayer.SquareFrameLayout;
import video.player.mp4player.videoplayer.Utils.SavedPreferences;
import video.player.mp4player.videoplayer.fragment.FolderVideoFragment;
import video.player.mp4player.videoplayer.fragment.FoldersFragment;
import video.player.mp4player.videoplayer.fragment.FoldersVideoListFragment;

public class OfflineVideoFragment extends Fragment {
    private static Toolbar toolbar;
    private int mSortingType = 2;

    public static TabLayout tabLayout;
    private ViewPager viewPager;
    DrawerLayout drawer;
    public static int[] drawer_icons = {R.drawable.ic_video,
            R.drawable.ic_directory,
            R.drawable.ic_equalizer,
            R.drawable.settings,
            R.drawable.ic_share,
            R.drawable.information,
            R.drawable.rate
    };

    ArrayList<String> navigation_items;
    private ListView lv_drawer;
    private ImageView img_ic_menu;
    private ImageView img_ic_menu2;
    private ImageView img_ic_search;
    private ImageView img_gift;
    private TextView tv_title;

    private SquareFrameLayout layout_gift;
    private SquareFrameLayout layout_search;
    private SquareFrameLayout layout_menu2;

    RelativeLayout search_layout;
    private EditText et_search;
    private ImageView img_close;

    public static final int NAME_ASC = 0;
    public static final int NAME_DESC = 1;
    public static final int DATE_ASC = 2;
    public static final int DATE_DESC = 3;
    public static final int SIZE_ASC = 4;
    public static final int SIZE_DESC = 5;

    SavedPreferences preference;
    View root;


    MainActivity activity;

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        assert activity != null;
        preference = new SavedPreferences(activity);
        root = inflater.inflate(R.layout.offline_video_fragment, container, false);

        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        find_view_by_id();
        changeTheme();
        init();
        viewPager();
        search_code();


        return root;
    }

    public static void setTitle(String title) {
        toolbar.setTitle(title);
    }


    private void find_view_by_id() {
        toolbar = root.findViewById(R.id.toolbar);
        viewPager = root.findViewById(R.id.viewpager);
        tabLayout = root.findViewById(R.id.tabs);
        toolbar = root.findViewById(R.id.toolbar);
        drawer = root.findViewById(R.id.drawer_layout);
        lv_drawer = root.findViewById(R.id.lv_drawer);
        img_ic_menu = root.findViewById(R.id.img_ic_menu);
        img_ic_menu2 = root.findViewById(R.id.img_ic_menu2);
        img_gift = root.findViewById(R.id.img_gift);
        img_ic_search = root.findViewById(R.id.img_ic_search);
        tv_title = root.findViewById(R.id.tv_title);
        et_search = root.findViewById(R.id.et_search);
        layout_gift = root.findViewById(R.id.layout_gift);
        layout_search = root.findViewById(R.id.layout_search);
        layout_menu2 = root.findViewById(R.id.layout_menu2);
        search_layout = root.findViewById(R.id.search_layout);
        img_close = root.findViewById(R.id.img_close);
    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeTheme() {
        preference = new SavedPreferences(activity);
        if (preference.getTheem() == 1) {
            activity.setTheme(R.style.AppTheme);
            activity.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            activity.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            toolbar.setBackgroundResource(R.color.colorPrimary);
        } else if (preference.getTheem() == 2) {
            activity.setTheme(R.style.AppTheme_2);
            activity.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_2));
            activity.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_2));
            toolbar.setBackgroundResource(R.color.colorPrimary_2);

        } else if (preference.getTheem() == 3) {
            activity.setTheme(R.style.AppTheme_3);
            activity.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_3));
            activity.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_3));
            toolbar.setBackgroundResource(R.color.colorPrimary_3);

        } else if (preference.getTheem() == 4) {
            activity.setTheme(R.style.AppTheme_4);
            activity.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_4));
            activity.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_4));
            toolbar.setBackgroundResource(R.color.colorPrimary_4);
        } else if (preference.getTheem() == 5) {
            activity.setTheme(R.style.AppTheme_5);
            activity.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_5));
            activity.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_5));
            toolbar.setBackgroundResource(R.color.colorPrimary_5);

        } else if (preference.getTheem() == 6) {
            activity.setTheme(R.style.AppTheme_6);
            activity.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_6));
            activity.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_6));
            toolbar.setBackgroundResource(R.color.colorPrimary_6);
        } else {

        }


    }

    private void init() {
        activity.setSupportActionBar(toolbar);
        navigation_items = new ArrayList<>();
        navigation_items.add("Video");
        navigation_items.add("Directory");
        navigation_items.add("Equalizer");
        navigation_items.add("Settings");
        navigation_items.add("Share");
        navigation_items.add("Privacy & Policy");
        navigation_items.add("Rate");


        video.player.mp4player.videoplayer.Adapter.DrawerItemCustomAdapter drawerItemCustomAdapter = new DrawerItemCustomAdapter(activity, navigation_items, drawer_icons);
        lv_drawer.setAdapter(drawerItemCustomAdapter);

        lv_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (navigation_items.get(position).equalsIgnoreCase("Video")) {
                    viewPager.setCurrentItem(1, true);
                } else if (navigation_items.get(position).equalsIgnoreCase("Directory")) {
                    viewPager.setCurrentItem(0, true);
                } else if (navigation_items.get(position).equalsIgnoreCase("Equalizer")) {
                    toolbar.setVisibility(View.GONE);
                    EqualizerFragment fragment = new EqualizerFragment();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.MainContainer, fragment);
                    fragmentTransaction.commit();


                } else if (navigation_items.get(position).equalsIgnoreCase("Settings")) {

                    Intent intent = new Intent(activity, setting.class);

                    activity.startActivity(intent);

                } else if (navigation_items.get(position).equalsIgnoreCase("Share")) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Hey check out my app at: https://play.google.com/store/apps/details?id=" + activity.getPackageName());
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);


                } else if (navigation_items.get(position).equalsIgnoreCase("Privacy & Policy")) {

                    String url = "https://www.google.co.in/";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);


                } else if (navigation_items.get(position).equalsIgnoreCase("Rate")) {
                    launchMarket();
                }
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        img_ic_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        img_ic_menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(activity, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_main, popup.getMenu());
                if (mSortingType == 0) {
                    popup.getMenu().findItem(R.id.sort_name_asc).setChecked(true);
                } else if (mSortingType == 1) {
                    popup.getMenu().findItem(R.id.sort_name_dsc).setChecked(true);
                } else if (mSortingType == 2) {
                    popup.getMenu().findItem(R.id.sort_date_asc).setChecked(true);
                } else if (mSortingType == 3) {
                    popup.getMenu().findItem(R.id.sort_date_dsc).setChecked(true);
                } else if (mSortingType == 4) {
                    popup.getMenu().findItem(R.id.sort_size_asc).setChecked(true);
                } else if (mSortingType == 5) {
                    popup.getMenu().findItem(R.id.sort_size_dsc).setChecked(true);
                } else {
                }

                popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
                popup.show();
            }
        });
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.sort_name_asc:
                    updateSharedPreferenceAndGetNewList(NAME_ASC);
                    menuItem.setChecked(true);
                    break;
                case R.id.sort_name_dsc:
                    updateSharedPreferenceAndGetNewList(NAME_DESC);
                    menuItem.setChecked(true);
                    break;
                case R.id.sort_date_asc:
                    updateSharedPreferenceAndGetNewList(DATE_ASC);
                    menuItem.setChecked(true);
                    break;
                case R.id.sort_date_dsc:
                    updateSharedPreferenceAndGetNewList(DATE_DESC);
                    menuItem.setChecked(true);
                    break;
                case R.id.sort_size_asc:
                    updateSharedPreferenceAndGetNewList(SIZE_ASC);
                    menuItem.setChecked(true);
                    break;
                case R.id.sort_size_dsc:
                    updateSharedPreferenceAndGetNewList(SIZE_DESC);
                    menuItem.setChecked(true);
                    break;
                default:
                    break;
            }

            return false;
        }
    }

    private void updateSharedPreferenceAndGetNewList(int sortType) {
        preference.setShort_list(sortType);
        mSortingType = sortType;

        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.MainContainer);
        if (fragment instanceof FoldersVideoListFragment) {
            ((FoldersVideoListFragment) fragment).getAdapter().getSortedArray();
        } else if (viewPager.getCurrentItem()==1) {
            FolderVideoFragment.adapter.getSortedArray();
        } else if (viewPager.getCurrentItem()==0) {
            FoldersFragment.adapter.getSortedArray();
        }

    }


    private void viewPager() {
        activity.setSupportActionBar(toolbar);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"));
    }

    ViewPagerAdapter adapter;

    private void setupViewPager(final ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FoldersFragment(), "Folder");
        adapter.addFragment(new FolderVideoFragment(), "Video");
        viewPager.setAdapter(adapter);
        viewPager.addOnAdapterChangeListener(new ViewPager.OnAdapterChangeListener() {
            @Override
            public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter pagerAdapter, @Nullable PagerAdapter pagerAdapter1) {

            }
        });
/*
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (!et_search.getText().toString().equals("") && et_search.getVisibility() == View.VISIBLE) {
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.MainContainer);
                    if (fragment instanceof folder_video_list) {
                        folder_video_list.adapter.filter(et_search.getText().toString());
                    } else if (viewPager.getCurrentItem() == 1) {
                        if (f_video.adapter != null) {
                            f_video.adapter.filter(et_search.getText().toString());
                        }
                    } else if (viewPager.getCurrentItem() == 0) {
                        f_folder.adapter.filter(et_search.getText().toString());
                    } else {
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (et_search.getText().toString() != null && et_search.getVisibility() == View.VISIBLE) {
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.MainContainer);
                    if (fragment instanceof folder_video_list) {
                        folder_video_list.adapter.filter(et_search.getText().toString( ));
                    } else if (viewPager.getCurrentItem() == 1) {
                        if (f_video.adapter != null) {
                            f_video.adapter.filter(et_search.getText().toString());
                        }
                    } else if (viewPager.getCurrentItem() == 0) {
                        f_folder.adapter.filter(et_search.getText().toString());
                    } else {
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (et_search.getText().toString() != null && et_search.getVisibility() == View.VISIBLE) {
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.MainContainer);
                    if (fragment instanceof folder_video_list) {
                        folder_video_list.adapter.filter(et_search.getText().toString());
                    } else if (viewPager.getCurrentItem() == 1) {
                        if (f_video.adapter != null) {
                            f_video.adapter.filter(et_search.getText().toString());
                        }
                    } else if (viewPager.getCurrentItem() == 0) {
                        f_folder.adapter.filter(et_search.getText().toString());
                    } else {
                    }
                }

            }
        });
*/
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }

    private void search_code() {
        img_ic_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_title.setVisibility(View.GONE);
                layout_menu2.setVisibility(View.GONE);
                layout_gift.setVisibility(View.GONE);
                layout_search.setVisibility(View.GONE);

                search_layout.setVisibility(View.VISIBLE);

            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getChildFragmentManager().findFragmentById(R.id.MainContainer);
                tv_title.setVisibility(View.VISIBLE);
                layout_gift.setVisibility(View.VISIBLE);
                layout_search.setVisibility(View.VISIBLE);
                layout_menu2.setVisibility(View.VISIBLE);
                search_layout.setVisibility(View.GONE);
                et_search.setText("");

                if (fragment instanceof FoldersVideoListFragment) {
                    ((FoldersVideoListFragment) fragment).getAdapter().filter("");
                } else if (viewPager.getCurrentItem()==1) {
                    FoldersVideoListFragment.adapter.filter("");
                } else if (viewPager.getCurrentItem()==0) {
                  FoldersFragment.adapter.filter("");
                }


//                InputMethodManager inputManager =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputManager.hideSoftInputFromWindow(home.activity.getCurrentFocus().activity.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        img_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        // Capture Text in EditText
        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

                String text = et_search.getText().toString().toLowerCase(Locale.getDefault());
                Fragment fragment = getChildFragmentManager().findFragmentById(R.id.MainContainer);


                if (fragment instanceof FoldersVideoListFragment) {
                    ((FoldersVideoListFragment) fragment).getAdapter().filter(text);
                } else if (viewPager.getCurrentItem()==1) {
                    FoldersVideoListFragment.adapter.filter(text);
                } else if (viewPager.getCurrentItem()==0) {
                    FoldersFragment.adapter.filter(text);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }



   /* @Override
     void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {
            f_video.adapter.notifyDataSetChanged();
        }
    }*/


    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }
}
