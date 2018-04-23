package com.groupProject.borrowMe.adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.groupProject.borrowMe.ItemDetails;
import com.groupProject.borrowMe.R;
import com.groupProject.borrowMe.models.ItemDepartments;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Enache on 20/04/2018.
 */

public class AdaptorItemDepartments extends RecyclerView.Adapter<AdaptorItemDepartments.ViewHolder>
{

    private static Context context;
    private List<ItemDepartments> items;
    String ID;

    public AdaptorItemDepartments(Context context, List<ItemDepartments> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_items_department, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ItemDepartments product = items.get(position);

        holder.title.setText(product.getItemTitle());
        holder.price.setText(product.getItemPrice()+ " £/day");
       // holder.id.setText(product.getItemId());

        holder.id = product.getItemId();


            }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        public AppCompatTextView title,price;
        String id;



        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);


            title = (AppCompatTextView) itemView.findViewById(R.id.textViewTitle);
            price = (AppCompatTextView) itemView.findViewById(R.id.textViewPrice);
           // id = (AppCompatTextView) itemView.findViewById(R.id.textViewID);


        }

        @Override
        public void onClick(View view) {
            Intent listItems = new Intent(context, ItemDetails.class);
             listItems.putExtra("item_id", id);
              context.startActivity(listItems);
        }
    }



}

