package com.example.vrindavan.activites;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.vrindavan.CheckoutAll.FirebaseOrders;
import com.example.vrindavan.R;
import com.example.vrindavan.utils.Tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    TextView signUp;
    Button Login;
    FirebaseAuth fauth;
    TextInputEditText ho_email,ho_pass;
    SQLiteDatabase db;

    DatabaseReference dref;

    UserInfoData userInfoData;

    TextView forgetpass;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        ho_email = findViewById(R.id.hoemail);
        ho_pass = findViewById(R.id.hopass);
        forgetpass = findViewById(R.id.forgetpass);

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgetPass.class));
            }

        });

        dref = FirebaseDatabase.getInstance().getReference("UserData");

        if (checkOut().equals("1"))
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();

        }


        Login=findViewById(R.id.loginBtn);


        fauth = FirebaseAuth.getInstance();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String em,pa;
                em = ho_email.getText().toString();
                pa = ho_pass.getText().toString();

                if(em.isEmpty() || pa.isEmpty()) {
                    Snackbar snackbar=Snackbar.make(findViewById(R.id.logLinear),"Checkout All Fields",Snackbar.LENGTH_LONG);
                    View view=snackbar.getView();
                    view.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red_400));
                    snackbar.show();
                    //Toast.makeText(LoginActivity.this,"Please complete all field",Toast.LENGTH_SHORT).show();
                }
                else {
                    fauth.signInWithEmailAndPassword(em, pa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {


                            if (task.isSuccessful()) {
                                dref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            UserInfoData uid = ds.getValue(UserInfoData.class);

                                            if (uid.getUsaid().equals(fauth.getCurrentUser().getUid())) {
                                                userInfoData = uid;
                                            }

                                        }

                                        insertData(fauth.getCurrentUser().getUid(), "1", userInfoData.getUsuid(), userInfoData.getUsemail(), userInfoData.getUsname(), userInfoData.getUsmobile());

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            } else {
                              //  Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                Snackbar snackbar=Snackbar.make(findViewById(R.id.logLinear),"Incorrect Email Or Password",Snackbar.LENGTH_LONG);
                                View view=snackbar.getView();
                                view.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red_400));

                                snackbar.show();


                            }
                        }
                    });
                }
            }
        });
        signUp=findViewById(R.id.sign_up);
         signUp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
                 startActivity(new Intent(getApplicationContext(),RegisterActivity.class));

             }
         });
        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);


    }

    void insertData(String aid,String flage,String ui,String ue,String un,String um)
    {
        Cursor c = null;

        String val="1";

        db = openOrCreateDatabase("UserData", MODE_PRIVATE, null);

        String sql = "create table if not exists userdata (aid text,val text,uid text,uemai text,uname text,umobile text);";
        db.execSQL(sql);


        ContentValues values = new ContentValues();
        values.put("aid",aid);
        values.put("val","0");
        values.put("uid",ui);
        values.put("uemai",ue);
        values.put("uname",un);
        values.put("umobile",um);



        db.insert("userdata",null,values);

        db.execSQL("update userdata set val='" + val + "' where aid='" + aid + "';");


        // String q = "insert into usdata values('"+aid+"','1')";

      //  db.execSQL(q);

    }

    String checkOut()
    {
        Cursor c = null;
        String i = "";
        db = openOrCreateDatabase("UserData", MODE_PRIVATE, null);
        db.execSQL("create table if not exists userdata (aid text,val text,uid text,uemai text,uname text,umobile text);");
        c = db.rawQuery("select * from userdata;", null);
        c.moveToFirst();
        for (int ii = 0; c.moveToPosition(ii); ii++) {
            i = c.getString(1);
        }
        return i;
    }
}
