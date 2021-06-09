package hcmute.edu.vn.mssv18110324.salesmanager.utils;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.models.CartItem;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;

public class InsertData extends Application {

    CategoryDatabaseHandler dbCategory = new CategoryDatabaseHandler(this);
    ProductDatabaseHandler dbProduct = new ProductDatabaseHandler(this);

    @Override
    public void onCreate() {
        super.onCreate();


    }
    public void addCategory(Bitmap bitmap) {
//        Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.ancol)).getBitmap();

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ancol);
        Category category = new Category();
        category.set_id(1);
        category.set_name("Rượu");
        category.set_status(1);
        category.set_image(bitmap);
        dbCategory.addCategory(category);
    }

    public void addProduct(Bitmap bitmap) {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.froster_unicorn);
        Product product = new Product();
        product.set_id(1);
        product.set_status(1);
        product.set_category_id(1);
        product.set_image(bitmap);
        product.set_describe("Nothing");
        product.set_name("Vinamilk");
        product.set_price(20000);
        product.set_quantity(22);
        dbProduct.addProduct(product);


    }

}
