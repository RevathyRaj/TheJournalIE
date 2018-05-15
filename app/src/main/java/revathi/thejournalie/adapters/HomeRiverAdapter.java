package revathi.thejournalie.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import revathi.thejournalie.ArticleDetailsActivity;
import revathi.thejournalie.HomeRiverActivity;
import revathi.thejournalie.R;
import revathi.thejournalie.TagRiverActivity;
import revathi.thejournalie.application.JournalIeApplication;

/**
 * Created by revathi on 14/05/18.
 */

public class HomeRiverAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    public JSONArray itemList;
    private Context context;

    public HomeRiverAdapter(Context context, JSONArray articleArrayList) {
        this.itemList = articleArrayList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_home_river, null);

        RecyclerViewHolder rcv = new RecyclerViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        try {
            JSONObject jsonObject = new JSONObject(itemList.get(position).toString());
            holder.titleTextView.setText(jsonObject.getString("title"));

            JSONObject imagesObj = new JSONObject(jsonObject.getString("images"));
            JSONObject thumbnail = new JSONObject(imagesObj.getString("thumbnail"));
            Picasso.get().load(thumbnail.getString("image")).into(holder.itemImage);

            if (JournalIeApplication.getCurrentActivityContext() instanceof HomeRiverActivity) {
                holder.similarTagTextVew.setVisibility(View.VISIBLE);
                holder.similarTagTextVew.setTag(jsonObject.getString("slug").toString());
                holder.similarTagTextVew.setOnClickListener(clickListener);
            } else {
                holder.similarTagTextVew.setVisibility(View.GONE);
            }

            holder.parentLayout.setTag(itemList.get(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.itemList.length();
    }

    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(JournalIeApplication.getCurrentActivityContext(), TagRiverActivity.class);
            intent.putExtra("slug", v.getTag().toString());
            JournalIeApplication.getCurrentActivityContext().startActivity(intent);
        }
    };
}


class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView titleTextView;
    public TextView similarTagTextVew;
    public ImageView itemImage;
    public RelativeLayout parentLayout;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        titleTextView = itemView.findViewById(R.id.home_river_item_text_view);
        similarTagTextVew = itemView.findViewById(R.id.similar_tag_text_view);
        itemImage = itemView.findViewById(R.id.home_river_item_image_view);
        parentLayout = itemView.findViewById(R.id.parent_layout);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 10, 10, 10);
        parentLayout.setLayoutParams(params);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(JournalIeApplication.getCurrentActivityContext(), ArticleDetailsActivity.class);
        intent.putExtra("article", view.getTag().toString());
        JournalIeApplication.getCurrentActivityContext().startActivity(intent);
    }
}