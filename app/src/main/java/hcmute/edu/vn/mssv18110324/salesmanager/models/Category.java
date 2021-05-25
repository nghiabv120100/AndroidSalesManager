package hcmute.edu.vn.mssv18110324.salesmanager.models;

import android.graphics.Bitmap;

public class Category {
    private Integer _id;
    private Bitmap _image;
    private String _name;
    private Integer _status;

    public Category() {
    }

    public Category(Integer _id, Bitmap _image, String _name, Integer _status) {
        this._id = _id;
        this._image = _image;
        this._name = _name;
        this._status = _status;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Bitmap get_image() {
        return _image;
    }

    public void set_image(Bitmap _image) {
        this._image = _image;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public Integer get_status() {
        return _status;
    }

    public void set_status(Integer _status) {
        this._status = _status;
    }
}
