package com.example.vrindavan.CheckoutAll;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.vrindavan.R;
import com.example.vrindavan.ThreeTabs.HomeAll.ProductClass;
import com.example.vrindavan.activites.DialogSuccessFragment;
import com.example.vrindavan.activites.MainActivity;
import com.example.vrindavan.utils.Tools;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShoppingCheckoutStep extends AppCompatActivity {

    DatabaseReference dref,drefor;
    Orders or= new Orders();
    int a = 0;
    SQLiteDatabase db;
    private enum State {
        SHIPPING,
        PAYMENT,
        CONFIRMATION
    }


    State[] array_state = new State[]{State.SHIPPING, State.PAYMENT, State.CONFIRMATION};

    private View line_first, line_second;
    private ImageView image_shipping, image_confirm;
    private TextView tv_shipping, tv_confirm;
    private int idx_state = 0;

    ProductClass productClass = new ProductClass();
    String pid,quantity,deliverydate,urgency,predictedprice,remark,time,orderdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_checkout_step);


        Orders.setUname("none");
        Orders.setUaddress("none");
        Orders.setMobileno1("none");
        Orders.setPincode("none");
        Orders.setEmail("none");


        dref = FirebaseDatabase.getInstance().getReference("Product");
        drefor = FirebaseDatabase.getInstance().getReference("Orders");

        pid = getIntent().getStringExtra("pid");
        quantity = getIntent().getStringExtra("quant");
        deliverydate = getIntent().getStringExtra("deliverydate");
        urgency = getIntent().getStringExtra("urgency");
        predictedprice = getIntent().getStringExtra("predictedprice");
        remark = getIntent().getStringExtra("remark");
        time = getIntent().getStringExtra("time");
        orderdate = getIntent().getStringExtra("orderdate");

        or.setProductid(pid);
        or.setProductquan(quantity);
        or.setUrgency(urgency);
        or.setPredictedprice(predictedprice);
        or.setRemark(remark);
        or.setDeliverydate(deliverydate);
        or.setOrderdate(orderdate);
        or.setTime(time);

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    ProductClass pc = ds.getValue(ProductClass.class);

                    if(pc.pid.equals(pid))
                    {
                        productClass = pc;
                    }
                }


                or.setProductClass(productClass);
                initToolbar();
                initComponent();

                displayFragment(State.SHIPPING);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void initComponent() {
        line_first = (View) findViewById(R.id.line_first);
        line_second = (View) findViewById(R.id.line_second);
        image_shipping = (ImageView) findViewById(R.id.image_shipping);
        image_confirm = (ImageView) findViewById(R.id.image_confirm);

        tv_shipping = (TextView) findViewById(R.id.tv_shipping);
       tv_confirm = (TextView) findViewById(R.id.tv_confirm);

       image_confirm.setColorFilter(getResources().getColor(R.color.grey_10), PorterDuff.Mode.SRC_ATOP);


        (findViewById(R.id.lyt_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idx_state == array_state.length - 1)
                    return;
                else

                a++;
                String email= Orders.getEmail().toString().trim();
                if(Orders.getUname().equals("none") || Orders.getUaddress().equals("none") || Orders.getMobileno1().equals("none") || Orders.getPincode().equals("none") || email.equals("none")||!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    Toast.makeText(ShoppingCheckoutStep.this,"Complete All Fields Properly",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    if(a>=2) {


                        String oid = drefor.push().getKey();
                        Orders.setOid(oid);
                        Orders.setUserid(getUid());
                        Orders.setYourprice("0");
                        Orders.setProductstatus("work in progress");

                        FirebaseOrders firebaseOrders = new FirebaseOrders(Orders.productid,Orders.userid,Orders.productquan,Orders.uaddress,Orders.deliverydate,Orders.urgency,Orders.predictedprice,Orders.yourprice,Orders.productstatus,Orders.remark,Orders.mobileno1,Orders.time,Orders.orderdate,Orders.uname, email,Orders.pincode,Orders.oid,Orders.ustate,Orders.productClass.pname,Orders.productClass.psnap);
                        drefor.child(oid).setValue(firebaseOrders);

                        Toast.makeText(ShoppingCheckoutStep.this, "Your Order Placed Successfully. Press Back to Exit", Toast.LENGTH_LONG).show();


                        //showDialogSuccess();
                    }

                    idx_state++;
                    displayFragment(State.CONFIRMATION);//array_state[idx_state]
                }
            }
        });


       (findViewById(R.id.lyt_previous)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idx_state < 1) return;
                idx_state--;

                    displayFragment(State.SHIPPING);

            }
        });
    }
    private void showDialogSuccess() {
       // FragmentManager fragmentManager = getSupportFragmentManager();
       /* DialogSuccessFragment newFragment = new DialogSuccessFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();*/
    }

        private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);
    }

    private void displayFragment(State state) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        refreshStepTitle();

        if (state.name().equalsIgnoreCase(State.SHIPPING.name())) {
            fragment = new FragmentShipping();
            TextView textView=findViewById(R.id.confirm_next);
            ImageView imageView=findViewById(R.id.confirm_image);
            textView.setText("Next");
            imageView.setImageResource(R.drawable.ic_arrow_forward_black_24dp);
            tv_shipping.setTextColor(getResources().getColor(R.color.grey_90));
            image_shipping.clearColorFilter();
        }else  {

            fragment = new FragmentConfirmation();
            TextView textView=findViewById(R.id.confirm_next);
            ImageView imageView=findViewById(R.id.confirm_image);
            textView.setText("SUBMIT ORDER");
            imageView.setImageResource(R.drawable.ic_check_black_24dp);

            line_first.setBackgroundColor(getResources().getColor(R.color.grey_1000));
            image_shipping.setColorFilter(getResources().getColor(R.color.grey_1000), PorterDuff.Mode.SRC_ATOP);
            line_second.setBackgroundColor(getResources().getColor(R.color.grey_1000));
            image_confirm.clearColorFilter();
            tv_confirm.setTextColor(getResources().getColor(R.color.grey_90));
        }

        if (fragment == null) return;
        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    private void refreshStepTitle() {
        tv_shipping.setTextColor(getResources().getColor(R.color.grey_20));
        tv_confirm.setTextColor(getResources().getColor(R.color.grey_20));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_setting, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.grey_60));
        return true;
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
       /* new AlertDialog.Builder(getApplicationContext())
                .setTitle("Thank You")
                .setMessage("You Order Was Placed Successfully.\n Press yes to continue shopping!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                           }
                }).create().show();*/
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));

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

    String getUid()
    {
        Cursor c = null;
        String i = "";
        db = openOrCreateDatabase("UserData", MODE_PRIVATE, null);
        db.execSQL("create table if not exists userdata (aid text,val text);");
        c = db.rawQuery("select * from userdata;", null);
        c.moveToFirst();
        for (int ii = 0; c.moveToPosition(ii); ii++) {
            i = c.getString(2);
        }


        return i;

    }

}

