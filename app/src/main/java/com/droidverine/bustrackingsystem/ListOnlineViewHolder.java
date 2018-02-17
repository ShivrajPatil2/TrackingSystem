package com.droidverine.bustrackingsystem;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by DELL on 17-02-2018.
 */

public class ListOnlineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView textViewemail;
    ItemClickListener itemClickListener;
    public ListOnlineViewHolder(View itemView) {
        super(itemView);
        textViewemail=(TextView)itemView.findViewById(R.id.emailtxt);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition());
    }
}
