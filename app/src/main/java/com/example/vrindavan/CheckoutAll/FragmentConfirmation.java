package com.example.vrindavan.CheckoutAll;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.vrindavan.R;
import com.example.vrindavan.ThreeTabs.HomeAll.ProductClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FragmentConfirmation extends Fragment {

    ProductClass productClass;
    private ImageView prod_img;
    TextView usname,usaddress,usmobileno,usquan,usprodname;

    public FragmentConfirmation() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_confirmation, container, false);

        prod_img=root.findViewById(R.id.prod_img);
        usname = root.findViewById(R.id.username);
        usaddress = root.findViewById(R.id.useraddress);
        usmobileno = root.findViewById(R.id.usermobileno);
        usquan = root.findViewById(R.id.productquant);
        usprodname = root.findViewById(R.id.productname);



        ProductClass pc = Orders.getProductClass();

        Picasso.get().load(pc.getPsnap()).into(prod_img);

        usname.setText(Orders.getUname());
        usaddress.setText(Orders.getUaddress()+" , "+Orders.getUstate()+" , "+Orders.getPincode());
        usmobileno.setText(Orders.getMobileno1());
        usquan.setText(""+Orders.getProductquan());
        usprodname.setText(pc.getPname());


        return root;
    }

}