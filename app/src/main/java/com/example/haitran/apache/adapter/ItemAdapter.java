package com.example.haitran.apache.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haitran.apache.R;
import com.example.haitran.apache.model.FileItem;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Hai Tran on 10/2/2016.
 */

public class ItemAdapter extends BaseAdapter {
    private OnClickItemCreate onClickItemCreate;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<FileItem> arrFileItem;

    public ItemAdapter(Context context, ArrayList<FileItem> arrFileItem) {
        this.context = context;
        this.arrFileItem = arrFileItem;
    }

    @Override
    public int getCount() {
        return arrFileItem.size();
    }

    @Override
    public FileItem getItem(int i) {
        return arrFileItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        inflater = LayoutInflater.from(context);
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.item_file, viewGroup, false);
            viewHolder.imgFolder = (ImageView) view.findViewById(R.id.img_folder);
            viewHolder.txtNameFile = (TextView) view.findViewById(R.id.txt_name_folder);
            viewHolder.txtDate = (TextView) view.findViewById(R.id.txt_date);
            viewHolder.imgCreate = (ImageView) view.findViewById(R.id.img_create);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        FileItem fileItem = getItem(i);
        if (fileItem.getPathFile().endsWith(".mp3") || fileItem.getPathFile().endsWith(".MP3")) {
            viewHolder.imgFolder.setImageResource(R.drawable.bg_music);
        } else if (fileItem.getPathFile().endsWith(".jpg") || fileItem.getPathFile().endsWith(".png")) {
            Picasso.with(context).load(new File(fileItem.getPathFile())).resize(64, 64).into(viewHolder.imgFolder);
        } else if (fileItem.isDirectory()) {
            viewHolder.imgFolder.setImageResource(R.drawable.bg_folder);
        } else if (fileItem.isFile()) {
            viewHolder.imgFolder.setImageResource(R.drawable.bg_file);
        }
        viewHolder.txtNameFile.setText(fileItem.getName());
        viewHolder.txtDate.setText(fileItem.getDate());
        viewHolder.imgCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItemCreate.onClickCreate(i, viewHolder.imgCreate);

            }
        });
        return view;
    }

    private class ViewHolder {
        ImageView imgFolder;
        TextView txtNameFile;
        TextView txtDate;
        ImageView imgCreate;
    }

    public interface OnClickItemCreate {
        void onClickCreate(int position, ImageView imageView);
    }

    public void setOnClickItemCreate(OnClickItemCreate onClickItemCreate) {
        this.onClickItemCreate = onClickItemCreate;
    }
}
