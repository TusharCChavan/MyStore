package com.example.vrindavan.activites;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.vrindavan.CheckoutAll.FirebaseOrders;
import com.example.vrindavan.CheckoutAll.Orders;
import com.example.vrindavan.R;
import com.example.vrindavan.ThreeTabs.HomeAll.ProductClass;
import com.example.vrindavan.utils.Tools;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class NewCheckout extends AppCompatActivity {
    SQLiteDatabase db;
    ImageView prodImg;
    TextView prodName,prodQty,userNameC,userMobileC,userAddressC,userEmailC;
    EditText userNameI,userEmailI,userPhoneI,userAddressI,userCityI,zipCodeI,districtI;
    private String[] array_states;
   public Button stateI;
    MaterialRippleLayout submitOrder;

    String pid,pname,psnap,pquantity,pdeliverydate,purgency,ppredictedprice,premark,ptime,porderdate,pyourprice;
    String username,usermobile,useraddress,useremail,userdistrict,usercity,userzippin,userstate = "state";
    DatabaseReference dref;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_checkout);
        initToolbar();
        array_states = getResources().getStringArray(R.array.states);
        submitOrder=findViewById(R.id.submitOrder);
        prodImg=findViewById(R.id.productImg);
        prodName=findViewById(R.id.productName);
        prodQty=findViewById(R.id.productQuantity);

        userNameI=findViewById(R.id.userName);
        userEmailI=findViewById(R.id.userEmail);
        userPhoneI=findViewById(R.id.userPhone);
        userAddressI=findViewById(R.id.userAddress);
        userCityI=findViewById(R.id.userCity);
        zipCodeI=findViewById(R.id.zipCode);
        stateI=findViewById(R.id.btn_state);
        districtI=findViewById(R.id.userDistrict);

         userNameC=findViewById(R.id.username);
         userMobileC=findViewById(R.id.usermobileno);
         userAddressC=findViewById(R.id.useraddress);
         userEmailC=findViewById(R.id.useremail);

        pid = getIntent().getStringExtra("pid");
        pname = getIntent().getStringExtra("pname");
        psnap = getIntent().getStringExtra("psnap");
        pquantity = getIntent().getStringExtra("quant");
        pdeliverydate = getIntent().getStringExtra("deliverydate");
        purgency = getIntent().getStringExtra("urgency");
        ppredictedprice = getIntent().getStringExtra("predictedprice");
        premark = getIntent().getStringExtra("remark");
        ptime = getIntent().getStringExtra("time");
        porderdate = getIntent().getStringExtra("orderdate");
        pyourprice = getIntent().getStringExtra("pyourprice");


        Picasso.get().load(psnap).into(prodImg);
        prodName.setText(pname);
        prodQty.setText(pquantity);

        // text change listener

        userNameI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(String.valueOf(userNameI.getText()).isEmpty())
                {

                }
                else {
                    username = String.valueOf(userNameI.getText());
                    userNameC.setText(username);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        userEmailI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(String.valueOf(userEmailI.getText()).isEmpty())
                {

                }
                else
                {
                    useremail = String.valueOf(userEmailI.getText());
                    userEmailC.setText(useremail);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        userPhoneI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(String.valueOf(userPhoneI.getText()).isEmpty())
                {

                }
                else
                {
                    usermobile = String.valueOf(userPhoneI.getText());
                    userMobileC.setText(usermobile);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        userAddressI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (String.valueOf(userAddressI.getText()).isEmpty()) {

                } else {
                    useraddress = String.valueOf(userAddressI.getText());
                    userAddressC.setText(useraddress + " , "+usercity+" , "+userdistrict+" , "+userzippin);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        userCityI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(String.valueOf(userCityI.getText()).isEmpty())
                {

                }
                else {

                    usercity = String.valueOf(userCityI.getText());
                    userAddressC.setText(useraddress + " , "+usercity+" , "+userdistrict+" , "+userzippin);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        zipCodeI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(String.valueOf(zipCodeI.getText()).isEmpty())
                {

                }
                else {

                    userzippin = String.valueOf(zipCodeI.getText());
                    userAddressC.setText(useraddress + " , "+usercity+" , "+userdistrict+" , "+userzippin);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        districtI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(String.valueOf(districtI.getText()).isEmpty())
                {

                }
                else {
                    userdistrict = String.valueOf(districtI.getText());
                    userAddressC.setText(useraddress + " , "+usercity+" , "+userdistrict+" , "+userzippin);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // over of text change  listener






        stateI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStateChoiceDialog((Button) v);
            }
        });

        submitOrder.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(userNameI.getText().toString().equals("")) {
                   userNameI.setError("Enter Your First & Last Name");
               }
               else if(userPhoneI.getText().toString().equals(""))
               {
                   userPhoneI.setError("Enter Your Mobile No.");
               }
               else if(userEmailI.getText().toString().equals(""))
               {
                   userEmailI.setError("Enter Your Email");
               }
               else if(!userEmailI.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
               {
                   userEmailI.setError("Enter Valid Email");
               }
               else if(userAddressI.getText().toString().equals(""))
               {
                   userAddressI.setError("Enter landmark");
               }
               else if(userCityI.getText().toString().equals(""))
               {
                   userCityI.setError("Enter Your City");
               }
               else if(districtI.getText().toString().equals(""))
               {
                   districtI.setError("Enter Your District");
               }
               else if(zipCodeI.getText().toString().equals(""))
               {
                   zipCodeI.setError("Enter Zip Code");
               }
               else {

                   if(userstate.equals("state"))
                   {
                       Toast.makeText(NewCheckout.this,"Please select state",Toast.LENGTH_SHORT).show();
                   }
                   else
                   {
                       useraddress = useraddress + " , " + usercity + " , " + userdistrict;
                       dref = FirebaseDatabase.getInstance().getReference("Orders");
                       String oid = dref.push().getKey();
                       FirebaseOrders firebaseOrders = new FirebaseOrders(pid, getUid(), pquantity, useraddress, pdeliverydate, purgency, ppredictedprice, pyourprice, "work in progress", premark, usermobile, ptime, porderdate, username, useremail, userzippin, oid, userstate, pname, psnap);

                       dref.child(oid).setValue(firebaseOrders);
                       showDialogSuccess();
                       submitOrder.setVisibility(View.INVISIBLE);
                   }
               }
           }
       });

    }

    private void showDialogSuccess() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogSuccessFragment newFragment = new DialogSuccessFragment(pdeliverydate,porderdate,ptime);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    private void showStateChoiceDialog(final Button bt) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(NewCheckout.this);
        builder.setCancelable(true);
        builder.setSingleChoiceItems(array_states, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                bt.setTextColor(Color.BLACK);
                bt.setText(array_states[which]);
                stateI.setText(bt.getText().toString());
                userstate = bt.getText().toString();

            }
        });
        builder.show();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_1000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Tools.changeMenuIconColor(menu,getResources().getColor(R.color.grey_1000));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
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