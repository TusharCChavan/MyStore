package com.example.vrindavan.ThreeTabs.HomeAll;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.vrindavan.CheckoutAll.ShoppingCheckoutStep;
import com.example.vrindavan.activites.NewCheckout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vrindavan.R;
import com.example.vrindavan.utils.Tools;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FragmentBottomSheetDialogFull extends BottomSheetDialogFragment {

    private BottomSheetBehavior mBehavior;
    private AppBarLayout app_bar_layout;
    private LinearLayout lyt_profile;
    private Button proceed_order;
    private ProductClass people;
    public TextInputEditText dateEditText,quantityEditText,predicted,remark;
    TextView st,odt,ddt,rd,itemdis,name;

    RadioGroup radioGroup;
    RadioButton radioButton;


    public void setPeople(ProductClass people) {
        this.people = people;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog)
                super.onCreateDialog(savedInstanceState);
        final View view = View.inflate(getContext(), R.layout.fragment_bottom_sheet_dialog_full, null);


        radioGroup = view.findViewById(R.id.rg1);
        name=view.findViewById(R.id.name);
        st = view.findViewById(R.id.recycler_subTotal);
        odt = view.findViewById(R.id.order_date_text);
        ddt = view.findViewById(R.id.delivery_date_text);
        rd = view.findViewById(R.id.recycler_delivery);
        predicted = view.findViewById(R.id.predicted_edit);
        remark = view.findViewById(R.id.remark_edit);

        itemdis = view.findViewById(R.id.desc);


        rd.setText("Not Urgent");
        st.setText("0");


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.other_button1)
                {
                    rd.setText("Urgent");
                }
                else if(checkedId==R.id.other_button2)
                {
                    rd.setText("Not Urgent");
                }
            }
        });


        Calendar cl = Calendar.getInstance();
        String currentdate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(cl.getTime());

        odt.setText(currentdate);


        quantityEditText=view.findViewById(R.id.quantity_edit);
        proceed_order=view.findViewById(R.id.proceed_order);
        dateEditText=view.findViewById(R.id.delivery_date);



        //listener on quality field

        try{
            quantityEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(String.valueOf(quantityEditText.getText()).isEmpty())
                    {
                        st.setText("0");
                    }
                    else {
                        Long a = Long.parseLong(String.valueOf(quantityEditText.getText()));
                        long b = Long.parseLong(people.pquantity);
                        if (a > b) {
                            st.setText(String.valueOf(quantityEditText.getText()));
                        } else if (a < b) {
                            st.setText(String.valueOf(quantityEditText.getText()));
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        }
        catch (Exception ex)
        {

        }

        // listener on dilevery date
        dateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ddt.setText(String.valueOf(dateEditText.getText()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        dateEditText.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar cur_calender = Calendar.getInstance();
            DatePickerDialog datePicker = DatePickerDialog.newInstance(
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, monthOfYear);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            long date_ship_millis = calendar.getTimeInMillis();
                            dateEditText.setText(Tools.getFormattedDateSimple(date_ship_millis));
                        }
                    },
                    cur_calender.get(Calendar.YEAR),
                    cur_calender.get(Calendar.MONTH),
                    cur_calender.get(Calendar.DAY_OF_MONTH)
            );
            //set dark theme
            datePicker.setThemeDark(true);
            datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
            datePicker.setMinDate(cur_calender);
            datePicker.show(getActivity().getFragmentManager(),"DatepickerDialog");
        }
    });

        proceed_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(quantityEditText.getText().toString().trim().equalsIgnoreCase(""))
              {
                  quantityEditText.setError("Please enter a amount of item you required.");
              }
              if(dateEditText.getText().toString().trim().equalsIgnoreCase(""))
              {
                  dateEditText.setError("Please enter delivery date.");
              }
              else
              {
                  String premark;
                  if(String.valueOf(remark.getText()).isEmpty())
                  {
                      premark = "remark not present";
                  }
                  else
                  {
                      premark = String.valueOf(remark.getText());
                  }

                  Calendar calendar = Calendar.getInstance();
                  SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                  String time = format.format(calendar.getTime());

                  Intent intent = new Intent(getActivity(), NewCheckout.class);
                  intent.putExtra("pid",people.pid);
                  intent.putExtra("pname",String.valueOf(name.getText()));
                  intent.putExtra("psnap",people.getPsnap());
                  intent.putExtra("quant",String.valueOf(st.getText()));
                  intent.putExtra("deliverydate",String.valueOf(ddt.getText()));
                  intent.putExtra("urgency",String.valueOf(rd.getText()));
                  intent.putExtra("predictedprice",String.valueOf(predicted.getText()));
                  intent.putExtra("remark",premark);
                  intent.putExtra("time",time);
                  intent.putExtra("orderdate",String.valueOf(odt.getText()));
                  intent.putExtra("pyourprice",String.valueOf(people.getPrice()));
                  startActivity(intent);
              }
            }
        });



        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        app_bar_layout = (AppBarLayout) view.findViewById(R.id.app_bar_layout);
        lyt_profile = (LinearLayout) view.findViewById(R.id.lyt_profile);

        // set data to view
        ImageView im = (ImageView) view.findViewById(R.id.image);
        itemdis.setText(people.getPdiscription());
        Picasso.get().load(people.psnap).into(im);
        //Tools.displayImageRound(getActivity(), (ImageView) view.findViewById(R.id.image), people.image);
        ((TextView) view.findViewById(R.id.name)).setText(people.pname);
        ((TextView) view.findViewById(R.id.name_toolbar)).setText(people.pname);
        ((View) view.findViewById(R.id.lyt_spacer)).setMinimumHeight(Tools.getScreenHeight() / 2);

        hideView(app_bar_layout);

        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                    showView(app_bar_layout, getActionBarSize());
                    hideView(lyt_profile);
                }
                if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    hideView(app_bar_layout);
                    showView(lyt_profile, getActionBarSize());
                }

                if (BottomSheetBehavior.STATE_HIDDEN == newState) {
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        ((ImageButton) view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void hideView(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = 0;
        view.setLayoutParams(params);
    }

    private void showView(View view, int size) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = size;
        view.setLayoutParams(params);
    }

    private int getActionBarSize() {
        final TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int size = (int) styledAttributes.getDimension(0, 0);
        return size;
    }

    public void onRadioClicked(View view) {
        CharSequence charSequence;
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.other_button1:
                if (isChecked) {
                    charSequence = "YES";
                    break;
                }
            case R.id.other_button2:
                if (isChecked) {
                    charSequence = "No";
                    break;
                }
                return;
            default:
                return;
        }
        Toast.makeText(getActivity(), charSequence, Toast.LENGTH_LONG).show();
    }
}
