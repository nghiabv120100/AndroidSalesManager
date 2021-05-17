package hcmute.edu.vn.mssv18110324.salesmanager.models;


public class User {
    private Integer _id;
    private String _full_name;
    private String _email;
    private String _phone_number;
    private String _password;
    private Integer _role;
    private Integer _status;

    public User() {
        this._role=0;
        this._status=1;
    }

    public User(Integer _id, String _full_name, String _email, String _phone_number, String _password, Integer _role, Integer _status) {
        this._id = _id;
        this._full_name = _full_name;
        this._email = _email;
        this._phone_number = _phone_number;
        this._password = _password;
        this._role = _role;
        this._status = _status;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String get_full_name() {
        return _full_name;
    }

    public void set_full_name(String _full_name) {
        this._full_name = _full_name;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_phone_number() {
        return _phone_number;
    }

    public void set_phone_number(String _phone_number) {
        this._phone_number = _phone_number;
    }


    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public Integer get_role() {
        return _role;
    }

    public void set_role(Integer _role) {
        this._role = _role;
    }

    public Integer get_status() {
        return _status;
    }

    public void set_status(Integer _status) {
        this._status = _status;
    }
}
