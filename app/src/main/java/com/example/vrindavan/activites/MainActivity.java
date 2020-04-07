package com.example.vrindavan.activites;


import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.example.vrindavan.CheckoutAll.FirebaseOrders;
import com.example.vrindavan.ThreeTabs.HomeAll.AdapterGridShopProductCard;
import com.example.vrindavan.ThreeTabs.HomeAll.FragmentBottomSheetDialogFull;
import com.example.vrindavan.ThreeTabs.HomeAll.HomeFragment;
import com.example.vrindavan.ThreeTabs.HomeAll.ProductClass;
import com.example.vrindavan.ThreeTabs.OrderAll.AdapterListBasic;
import com.example.vrindavan.ThreeTabs.OrderAll.OrderFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.vrindavan.R;
import com.example.vrindavan.ThreeTabs.HomeAll.SectionsPagerAdapter;
import com.example.vrindavan.utils.Tools;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {

    private ViewPager view_pager;
    private TabLayout tab_layout;
    SectionsPagerAdapter adapter;
    SQLiteDatabase db;
    ArrayList<ProductClass> real;
    ArrayList<FirebaseOrders> freal;
    FragmentActivity fa;
    RecyclerView rv;
    int indicator=0;
    ArrayList<ProductClass> arrayList;
    ArrayList<FirebaseOrders> arrayList1;
    AdapterListBasic mAdapter;
    public static String type="nun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Welcome..", Toast.LENGTH_SHORT).show();



        view_pager = (ViewPager) findViewById(R.id.view_pager);
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);


        freal = new ArrayList<>();

        arrayList = new ArrayList<>();

        arrayList1 = new ArrayList<>();



