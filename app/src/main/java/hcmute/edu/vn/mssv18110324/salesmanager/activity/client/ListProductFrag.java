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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListProductFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListProductFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListProductFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListProductFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ListProductFrag newInstance(String param1, String param2) {
        ListProductFrag fragment = new ListProductFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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