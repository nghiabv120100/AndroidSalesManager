package hcmute.edu.vn.mssv18110324.salesmanager.activity.client;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hcmute.edu.vn.mssv18110324.salesmanager.ApplicationClass;
import hcmute.edu.vn.mssv18110324.salesmanager.R;
import hcmute.edu.vn.mssv18110324.salesmanager.adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110324.salesmanager.models.Category;
import hcmute.edu.vn.mssv18110324.salesmanager.utils.CategoryDatabaseHandler;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListCategoryFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListCategoryFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context activity;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListCategoryFrag() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity=context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListCategoryFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ListCategoryFrag newInstance(String param1, String param2) {
        ListCategoryFrag fragment = new ListCategoryFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager manager;
    View view;
    ArrayList<Category> lstCategory;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_list_category, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CategoryDatabaseHandler db = new CategoryDatabaseHandler(activity);

        recyclerView =  view.findViewById(R.id.lstCategory);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(manager);

        lstCategory = new ArrayList<Category>();

        lstCategory = db.findAllCategory();

        myAdapter = new CategoryAdapter(this.getActivity(),lstCategory);

        recyclerView.setAdapter(myAdapter);

    }
}