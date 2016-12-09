package com.example.haitran.apache.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haitran.apache.R;
import com.example.haitran.apache.holder.ViewHolderHeader;
import com.example.haitran.apache.holder.ViewHolderLike;
import com.example.haitran.apache.model.HeaderItem;
import com.example.haitran.apache.model.LikeItem;

import java.util.ArrayList;

/**
 * Created by Hai Tran on 10/2/2016.
 */

public class NavigationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnClickNavigation onClickNavigation;
    private static final int HEADER_VIEW = 0;
    private static final int LIKE_VIEW = 1;
    private ArrayList<Object> arrItems;
    private Context context;

    public NavigationAdapter(ArrayList<Object> arrItems, Context context) {
        this.arrItems = arrItems;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case HEADER_VIEW:
                View viewHeader = inflater.inflate(R.layout.item_header, parent, false);
                viewHolder = new ViewHolderHeader(viewHeader);
                break;
            case LIKE_VIEW:
                final View viewLike = inflater.inflate(R.layout.item_navigation, parent, false);
                viewHolder = new ViewHolderLike(viewLike);
                viewLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickNavigation.OnClickItemNavigation(viewLike);
                    }
                });
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (arrItems.get(position) instanceof HeaderItem) {
            return HEADER_VIEW;
        } else if (arrItems.get(position) instanceof LikeItem) {
            return LIKE_VIEW;
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case HEADER_VIEW:
                HeaderItem headerItem = (HeaderItem) arrItems.get(position);
                ViewHolderHeader holderHeader = (ViewHolderHeader) holder;
                holderHeader.getImageHeader().setImageResource(getImageId(headerItem.getImageNameHeader()));
                break;
            case LIKE_VIEW:
                LikeItem likeItem = (LikeItem) arrItems.get(position);
                ViewHolderLike holderLike = (ViewHolderLike) holder;
                holderLike.getImageItem().setImageResource(getImageId(likeItem.getImageItemLike()));
                holderLike.getTxtNameItem().setText(likeItem.getItemName());
                break;
        }
    }

    public int getImageId(String nameImage) {
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(nameImage, "drawable", pkgName);
        return resID;
    }

    @Override
    public int getItemCount() {
        return arrItems.size();
    }

    public interface OnClickNavigation {
        void OnClickItemNavigation(View view);
    }

    public void setOnClickNavigation(OnClickNavigation onClickNavigation) {
        this.onClickNavigation = onClickNavigation;
    }
}
