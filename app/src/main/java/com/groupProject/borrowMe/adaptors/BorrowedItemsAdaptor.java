package com.groupProject.borrowMe.adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.groupProject.borrowMe.Item.BorrowedItemsDetails;
import com.groupProject.borrowMe.R;
import com.groupProject.borrowMe.models.BorrowItem;

import java.util.List;


/**
 * Created by Enache on 25/04/2018.
 */

public class BorrowedItemsAdaptor extends  RecyclerView.Adapter<BorrowedItemsAdaptor.ViewHolder>{

    private static Context context;
    private List<BorrowItem> req;

    public BorrowedItemsAdaptor(Context context, List<BorrowItem> req) {
        this.context = context;
        this.req = req;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_borrowed_items, null);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final BorrowItem LIST = req.get(position);

        holder.Lemail.setText(LIST.getEmailLender());

        holder.id = LIST.getBorrowId();


    }

    @Override
    public int getItemCount() {
        return req.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        public AppCompatTextView Lemail;
        String id;





        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            Lemail = (AppCompatTextView) itemView.findViewById(R.id.emailLender);

        }

        @Override
        public void onClick(View view) {
            Intent listItems = new Intent(context, BorrowedItemsDetails.class);
            listItems.putExtra("borrow_id", id);

            context.startActivity(listItems);
        }
    }

}
