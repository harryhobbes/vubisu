package com.vubisu.acs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vubisu.acs.databinding.ItemAppointmentBinding;

/**
 * Created by harryhobbes on 20/1/18.
 */

public class AppointmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ItemAppointmentBinding itemAppointmentBinding;
    int recordId;

    public AppointmentViewHolder(View itemView) {
        super(itemView);
        itemAppointmentBinding = DataBindingUtil.bind(itemView);
        itemView.setOnClickListener(this);
    }

    public void bindCursor(Cursor cursor) {
        recordId = cursor.getInt(cursor.getColumnIndexOrThrow(Appointment.KEY_ID));

        itemAppointmentBinding.txtStart.setText(cursor.getString(cursor.getColumnIndexOrThrow(Appointment.KEY_start)));
        itemAppointmentBinding.txtNotes.setText(cursor.getString(cursor.getColumnIndexOrThrow(Appointment.KEY_notes)));
    }

    @Override
    public void onClick(View view) {
        /*
        Context mContext = view.getContext();
        Intent mIntent = new Intent(mContext, AppointmentViewActivity.class);

        Bundle mBundle = new Bundle();
        mBundle.putInt("recordId", recordId);

        mIntent.putExtras(mBundle);
        mContext.startActivity(mIntent);
        */
    }
}
