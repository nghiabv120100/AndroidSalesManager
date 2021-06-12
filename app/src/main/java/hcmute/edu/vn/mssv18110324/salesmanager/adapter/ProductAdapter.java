package hcmute.edu.vn.mssv18110324.salesmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    public static ArrayList<Product> lstProduct;
    ProductClicked activity;

    public interface ProductClicked {
        void OnProductClicked(int index);
    }
    public ProductAdapter(Context context, ArrayList<Product> lstProduct) {
        this.lstProduct = lstProduct;
        activity = (ProductClicked) context;
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
                    activity.OnProductClicked(lstProduct.indexOf(itemView.getTag()));
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
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String sPrice = formatter.format(lstProduct.get(position).get_price());

        holder.itemView.setTag(lstProduct.get(position));
        holder.txtPriceProduct.setText(sPrice+" đ");
        holder.imgImageProduct.setImageBitmap(lstProduct.get(position).get_image());
        holder.txtNameProduct.setText(lstProduct.get(position).get_name());
    }

    @Override
    public int getItemCount() {
        if (lstProduct ==null)
            return 0;
        else
            return lstProduct.size();
    }
}
