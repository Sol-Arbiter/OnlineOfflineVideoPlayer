package video.player.mp4player.videoplayer.extractor;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface YouTube {

    @GET("get_video_info?el=info&ps=default&gl=US")
    Single<YouTubeExtractionResult> extract(@Query("video_id") String videoId);

}