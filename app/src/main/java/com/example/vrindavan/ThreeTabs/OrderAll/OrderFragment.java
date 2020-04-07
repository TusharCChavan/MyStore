package com.example.vrindavan.ThreeTabs.OrderAll;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vrindavan.CheckoutAll.FirebaseOrders;
import com.example.vrindavan.R;
import com.example.vrindavan.ThreeTabs.HomeAll.ProductClass;
import com.example.vrindavan.activites.MainActivity;
import com.example.vrindavan.activites.OrderInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.Context.MODE_PRIVATE;


public class OrderFragment extends Fragment {

    private View parent_view;
    public static AdapterListBasic mAdapter;
    DatabaseReference dref,drefp;
    static ArrayList<FirebaseOrders> firebaseOrders = new ArrayList<>();
    static RecyclerView recyclerView;
    SQLiteDatabase db;
    View r;
    People peopleclass;
    static FragmentActivity fa;
    static FragmentManager fm;

    public OrderFragment() {
    }

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = layoutInflater.inflate(R.layout.order_fragment, container, false);

        fa = getActivity();
        fm= getFragmentManager();

        r = root;

        parent_view = root.findViewById(android.R.id.content);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);



        //firebase code

        dref = FirebaseDatabase.getInstance().getReference("Orders");

        drefp = FirebaseDatabase.getInstance().getReference("Product");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firebaseOrders.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    FirebaseOrders fo = ds.getValue(FirebaseOrders.class);

                    if(fo.getUserid().equals(getUid()))
                    {
                          firebaseOrders.add(fo);
                    }

                }
                Collections.reverse(firebaseOrders);
                mAdapter = new AdapterListBasic(getActivity(), firebaseOrders);
                recyclerView.setAdapter(mAdapter);
                // on item list clicked
                mAdapter.setOnItemClickListener(new AdapterListBasic.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, FirebaseOrders obj, int position) {
                        //   showCustomDialog(obj);

                        Intent intentinfo=new Intent(getActivity(), OrderInfo.class);

                        intentinfo.putExtra("oid",firebaseOrders.get(position).getOid());

                        startActivity(intentinfo);
                    }

                    @Override
                    public void onItemLongClick(View view, OrderInfo obj, int pos) {

                    }


                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // firebase code end






     //   List<People> items = DataGenerator.getPeopleData(getActivity());
      //  Collections.shuffle(items);

        //set data and list adapter


        return root;

    }

   /* private void showCustomDialog(People p) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_dark);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((TextView) dialog.findViewById(R.id.title)).setText(p.name);
        ((CircularImageView) dialog.findViewById(R.id.image)).setImageResource(p.image);

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

       /* ((AppCompatButton) dialog.findViewById(R.id.bt_follow)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Follow Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
*/

    String getUid()
    {
        Cursor c = null;
        String i = "";
        db = r.getContext().openOrCreateDatabase("UserData", MODE_PRIVATE, null);
        db.execSQL("create table if not exists userdata (aid text,val text,uid text,uemai text,uname text,umobile text);");
        c = db.rawQuery("select * from userdata;", null);
        c.moveToFirst();
        for (int ii = 0; c.moveToPosition(ii); ii++) {
            i = c.getString(2);
        }


        return i;

    }


    public static String getType()
    {
        String s = "order";
        return s;
    }

    public static AdapterListBasic getmAdapter() {
        return mAdapter;
    }

    public static RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public static FragmentActivity getFa() {
        return fa;
    }

    public static FragmentManager getFm() {
        return fm;
    }

    public static ArrayList<FirebaseOrders> getFirebaseOrders() {
        return firebaseOrders;
    }
}

