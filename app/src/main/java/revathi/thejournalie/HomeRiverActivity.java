package revathi.thejournalie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;

import revathi.thejournalie.adapters.HomeRiverAdapter;
import revathi.thejournalie.application.JournalIeApplication;
import revathi.thejournalie.retrofit.Mappers;

public class HomeRiverActivity extends AppCompatActivity {
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView homeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JournalIeApplication.setCurrentActivityContext(this);

        linearLayoutManager = new LinearLayoutManager(HomeRiverActivity.this);

        homeRecyclerView = findViewById(R.id.home_recycler_view);
        homeRecyclerView.setHasFixedSize(true);
        homeRecyclerView.setLayoutManager(linearLayoutManager);

        String publication = getIntent().getStringExtra("publication");

        if (JournalIeApplication.getInstance().isConnectedToInterNet()) {

            fetchHomeRiverArticles(publication);
        } else {
            JournalIeApplication.getInstance().showToast(getResources().getString(R.string.internet_error));
        }
    }


    private void fetchHomeRiverArticles(String publication) {
        Mappers apiMapper = new Mappers();
        apiMapper.getHomeRiverArticles(publication, detailsListener);
    }

    private Mappers.DetailsListener detailsListener = new Mappers.DetailsListener() {

        @Override
        public void onSuccess(JSONArray generalApiResponse) {
            if (generalApiResponse != null) {
                HomeRiverAdapter rcAdapter = new HomeRiverAdapter(HomeRiverActivity.this,
                        generalApiResponse);
                homeRecyclerView.setAdapter(rcAdapter);
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