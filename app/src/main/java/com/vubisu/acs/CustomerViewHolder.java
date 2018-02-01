package com.vubisu.acs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vubisu.acs.databinding.ItemBinding;

/**
 * Created by harryhobbes on 20/1/18.
 */

public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ItemBinding itemBinding;
    int recordId;

    public CustomerViewHolder(View itemView) {
        super(itemView);
        itemBinding = DataBindingUtil.bind(itemView);
        itemView.setOnClickListener(this);
    }

    public void bindCursor(Cursor cursor) {
        recordId = cursor.getInt(cursor.getColumnIndexOrThrow(Student.KEY_ID));

        itemBinding.txtName.setText(cursor.getString(cursor.getColumnIndexOrThrow(Student.KEY_name)));
        itemBinding.txtPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow(Student.KEY_phone)));
    }

    @Override
    public void onClick(View view) {
        Context mContext = view.getContext();
        Intent mIntent = new Intent(mContext, ViewActivity.class);

        Bundle mBundle = new Bundle();
        mBundle.putInt("recordId", recordId);

        mIntent.putExtras(mBundle);
        mContext.startActivity(mIntent);
    }
}
