package revathi.thejournalie.retrofit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import revathi.thejournalie.application.JournalIeApplication;

/**
 * Calls the journal.ie api and gets response in callback
 * Created by revathi on 14/05/18.
 */
public class Mappers {

    private DetailsListener detailsListener;

    public interface DetailsListener {

        void onSuccess(JSONArray response);

        void onFailure(String message);
    }

    public void getHomeRiverArticles(String publication, DetailsListener listener) {
        this.detailsListener = listener;

        final Services services = SetupRetrofit.createService(Services.class);

        Call<ResponseBody> call = services.fetchHomeArticles(publication);
        JournalIeApplication.getInstance().showProgressDialog("Fetching details....");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                JournalIeApplication.getInstance().hideProgressDialog();
                if (response != null) {
                    if (response.body() != null) {
                        try {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            jsonObject = jsonObject.getJSONObject("response");
                            JSONArray jsonArray = jsonObject.getJSONArray("articles");
                            detailsListener.onSuccess(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        detailsListener.onSuccess(null);
                    }
                } else {
                    detailsListener.onSuccess(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                JournalIeApplication.getInstance().hideProgressDialog();
                if (t != null) {
                    if (t.getMessage() != null) {
                        detailsListener.onFailure(t.getMessage());
                    } else {
                        detailsListener.onFailure(null);
                    }
                } else {
                    detailsListener.onFailure(null);
                }
            }
        });
    }

    public void getTagRiverArticles(String slug, DetailsListener listener) {
        this.detailsListener = listener;

        final Services services = SetupRetrofit.createService(Services.class);

        Call<ResponseBody> call = services.fetchTagArticles(slug);
        JournalIeApplication.getInstance().showProgressDialog("Fetching details....");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                JournalIeApplication.getInstance().hideProgressDialog();
                if (response != null) {
                    if (response.body() != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            jsonObject = jsonObject.getJSONObject("response");
                            JSONArray jsonArray = jsonObject.getJSONArray("articles");
                            detailsListener.onSuccess(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        detailsListener.onSuccess(null);
                    }
                } else {
                    detailsListener.onSuccess(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                JournalIeApplication.getInstance().hideProgressDialog();
                if (t != null) {
                    if (t.getMessage() != null) {
                        detailsListener.onFailure(t.getMessage());
                    } else {
                        detailsListener.onFailure(null);
                    }
                } else {
                    detailsListener.onFailure(null);
                }
            }
        });
    }
}
