package hcmute.edu.vn.mssv18110324.salesmanager.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import hcmute.edu.vn.mssv18110324.salesmanager.activity.Login;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Cart;
import hcmute.edu.vn.mssv18110324.salesmanager.models.CartItem;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;
import hcmute.edu.vn.mssv18110324.salesmanager.repositories.CartRepo;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CartDatabaseHandler;

public class ShopViewModel extends ViewModel {


    CartRepo cartRepo = new CartRepo();
    public CartDatabaseHandler cartDatabaseHandler;
    public Context context;

    public ShopViewModel() {

    }
    public ShopViewModel(Context context) {
        cartDatabaseHandler = new CartDatabaseHandler(context);
        this.context = context;
    }

    public LiveData<List<CartItem>> getCart() {
        return cartRepo.getCart();
    }

    public boolean addItemToCart(Product product) {
        return cartRepo.addItemToCart(product);
    }

    public void removeItemFromCart(CartItem cartItem) {
        cartRepo.removeItemFromCart(cartItem);
    }

    public void changeQuantity(CartItem cartItem,int quantity) {
        cartRepo.changeQuantity(cartItem,quantity);
    }

    public LiveData<Integer> getTotalPrice() {
        return cartRepo.getTotalPrice();
    }

    public void resetCart() {
        cartRepo.initCart();
    }

    public int insertCart() {
        Cart cart = new Cart();
        List<CartItem> lstCartItem= cartRepo.getCart().getValue();

        SharedPreferences pref = context.getSharedPreferences(Login.MY_PREFS_FILENAME,Context.MODE_PRIVATE);
        Integer id = pref.getInt("userId",-1);  // Second argument is default value

        cart.set_lst_cart_item(lstCartItem);
        cart.set_customer_id(id);
        cart.set_total_price(cartRepo.getTotalPrice().getValue());
        cartDatabaseHandler.addCart(cart);
        return 1;
    }
}
