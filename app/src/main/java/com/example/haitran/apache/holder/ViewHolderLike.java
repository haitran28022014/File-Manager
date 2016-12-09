package com.example.haitran.apache.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haitran.apache.R;

/**
 * Created by Hai Tran on 10/2/2016.
 */

public class ViewHolderLike extends RecyclerView.ViewHolder {
    private ImageView imageItem;
    private TextView txtNameItem;

    public ViewHolderLike(View itemView) {
        super(itemView);
        imageItem=(ImageView) itemView.findViewById(R.id.img_item_navigation);
        txtNameItem=(TextView)itemView.findViewById(R.id.txt_name_navigation);
    }

    public ImageView getImageItem() {
        return imageItem;
    }

    public TextView getTxtNameItem() {
        return txtNameItem;
    }
}
