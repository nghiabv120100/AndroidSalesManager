package hcmute.edu.vn.mssv18110324.salesmanager.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110324.salesmanager.models.CartItem;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;

public class CartRepo {
    private MutableLiveData<List<CartItem>> mutableCart = new MutableLiveData<>();
    private MutableLiveData<Integer> mutableTotalPrice = new MutableLiveData<>();

    public LiveData<List<CartItem>> getCart() {
        if (mutableCart.getValue() == null) {
            initCart();
        }
        return mutableCart;
    }

    public void initCart() {
        mutableCart.setValue(new ArrayList<CartItem>());
        calculateCartTotal();
    }

    public boolean addItemToCart(Product product) {
        if (mutableCart.getValue() == null) {
            initCart();
        }
        List<CartItem> lstCartItem = new ArrayList<>(mutableCart.getValue());
        // Check same product
        for (CartItem cartItem : lstCartItem) {
            if (product.get_id()==cartItem.get_product().get_id()) {
                if(cartItem.get_quantity() >= 5)
                    return false;

                int index = lstCartItem.indexOf(cartItem);
                cartItem.set_quantity(cartItem.get_quantity()+1);
                lstCartItem.set(index,cartItem);
                mutableCart.setValue(lstCartItem);
                calculateCartTotal();
                return true;
            }
        }
        CartItem cartItem = new CartItem(product,1);
        lstCartItem.add(cartItem);
        mutableCart.setValue(lstCartItem);
        calculateCartTotal();
        return true;
    }

    public void removeItemFromCart(CartItem cartItem) {
        if (mutableCart.getValue() == null) {
            return;
        }
        List<CartItem> lstCartItem = new ArrayList<>(mutableCart.getValue());

        lstCartItem.remove(cartItem);
        mutableCart.setValue(lstCartItem);
        calculateCartTotal();
    }

    public void changeQuantity(CartItem cartItem,int quantity) {
        if(mutableCart.getValue() == null) {
            return;
        }
        List<CartItem> lstCartItem = new ArrayList<>(mutableCart.getValue());
        CartItem updateItem = new CartItem(cartItem.get_product(),quantity);
        lstCartItem.set(lstCartItem.indexOf(cartItem),updateItem);
        mutableCart.setValue(lstCartItem);
        calculateCartTotal();
    }
    private void calculateCartTotal() {
        if (mutableCart.getValue()==null) {
            return;
        }
        Integer total =0;
        List<CartItem> lstCartItem = mutableCart.getValue();
        for (CartItem cartItem:lstCartItem) {
            total += cartItem.get_product().get_price()*cartItem.get_quantity();
        }
        mutableTotalPrice.setValue(total);
    }

    public LiveData<Integer> getTotalPrice() {
        if(mutableTotalPrice.getValue()==null) {
            mutableTotalPrice.setValue(0);
        }
        return mutableTotalPrice;
    }
}
