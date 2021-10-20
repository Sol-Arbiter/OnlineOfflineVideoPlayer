package video.player.mp4player.videoplayer.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
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
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import video.player.mp4player.videoplayer.R;
import video.player.mp4player.videoplayer.extractor.YouTubeExtractionResult;
import video.player.mp4player.videoplayer.extractor.YouTubeExtractor;
import video.player.mp4player.videoplayer.fragment.OnlineVideoListAdapter;
import video.player.mp4player.videoplayer.fragment.OnlineVideoListModel;

import static android.view.View.GONE;
import static com.android.volley.VolleyLog.TAG;
import static io.reactivex.plugins.RxJavaPlugins.onError;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OnlineVideoPlayer extends AppCompatActivity {
    private XRecyclerView recyclerView;
    private static String JSON_URL = "https://www.googleapis.com/youtube/v3/videos?part=contentDetails&chart=mostPopular&regionCode=IN&maxResults=10&key=";
    YouTubePlayerView youtubePlayerView;
    public OnlineVideoListAdapter adapter;
    String videoId = "", title = "", description = "", date = "", views = "";
    String channelId = "";
    TextView tvTitle, tvUploadDate, tvDescription, tvView, info;
    ProgressBar loading,dProgress;
    LinearLayout llShare, llWhatsapp;
    Button hideDetails, showDetails;
    ImageView downloadVideo;
    String nextPageToken = "";
    int nextPagesCount = 0;

    ExecutorService executor = Executors.newFixedThreadPool(1);
    Handler handler = new Handler(Looper.getMainLooper());

    private final YouTubeExtractor extractor = YouTubeExtractor.create();


    private Callback<YouTubeExtractionResult> mExtractionCallback = new Callback<YouTubeExtractionResult>() {


        @Override
        public void onResponse(Call<YouTubeExtractionResult> call, retrofit2.Response<YouTubeExtractionResult> response) {

        }

        @Override
        public void onFailure(Call<YouTubeExtractionResult> call, Throwable t) {
            onError(t);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_player);

        getIntentData();
        findIds();
        bindViewsData();
        clickHandlers();
        loadVideo();
    }

    private void getIntentData() {
        //Initial Data from OnlineVideoFragment
        final Bundle extras = getIntent().getExtras();
        videoId = extras.getString("video_id");
        title = extras.getString("title");
        description = extras.getString("description");
        views = extras.getString("views");
        date = extras.getString("date");

    }

    private void findIds() {
        recyclerView = findViewById(R.id.rv_suggesstion_video);
        tvTitle = (TextView) findViewById(R.id.tv_yv_title);
        tvUploadDate = (TextView) findViewById(R.id.tv_yv_upload_date);
        tvView = (TextView) findViewById(R.id.tv_yv_view);
        info = (TextView) findViewById(R.id.info);
        tvDescription = (TextView) findViewById(R.id.tv_yv_detail);
        llShare = findViewById(R.id.lil_share);
        llWhatsapp = findViewById(R.id.lil_whatsapp);
        hideDetails = findViewById(R.id.diogs_hide);
        showDetails = findViewById(R.id.diogs_show);
        loading = findViewById(R.id.loading);
        dProgress = findViewById(R.id.progressBar);
        downloadVideo = findViewById(R.id.download_video);


    }

    private void getVideoDetails(final String videoId) {
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

                            channelId = tutorialsObject.getJSONObject("snippet").getString("title");
                            String title = tutorialsObject.getJSONObject("snippet").getString("title");
                            String viewCount = tutorialsObject.getJSONObject("statistics").getString("viewCount");
                            String uploadDate = separated[0];
                            String description = tutorialsObject.getJSONObject("snippet").getString("description");
                            String duration = tutorialsObject.getJSONObject("contentDetails").getString("duration");

                            adapter.addVideo(new OnlineVideoListModel(videoId,
                                    channelId,
                                    title,
                                    viewCount,
                                    description, uploadDate, duration, "thumbnail"));

                            if (loading.getVisibility() == View.VISIBLE) {
                                loading.setVisibility(GONE);
                                info.setVisibility(GONE);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (loading.getVisibility() == View.VISIBLE) {
                    loading.setVisibility(GONE);
                    info.setVisibility(View.VISIBLE);
                }

                if (error instanceof NetworkError || error instanceof ServerError || error instanceof AuthFailureError || error instanceof ParseError || error instanceof NoConnectionError || error instanceof TimeoutError) {
                    Log.e("Loading exception", " More video loading exception :::::::::   ".concat(error.getLocalizedMessage()));
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setTag(TAG);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    Thread downloadUrlExtractorThread = new Thread(new Runnable() {

        @Override
        public void run() {
            try {

                YouTubeExtractionResult result = extractor.extract("watch?v=" + videoId)
                        .blockingGet();
                Log.e("Video Uri ", result.getHd720VideoUri().toString());
            } catch (Exception e) {
                Log.e("excep ", e.getLocalizedMessage());
            }
        }
    });


    private void clickHandlers() {
        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Sharing VIdeo");
                i.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + videoId);
                startActivity(Intent.createChooser(i, "Share URL"));
            }
        });

        llWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.setPackage("com.whatsapp");
                intent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + videoId);
                startActivity(intent);
            }
        });

        hideDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetails.setVisibility(View.VISIBLE);
                hideDetails.setVisibility(GONE);
                tvDescription.setVisibility(View.VISIBLE);

            }
        });
        showDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetails.setVisibility(GONE);
                hideDetails.setVisibility(View.VISIBLE);
                tvDescription.setVisibility(GONE);
            }
        });

        downloadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dProgress.setVisibility(View.VISIBLE);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                //TODO your background code
                                YouTubeExtractionResult result = extractor.extract("watch?v=" + videoId)
                                        .blockingGet();
                                Log.e("Video Uri ", result.getHd720VideoUri().toString());

                            }
                        });
                       /* executor.execute(new Runnable() {
                            @Override
                            public void run() {

                               //Background work here

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        dProgress.setVisibility(GONE);
                                        //UI Thread work here
                                    }
                                });
                            }
                        });*/
                    }
                });

