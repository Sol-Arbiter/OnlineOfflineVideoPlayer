package video.player.mp4player.videoplayer.model;

public class FolderDataModel {
    private int items;
    private String folder_name;
    private boolean newtab = false;

    public FolderDataModel(String folder_name, int items) {
        this.items = items;
        this.folder_name = folder_name;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public String getFolder_name() {
        return folder_name;
    }

    public void setFolder_name(String folder_name) {
        this.folder_name = folder_name;
    }

    public boolean isNewtab() {
        return newtab;
    }

    public void setNewtab(boolean newtab) {
        this.newtab = newtab;
    }
}
