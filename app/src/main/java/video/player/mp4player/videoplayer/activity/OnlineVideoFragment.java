package video.player.mp4player.videoplayer.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarteist.autoimageslider.SliderLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import video.player.mp4player.videoplayer.R;
import video.player.mp4player.videoplayer.Utils.SavedPreferences;
import video.player.mp4player.videoplayer.fragment.OnlineVideoListAdapter;
import video.player.mp4player.videoplayer.fragment.OnlineVideoListModel;
import video.player.mp4player.videoplayer.fragment.SearchResultFragment;

import static com.android.volley.VolleyLog.TAG;

public class OnlineVideoFragment extends Fragment {
    private ImageView img_ic_search;

    LinearLayout lil_action_bar;
    RelativeLayout rlo_screen, rlo_search_bar;
    MainActivity activity;
    ProgressBar loading;
    TextView info;
    private XRecyclerView recyclerView;
    public OnlineVideoListAdapter adapter;
    String nextPageToken = "";
    SliderLayout sliderLayout;
    ImageView img_back, img_clear;
    EditText et_search;
    LinearLayout ol_action_bar;
    private SavedPreferences preference;

    View root;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.online_video_fragment, container, false);
        activity = ((MainActivity) getContext());
        assert activity != null;
        preference = new SavedPreferences(activity);
        findViews();
        changeTheme();
        searchVideo();

        loadInitialVideos();

        return root;
    }


    private void findViews() {
        img_ic_search = root.findViewById(R.id.img_ic_search);
        ol_action_bar = root.findViewById(R.id.ol_action_bar);
        recyclerView = root.findViewById(R.id.rv_suggesstion_video);
        rlo_screen = root.findViewById(R.id.rlo_screen);
        rlo_search_bar = root.findViewById(R.id.rlo_search_bar);
        lil_action_bar = root.findViewById(R.id.lil_action_bar);
        et_search = root.findViewById(R.id.et_search);
        img_back = root.findViewById(R.id.img_back);
        img_clear = root.findViewById(R.id.img_clear);
        info = root.findViewById(R.id.info);
        loading = root.findViewById(R.id.loading);
        recyclerView.setLoadingMoreEnabled(true);

        adapter = new OnlineVideoListAdapter(activity);
        videoRecycleView();
    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeTheme() {
        if (preference.getTheem() == 1) {
            activity.setTheme(R.style.AppTheme);
            activity.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            activity.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            ol_action_bar.setBackgroundResource(R.color.colorPrimary);

        } else if (preference.getTheem() == 2) {
            activity.setTheme(R.style.AppTheme_2);
            activity.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_2));
            activity.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_2));
            ol_action_bar.setBackgroundResource(R.color.colorPrimary_2);

        } else if (preference.getTheem() == 3) {
            activity.setTheme(R.style.AppTheme_3);
            activity.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_3));
            activity.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_3));
            ol_action_bar.setBackgroundResource(R.color.colorPrimary_3);

        } else if (preference.getTheem() == 4) {
            activity.setTheme(R.style.AppTheme_4);
            activity.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_4));
            activity.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_4));
            ol_action_bar.setBackgroundResource(R.color.colorPrimary_4);
        } else if (preference.getTheem() == 5) {
            activity.setTheme(R.style.AppTheme_5);
            activity.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_5));
            activity.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_5));
            ol_action_bar.setBackgroundResource(R.color.colorPrimary_5);

        } else if (preference.getTheem() == 6) {
            activity.setTheme(R.style.AppTheme_6);
            activity.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary_6));
            activity.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary_6));
            ol_action_bar.setBackgroundResource(R.color.colorPrimary_6);
        } else {

        }


    }

    private void searchVideo() {
        img_ic_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlo_search_bar.setVisibility(View.VISIBLE);
                lil_action_bar.setVisibility(View.GONE);
                et_search.requestFocus();
                showKeyboard(activity);
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = getChildFragmentManager().findFragmentById(R.id.rlo_screen);
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (fragment != null) {
                    fragmentTransaction.remove(fragment);
                    fragmentTransaction.commit();
                } else {
                    rlo_search_bar.setVisibility(View.GONE);
                    lil_action_bar.setVisibility(View.VISIBLE);
                    et_search.setText(null);
                }

            }
        });
        img_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText(null);
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String text = String.valueOf(et_search.getText());
                    if (!text.equals("")) {
                        hideKeyboard(activity);
                        SearchResultFragment frag = new SearchResultFragment();
                        FragmentManager manager = getChildFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.add(R.id.rlo_screen, frag, null);
                        transaction.commit();

                        Bundle arguments = new Bundle();
                        arguments.putString("search_string", text);
                        frag.setArguments(arguments);

                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.showSoftInputFromInputMethod(view.getWindowToken(), 0);
    }

    public void videoRecycleView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setPullRefreshEnabled(false);
