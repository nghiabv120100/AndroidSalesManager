package hcmute.edu.vn.mssv18110324.salesmanager.adapter;

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

public class ProductAdminAdapter extends RecyclerView.Adapter<ProductAdminAdapter.ViewHolder> {
    public ArrayList<Product> lstProduct;
    ProductClicked activity;

    public interface ProductClicked {
        void OnProductClicked(Product product);
    }
    public ProductAdminAdapter(Context context, ArrayList<Product> lstProduct) {
        this.lstProduct = lstProduct;
        activity = (ProductClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgImageProduct;
        TextView txtNameProduct,txtPriceProduct,txtDescribe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgImageProduct = itemView.findViewById(R.id.imgImageProduct);
            txtNameProduct = itemView.findViewById(R.id.txtNameProduct);
            txtPriceProduct = itemView.findViewById(R.id.txtPriceProduct);
            txtDescribe = itemView.findViewById(R.id.txtDescribe);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.OnProductClicked((Product) itemView.getTag());
                }
            });
        }
    }

    @NonNull
    @Override
    public ProductAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_admin,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdminAdapter.ViewHolder holder, int position) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String sPrice = formatter.format(lstProduct.get(position).get_price());

        holder.itemView.setTag(lstProduct.get(position));
        holder.txtPriceProduct.setText(sPrice+" đ");
        holder.imgImageProduct.setImageBitmap(lstProduct.get(position).get_image());
        holder.txtNameProduct.setText(lstProduct.get(position).get_name());

        String sDescribe ="";
        try {
            sDescribe=lstProduct.get(position).get_describe();
        } catch (Exception e) {

        }
        holder.txtDescribe.setText(sDescribe+"");
    }

    @Override
    public int getItemCount() {
        if (lstProduct ==null)
            return 0;
        else
            return lstProduct.size();
    }
}
