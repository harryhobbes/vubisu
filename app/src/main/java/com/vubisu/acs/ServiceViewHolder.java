package com.vubisu.acs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vubisu.acs.databinding.ItemServiceBinding;

/**
 * Created by harryhobbes on 20/1/18.
 */

public class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ItemServiceBinding itemServiceBinding;
    int recordId;

    public ServiceViewHolder(View itemView) {
        super(itemView);
        itemServiceBinding = DataBindingUtil.bind(itemView);
        itemView.setOnClickListener(this);
    }

    public void bindCursor(Cursor cursor) {
        recordId = cursor.getInt(cursor.getColumnIndexOrThrow(Service.KEY_ID));

        itemServiceBinding.txtName.setText(cursor.getString(cursor.getColumnIndexOrThrow(Service.KEY_NAME)));
        itemServiceBinding.txtGroup.setText(cursor.getString(cursor.getColumnIndexOrThrow(Service.KEY_GROUP)));
        itemServiceBinding.txtPrice.setText(cursor.getString(cursor.getColumnIndexOrThrow(Service.KEY_PRICE)));
    }

    @Override
    public void onClick(View view) {
        Context mContext = view.getContext();
        Intent mIntent = new Intent(mContext, com.vubisu.acs.ServiceViewActivity.class);

        Bundle mBundle = new Bundle();
        mBundle.putInt("recordId", recordId);

        mIntent.putExtras(mBundle);
        mContext.startActivity(mIntent);
    }
}
