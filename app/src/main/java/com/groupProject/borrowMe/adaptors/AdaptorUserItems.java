package com.groupProject.borrowMe.adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.groupProject.borrowMe.Item.ItemDetails;
import com.groupProject.borrowMe.Item.PostedItem;
import com.groupProject.borrowMe.R;
import com.groupProject.borrowMe.models.UserItems;

import java.util.List;

/**
 * Created by Enache on 23/04/2018.
 */

public class AdaptorUserItems extends RecyclerView.Adapter<AdaptorUserItems.ViewHolder> {



    private static Context context;
    private List<UserItems> items;


    public AdaptorUserItems(Context context, List<UserItems> items) {
        this.context = context;
        this.items = items;

    }

    @Override
    public AdaptorUserItems.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_item_user, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final UserItems product = items.get(position);

        holder.title.setText(product.getItemTitle());

        holder.id = product.getItemId();


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        public AppCompatTextView title;
        String id;





        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            title = (AppCompatTextView) itemView.findViewById(R.id.ItemTitle);





        }

        @Override
        public void onClick(View view) {
            Intent listItems = new Intent(context, PostedItem.class);
            listItems.putExtra("item_id", id);

            context.startActivity(listItems);
        }
    }


}
