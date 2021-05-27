package hcmute.edu.vn.mssv18110324.salesmanager.activity.client;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CategoryDatabaseHandler;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.ProductDatabaseHandler;

public class ListProductFrag extends Fragment {

    public ListProductFrag() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager manager;
    View view;
    ArrayList<Product> lstProduct;
    Context activity;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity=context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_list_product, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductDatabaseHandler db = new ProductDatabaseHandler(activity);

        recyclerView =  view.findViewById(R.id.lstProduct);
        recyclerView.setHasFixedSize(true);

        manager = new GridLayoutManager(activity,2);
//        manager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(manager);

        lstProduct = new ArrayList<Product>();

        lstProduct = db.findAllProduct();

        myAdapter = new ProductAdapter(this.getActivity(),lstProduct);

        recyclerView.setAdapter(myAdapter);

        Log.d("onActivityCreated: ","onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("onStart: ","OnStart");
    }
}