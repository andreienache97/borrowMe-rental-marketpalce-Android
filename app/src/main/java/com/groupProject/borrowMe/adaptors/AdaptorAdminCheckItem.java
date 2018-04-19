package com.groupProject.borrowMe.adaptors;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.groupProject.borrowMe.R;
import com.groupProject.borrowMe.models.ItemCheck;


import java.util.List;

/**
 * Created by Enache on 19/04/2018.
 */

public class AdaptorAdminCheckItem extends RecyclerView.Adapter<AdaptorAdminCheckItem.ViewHolder> {

    private Context context;
    private List<ItemCheck> items;

    public AdaptorAdminCheckItem(Context context, List<ItemCheck> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_check_admin, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemCheck product = items.get(position);

        holder.title.setText(product.getItemTitle());
        holder.price.setText(product.getItemPrice());
        holder.details.setText(product.getItemDetails());
        holder.department.setText(product.getItemDepartment());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public AppCompatTextView title;
        public AppCompatTextView price;
        public AppCompatTextView details;
        public AppCompatTextView department;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (AppCompatTextView) itemView.findViewById(R.id.titleItem);
            price = (AppCompatTextView) itemView.findViewById(R.id.priceItem);
            details = (AppCompatTextView) itemView.findViewById(R.id.descriptionItem);
            department = (AppCompatTextView) itemView.findViewById(R.id.departmentItem);


          //  itemView.setOnClickListener(new View.OnClickListener() {
          //      @Override
           //     public void onClick(View view) {

          //          ItemCheck cpu = (ItemCheck) view.getTag();

           //         Toast.makeText(view.getContext(), cpu.getItemTitle()+" "+cpu.getItemPrice()+"  "+ cpu.getItemDetails()+"  "+ cpu.getItemDepartment(), Toast.LENGTH_SHORT).show();

          //      }
          //  });

        }
    }

}
