package hcmute.edu.vn.mssv18110324.salesmanager.models;

import java.util.Date;
import java.util.List;

public class Cart {
    private int _id;
    private int _total_price;
    private int _customer_id;
    private int _status;
    private Date _buy_date;
    private List<CartItem> _lst_cart_item;

    public Cart() {
        this._status=1;
        //buydate
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_total_price() {
        return _total_price;
    }

    public void set_total_price(int _total_price) {
        this._total_price = _total_price;
    }

    public int get_customer_id() {
        return _customer_id;
    }

    public void set_customer_id(int _customer_id) {
        this._customer_id = _customer_id;
    }

    public int get_status() {
        return _status;
    }

    public void set_status(int _status) {
        this._status = _status;
    }

    public Date get_buy_date() {
        return _buy_date;
    }

    public void set_buy_date(Date _buy_date) {
        this._buy_date = _buy_date;
    }

    public List<CartItem> get_lst_cart_item() {
        return _lst_cart_item;
    }

    public void set_lst_cart_item(List<CartItem> _lst_cart_item) {
        this._lst_cart_item = _lst_cart_item;
    }
}
