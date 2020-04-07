package com.example.vrindavan.ThreeTabs.HomeAll;

import android.os.Bundle;

import com.example.vrindavan.activites.MainActivity;
import com.example.vrindavan.widget.ViewAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.vrindavan.R;
import com.example.vrindavan.widget.SpacingItemDecoration;
import com.example.vrindavan.utils.Tools;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment  {
    TextInputEditText editText;
    static RecyclerView recyclerView;
    static AdapterGridShopProductCard mAdapter;
    // firebase database

    static FragmentActivity fa;
    DatabaseReference dref;

    static FragmentManager fragmentManager;

    // product class arraylist
    static ArrayList <ProductClass> pclass;

    public HomeFragment() {
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.home_fragment, container, false);

        fa = getActivity();
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        pclass = new ArrayList();
        fragmentManager = getFragmentManager();
        final LinearLayout lyt_progress = (LinearLayout) root.findViewById(R.id.lyt_progress);
            lyt_progress.setVisibility(View.VISIBLE);
            lyt_progress.setAlpha(1.0f);
           //    recyclerView.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ViewAnimation.fadeOut(lyt_progress);
                }
            }, 3000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initComponent();
                }
            }, 3400);
        return root;
    }

    private void initComponent() {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getActivity(), 8), true));
        recyclerView.setHasFixedSize(true);

        // firebase code



        dref = FirebaseDatabase.getInstance().getReference("Product");

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                pclass.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    ProductClass pc = ds.getValue(ProductClass.class);
                    if(pc.pactivation.equals("true")) {
                        pclass.add(pc);
                    }
                }


                // ArrayList<ProductClass> items = DataGenerator.getShoppingProduct(getActivity());
                // Collections.shuffle(items);

                //set data and list adapter


                mAdapter = new AdapterGridShopProductCard(getActivity(), pclass);
                recyclerView.setAdapter(mAdapter);


                // on item list clicked
                mAdapter.setOnItemClickListener(new AdapterGridShopProductCard.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, ProductClass obj, int position) {

                        FragmentBottomSheetDialogFull fragment = new FragmentBottomSheetDialogFull();
                        fragment.setPeople(obj);
                        fragment.show(getFragmentManager(), fragment.getTag());

                        //    Snackbar.make(root, "Item " + obj.title + " clicked", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, ProductClass obj, int pos) {

                    }


                });

                /*mAdapter.setOnMoreButtonClickListener(new AdapterGridShopProductCard.OnMoreButtonClickListener() {
                    @Override
                    public void onItemClick(View view, ProductClass obj, MenuItem item) {
                      //  Snackbar.make(root, obj.pname + " (" + item.getTitle() + ") clicked", Snackbar.LENGTH_SHORT).show();
                    }
                });*/
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public static ArrayList<ProductClass> getPclass()
    {
        return pclass;
    }

    public static FragmentActivity getFActivity()
    {
        return fa;
    }
    public static RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public static AdapterGridShopProductCard getmAdapter() {
        return mAdapter;
    }

    public static FragmentManager getFManager()
    {
        return fragmentManager;
    }
    public static String getType()
    {
        String s = "home";
        return s;
    }

}
