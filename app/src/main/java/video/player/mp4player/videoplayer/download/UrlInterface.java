package video.player.mp4player.videoplayer.download;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface UrlInterface {

    @GET()
    @Streaming
    Call<ResponseBody> downloadVideo(@Url String fileUrl);
}