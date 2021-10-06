package video.player.mp4player.videoplayer.fragment;

public class OnlineVideoListModel {
    private String videoId;
    private String channelId;
    private String videoTitle;
    private String viewsCount;
    private String videoDetails;
    private String uploadDate;
    private String duration;
    private String thumbnail;

    public OnlineVideoListModel(String videoId, String channelId, String videoTitle, String viewsCount, String videoDetails, String uploadDate, String duration, String thumbnail) {
        this.videoId = videoId;
        this.channelId = channelId;
        this.videoTitle = videoTitle;
        this.viewsCount = viewsCount;
        this.videoDetails = videoDetails;
        this.uploadDate = uploadDate;
        this.duration = duration;
        this.thumbnail = thumbnail;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(String viewsCount) {
        this.viewsCount = viewsCount;
    }

    public String getVideoDetails() {
        return videoDetails;
    }

    public void setVideoDetails(String videoDetails) {
        this.videoDetails = videoDetails;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "OnlineVideoListModel{" +
                "videoId='" + videoId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                ", viewsCount='" + viewsCount + '\'' +
                ", videoDetails='" + videoDetails + '\'' +
                ", uploadDate='" + uploadDate + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
