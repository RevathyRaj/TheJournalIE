package revathi.thejournalie.retrofit;

import android.util.Base64;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import revathi.thejournalie.R;
import revathi.thejournalie.application.JournalIeApplication;

/**
 * Created by revathi on 14/05/18.
 */

/**
 * @author revathim initiates retrofit adapter and http cache
 */
public class SetupRetrofit {


    /**
     * Initiates retrofit adapter and http cache
     */
    private static int cacheSize = 10 * 1024 * 1024;
    public static final String API_BASE_URL = JournalIeApplication.getInstance().getResources()
            .getString(R.string.base_url);
    public static Cache cache = new Cache(JournalIeApplication.getInstance().getCacheDir(), cacheSize);

    public static <S> S createService(Class<S> serviceClass) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor).writeTimeout(10, TimeUnit.MINUTES)
                .connectTimeout(10, TimeUnit.MINUTES).readTimeout(10, TimeUnit.MINUTES)
                .addInterceptor(new AuthenticationInterceptor())
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(okHttpClient).build();
        return retrofit.create(serviceClass);

    }
}

/**
 * Adding required authorization headers and cache
 */
class AuthenticationInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        String credentials = "sample" + ":" + "eferw5wr335£65";
        // create Base64 encoded string
        final String basic =
                "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        Request original = chain.request();
        Response originalResponse;

        Request.Builder builder = original.newBuilder()
                .header("Authorization", basic);

        Request request = builder.build();
        originalResponse = chain.proceed(request);
        return originalResponse.newBuilder().removeHeader("Pragma")
                .header("Cache-Control", String.format("max-age=%d", 120))
                .build();
    }
}