package hcmute.edu.vn.mssv18110324.salesmanager.adapter;

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
import hcmute.edu.vn.mssv18110324.salesmanager.models.CartItem;

public class DetailOderAdapter extends RecyclerView.Adapter<DetailOderAdapter.ViewHolder> {

    ArrayList<CartItem> lstCartItem= new ArrayList<CartItem>();

    public DetailOderAdapter(ArrayList<CartItem> lstCartItem) {
        this.lstCartItem = lstCartItem;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgImageProduct;
        TextView txtNameProduct,txtUnitPrice,txtQuantity,txtPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgImageProduct = itemView.findViewById(R.id.imgImageProduct);
            txtNameProduct = itemView.findViewById(R.id.txtNameProduct);
            txtUnitPrice = itemView.findViewById(R.id.txtUnitPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_detail_order,parent,false);
        return new DetailOderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String sUnitPrice = formatter.format(lstCartItem.get(position).get_unit_price());
        String sTotalPrice = formatter.format(lstCartItem.get(position).get_unit_price()* lstCartItem.get(position).get_quantity());

        holder.itemView.setTag(lstCartItem.get(position));
        holder.imgImageProduct.setImageBitmap(lstCartItem.get(position).get_product().get_image());
        holder.txtNameProduct.setText(lstCartItem.get(position).get_product().get_name());
        holder.txtPrice.setText(sTotalPrice+"đ");
        holder.txtUnitPrice.setText(sUnitPrice+"đ");
    }

    @Override
    public int getItemCount() {
        return lstCartItem.size();
    }
}
