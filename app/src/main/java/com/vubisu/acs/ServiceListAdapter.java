package com.vubisu.acs;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by harryhobbes on 22/1/18.
 */

public class ServiceListAdapter extends RecyclerView.Adapter {

    private Cursor mCursor;

    public ServiceListAdapter(Cursor cursor) {
        mCursor = cursor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        ((ServiceViewHolder) holder).bindCursor(mCursor);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_service;
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