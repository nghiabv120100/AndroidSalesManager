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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CategoryDatabaseHandler;

public class AdminCategoryActionActivity extends AppCompatActivity {
    private static final int IMAGE_PICK_CODE_3 = 1000;
    private static final int PERMISSION_CODE_3 = 1001;

    TextView txtTitle;
    ImageView imgImageCategory;
    EditText txtNameCategory;
    Button btnActionCategory;
    CategoryDatabaseHandler dbCategory = new CategoryDatabaseHandler(this);

    Integer id=-1; //id = -1: Add -- id != -1: Edit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category_action);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addControls();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent !=null) {
            id = intent.getIntExtra("id",-1);
            if (id != -1) {
                Category category = dbCategory.findByID(id);
                txtTitle.setText("Chỉnh sửa danh mục");
                btnActionCategory.setText("Chỉnh sửa danh mục");
                txtNameCategory.setText(category.get_name());
                imgImageCategory.setImageBitmap(category.get_image());
            }
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE_3);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE_3:{
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
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE_3) {
            imgImageCategory.setImageURI(data.getData());
        }
    }

    private void addControls() {
        txtTitle = findViewById(R.id.txtTitle);
        imgImageCategory = findViewById(R.id.imgImageCategory);
        txtNameCategory = findViewById(R.id.txtNameCategory);
        btnActionCategory = findViewById(R.id.btnActionCategory);
    }

    private void addEvents() {
        btnActionCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameCate =txtNameCategory.getText().toString().trim();
                if (nameCate !=null && !nameCate.equals("") && nameCate.length() > 0) {
                    Category category = new Category();
                    Bitmap bm=((BitmapDrawable)imgImageCategory.getDrawable()).getBitmap();
                    category.set_image(bm);
                    category.set_name(nameCate);
                    if (id == -1 ) {
                        dbCategory.addCategory(category);
                        Toast.makeText(AdminCategoryActionActivity.this,"Thêm danh mục thành công",Toast.LENGTH_SHORT).show();
                    } else {
                        category.set_id(id);
                        dbCategory.updateCategory(category);
                        Toast.makeText(AdminCategoryActionActivity.this,"Sửa danh mục thành công",Toast.LENGTH_SHORT).show();
                    }

                    AdminCategoryActionActivity.this.finish();

                } else {
                    Toast.makeText(AdminCategoryActionActivity.this,"Tên danh mục không được để trống",Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgImageCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED) {
                        //permission not granted request it
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        // show popup for runtime permission
                        requestPermissions(permission,PERMISSION_CODE_3);
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
}