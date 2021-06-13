package hcmute.edu.vn.mssv18110324.salesmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;

public class CategorySpinnerAdapter extends BaseAdapter {
    Context context;
    ArrayList<Category> lstCategory;
    int flags[];
    String[] countryNames;
    LayoutInflater inflter;

    public CategorySpinnerAdapter() {
    }

    public CategorySpinnerAdapter(Context context, ArrayList<Category> lstCategory) {
        this.context = context;
        this.lstCategory = lstCategory;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return lstCategory.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
       return  getView(position,convertView,parent);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.row_category, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imgCategory);
        TextView names = (TextView) view.findViewById(R.id.txtCategory);
        icon.setImageBitmap(lstCategory.get(position).get_image());
        names.setText(lstCategory.get(position).get_name());
        return  view;
    }
}
