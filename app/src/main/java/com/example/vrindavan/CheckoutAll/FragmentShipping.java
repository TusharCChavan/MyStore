package com.example.vrindavan.CheckoutAll;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.vrindavan.R;

public class FragmentShipping extends Fragment {
    private String[] array_states;
    EditText uname,uemail,umobileno,uaddress,upincode;

    Orders or = new Orders();

    public FragmentShipping() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shipping, container, false);

        uname = root.findViewById(R.id.uname);
        uemail = root.findViewById(R.id.email);
        umobileno = root.findViewById(R.id.mobileno);
        uaddress = root.findViewById(R.id.uaddress);
        upincode = root.findViewById(R.id.upincode);

        final String email=uemail.getText().toString().trim();
        //uname



            uname.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(String.valueOf(uname.getText()).isEmpty())
                    {
                     or.setUname("none");
                    }
                    else {
                        or.setUname(String.valueOf(uname.getText()));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        uemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(String.valueOf(uemail.getText()).isEmpty())
                {
                    or.setUname("none");
                }
                else
                {
                    or.setEmail(String.valueOf(uemail.getText()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        umobileno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(String.valueOf(umobileno.getText()).isEmpty())
                {
                    or.setUname("none");
                }
                else
                {
                    or.setMobileno1(String.valueOf(umobileno.getText()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        uaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (String.valueOf(uaddress.getText()).isEmpty()) {
                    or.setUname("none");
                } else {
                    or.setUaddress(String.valueOf(uaddress.getText()));

                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        upincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(String.valueOf(upincode.getText()).isEmpty())
                {
                    or.setUname("none");
                }
                else {
                   or.setPincode(String.valueOf(upincode.getText()));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });





        array_states = getResources().getStringArray(R.array.states);
        ((Button) root.findViewById(R.id.btn_state)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStateChoiceDialog((Button) v);
            }
        });
        return root;
    }
    private void showStateChoiceDialog(final Button bt) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setSingleChoiceItems(array_states, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                bt.setTextColor(Color.BLACK);
                bt.setText(array_states[which]);
                or.setUstate(bt.getText().toString());
            }
        });
        builder.show();
    }
}