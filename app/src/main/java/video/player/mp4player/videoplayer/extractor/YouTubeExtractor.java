package video.player.mp4player.videoplayer.extractor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Class that allows you to extract desired data from a YouTube video, such as streamable {@link android.net.Uri}s
 * given its video id, which is typically contained within the YouTube video url, ie. https://www.youtube.com/watch?v=dQw4w9WgXcQ
 * has a video id of dQw4w9WgXcQ
 */
public class YouTubeExtractor {

    private static final String BASE_URL = "https://www.youtube.com/";

    static final int YOUTUBE_VIDEO_QUALITY_SMALL_240 = 36;
    static final int YOUTUBE_VIDEO_QUALITY_MEDIUM_360 = 18;
    static final int YOUTUBE_VIDEO_QUALITY_HD_720 = 22;
    static final int YOUTUBE_VIDEO_QUALITY_HD_1080 = 37;

    /**
     * Create a YouTubeExtractor
     * @return a new {@link YouTubeExtractor}
     */
    public static YouTubeExtractor create() {
        return create(null);
    }

    /**
     * Create a new YouTubeExtractor with a custom OkHttp client builder
     * @return a new {@link YouTubeExtractor}
     */
    public static YouTubeExtractor create(OkHttpClient.Builder okHttpBuilder) {
        return new YouTubeExtractor(okHttpBuilder);
    }

    private final YouTube youTube;
    private final LanguageInterceptor interceptor;

    /**
     * Create a new YouTubeExtractor
     */
    private YouTubeExtractor(@Nullable OkHttpClient.Builder okBuilder) {

        if (okBuilder == null) {
            okBuilder = new OkHttpClient.Builder();
        }

        interceptor = new LanguageInterceptor();
        okBuilder.addInterceptor(interceptor);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();

        retrofitBuilder
                .baseUrl(BASE_URL)
                .client(okBuilder.build())
                .addConverterFactory(YouTubeExtractionConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        youTube = retrofitBuilder.build().create(YouTube.class);
    }

    /**
     * Extract the video information
     * @param videoId the video ID
     * @return the extracted result
     */
    public Single<YouTubeExtractionResult> extract(@NonNull String videoId) {
        return youTube.extract(videoId);
    }

    /**
     * Set the language. Defaults to {@link java.util.Locale#getDefault()}
     * @param language the language
     */
    public void setLanguage(@NonNull String language) {
        interceptor.setLanguage(language);
    }
}