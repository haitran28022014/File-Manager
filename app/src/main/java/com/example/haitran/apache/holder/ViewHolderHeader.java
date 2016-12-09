package com.example.haitran.apache.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.haitran.apache.R;

/**
 * Created by Hai Tran on 10/2/2016.
 */

public class ViewHolderHeader extends RecyclerView.ViewHolder {
    private ImageView imageHeader;

    public ViewHolderHeader(View itemView) {
        super(itemView);
        imageHeader=(ImageView)itemView.findViewById(R.id.img_header);
    }

    public ImageView getImageHeader() {
        return imageHeader;
    }
}
