package com.example.vrindavan.activites;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.vrindavan.CheckoutAll.FirebaseOrders;
import com.example.vrindavan.R;
import com.example.vrindavan.ThreeTabs.HomeAll.ProductClass;
import com.example.vrindavan.utils.Tools;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderInfo extends AppCompatActivity {

    TextView productstatus,productname,productquantity,recyclar_sub_total,order_date_text,delivery_date_text,recyclar_delivery,orderaddress,holdername,holdermobileno;
    ImageView producticon;
    Button ordercancel;
    FirebaseOrders firebaseOrders;
    DatabaseReference databaseReference,dref;
    ArrayList<ProductClass> pc;
    ProductClass productClass;
    String status="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_info);
        final String orderid = getIntent().getStringExtra("oid");

        // assignment

        productname = findViewById(R.id.productname);
        productstatus = findViewById(R.id.productstatus);
        productquantity = findViewById(R.id.productquantity);
        recyclar_sub_total = findViewById(R.id.recycler_subTotal);
        order_date_text = findViewById(R.id.order_date_text);
        delivery_date_text = findViewById(R.id.delivery_date_text);
        recyclar_delivery = findViewById(R.id.recycler_delivery);
        orderaddress = findViewById(R.id.orderaddress);
        holdername = findViewById(R.id.holdername);
        holdermobileno = findViewById(R.id.holdermobileno);

        producticon = findViewById(R.id.producticon);

        ordercancel = findViewById(R.id.bt_decline);


        pc = new ArrayList<>();
        initToolbar();
        //firebase code

        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {

                    FirebaseOrders fo = ds.getValue(FirebaseOrders.class);

                    if(fo.getOid().equals(orderid))
                    {
                        firebaseOrders = fo;
                    }
                }
                productname.setText(firebaseOrders.getProdname());
                productstatus.setText(firebaseOrders.getProductstatus());
                productquantity.setText(firebaseOrders.getProductquan());
                recyclar_sub_total.setText(firebaseOrders.getProductid());
                order_date_text.setText(firebaseOrders.getOrderdate());
                delivery_date_text.setText(firebaseOrders.getDeliverydate());
                recyclar_delivery.setText(firebaseOrders.getUrgency());
                orderaddress.setText(firebaseOrders.getUaddress()+" , "+firebaseOrders.getUstate()+" , "+firebaseOrders.getPincode());
                holdername.setText(firebaseOrders.getUname());
                holdermobileno.setText(firebaseOrders.getMobileno1());


                Picasso.get().load(firebaseOrders.getProdsnap()).into(producticon);





             /*   if(firebaseOrders.getProductstatus().equals("departed"))
                {
                    ordercancel.setEnabled(true);
                }
                else
                {
                    ordercancel.setEnabled(false);
                }

*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference("Product");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pc.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    ProductClass p = ds.getValue(ProductClass.class);
                    pc.add(p);
                }
                for(int i=0;i<pc.size();i++)
                {
                    ProductClass p = pc.get(i);

                    if(p.getPid().equals(firebaseOrders.getProductid()))
                    {
                        productClass = p;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ordercancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                status = firebaseOrders.getProductstatus();

                if (status.equals("rejected"))
                {
                }
                else if (status.equals("accepted")) {
                    int a = Integer.parseInt(firebaseOrders.getProductquan());
                    int b = Integer.parseInt(productClass.getPquantity());
                    int c = 0;

                    c = a + b;

                    dref = FirebaseDatabase.getInstance().getReference("Product").child(firebaseOrders.getProductid());
                    dref.child("pquantity").setValue(String.valueOf(c));

                    dref = FirebaseDatabase.getInstance().getReference("Orders").child(firebaseOrders.getOid());
                    dref.child("productstatus").setValue("canceled by user");

                    Toast.makeText(OrderInfo.this,"you click canceled",Toast.LENGTH_SHORT).show();

                }
                else if(status.equals("work in progress"))
                {
                    dref = FirebaseDatabase.getInstance().getReference("Orders").child(firebaseOrders.getOid());
                    dref.child("productstatus").setValue("canceled by user");
                    Toast.makeText(OrderInfo.this,"you click canceled",Toast.LENGTH_SHORT).show();

                }
            }
        });



    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHistory);
        toolbar.setNavigationIcon(R.drawable.ic_close);
//        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_1000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_setting, menu);
        Tools.changeMenuIconColor(menu,getResources().getColor(R.color.white));
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

}
