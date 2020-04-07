package com.example.vrindavan.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.vrindavan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPass extends AppCompatActivity {
    TextInputEditText editText;
    Button bt;
    FirebaseAuth fauth;
   ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
      back=findViewById(R.id.backToLogin);
      back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(getApplicationContext(),LoginActivity.class));
              finish();
          }
      });
      editText = findViewById(R.id.forgetpassfield);
      bt = findViewById(R.id.changepass);

      fauth = FirebaseAuth.getInstance();

      bt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              String em = editText.getText().toString();

              fauth.sendPasswordResetEmail(em).addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ForgetPass.this,"Reset password mail is sent to your Gmail",Toast.LENGTH_SHORT).show();
                        }
                  }
              });
          }
      });

    }
}
