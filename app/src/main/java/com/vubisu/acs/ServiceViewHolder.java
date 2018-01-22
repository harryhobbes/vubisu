package com.vubisu.acs;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vubisu.acs.databinding.ItemServiceBinding;

/**
 * Created by harryhobbes on 20/1/18.
 */

public class ServiceViewHolder extends RecyclerView.ViewHolder{
    ItemServiceBinding itemServiceBinding;
    int recordId;

    public ServiceViewHolder(View itemView) {
        super(itemView);
        itemServiceBinding = DataBindingUtil.bind(itemView);
    }

    public void bindCursor(Cursor cursor) {
        recordId = cursor.getInt(cursor.getColumnIndexOrThrow(Service.KEY_ID));

        itemServiceBinding.txtName.setText(cursor.getString(cursor.getColumnIndexOrThrow(Service.KEY_NAME)));
        itemServiceBinding.txtGroup.setText(cursor.getString(cursor.getColumnIndexOrThrow(Service.KEY_GROUP)));
        itemServiceBinding.txtPrice.setText(cursor.getString(cursor.getColumnIndexOrThrow(Service.KEY_PRICE)));
    }
}
