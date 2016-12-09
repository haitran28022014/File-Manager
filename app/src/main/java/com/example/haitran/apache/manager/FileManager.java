package com.example.haitran.apache.manager;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.haitran.apache.model.FileItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Hai Tran on 10/2/2016.
 */

public class FileManager {
    public static String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    public ArrayList<FileItem> readFile(String path) {
        File file = new File(path);
        ArrayList<FileItem> arrFileItem = new ArrayList<>();
        if (file.isFile()) {
            arrFileItem.add(getFileItem(file));
        } else {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                arrFileItem.add(getFileItem(files[i]));
            }
        }
        return arrFileItem;
    }

    public ArrayList<FileItem> getSong(Context context) {
        Cursor cursor = context.getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                        MediaStore.Audio.Media.TITLE + " ASC");
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        ArrayList<FileItem> arrSongs = new ArrayList<>();
        int indexName = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int indexData=cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int indexDate = cursor.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED);
        cursor.moveToFirst();
        while (!cursor.isLast()) {
            String name = cursor.getString(indexName);
            String path=cursor.getString(indexData);
            String date = cursor.getString(indexDate);
            arrSongs.add(new FileItem(name,path, date, true, false));
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("Hai", arrSongs.size() + "");
        return arrSongs;
    }


    public FileItem getFileItem(File file) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        String name = file.getName();
        String pathFile = file.getPath();
        String date = sdf.format(file.lastModified());
        boolean isFile = file.isFile();
        boolean isDirectory = file.isDirectory();
        FileItem fileItem = new FileItem(name, pathFile, date, isFile, isDirectory);
        return fileItem;
    }

    public void deleteDirectory(String folder) {
        File file = new File(folder);
        ArrayList<File> arrFiles = new ArrayList<>();
        if (file.isDirectory()) {
            if (file.exists()) {
                File[] files = file.listFiles();
                if (files == null) {
                    return;
                }
                for (int i = 0; i < files.length; i++) {
                    deleteDirectory(files[i].getPath());
                    arrFiles.add(files[i]);
                }
            }
        } else {
            file.delete();
            return;
        }
        arrFiles.add(file);
        for (File file1 : arrFiles) {
            file1.delete();
        }
    }

    public void renameFile(String path, String nameChange) {
        File file = new File(path);
        String name = path.substring(0, path.lastIndexOf("/") + 1) + nameChange;
        file.renameTo(new File(name));
    }

    public void copyDirectory(File sourceLocation, File targetLocation) {
        try {
            if (sourceLocation.isDirectory()) {
                File fileTarget = new File(targetLocation.getPath() + "/" + sourceLocation.getName());
                if (!fileTarget.exists()) {
                    fileTarget.mkdirs();
                }

                String[] children = sourceLocation.list();
                for (int i = 0; i < children.length; i++) {
                    copyDirectory(new File(children[i]), fileTarget);
                }

            } else {
                String fileTarget = sourceLocation.getName();
                File file = new File(targetLocation.getPath() + "/" + fileTarget);
                if (!file.exists()) {
                    file.createNewFile();
                }
                InputStream in = new FileInputStream(sourceLocation);
                OutputStream out = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
