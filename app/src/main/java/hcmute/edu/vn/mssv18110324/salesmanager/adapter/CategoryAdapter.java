package hcmute.edu.vn.mssv18110324.salesmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<Category> lstCategory;
    ItemClicked activity;

    public interface ItemClicked {
        void OnItemClicked(int index);
    }

    public CategoryAdapter(Context context, ArrayList<Category> lstCategory) {
        this.lstCategory = lstCategory;
        activity =(ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory;
        ImageView imgCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            imgCategory = itemView.findViewById(R.id.imgCategory);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.OnItemClicked(1);
                }
            });

        }
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(lstCategory.get(position));
        holder.imgCategory.setImageResource(R.drawable.milk);
        holder.txtCategory.setText(lstCategory.get(position).get_name());
    }

    @Override
    public int getItemCount() {
        return lstCategory.size();
    }
}
