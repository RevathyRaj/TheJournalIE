package revathi.thejournalie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;

import revathi.thejournalie.adapters.HomeRiverAdapter;
import revathi.thejournalie.application.JournalIeApplication;
import revathi.thejournalie.retrofit.Mappers;

/**
 * Displays articles related to the one selected from All Articles list
 */
public class TagRiverActivity extends AppCompatActivity {
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JournalIeApplication.setCurrentActivityContext(this);

        linearLayoutManager = new LinearLayoutManager(TagRiverActivity.this);

        recyclerView = findViewById(R.id.home_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (JournalIeApplication.getInstance().isConnectedToInterNet()) {

            fetchHomeRiverArticles(getIntent().getExtras().getString("slug"));
        } else {
            JournalIeApplication.getInstance().showToast(getResources().getString(R.string.internet_error));
        }

    }

    private void fetchHomeRiverArticles(String slug) {
        Mappers apiMapper = new Mappers();
        apiMapper.getTagRiverArticles(slug, detailsListener);
    }

    private Mappers.DetailsListener detailsListener = new Mappers.DetailsListener() {

        @Override
        public void onSuccess(JSONArray generalApiResponse) {
            if (generalApiResponse != null) {
                HomeRiverAdapter rcAdapter = new HomeRiverAdapter(TagRiverActivity.this,
                        generalApiResponse);
                recyclerView.setAdapter(rcAdapter);
            } else {
                JournalIeApplication.getInstance().showToast(getResources().getString(R.string.response_error));
            }
        }

        @Override
        public void onFailure(String message) {
            JournalIeApplication.getInstance().showToast(getResources().getString(R.string.internet_error));
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        JournalIeApplication.setCurrentActivityContext(this);
    }
}