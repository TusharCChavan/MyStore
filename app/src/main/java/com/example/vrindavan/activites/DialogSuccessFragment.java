package com.example.vrindavan.activites;
/*
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.example.vrindavan.CheckoutAll.FragmentShipping;
import com.example.vrindavan.R;
import com.example.vrindavan.ThreeTabs.HomeAll.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class DialogSuccessFragment extends DialogFragment {

    private View root_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.dialog_dark, container, false);
       // setCancelable(true);
        ((Button) root_view.findViewById(R.id.continue_to_shopping)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getActivity(), MainActivity.class));

            }
        });
        return root_view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       //setCancelable(true);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
      /*  AlertDialog.Builder builder=new AlertDialog.Builder(getActivity().getApplicationContext());
        builder.setTitle("Order");
        builder.setMessage("Do you want to continue shopping?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
                startActivity(new Intent(getActivity(),MainActivity.class));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getActivity(), FragmentShipping.class));

            }
        });
        builder.create().show();


        getActivity().finish();
        startActivity(new Intent(getActivity(),MainActivity.class));


    }
}*/


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.vrindavan.R;
import com.example.vrindavan.ThreeTabs.HomeAll.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class DialogSuccessFragment extends DialogFragment {

    private View root_view;
    String deliverydate,orderdate,time;

    TextView todate,totime,tadate;
    public DialogSuccessFragment(String deliverydate, String orderdate, String time) {
        this.deliverydate = deliverydate;
        this.orderdate = orderdate;
        this.time = time;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.dialog_dark, container, false);
        todate = root_view.findViewById(R.id.ordate);
        totime = root_view.findViewById(R.id.ortime);
        tadate = root_view.findViewById(R.id.ardate);

        todate.setText(orderdate);
        totime.setText(time);
        tadate.setText(deliverydate);

       /* ((FloatingActionButton) root_view.findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
*/

        ((Button) root_view.findViewById(R.id.continue_to_shopping)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MainActivity.class));
                // getActivity().finish();
            }
        });
        return root_view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}