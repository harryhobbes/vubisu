package com.vubisu.acs;

import android.content.Context;
import android.provider.BaseColumns;
import android.support.design.widget.TextInputEditText;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by harryhobbes on 1/2/18.
 */

public class ServiceHelper {
    private Context context;
    private int id;
    private String switchText;
    private Switch toggle;
    private boolean isChecked = false;
    private String priceText;
    private TextInputEditText price;
    private String groupText;

    public ServiceHelper(Context context, int id, String switchText, String priceText, String groupText) {
        this.context = context;
        this.id = id;
        this.switchText = switchText;
        this.priceText = priceText;
        this.groupText = groupText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSwitchText() {
        return switchText;
    }

    public void setSwitchText(String switchText) {
        this.switchText = switchText;
    }

    public String getPriceText() {
        return priceText;
    }

    public void setPriceText(String priceText) {
        this.priceText = priceText;
    }

    public Switch getToggle() {
        return toggle;
    }

    public void setToggle(Switch toggle) {
        this.toggle = toggle;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public TextInputEditText getPrice() {
        return price;
    }

    public void setPrice(TextInputEditText price) {
        this.price = price;
    }

    public String getGroupText() {
        return groupText;
    }

    public void setGroupText(String groupText) {
        this.groupText = groupText;
    }

    public TextView getGroupLabelTemplate() {
        // Add next group heading
        TextView groupLabel = new TextView(context);
        groupLabel.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        groupLabel.setPadding(0, 10, 0, 0);
        groupLabel.setText(groupText);
        return groupLabel;
    }

    public LinearLayout getGroupLayoutTemplate() {
        // Add next group LinearLayout
        LinearLayout groupServices = new LinearLayout(context);
        groupServices.setOrientation(LinearLayout.VERTICAL);
        groupServices.setPadding(18, 0, 0, 0);
        groupServices.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return groupServices;
    }

    public LinearLayout getAppointmentEditServiceTemplate() {

        LinearLayout serviceLayout = new LinearLayout(context);
        serviceLayout.setOrientation(LinearLayout.HORIZONTAL);
        serviceLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        // Add the switch
        this.toggle = new Switch(context);
        this.toggle.setText(switchText);
        this.toggle.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                3.0f
        ));
        this.toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChecked(((Switch)view).isChecked());
            }
        });
        serviceLayout.addView(toggle);

        // Add the editable price field
        this.price = new TextInputEditText(context);
        this.price.setLayoutParams(new LinearLayout.LayoutParams(
                40,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f
        ));
        this.price.setText(priceText);
        this.price.setInputType(InputType.TYPE_CLASS_NUMBER);
        this.price.setGravity(Gravity.END);
        serviceLayout.addView(price);

        return serviceLayout;
    }

    public LinearLayout getAppointmentListServiceTemplate() {
        LinearLayout serviceLayout = new LinearLayout(context);
        serviceLayout.setOrientation(LinearLayout.HORIZONTAL);
        serviceLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        // Add the switch
        TextView serviceName = new TextView(context);
        serviceName.setText(switchText);
        serviceName.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                3.0f
        ));
        serviceLayout.addView(serviceName);

        // Add the editable price field
        TextView servicePrice = new TextView(context);
        servicePrice.setLayoutParams(new LinearLayout.LayoutParams(
                40,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f
        ));
        servicePrice.setText(priceText);
        servicePrice.setGravity(Gravity.END);
        serviceLayout.addView(servicePrice);

        return serviceLayout;
    }
}
