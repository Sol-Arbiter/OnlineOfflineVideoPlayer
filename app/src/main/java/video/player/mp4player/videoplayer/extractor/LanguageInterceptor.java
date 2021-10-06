package video.player.mp4player.videoplayer.extractor;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Locale;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class LanguageInterceptor implements Interceptor {

    static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";
    static final String LANGUAGE_QUERY_PARAM = "language";

    @NonNull
    String language;

    LanguageInterceptor() {
        this.language = Locale.getDefault().getLanguage();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        HttpUrl url = request.url()
                .newBuilder()
                .addQueryParameter(LANGUAGE_QUERY_PARAM, language)
                .build();

        Request requestWithHeaders = request.newBuilder()
                .addHeader(ACCEPT_LANGUAGE_HEADER, language)
                .url(url)
                .build();
        return chain.proceed(requestWithHeaders);
    }

    void setLanguage(@NonNull String language) {
        this.language = language;
    }
}