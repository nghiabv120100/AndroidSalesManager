package hcmute.edu.vn.mssv18110324.salesmanager.utils;

import android.app.Application;

import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Product;

public class InsertData extends Application {

    CategoryDatabaseHandler dbCategory = new CategoryDatabaseHandler(this);
    ProductDatabaseHandler dbProduct = new ProductDatabaseHandler(this);

    @Override
    public void onCreate() {
        super.onCreate();


    }
    private void addCategory() {
        Category category = new Category();
        category.set_id(5);
        category.set_name("Cơm");
        category.set_status(1);
        category.set_image("milk.png");
        dbCategory.addCategory(category);
    }

    private void addProduct(int i) {
        Product product = new Product();
        product.set_id(i);
        product.set_status(1);
        product.set_category_id(1);
        product.set_image("image");
        product.set_describe("Nothing");
        product.set_name("Vinamilk");
        product.set_price(20000);
        product.set_quantity(22);
        dbProduct.addProduct(product);


    }

}