//        Toast.makeText(this,getAid(),Toast.LENGTH_SHORT).show();

        initToolbar();
        initComponent();

    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Toast.makeText(this, "Visit Again..", Toast.LENGTH_SHORT).show();
            finishAffinity();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //  toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" Vrundaavan");
      //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_1000);
    }

    private void initComponent() {
        setupViewPager(view_pager);
        tab_layout.setupWithViewPager(view_pager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new SectionsPagerAdapter(getSupportFragmentManager(),tab_layout.getTabCount());
        adapter.addFragment(tab_layout.newTab(), "HOME");
        adapter.addFragment(tab_layout.newTab(), "ORDERS");
        adapter.addFragment(tab_layout.newTab(), "PROFILE");
        viewPager.setAdapter(adapter);


    }

    AdapterGridShopProductCard mAdapter1;


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) menuItem.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(view_pager.getCurrentItem() == 0) {

                   if(arrayList1.isEmpty())
                    {

                    }
                    else {
                        if (indicator == 1) {
                            mAdapter = new AdapterListBasic(fa, arrayList1);
                            rv.setAdapter(mAdapter);

                            // on item list clicked
                            mAdapter.setOnItemClickListener(new AdapterListBasic.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, FirebaseOrders obj, int position) {
                                    //   showCustomDialog(obj);

                                    Intent intentinfo=new Intent(fa, OrderInfo.class);

                                    intentinfo.putExtra("oid",freal.get(position).getOid());

                                    startActivity(intentinfo);
                                }

                                @Override
                                public void onItemLongClick(View view, OrderInfo obj, int pos) {

                                }
                            });




                            indicator--;
                        }
                    }

                    arrayList = (ArrayList<ProductClass>) HomeFragment.getPclass();

                    mAdapter1 = HomeFragment.getmAdapter();


                    fa = HomeFragment.getFActivity();


                    rv = HomeFragment.getRecyclerView();


                    real = new ArrayList<>();


                    if(arrayList.isEmpty())
                    {
                    }
                    else
                    {
                        for (ProductClass al : arrayList) {

                            StringBuffer sb = new StringBuffer(al.getPname());

                            if(sb.length() > newText.length())
                            {
                                String s = sb.substring(0,newText.length());
                                if(s.equalsIgnoreCase(newText))
                                {
                                    real.add(al);
                                    mAdapter1 = new AdapterGridShopProductCard(fa, real);
                                    rv.setAdapter(mAdapter1);
                                }
                            }


                            // on item list clicked
                            mAdapter1.setOnItemClickListener(new AdapterGridShopProductCard.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, ProductClass obj, int position) {

                                    FragmentBottomSheetDialogFull fragment = new FragmentBottomSheetDialogFull();
                                    fragment.setPeople(obj);
                                    fragment.show(HomeFragment.getFManager(), fragment.getTag());

                                    //    Snackbar.make(root, "Item " + obj.title + " clicked", Snackbar.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onItemLongClick(View view, ProductClass obj, int pos) {

                                }

                            });

                           /* mAdapter1.setOnMoreButtonClickListener(new AdapterGridShopProductCard.OnMoreButtonClickListener() {
                                @Override
                                public void onItemClick(View view, ProductClass obj, MenuItem item) {
                                    //  Snackbar.make(root, obj.pname + " (" + item.getTitle() + ") clicked", Snackbar.LENGTH_SHORT).show();
                                }
                            });*/
                        }
                    }

                   /* if(real.isEmpty())
                    {
                        Toast.makeText(MainActivity.this,"not found",Toast.LENGTH_SHORT).show();
                    }*/
                }

                else if(view_pager.getCurrentItem() == 1)
                {

                    if(indicator==0)
                    {
                        if(arrayList.isEmpty())
                        {

                        }
                        else {
                            mAdapter1 = new AdapterGridShopProductCard(fa, arrayList);
                            rv.setAdapter(mAdapter1);


                            // on item list clicked
                            mAdapter1.setOnItemClickListener(new AdapterGridShopProductCard.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, ProductClass obj, int position) {

                                    FragmentBottomSheetDialogFull fragment = new FragmentBottomSheetDialogFull();
                                    fragment.setPeople(obj);
                                    fragment.show(HomeFragment.getFManager(), fragment.getTag());

                                    //    Snackbar.make(root, "Item " + obj.title + " clicked", Snackbar.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onItemLongClick(View view, ProductClass obj, int pos) {

                                }

                            });

                        /*    mAdapter1.setOnMoreButtonClickListener(new AdapterGridShopProductCard.OnMoreButtonClickListener() {
                                @Override
                                public void onItemClick(View view, ProductClass obj, MenuItem item) {
                                    //  Snackbar.make(root, obj.pname + " (" + item.getTitle() + ") clicked", Snackbar.LENGTH_SHORT).show();
                                }
                            });
*/
                            indicator++;
                        }
                    }

                    //searchView.set


                    arrayList1 = (ArrayList<FirebaseOrders>) OrderFragment.getFirebaseOrders();


                    mAdapter = OrderFragment.getmAdapter();


                    fa = OrderFragment.getFa();


                    rv = OrderFragment.getRecyclerView();

                    if(arrayList1.isEmpty())
                    {
                    }
                    else
                    {
                        freal.clear();
                        for (FirebaseOrders fo : arrayList1) {

                            StringBuffer sb = new StringBuffer(fo.getProdname());

                            if(sb.length() > newText.length())
                            {
                                String s = sb.substring(0,newText.length());
                                if(s.equalsIgnoreCase(newText))
                                {
                                    freal.add(fo);

                                }
                            }

                        }

                        mAdapter = new AdapterListBasic(fa, freal);
                        rv.setAdapter(mAdapter);

                        // on item list clicked
                        mAdapter.setOnItemClickListener(new AdapterListBasic.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, FirebaseOrders obj, int position) {
                                //   showCustomDialog(obj);

                                Intent intentinfo=new Intent(fa, OrderInfo.class);

                                intentinfo.putExtra("oid",freal.get(position).getOid());

                                startActivity(intentinfo);
                            }

                            @Override
                            public void onItemLongClick(View view, OrderInfo obj, int pos) {

                            }
                        });


                       /* if(freal.isEmpty())
                        {
                            Toast.makeText(MainActivity.this,"not found",Toast.LENGTH_SHORT).show();
                        }*/
                    }

                }



                return false;
            }
        });


        Tools.changeMenuIconColor(menu, Color.WHITE);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    String getAid()
    {
        Cursor c = null;
        String i = "";
        db = openOrCreateDatabase("UserData", MODE_PRIVATE, null);
        db.execSQL("create table if not exists userdata (aid text,val text);");
        c = db.rawQuery("select * from userdata;", null);
        c.moveToFirst();
        for (int ii = 0; c.moveToPosition(ii); ii++) {
            i = c.getString(0);
        }

        return i;

    }
}
