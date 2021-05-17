package hcmute.edu.vn.mssv18110324.salesmanager;

import android.app.Application;

import com.backendless.Backendless;

public class ApplicationClass extends Application {

    public static final String APPLICATION_ID = "0D7E1DAD-B599-4519-931F-04D2BA1C01FA";
    public static final String API_KEY = "056D9A3F-C084-452B-9AF9-C8FA1E017DA8";
    public static final String SERVER_URL = "https://api.backendless.com";

    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.setUrl(SERVER_URL);
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );
    }
}
