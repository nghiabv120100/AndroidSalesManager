package hcmute.edu.vn.mssv18110324.salesmanager.activity.client;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.CartItemAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.CartItem;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;
import hcmute.edu.vn.mssv18110324.salesmanager.viewmodels.ShopViewModel;

public class CartFrag extends Fragment implements CartItemAdapter.DeleteItem {


    TextView orderTotalTextView;
    Button placeOrderButton;

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager manager;
    ArrayList<CartItem> lstCartItem;
    Context activity;
    CartItemAdapter.DeleteItem deleteItem;

    ShopViewModel shopViewModel = new ShopViewModel();

    FragmentActivity fragmentActivity;

    NavController navController;

    private void addControl(View view) {
        orderTotalTextView = view.findViewById(R.id.orderTotalTextView);
        placeOrderButton = view.findViewById(R.id.placeOrderButton);
    }

    public CartFrag() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = context;
        deleteItem = (CartItemAdapter.DeleteItem) this;
        fragmentActivity = (FragmentActivity)context;
        navController = new NavController(fragmentActivity.getSupportFragmentManager());
//        deleteItem=this::deleteItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControl(view);
        recyclerView =  view.findViewById(R.id.cartRecyclerView);
        recyclerView.setHasFixedSize(true);

        manager = new GridLayoutManager(activity,1);
//        manager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(manager);
        lstCartItem = new ArrayList<CartItem>(shopViewModel.getCart().getValue());

//        myAdapter = new CartItemAdapter(this.getActivity(),lstCartItem);
        myAdapter = new CartItemAdapter(deleteItem,lstCartItem);

        recyclerView.setAdapter(myAdapter);



        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        shopViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                lstCartItem = new ArrayList<CartItem>(shopViewModel.getCart().getValue());

                myAdapter = new CartItemAdapter(deleteItem,lstCartItem);

                recyclerView.setAdapter(myAdapter);

                placeOrderButton.setEnabled(lstCartItem.size() > 0);
            }
        });

        shopViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                orderTotalTextView.setText("Total $:"+integer.toString());
            }
        });

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.showFragmentOrder();
            }
        });

        Log.d("onViewCreated: ","List Cart Item");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("TagCart","started");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TagCart2","onResume");
    }

    @Override
    public void deleteItem(CartItem cartItem) {
        Log.d("Deleted",cartItem.get_product().get_name());
        shopViewModel.removeItemFromCart(cartItem);
    }

    @Override
    public void changeQuantity(CartItem cartItem, int quantity) {
        shopViewModel.changeQuantity(cartItem,quantity);
    }

}