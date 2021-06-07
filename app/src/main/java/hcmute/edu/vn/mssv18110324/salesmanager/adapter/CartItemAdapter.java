package hcmute.edu.vn.mssv18110324.salesmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.models.CartItem;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    public ArrayList<CartItem> lstCartItem;
    public Context activity;
    public DeleteItem IdeleteItem;

    public interface DeleteItem {
        void deleteItem(CartItem cartItem);
        void changeQuantity(CartItem cartItem, int quantity);
    }

    public CartItemAdapter(Context activity, ArrayList<CartItem> lstCartItem) {
        this.activity=activity;
        this.lstCartItem = lstCartItem;
    }
    public CartItemAdapter(DeleteItem deleteItem,ArrayList<CartItem> lstCartItem) {
        this.IdeleteItem = deleteItem;
        this.lstCartItem = lstCartItem;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        ImageButton deleteProductButton;
        TextView textView,productNameTextView,productTotalPriceTextView;
        Spinner quantitySpinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImageView);
            deleteProductButton = itemView.findViewById(R.id.deleteProductButton);
            textView = itemView.findViewById(R.id.textView);
            productNameTextView= itemView.findViewById(R.id.productNameTextView);
            productTotalPriceTextView = itemView.findViewById(R.id.productTotalPriceTextView);
            quantitySpinner = itemView.findViewById(R.id.quantitySpinner);

            deleteProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IdeleteItem.deleteItem((CartItem)itemView.getTag());
                }
            });
            quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int quantity = position + 1;
                    if (quantity == ((CartItem)itemView.getTag()).get_quantity()) {
                        return;
                    }
                    IdeleteItem.changeQuantity(((CartItem)itemView.getTag()),quantity);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(lstCartItem.get(position));
        holder.productImageView.setImageBitmap(lstCartItem.get(position).get_product().get_image());
        holder.quantitySpinner.setSelection(lstCartItem.get(position).get_quantity()-1);
        holder.deleteProductButton.setImageResource(R.drawable.ic_baseline_delete_24);
        holder.productNameTextView.setText(lstCartItem.get(position).get_product().get_name());
        holder.productTotalPriceTextView.setText(lstCartItem.get(position).get_quantity()*lstCartItem.get(position).get_unit_price()+"");
    }

    @Override
    public int getItemCount() {
        return lstCartItem.size();
    }


}
