package revathi.thejournalie.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by revathi on 06/03/18.
 */

public interface Services {

    @GET("sample/{publication}")
    Call<ResponseBody> fetchHomeArticles(@Path("publication") String publication);

    @GET("sample/tag/{slug}")
    Call<ResponseBody> fetchTagArticles(@Path("slug") String slug);

}