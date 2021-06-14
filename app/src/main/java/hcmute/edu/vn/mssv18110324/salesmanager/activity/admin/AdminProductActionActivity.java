package hcmute.edu.vn.mssv18110324.salesmanager.activity.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.CategorySpinnerAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CategoryDatabaseHandler;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.ProductDatabaseHandler;

public class AdminProductActionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int IMAGE_PICK_CODE_4 = 1000;
    private static final int PERMISSION_CODE_4 = 1001;

    TextView txtTitle;
    ImageView imgImageProduct;
    EditText txtNameProduct,txtPriceProduct,txtQuantity;
    Button btnActionProduct;

    Integer id =-1; // id =-1: Add Product  ----- id != -1: Edit Product

    ArrayList<Category> lstCategory;
    Spinner spinnerCategory;
    ProductDatabaseHandler dbProduct;

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE_4);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE_4:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    //permission was granted
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this,"Permission denied...!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE_4) {
            imgImageProduct.setImageURI(data.getData());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_action);
        addControls();
        addEvents();
    }

    private void addEvents() {
        imgImageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED) {
                        //permission not granted request it
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        // show popup for runtime permission
                        requestPermissions(permission,PERMISSION_CODE_4);
                    }
                    else {
                        //permission already granted
                        pickImageFromGallery();
                    }
                }
                else {
                    //system os is less then marshmallow
                    pickImageFromGallery();
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
    }

    private void addControls() {

        txtTitle = findViewById(R.id.txtTitle);
        imgImageProduct = findViewById(R.id.imgImageProduct);
        txtNameProduct = findViewById(R.id.txtNameProduct);
        txtPriceProduct = findViewById(R.id.txtPriceProduct);
        txtQuantity = findViewById(R.id.txtQuantity);
        btnActionProduct = findViewById(R.id.btnActionProduct);

        if (id == -1) {
            txtTitle.setText("Thêm sản phẩm");
            btnActionProduct.setText("Thêm sản phẩm");
            btnActionProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int result = addProduct();
                    if (result != -1)
                        AdminProductActionActivity.this.finish();
                }
            });
        }


        CategoryDatabaseHandler dbCategory = new CategoryDatabaseHandler(this);
        lstCategory= dbCategory.findAllCategory();

        spinnerCategory = findViewById(R.id.spinnerCategory);
        CategorySpinnerAdapter spinnerAdapter = new CategorySpinnerAdapter(getApplicationContext(),lstCategory);
//        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(spinnerAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AdminProductActionActivity.this,"itemm",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private int addProduct() {
        dbProduct = new ProductDatabaseHandler(this);

        String sNameProduct = txtNameProduct.getText().toString().trim();
        String sPriceProduct = txtPriceProduct.getText().toString().trim();
        String sQuantity = txtQuantity.getText().toString().trim();
        Bitmap bm=((BitmapDrawable)imgImageProduct.getDrawable()).getBitmap();
        Product product = new Product();

        if (sNameProduct == null || sNameProduct.equals("") || sNameProduct.length() < 1 ) {
            Toast.makeText(this,"Vui lòng nhập tên sản phẩm",Toast.LENGTH_SHORT).show();
        } else if (sPriceProduct == null || sPriceProduct.equals("") || sPriceProduct.length() < 1 ) {
            Toast.makeText(this,"Vui lòng nhập giá sản phẩm",Toast.LENGTH_SHORT).show();
        } else if (sQuantity == null || sQuantity.equals("") || sQuantity.length() < 1 ) {
            Toast.makeText(this,"Vui lòng nhập số lương sản phẩm",Toast.LENGTH_SHORT).show();
        } else if (bm == null ) {
            Toast.makeText(this,"Vui lòng chọn ảnh sản phẩm",Toast.LENGTH_SHORT).show();
        } else {
            product.set_name(sNameProduct);
            product.set_price(Integer.parseInt(sPriceProduct));
            product.set_quantity(Integer.parseInt(sQuantity));
            product.set_image(bm);
            int idProduct= dbProduct.addProduct(product);
            Toast.makeText(this,"Thêm sản phẩm thành công",Toast.LENGTH_SHORT).show();
            return idProduct;
        }
        return -1;

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}