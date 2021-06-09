package hcmute.edu.vn.mssv18110324.salesmanager.models;


public class CartItem {
    private Product _product;
    private Integer _cart_id;
    private Integer _quantity;
    private Integer _unit_price;



    private Integer _status;

    public CartItem() {
        this._status=1;
    }

    public CartItem(Product _product, Integer _quantity) {
        this._product = _product;
        this._quantity = _quantity;
        this._unit_price = _product.get_price();
        this._status = 1;
    }

    public Integer get_cart_id() {
        return _cart_id;
    }

    public void set_cart_id(Integer _cart_id) {
        this._cart_id = _cart_id;
    }

    public Product get_product() {
        return _product;
    }

    public void set_product(Product _product) {
        this._product = _product;
    }

    public Integer get_quantity() {
        return _quantity;
    }

    public void set_quantity(Integer _quantity) {
        this._quantity = _quantity;
    }

    public Integer get_unit_price() {
        return _unit_price;
    }

    public void set_unit_price(Integer _unit_price) {
        this._unit_price = _unit_price;
    }

    public Integer get_status() {
        return _status;
    }

    public void set_status(Integer _status) {
        this._status = _status;
    }
    @Override
    public String toString() {
        return "CartItem{" +
                "_product=" + _product +
                ", _quantity=" + _quantity +
                '}';
    }
}
