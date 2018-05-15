package revathi.thejournalie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import revathi.thejournalie.adapters.HomeRecyclerAdapter;
import revathi.thejournalie.application.JournalIeApplication;

/**
 * Displays multiple publications
 */
public class MainActivity extends AppCompatActivity {
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JournalIeApplication.setCurrentActivityContext(this);

        //hard coding the publications,they can also be included dynamically
        ArrayList<String> publicationList = new ArrayList<>();
        publicationList.add("The Journal");
        publicationList.add("The Score");
        publicationList.add("The Daily Edge");
        publicationList.add("Businessetc");

        int[] imageIds = {R.mipmap.jie, R.mipmap.score, R.mipmap.de, R.mipmap.tjie};

        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);

        RecyclerView rView =  findViewById(R.id.home_recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(gridLayoutManager);

        HomeRecyclerAdapter rcAdapter = new HomeRecyclerAdapter(MainActivity.this, publicationList, imageIds);
        rView.setAdapter(rcAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JournalIeApplication.setCurrentActivityContext(this);
    }
}