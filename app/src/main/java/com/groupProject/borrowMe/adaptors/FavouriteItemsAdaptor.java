package com.groupProject.borrowMe.adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.groupProject.borrowMe.Item.FavouriteItemsDetails;
import com.groupProject.borrowMe.R;
import com.groupProject.borrowMe.models.FavouriteItem;

import java.util.List;


/**
 * Created by Diana/Sebastian on 03/05/2018.
 */

public class FavouriteItemsAdaptor extends  RecyclerView.Adapter<FavouriteItemsAdaptor.ViewHolder>{

    private static Context context;
    private List<FavouriteItem> req;

    public FavouriteItemsAdaptor(Context context, List<FavouriteItem> req) {
        this.context = context;
        this.req = req;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_favourite_items, null);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final FavouriteItem LIST = req.get(position);


        holder.id = LIST.getItemId();
        holder.Lemail = LIST.getEmailLiker();
        holder.name.setText(LIST.getItemName());


    }

    @Override
    public int getItemCount() {
        return req.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        public AppCompatTextView name;
        String id;
        String Lemail;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            name = (AppCompatTextView) itemView.findViewById(R.id.itemName);

        }

        @Override
        public void onClick(View view) {
            Intent listItems = new Intent(context, FavouriteItemsDetails.class);
            listItems.putExtra("email", Lemail);
            listItems.putExtra("item_id", id);

            context.startActivity(listItems);
        }
    }

}
