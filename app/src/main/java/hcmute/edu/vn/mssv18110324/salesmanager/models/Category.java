package hcmute.edu.vn.mssv18110324.salesmanager.models;

public class Category {
    private Integer _id;
    private String _image;
    private String _name;
    private Integer _status;

    public Category() {
    }

    public Category(Integer _id, String _image, String _name, Integer _status) {
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

    public String get_image() {
        return _image;
    }

    public void set_image(String _image) {
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
