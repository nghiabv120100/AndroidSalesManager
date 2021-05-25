package hcmute.edu.vn.mssv18110324.salesmanager.models;


import android.graphics.Bitmap;

public class Product {
    private Integer _id;
    private String _name;
    private Integer _quantity;
    private Integer _price;
    private String _describe;
    private Bitmap _image;
    private Integer _category_id;
    private Integer _status;

    public Product() {
    }

    public Product(Integer _id, String _name, Integer _quantity, Integer _price, String _describe, Bitmap _image, Integer _category_id, Integer _status) {
        this._id = _id;
        this._name = _name;
        this._quantity = _quantity;
        this._price = _price;
        this._describe = _describe;
        this._image = _image;
        this._category_id = _category_id;
        this._status = _status;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public Integer get_quantity() {
        return _quantity;
    }

    public void set_quantity(Integer _quantity) {
        this._quantity = _quantity;
    }

    public Integer get_price() {
        return _price;
    }

    public void set_price(Integer _price) {
        this._price = _price;
    }

    public String get_describe() {
        return _describe;
    }

    public void set_describe(String _describe) {
        this._describe = _describe;
    }

    public Bitmap get_image() {
        return _image;
    }

    public void set_image(Bitmap _image) {
        this._image = _image;
    }

    public Integer get_category_id() {
        return _category_id;
    }

    public void set_category_id(Integer _category_id) {
        this._category_id = _category_id;
    }

    public Integer get_status() {
        return _status;
    }

    public void set_status(Integer _status) {
        this._status = _status;
    }
}
