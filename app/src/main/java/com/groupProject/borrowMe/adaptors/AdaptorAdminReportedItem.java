package com.groupProject.borrowMe.adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.groupProject.borrowMe.R;
import com.groupProject.borrowMe.models.ItemCheck;
import com.groupProject.borrowMe.Item.InspectReportedItem;


import java.util.List;

/**
 * Created by Arocha on 25/04/2018.
 */

public class AdaptorAdminReportedItem extends RecyclerView.Adapter<AdaptorAdminReportedItem.ViewHolder> {

    private Context context;
    private List<ItemCheck> items;

    public AdaptorAdminReportedItem(Context context, List<ItemCheck> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_reported_admin, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ItemCheck product = items.get(position);

        holder.item_id.setText(product.getItemId());
        holder.email.setText(product.getItemEmail());
        holder.title.setText(product.getItemTitle());
        holder.price.setText(product.getItemPrice());
        holder.details.setText(product.getItemDetails());
        holder.department.setText(product.getItemDepartment());
        holder.reportEmail.setText(product.getReportEmail());
        holder.reportReason.setText(product.getReportReason());
        holder.inspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentChange = new Intent(context, InspectReportedItem.class);
                intentChange.putExtra("item_id", product.getItemId());
                intentChange.putExtra( "email",product.getItemEmail() );
                intentChange.putExtra( "title",product.getItemTitle() );
                intentChange.putExtra( "price",product.getItemPrice() );
                intentChange.putExtra( "description",product.getItemDetails() );
                intentChange.putExtra( "department",product.getItemDepartment() );
                intentChange.putExtra( "reportEmail",product.getReportEmail() );
                intentChange.putExtra( "reportReason",product.getReportReason() );
                context.startActivity(intentChange);

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public AppCompatTextView title,price,details,department, item_id, email,reportEmail,reportReason;
        public AppCompatButton inspect;

        public ViewHolder(View itemView) {
            super(itemView);

            item_id = (AppCompatTextView) itemView.findViewById(R.id.idItem);
            email = (AppCompatTextView) itemView.findViewById(R.id.emailItem);
            title = (AppCompatTextView) itemView.findViewById(R.id.titleItem);
            price = (AppCompatTextView) itemView.findViewById(R.id.priceItem);
            details = (AppCompatTextView) itemView.findViewById(R.id.descriptionItem);
            department = (AppCompatTextView) itemView.findViewById(R.id.departmentItem);
            reportEmail = (AppCompatTextView) itemView.findViewById(R.id.reportEmailItem);
            reportReason = (AppCompatTextView) itemView.findViewById(R.id.reportReasonItem);
            inspect = (AppCompatButton) itemView.findViewById(R.id.bInspect);

        }
    }

}
