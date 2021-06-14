package hcmute.edu.vn.mssv18110324.salesmanager.activity.client;

import android.app.Activity;
import android.content.Context;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.CategoryAdapter;

public class NavController extends AppCompatActivity  {
    private FragmentManager manager;
    public NavController(FragmentManager manager) {
        this.manager = manager;
    }

    public void showFragmentHome() {

        manager.beginTransaction()
                .hide(manager.findFragmentById(R.id.fragListCategory))
                .hide(manager.findFragmentById(R.id.fragListProduct))
                .hide(manager.findFragmentById(R.id.fragDetailProduct))
                .hide(manager.findFragmentById(R.id.fragCart))
                .hide(manager.findFragmentById(R.id.fragOrder))
                .hide(manager.findFragmentById(R.id.fragPersonalInfo))
                .show(manager.findFragmentById(R.id.fragHome))
                .addToBackStack(null)
                .commit();
    }
    public void showFragmentListCategory() {
        manager.beginTransaction()
                .hide(manager.findFragmentById(R.id.fragHome))
                .hide(manager.findFragmentById(R.id.fragListProduct))
                .hide(manager.findFragmentById(R.id.fragDetailProduct))
                .hide(manager.findFragmentById(R.id.fragCart))
                .hide(manager.findFragmentById(R.id.fragOrder))
                .hide(manager.findFragmentById(R.id.fragPersonalInfo))
                .show(manager.findFragmentById(R.id.fragListCategory))
                .addToBackStack(null)
                .commit();
    }
    public void showFragmentProduct() {
//        FragmentManager manager = this.getSupportFragmentManager();
        manager.beginTransaction()
                .hide(manager.findFragmentById(R.id.fragListCategory))
                .hide(manager.findFragmentById(R.id.fragHome))
                .hide(manager.findFragmentById(R.id.fragDetailProduct))
                .hide(manager.findFragmentById(R.id.fragCart))
                .hide(manager.findFragmentById(R.id.fragOrder))
                .hide(manager.findFragmentById(R.id.fragPersonalInfo))
                .show(manager.findFragmentById(R.id.fragListProduct))
                .addToBackStack(null)
                .commit();
    }
    public void showFragmentDetailProduct() {
        manager.beginTransaction()
                .hide(manager.findFragmentById(R.id.fragListCategory))
                .hide(manager.findFragmentById(R.id.fragHome))
                .hide(manager.findFragmentById(R.id.fragListProduct))
                .hide(manager.findFragmentById(R.id.fragCart))
                .hide(manager.findFragmentById(R.id.fragOrder))
                .hide(manager.findFragmentById(R.id.fragPersonalInfo))
                .show(manager.findFragmentById(R.id.fragDetailProduct))
                .addToBackStack(null)
                .commit();
    }
    public void showFragmentCart() {
        manager.beginTransaction()
                .hide(manager.findFragmentById(R.id.fragListCategory))
                .hide(manager.findFragmentById(R.id.fragHome))
                .hide(manager.findFragmentById(R.id.fragListProduct))
                .hide(manager.findFragmentById(R.id.fragDetailProduct))
                .hide(manager.findFragmentById(R.id.fragOrder))
                .hide(manager.findFragmentById(R.id.fragPersonalInfo))
                .show(manager.findFragmentById(R.id.fragCart))
                .addToBackStack(null)
                .commit();
    }
    public void showFragmentOrder() {
        manager.beginTransaction()
                .hide(manager.findFragmentById(R.id.fragListCategory))
                .hide(manager.findFragmentById(R.id.fragHome))
                .hide(manager.findFragmentById(R.id.fragListProduct))
                .hide(manager.findFragmentById(R.id.fragDetailProduct))
                .hide(manager.findFragmentById(R.id.fragCart))
                .hide(manager.findFragmentById(R.id.fragPersonalInfo))
                .show(manager.findFragmentById(R.id.fragOrder))
                .addToBackStack(null)
                .commit();
    }
    public void showFragmentPersonalInfo() {
        manager.beginTransaction()
                .hide(manager.findFragmentById(R.id.fragListCategory))
                .hide(manager.findFragmentById(R.id.fragHome))
                .hide(manager.findFragmentById(R.id.fragListProduct))
                .hide(manager.findFragmentById(R.id.fragDetailProduct))
                .hide(manager.findFragmentById(R.id.fragCart))
                .hide(manager.findFragmentById(R.id.fragOrder))
                .show(manager.findFragmentById(R.id.fragPersonalInfo))
                .addToBackStack(null)
                .commit();
    }
}
