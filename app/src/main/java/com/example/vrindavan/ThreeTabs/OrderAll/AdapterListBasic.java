package com.example.vrindavan.ThreeTabs.OrderAll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vrindavan.CheckoutAll.FirebaseOrders;
import com.example.vrindavan.CheckoutAll.Orders;
import com.example.vrindavan.R;
import com.example.vrindavan.ThreeTabs.HomeAll.ProductClass;
import com.example.vrindavan.activites.OrderInfo;
import com.example.vrindavan.utils.Tools;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterListBasic extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<FirebaseOrders> items = new ArrayList<>();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, FirebaseOrders obj, int position);

        void onItemLongClick(View view, OrderInfo obj, int pos);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterListBasic(Context context, ArrayList<FirebaseOrders> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name,qty;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
            qty = (TextView) v.findViewById(R.id.quantity_amount);
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order,parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            FirebaseOrders p = items.get(position);


            view.qty.setText(p.getProductquan());

            view.name.setText(p.getProdname());

            Picasso.get().load(p.getProdsnap()).into(view.image);

            //Tools.displayImageRound(ctx, view.image, p.image);
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}