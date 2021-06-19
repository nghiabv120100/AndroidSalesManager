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
import hcmute.edu.vn.mssv18110324.salesmanager.models.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    public  ArrayList<User> lstUser;

    UserClicked activity;

    public interface UserClicked {
        void onUserClicked(User user);
    }

    public UserAdapter(Context context, ArrayList<User> lstUser) {
        this.lstUser = lstUser;
        activity = (UserClicked) context;
    }


    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(lstUser.get(position));
        holder.txtFullName.setText(lstUser.get(position).get_full_name());
        holder.imgAvatar.setImageBitmap(lstUser.get(position).get_avatar());
        holder.txtPhoneNumber.setText(lstUser.get(position).get_phone_number());
        if (lstUser.get(position).get_role() == 1) {
            holder.txtRole.setText("Nhân viên");
        } else {
            holder.txtRole.setText("Khách hàng");
        }
    }

    @Override
    public int getItemCount() {
        return lstUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFullName,txtRole,txtPhoneNumber;
        ImageView imgAvatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFullName = itemView.findViewById(R.id.txtFullName);
            txtRole = itemView.findViewById(R.id.txtRole);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onUserClicked((User) v.getTag());
                }
            });

        }
    }
}
