package video.player.mp4player.videoplayer.fragment;

public class search_video_list {
    private String msvl_videoId;
    private String msvl_title;
    private String msvl_chanal;

    public search_video_list(String msvl_videoId, String msvl_title, String msvl_chanal) {
        this.msvl_videoId = msvl_videoId;
        this.msvl_title = msvl_title;
        this.msvl_chanal = msvl_chanal;
    }

    public String getMsvl_videoId() {
        return msvl_videoId;
    }

    public void setMsvl_videoId(String msvl_videoId) {
        this.msvl_videoId = msvl_videoId;
    }

    public String getMsvl_title() {
        return msvl_title;
    }

    public void setMsvl_title(String msvl_title) {
        this.msvl_title = msvl_title;
    }

    public String getMsvl_chanal() {
        return msvl_chanal;
    }

    public void setMsvl_chanal(String msvl_chanal) {
        this.msvl_chanal = msvl_chanal;
    }

}