//
//                try {
//                    downloadUrlExtractorThread.start();
//                }catch (Exception ex){
//                    Log.e("Exception in ","Starting thread");
//                }


                String youtubeLink = "http://youtube.com/watch?v=" + videoId;
               /* new YouTubeExtractor(OnlineVideoPlayer.this) {
                    @Override
                    public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                        if (ytFiles != null) {
                            int itag = 22;
                            String downloadUrl = ytFiles.get(itag).getUrl();
                            Intent startDownLoad = new Intent(OnlineVideoPlayer.this, DownloadNotificationService.class);
                            startDownLoad.putExtra("TITLE", title);
                            startDownLoad.putExtra("URL", downloadUrl);
                            startService(startDownLoad);

                            Log.e("Youtube downloadable ", ">>>>>>>>>>>>>>>>>>>>>       " + downloadUrl);
                        }
                    }
                }.extract(youtubeLink, true, true);*/

            }
        });
    }

    public void bindViewsData() {

        youtubePlayerView = findViewById(R.id.youtube_player_view);


        getLifecycle().addObserver(youtubePlayerView);

        youtubePlayerView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(@NonNull final YouTubePlayer initializedYouTubePlayer) {
                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        initializedYouTubePlayer.loadVideo(videoId, 0);
                    }
                });
            }
        }, true);

        youtubePlayerView.findViewById(R.id.app_video_fastForward);
        tvTitle.setText(title);
        tvView.setText(views);
        tvUploadDate.setText(date);
        tvDescription.setText(description);

        adapter = new OnlineVideoListAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                recyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
                loadMoreVideo();
            }
        });

    }

    private void loadMoreVideo() {
        Log.e("Load More Videos", nextPagesCount + "");
        nextPagesCount += 1;
//        if (nextPagesCount > 2)
//            return;
        String JSON_URL_NEW = "https://www.googleapis.com/youtube/v3/videos?pageToken=" + nextPageToken + "&part=contentDetails&chart=mostPopular&regionCode=IN&maxResults=10&key=AIzaSyBpT3K0OiojVRvZJWyjf35hH7kaYW9kmF4";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL_NEW,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            nextPageToken = obj.getString("nextPageToken");
                            nextPagesCount += 1;
                            JSONArray tutorialsArray = obj.getJSONArray("items");
                            for (int i = 0; i < tutorialsArray.length(); i++) {
                                JSONObject tutorialsObject = tutorialsArray.getJSONObject(i);
                                if (!tutorialsObject.getJSONObject("contentDetails").has("regionRestriction")) {
                                    if (!tutorialsObject.getString("id").equals(videoId)) {
                                        getVideoDetails(tutorialsObject.getString("id"));
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    if ((error instanceof NetworkError || error instanceof ServerError) || error instanceof AuthFailureError || error instanceof ParseError || error instanceof NoConnectionError || error instanceof TimeoutError) {
                        Log.e("Loading error ", "::::     Suggestion video loading error ::::    ".concat(error.getLocalizedMessage()));
                    }
                } catch (Exception e) {
                    Log.e("Loading error ", "::::     Suggestion video loading error ::::    ".concat(error.getLocalizedMessage()));
                }

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setTag(TAG);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void loadVideo() {
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
                                    if (!tutorialsObject.getString("id").equals(videoId)) {
                                        getVideoDetails(tutorialsObject.getString("id"));
                                    }
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
                if ((error instanceof NetworkError || error instanceof ServerError) || error instanceof AuthFailureError || error instanceof ParseError || error instanceof NoConnectionError || error instanceof TimeoutError) {
//                    Log.e("Loading error ", "::::     Suggestion video loading error ::::    ".concat(error.getLocalizedMessage()));
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setTag(TAG);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
