

package video.player.mp4player.videoplayer.giraffeplayer;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import tv.danmaku.ijk.media.player.IMediaPlayer;


public interface IRenderView {
    static final int AR_ASPECT_FIT_PARENT = 0;
    static final int AR_ASPECT_FILL_PARENT = 1;
    static final int AR_ASPECT_WRAP_CONTENT = 2;
    static final int AR_MATCH_PARENT = 3;
    static final int AR_16_9_FIT_PARENT = 4;
    static final int AR_4_3_FIT_PARENT = 5;

    View getView();

    boolean shouldWaitForResize();

    void setVideoSize(int videoWidth, int videoHeight);

    void setVideoSampleAspectRatio(int videoSarNum, int videoSarDen);

    void setVideoRotation(int degree);

    void setAspectRatio(int aspectRatio);

    void addRenderCallback(@NonNull IRenderCallback callback);

    void removeRenderCallback(@NonNull IRenderCallback callback);

    interface ISurfaceHolder {
        void bindToMediaPlayer(IMediaPlayer mp);

        @NonNull
        IRenderView getRenderView();

        @Nullable
        SurfaceHolder getSurfaceHolder();

        @Nullable
        Surface openSurface();

        @Nullable
        SurfaceTexture getSurfaceTexture();
    }

    public interface IRenderCallback {

        void onSurfaceCreated(@NonNull ISurfaceHolder holder, int width, int height);


        void onSurfaceChanged(@NonNull ISurfaceHolder holder, int format, int width, int height);

        void onSurfaceDestroyed(@NonNull ISurfaceHolder holder);
    }
}
