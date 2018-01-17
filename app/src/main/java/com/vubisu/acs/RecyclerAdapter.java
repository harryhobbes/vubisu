package com.vubisu.acs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vubisu.acs.databinding.ItemBinding;


/**
 * Created by harryhobbes on 5/10/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context mContext;
    Cursor mCursor;

    public RecyclerAdapter(Context context, Cursor cursor) {

        mContext = context;
        mCursor = cursor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemBinding itemBinding;

        public TextView txtViewTitle;
        //public ImageView imgViewIcon;
        public IMyViewHolderClicks mListener;
        public int mPosition;

        public String ClickId;

        public int ClickPosition;

        public ViewHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            itemBinding = DataBindingUtil.bind(itemView);

            mListener = listener;
            txtViewTitle = (TextView) itemView.findViewById(R.id.txtName);
            //imgViewIcon = (ImageView) itemView.findViewById(R.id.item_icon);
            //imgViewIcon.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void bindCursor(Cursor cursor) {
            itemBinding.txtName.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(Student.KEY_name)
            ));
            itemBinding.txtPhone.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(Student.KEY_phone)
            ));
        }

        @Override
        public void onClick(View v) {
            mPosition = getAdapterPosition();

            if (v instanceof ImageView){
                mListener.onTomato((ImageView)v);
            } else {
                mListener.onPotato(v, mPosition);
            }
        }

        public static interface IMyViewHolderClicks {
            public void onPotato(View caller, int position);
            public void onTomato(ImageView callerImage);
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;

        return mCursor.getCount();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.bindCursor(mCursor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view, new ViewHolder.IMyViewHolderClicks() {

            public void onPotato(View caller, int position) {
                //Toast.makeText(mContext,"The Item Clicked is: "+ (position),Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(mContext, com.vubisu.acs.ViewActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putInt("position", position);
                mCursor.moveToPosition(position);
                mBundle.putInt("recordId", mCursor.getInt(mCursor.getColumnIndex(Student.KEY_ID)));

                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            };
            public void onTomato(ImageView callerImage) {
                Log.d("VEGETABLES", "To-m8-tohs");
            }
        });

        return viewHolder;
    }

    public void updateCursor(Cursor cursor) {
        // Check for existing cursor and close
        if (mCursor != null) {
            mCursor.close();
        }

        // Update with new cursor
        if (cursor != null){
            mCursor = cursor;
        }
    }
}