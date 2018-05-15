package revathi.thejournalie.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import revathi.thejournalie.HomeRiverActivity;
import revathi.thejournalie.R;
import revathi.thejournalie.application.JournalIeApplication;

/**
 * Created by revathi on 14/05/18.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private ArrayList<String> itemList;
    private int[] itemImageIds;
    public Context context;

    public HomeRecyclerAdapter(Context context, ArrayList<String> itemList, int[] itemImageIds) {
        this.itemList = itemList;
        this.context = context;
        this.itemImageIds = itemImageIds;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_layout, null);

        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders holder, int position) {
        holder.itemName.setText(itemList.get(position));
        holder.itemImage.setImageResource(itemImageIds[position]);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}


class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView itemName;
    public ImageView itemImage;
    public RelativeLayout parentLayout;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemName =  itemView.findViewById(R.id.item_name_text_view);
        itemImage = itemView.findViewById(R.id.item_image_view);
        parentLayout = itemView.findViewById(R.id.parent_layout);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(30, 30, 30, 30);
        parentLayout.setLayoutParams(params);

    }

    /**
     * respective publication will be sent to next screen which can be used for api call
     * @param view
     */
    @Override
    public void onClick(View view) {
        String publication;

        switch (getAdapterPosition()) {
            case 0:
                publication = "thejournal";
                break;
            case 1:
                publication = "thescore";
                break;
            case 2:
                publication = "thedailyedge";
                break;
            case 3:
                publication = "businessetc";
                break;
            default:
                publication = "businessetc";
                break;
        }
        Intent intent = new Intent(JournalIeApplication.getCurrentActivityContext(), HomeRiverActivity.class);
        intent.putExtra("publication", publication);
        JournalIeApplication.getCurrentActivityContext().startActivity(intent);
    }
}
