package video.player.mp4player.videoplayer.extractor;

import java.io.IOException;

/**
 * An exception when trying to extract a YouTube video url
 */
public class YouTubeExtractionException extends IOException {
    public YouTubeExtractionException(String detailMessage) {
        super(detailMessage);
    }
}