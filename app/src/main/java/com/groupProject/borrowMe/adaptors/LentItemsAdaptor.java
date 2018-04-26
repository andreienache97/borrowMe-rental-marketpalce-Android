package com.groupProject.borrowMe.adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.groupProject.borrowMe.Item.LentItemsDetails;
import com.groupProject.borrowMe.R;
import com.groupProject.borrowMe.models.LentItem;

import java.util.List;

/**
 * Created by Enache on 26/04/2018.
 */

public class LentItemsAdaptor extends  RecyclerView.Adapter<LentItemsAdaptor.ViewHolder> {

    private static Context context;
    private List<LentItem> req;

    public LentItemsAdaptor(Context context, List<LentItem> req) {
        this.context = context;
        this.req = req;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_lent_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final LentItem LIST = req.get(position);

        holder.Gemail.setText(LIST.getEmailBorrower());

        holder.id = LIST.getBorrowId();

    }

    @Override
    public int getItemCount() {
        return req.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        public AppCompatTextView Gemail;
        String id;





        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            Gemail = (AppCompatTextView) itemView.findViewById(R.id.emailBorrower);

        }

        @Override
        public void onClick(View view) {
            Intent listItems = new Intent(context, LentItemsDetails.class);
            listItems.putExtra("borrow_id", id);

            context.startActivity(listItems);
        }
    }

}
