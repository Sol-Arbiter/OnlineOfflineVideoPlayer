package video.player.mp4player.videoplayer.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import video.player.mp4player.videoplayer.R;

import static android.view.View.GONE;
import static com.android.volley.VolleyLog.TAG;

public class SearchResultFragment extends Fragment {
    private static String JSON_URL = "";
    private String search_string;
    String nextPageToken = "";
    ProgressBar loading;
    public OnlineVideoListAdapter adapter;
    private XRecyclerView recyclerView;
    private EndlessRecyclerViewScrollListener scrollListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_online_search_result, container, false);
        recyclerView = v.findViewById(R.id.rv_suggesstion_video);
        loading = v.findViewById(R.id.loading);
        adapter = new OnlineVideoListAdapter(getActivity());
        search_string = getArguments().getString("search_string");
        JSON_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=25&q=" + search_string + "&key=" + getString(R.string.api_key);
        setupViewAdapter();
        loadVideos();
        return v;
    }

    private void loadVideos() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            nextPageToken = obj.getString("nextPageToken");
                            JSONArray tutorialsArray = obj.getJSONArray("items");
                            for (int i = 0; i < tutorialsArray.length(); i++) {
                                JSONObject tutorialsObject = tutorialsArray.getJSONObject(i);
                                if (tutorialsObject.getJSONObject("id").has("videoId")) {
                                    getDetails(tutorialsObject.getJSONObject("id").getString("videoId"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (loading.getVisibility() == View.VISIBLE)
                    loading.setVisibility(GONE);
                if (error instanceof NetworkError || error instanceof ServerError || error instanceof AuthFailureError || error instanceof ParseError || error instanceof TimeoutError) {
//                    Log.e("Loading initial videos", error.getLocalizedMessage());
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setTag(TAG);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void setupViewAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setPullRefreshEnabled(false);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadMoreVideos();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        adapter = new OnlineVideoListAdapter(getActivity());
        recyclerView.setAdapter(adapter);

    }

    private void loadMoreVideos() {
        String JSON_URL_NEW = "https://www.googleapis.com/youtube/v3/search?pageToken=" + nextPageToken + "&part=snippet&maxResults=25&q=" + search_string + "&key=" + getString(R.string.api_key);
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
                                getDetails(tutorialsObject.getJSONObject("id").getString("videoId"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NetworkError || error instanceof ServerError || error instanceof AuthFailureError || error instanceof ParseError || error instanceof TimeoutError) {
                    Log.e("Loading initial videos", error.getLocalizedMessage());
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setTag(TAG);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void getDetails(final String videoId) {
        String api = "https://www.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&id=" + videoId + "&key=" + getString(R.string.api_key);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject videoObject = obj.getJSONArray("items").getJSONObject(0);

                            String currentString = videoObject.getJSONObject("snippet").getString("publishedAt");
                            String[] separated = currentString.split("T");
                            String thumbnail = videoObject.getJSONObject("snippet").getJSONObject("thumbnails")
                                    .getJSONObject("medium").getString("url");

                            adapter.addVideo(new OnlineVideoListModel(videoId,
                                    videoObject.getJSONObject("snippet").getString("title"),
                                    videoObject.getJSONObject("snippet").getString("title"),
                                    videoObject.getJSONObject("statistics").getString("viewCount"),
                                    videoObject.getJSONObject("snippet").getString("description"), separated[0],
                                    videoObject.getJSONObject("contentDetails").getString("duration"), thumbnail
                            ));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError || error instanceof ServerError || error instanceof AuthFailureError || error instanceof ParseError || error instanceof TimeoutError) {
                    Log.e("Loading initial videos", error.getLocalizedMessage().concat(" Error"));
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setTag(TAG);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


}
