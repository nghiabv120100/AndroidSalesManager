package hcmute.edu.vn.mssv18110324.salesmanager.activity.client;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CartDatabaseHandler;
import hcmute.edu.vn.mssv18110324.salesmanager.viewmodels.ShopViewModel;


public class OrderFrag extends Fragment {
    Button btnContinueShopping;
    ShopViewModel shopViewModel;

    FragmentActivity fragmentActivity;

    NavController navController;

    Context context;

    public OrderFrag() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentActivity = (FragmentActivity)context;
        this.context = context;
        navController = new NavController(fragmentActivity.getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        shopViewModel.cartDatabaseHandler = new CartDatabaseHandler(context);
        shopViewModel.context =context;
        btnContinueShopping= view.findViewById(R.id.btnContinueShopping);
        btnContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopViewModel.insertCart();
                shopViewModel.resetCart();
                navController.showFragmentHome();
            }
        });
    }


}