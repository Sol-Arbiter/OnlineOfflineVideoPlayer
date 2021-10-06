

package video.player.mp4player.videoplayer.giraffeplayer;

import android.view.View;
import android.widget.MediaController;

public interface IMediaController {
    void hide();

    boolean isShowing();

    void setAnchorView(View view);

    void setEnabled(boolean enabled);

    void setMediaPlayer(MediaController.MediaPlayerControl player);

    void show(int timeout);

    void show();




    void showOnce(View view);
}
