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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.ProductDatabaseHandler;

public class HomeFrag extends Fragment {
    View view;
    RecyclerView.Adapter myAdapter;
    public HomeFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                return false;
            }
        });*/

        ProductDatabaseHandler db = new ProductDatabaseHandler(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerCellingProducts);
// Create an ArrayAdapter using the string array and a default spinner layout
        recyclerView.setHasFixedSize(true);

        GridLayoutManager manager;
//        LinearLayoutManager manager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        manager = new GridLayoutManager(getContext(),2);

//        manager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(manager);

        ArrayList<Product> lstProduct = new ArrayList<Product>();

        lstProduct = db.findAllProduct();

        myAdapter = new ProductAdapter(this.getActivity(),lstProduct);

        recyclerView.setAdapter(myAdapter);
// Apply the adapter to the spinner

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResumeHome","ResumeHome");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("onPauseHome","PauseHome");
    }

}