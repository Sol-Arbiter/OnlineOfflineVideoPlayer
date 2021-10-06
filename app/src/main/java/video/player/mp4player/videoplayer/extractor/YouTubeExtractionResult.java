package video.player.mp4player.videoplayer.extractor;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

/**
 * The result of a YouTube extraction.
 */
public class YouTubeExtractionResult implements Parcelable {

    private Uri sd240VideoUri;
    private Uri sd360VideoUri;
    private Uri hd720VideoUri;
    private Uri hd1080VideoUri;
    private Uri mediumThumbUri;
    private Uri highThumbUri;
    private Uri defaultThumbUri;
    private Uri standardThumbUri;

    protected YouTubeExtractionResult() {
        //Do the rest in the setters
    }

    @Nullable
    public Uri getSd240VideoUri() {
        return sd240VideoUri;
    }

    protected YouTubeExtractionResult setSd240VideoUri(Uri uri) {
        sd240VideoUri = uri;
        return this;
    }

    @Nullable
    public Uri getSd360VideoUri() {
        return sd360VideoUri;
    }

    protected YouTubeExtractionResult setSd360VideoUri(Uri uri) {
        sd360VideoUri = uri;
        return this;
    }

    @Nullable
    public Uri getHd720VideoUri() {
        return hd720VideoUri;
    }

    protected YouTubeExtractionResult setHd720VideoUri(Uri uri) {
        hd720VideoUri = uri;
        return this;
    }

    @Nullable
    public Uri getHd1080VideoUri() {
        return hd1080VideoUri;
    }

    protected YouTubeExtractionResult setHd1080VideoUri(Uri uri) {
        hd1080VideoUri = uri;
        return this;
    }

    /**
     * Get the best available quality video, starting with 1080p all the way down to 240p.
     * @return the best quality video uri, or null if no uri is available
     */
    @Nullable
    public Uri getBestAvailableQualityVideoUri() {
        Uri uri = getHd1080VideoUri();
        if (uri != null) {
            return uri;
        }
        uri = getHd720VideoUri();
        if (uri != null) {
            return uri;
        }
        uri = getSd360VideoUri();
        if (uri != null) {
            return uri;
        }
        uri = getSd240VideoUri();
        if (uri != null) {
            return uri;
        }
        return null;
    }

    @Nullable
    public Uri getMediumThumbUri() {
        return mediumThumbUri;
    }

    protected YouTubeExtractionResult setMediumThumbUri(Uri uri) {
        mediumThumbUri = uri;
        return this;
    }

    @Nullable
    public Uri getHighThumbUri() {
        return highThumbUri;
    }

    protected YouTubeExtractionResult setHighThumbUri(Uri uri) {
        highThumbUri = uri;
        return this;
    }

    @Nullable
    public Uri getDefaultThumbUri() {
        return defaultThumbUri;
    }

    protected YouTubeExtractionResult setDefaultThumbUri(Uri uri) {
        defaultThumbUri = uri;
        return this;
    }

    @Nullable
    public Uri getStandardThumbUri() {
        return standardThumbUri;
    }

    protected YouTubeExtractionResult setStandardThumbUri(Uri uri) {
        standardThumbUri = uri;
        return this;
    }

    /**
     * Convenience method which will go through all thumbnail {@link Uri}s and return you the best one
     * @return the best image uri, or null if none exist
     */
    @Nullable
    public Uri getBestAvailableQualityThumbUri() {
        Uri uri = getHighThumbUri();
        if (uri != null) {
            return uri;
        }
        uri = getMediumThumbUri();
        if (uri != null) {
            return uri;
        }
        uri = getDefaultThumbUri();
        if (uri != null) {
            return uri;
        }
        uri = getStandardThumbUri();
        if (uri != null) {
            return uri;
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.sd240VideoUri, flags);
        dest.writeParcelable(this.sd360VideoUri, flags);
        dest.writeParcelable(this.hd720VideoUri, flags);
        dest.writeParcelable(this.hd1080VideoUri, flags);
        dest.writeParcelable(this.mediumThumbUri, flags);
        dest.writeParcelable(this.highThumbUri, flags);
        dest.writeParcelable(this.defaultThumbUri, flags);
        dest.writeParcelable(this.standardThumbUri, flags);
    }

    protected YouTubeExtractionResult(Parcel in) {
        this.sd240VideoUri = in.readParcelable(Uri.class.getClassLoader());
        this.sd360VideoUri = in.readParcelable(Uri.class.getClassLoader());
        this.hd720VideoUri = in.readParcelable(Uri.class.getClassLoader());
        this.hd1080VideoUri = in.readParcelable(Uri.class.getClassLoader());
        this.mediumThumbUri = in.readParcelable(Uri.class.getClassLoader());
        this.highThumbUri = in.readParcelable(Uri.class.getClassLoader());
        this.defaultThumbUri = in.readParcelable(Uri.class.getClassLoader());
        this.standardThumbUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Parcelable.Creator<YouTubeExtractionResult> CREATOR = new Parcelable.Creator<YouTubeExtractionResult>() {
        @Override
        public YouTubeExtractionResult createFromParcel(Parcel source) {
            return new YouTubeExtractionResult(source);
        }

        @Override
        public YouTubeExtractionResult[] newArray(int size) {
            return new YouTubeExtractionResult[size];
        }
    };
}