package hcmute.edu.vn.mssv18110324.salesmanager.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Cart;

public class HistoryPurchaseAdapter extends RecyclerView.Adapter<HistoryPurchaseAdapter.ViewHolder> {

    public interface IOrderClicked {
        void onOrderClicked(Integer id);
    }

    ArrayList<Cart> lstCart = new ArrayList<Cart>();
    IOrderClicked itemClicked;

    public HistoryPurchaseAdapter(Activity activity, ArrayList<Cart> lstCart) {
        this.itemClicked = (IOrderClicked) activity;
        this.lstCart = lstCart;
    }

    @NonNull
    @Override
    public HistoryPurchaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history_purchase,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryPurchaseAdapter.ViewHolder holder, int position) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String sTotalPrice = formatter.format(lstCart.get(position).get_total_price());

        holder.itemView.setTag(lstCart.get(position));
        holder.txtTotalPrice.setText(sTotalPrice+"đ");
        holder.txtCodeOrder.setText("#"+lstCart.get(position).get_id());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        holder.txtDateBuy.setText(dateFormat.format(lstCart.get(position).get_buy_date()));
    }

    @Override
    public int getItemCount() {
        return lstCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCodeOrder,txtDateBuy,txtTotalPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCodeOrder = itemView.findViewById(R.id.txtCodeOrder);
            txtDateBuy = itemView.findViewById(R.id.txtDateBuy);
            txtTotalPrice = itemView.findViewById(R.id.txtTotalPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClicked.onOrderClicked(((Cart)itemView.getTag()).get_id());
                }
            });

        }
    }
}