//        View header = LayoutInflater.from(activity).inflate(R.layout.slider_header, (ViewGroup) root.findViewById(android.R.id.content), false);
//        sliderLayout = header.findViewById(R.id.imageSlider);
//        setSliderViews();
//        recyclerView.addHeaderView(header);
        recyclerView.setAdapter(adapter);

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                recyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
                loadMoreVideos();
            }
        });
    }

    private void loadMoreVideos() {
        String JSON_URL_NEW = "https://www.googleapis.com/youtube/v3/videos?pageToken=" + nextPageToken + "&part=contentDetails&chart=mostPopular&regionCode=IN&maxResults=10&key=AIzaSyBpT3K0OiojVRvZJWyjf35hH7kaYW9kmF4";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL_NEW,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            nextPageToken = obj.getString("nextPageToken");
                            JSONArray tutorialsArray = obj.getJSONArray("items");
                            for (int i = 0; i < tutorialsArray.length(); i++) {
                                JSONObject tutorialsObject = tutorialsArray.getJSONObject(i);
                                if (!tutorialsObject.getJSONObject("contentDetails").has("regionRestriction")) {
                                    getDetails(tutorialsObject.getString("id"));

                                }
                            }
                            recyclerView.loadMoreComplete();
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof TimeoutError) {
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setTag(TAG);
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);

    }

    private void loadInitialVideos() {
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(activity.TELEPHONY_SERVICE);
        String countryCode = tm.getNetworkCountryIso();

        String JSON_URL = "https://www.googleapis.com/youtube/v3/videos?part=contentDetails&chart=mostPopular&regionCode=" + countryCode + "&maxResults=10&key=";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL + getString(R.string.api_key),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            nextPageToken = obj.getString("nextPageToken");
                            JSONArray tutorialsArray = obj.getJSONArray("items");
                            for (int i = 0; i < tutorialsArray.length(); i++) {
                                JSONObject tutorialsObject = tutorialsArray.getJSONObject(i);
                                if (!tutorialsObject.getJSONObject("contentDetails").has("regionRestriction")) {
                                    getDetails(tutorialsObject.getString("id"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (loading.getVisibility() == View.VISIBLE){
                    loading.setVisibility(View.GONE);
                    info.setText("Loading failed...");
                }

                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof TimeoutError) {
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setTag(TAG);
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    OnlineVideoListModel video;

    private void getDetails(final String videoId) {
        String api = "https://www.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&id=" + videoId + "&key=" + getString(R.string.api_key);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject tutorialsObject = obj.getJSONArray("items").getJSONObject(0);

                            String currentString = tutorialsObject.getJSONObject("snippet").getString("publishedAt");
                            String[] separated = currentString.split("T");
                            video = new OnlineVideoListModel(videoId,
                                    tutorialsObject.getJSONObject("snippet").getString("title"),
                                    tutorialsObject.getJSONObject("snippet").getString("title"),
                                    tutorialsObject.getJSONObject("statistics").getString("viewCount"),
                                    tutorialsObject.getJSONObject("snippet").getString("description"), separated[0],
                                    tutorialsObject.getJSONObject("contentDetails").getString("duration"), "thumbnail"
                            );
                            adapter.addVideo(video);
                            if (loading.getVisibility() == View.VISIBLE){
                                loading.setVisibility(View.GONE);
                                info.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.GONE);
                info.setText("Loading failed...");

                if (error instanceof NetworkError || error instanceof ServerError || error instanceof AuthFailureError || error instanceof ParseError || error instanceof TimeoutError) {
//                    Log.e("Loading initial videos", error.getLocalizedMessage());
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setTag(TAG);
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);

    }


}
