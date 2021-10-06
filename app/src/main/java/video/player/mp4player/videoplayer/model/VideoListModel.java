package video.player.mp4player.videoplayer.model;

public class VideoListModel {
    private String path;
    private String txt_video_name;
    private String txt_video_size;
    private String lastModDate;
    private int space;
    private boolean newtab = false;
    private String mp_resolution;
    private String mp_duration;

    private String height;
    private String width;
    private long timeInMillisec;



    public VideoListModel(String path , String txt_video_name, String txt_video_size, String mp_resolution, int space, String lastModDate)
    {
        this.txt_video_name = txt_video_name;
        this.txt_video_size = txt_video_size;
        this.path = path;
        this.lastModDate = lastModDate;
        this.space = space;
        this.mp_resolution = mp_resolution;

    }



    public String getTxt_video_name() {
        return txt_video_name;
    }

    public String getTxt_video_size() {
        return txt_video_size;
    }

    public String getPath() { return path; }

    public void setTxt_video_name(String txt_video_name) {
        this.txt_video_name = txt_video_name;
    }

    public void setTxt_video_size(String txt_video_size) {
        this.txt_video_size = txt_video_size;
    }

    public void setPath(String path) {  this.path = path; }

    public String getLastModDate() {
        return lastModDate;
    }

    public void setLastModDate(String lastModDate) {
        this.lastModDate = lastModDate;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public boolean isNewtab() {
        return newtab;
    }

    public void setNewtab(boolean newtab) {
        this.newtab = newtab;
    }

    public String getMp_resolution() {
        return mp_resolution;
    }

    public void setMp_resolution(String mp_resolution) {
        this.mp_resolution = mp_resolution;
    }

    public String getMp_duration() {
        return mp_duration;
    }

    public void setMp_duration(String mp_duration) {
        this.mp_duration = mp_duration;
    }

    public long getTimeInMillisec() {
        return timeInMillisec;
    }

    public void setTimeInMillisec(long timeInMillisec) {
        this.timeInMillisec = timeInMillisec;
    }
}
