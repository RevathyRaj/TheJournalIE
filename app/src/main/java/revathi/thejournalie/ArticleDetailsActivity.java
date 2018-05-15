package revathi.thejournalie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Displays article description
 */
public class ArticleDetailsActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        titleTextView = findViewById(R.id.article_title_text_view);
        descriptionTextView = findViewById(R.id.article_desc_text_view);

        String article = getIntent().getExtras().getString("article");
        try {
            JSONObject jsonObject = new JSONObject(article);
            titleTextView.setText(jsonObject.getString("excerpt"));
            descriptionTextView.setText(Html.fromHtml(jsonObject.getString("content")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
