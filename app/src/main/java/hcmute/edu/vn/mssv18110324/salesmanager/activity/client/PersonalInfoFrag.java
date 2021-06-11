package hcmute.edu.vn.mssv18110324.salesmanager.activity.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.activity.Login;
import hcmute.edu.vn.mssv18110324.salesmanager.models.User;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.UserDatabaseHandler;

public class PersonalInfoFrag extends Fragment {

    TextView txtChangePersonalInformation,txtPurchaseHistory,txtFullName;
    Button btnLogout;
    View view;
    UserDatabaseHandler userDatabaseHandler;
    public PersonalInfoFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userDatabaseHandler  =new UserDatabaseHandler(getContext());
        addControls();
        addEvents();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref = getActivity().getSharedPreferences(Login.MY_PREFS_FILENAME, Context.MODE_PRIVATE);
        Integer id = pref.getInt("userId",-1);  // Second argument is default value
        if (id != -1) {
            User user =  userDatabaseHandler.getUserByID(id);
            txtFullName.setText(user.get_full_name());
        } else {
            // toward login
        }
    }

    private void addControls() {
        txtChangePersonalInformation =view.findViewById(R.id.txtChangePersonalInformation);
        txtPurchaseHistory = view.findViewById(R.id.txtPurchaseHistory);
        btnLogout = view.findViewById(R.id.btnLogout);
        txtFullName = view.findViewById(R.id.txtFullName);

        SharedPreferences pref = getActivity().getSharedPreferences(Login.MY_PREFS_FILENAME, Context.MODE_PRIVATE);
        Integer id = pref.getInt("userId",-1);  // Second argument is default value
        if (id != -1) {
            User user =  userDatabaseHandler.getUserByID(id);
            txtFullName.setText(user.get_full_name());
        } else {
            // toward login
        }

    }

    private void addEvents() {
        txtChangePersonalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),PersonalInfoActivity.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(Login.MY_PREFS_FILENAME, Context.MODE_PRIVATE).edit();
                editor.clear().commit();
                Intent i = new Intent(getContext(), Login.class);
                startActivity(i);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_personal_info, container, false);
        return view;
    }
}