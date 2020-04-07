package com.example.vrindavan.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vrindavan.R;
import com.example.vrindavan.utils.Tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    TextView logIn;
    ImageButton backLog;
    FirebaseAuth fauth;
    DatabaseReference dref;
    Button register;
    TextInputEditText h_name,h_mobile,h_email,h_pass,h_re_pass;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        logIn=findViewById(R.id.log_in);
        backLog=findViewById(R.id.backToLogin);
        register = findViewById(R.id.register);

        h_name = findViewById(R.id.holder_name);
        h_email = findViewById(R.id.holder_email);
        h_mobile = findViewById(R.id.holder_mobileno);
        h_pass = findViewById(R.id.holder_password);
        h_re_pass = findViewById(R.id.holder_re_password);


        // firebase intialization

        dref = FirebaseDatabase.getInstance().getReference("UserData");
        fauth = FirebaseAuth.getInstance();




        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(String.valueOf(h_email.getText()).isEmpty() || String.valueOf(h_pass.getText()).isEmpty() || String.valueOf(h_mobile.getText()).isEmpty() || String.valueOf(h_pass.getText()).isEmpty() || String.valueOf(h_name.getText()).isEmpty())
                {
                   // Snackbar snackbar=Snackbar.make(findViewById(R.id.logLinear),"Check All Fields",Snackbar.LENGTH_LONG);
                   // snackbar.show();
                    Toast.makeText(RegisterActivity.this,"You must complete all given fields",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(String.valueOf(h_pass.getText()).equals(String.valueOf(h_re_pass.getText())))
                    {
                        String em = h_email.getText().toString();
                        String pa = h_pass.getText().toString();

                        fauth.createUserWithEmailAndPassword(em, pa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Register Succesfuly", Toast.LENGTH_SHORT).show();

                                    String aid = fauth.getCurrentUser().getUid();

                                    String uid = dref.push().getKey();

                                    UserInfoData u = new UserInfoData(String.valueOf(h_name.getText()), String.valueOf(h_email.getText()), String.valueOf(h_pass.getText()), String.valueOf(h_mobile.getText()), uid, aid);

                                    dref.child(uid).setValue(u);

                                    finish();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                }
                                else {
                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this,"re enter password not match", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });



        backLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);

    }
}