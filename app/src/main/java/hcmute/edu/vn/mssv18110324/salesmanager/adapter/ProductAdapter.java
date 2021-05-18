package hcmute.edu.vn.mssv18110324.salesmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    ArrayList<Product> lstProduct;

    public ProductAdapter(Context context, ArrayList<Product> lstProduct) {
        this.lstProduct = lstProduct;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgImageProduct;
        TextView txtNameProduct,txtPriceProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgImageProduct = itemView.findViewById(R.id.imgImageProduct);
            txtNameProduct = itemView.findViewById(R.id.txtNameProduct);
            txtPriceProduct = itemView.findViewById(R.id.txtPriceProduct);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(lstProduct.get(position));
        holder.txtPriceProduct.setText(lstProduct.get(position).get_price().toString());
        holder.txtNameProduct.setText(lstProduct.get(position).get_name());
    }

    @Override
    public int getItemCount() {
        return lstProduct.size();
    }
}
