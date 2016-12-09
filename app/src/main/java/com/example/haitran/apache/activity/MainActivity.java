package com.example.haitran.apache.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haitran.apache.R;
import com.example.haitran.apache.adapter.ItemAdapter;
import com.example.haitran.apache.adapter.NavigationAdapter;
import com.example.haitran.apache.dialog.CreateDialog;
import com.example.haitran.apache.dicoration.DividerItemDecoration;
import com.example.haitran.apache.manager.FileManager;
import com.example.haitran.apache.manager.NavigationManager;
import com.example.haitran.apache.model.FileItem;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, NavigationAdapter.OnClickNavigation,
        ItemAdapter.OnClickItemCreate, AdapterView.OnItemLongClickListener {

    private static final String TAG = "MainActivity";
    public static final String AUDIO_SDCARD = "Audio";
    public static final String SDCARD = "sdcard";
    private ItemAdapter itemAdapter;
    private FileManager fileManager;
    private ArrayList<FileItem> arrayList;
    private String pathItem;
    private NavigationAdapter navigationAdapter;
    private String pathCopy = "";
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton floatingFolder;
    private FloatingActionButton floatingFile;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.img_paste)
    ImageView imgPaste;

    @Bind(R.id.txt_name_file)
    TextView txtNameFile;

    @Bind(R.id.nv_view)
    NavigationView linearLayout;

    @Bind(R.id.img_menu)
    ImageView imgMenu;

    @Bind(R.id.img_more)
    ImageView imgMore;

    @Bind(R.id.img_home)
    ImageView imgHome;

    @Bind(R.id.list_view_item)
    ListView listViewItem;

    @Bind(R.id.list_navigation)
    RecyclerView listNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeComponent();
        initializeFileItem();
        initializeNavigation();
        readFile(FileManager.PATH);
    }

    private void initializeComponent() {
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.floating_action_menu);
        floatingFolder = (FloatingActionButton) findViewById(R.id.floating_folder);
        floatingFile = (FloatingActionButton) findViewById(R.id.floating_file);
        imgPaste.setVisibility(View.INVISIBLE);
        imgMenu.setOnClickListener(this);
        imgMore.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        imgPaste.setOnClickListener(this);
        floatingFolder.setOnClickListener(this);
        floatingFile.setOnClickListener(this);
    }

    private void initializeNavigation() {
        navigationAdapter = new NavigationAdapter(NavigationManager.getInstance().getArrayList(), this);
        listNavigation.setLayoutManager(new LinearLayoutManager(this));
        listNavigation.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        navigationAdapter.setOnClickNavigation(this);
        listNavigation.setAdapter(navigationAdapter);
    }

    private void initializeFileItem() {
        fileManager = new FileManager();
        arrayList = new ArrayList<>();
        itemAdapter = new ItemAdapter(this, arrayList);
        itemAdapter.setOnClickItemCreate(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.custom_list);
        listViewItem.startAnimation(animation);
        listViewItem.setAdapter(itemAdapter);
        listViewItem.setOnItemClickListener(this);
        listViewItem.setOnItemLongClickListener(this);
    }

    private void readFile(String path) {
        arrayList.clear();
        arrayList.addAll(fileManager.readFile(path));
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_menu:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.img_more:
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, imgMore);
                popupMenu.getMenuInflater().inflate(R.menu.select_more, popupMenu.getMenu());
                selectMore(popupMenu);
                popupMenu.show();
                break;
            case R.id.img_home:
                arrayList.clear();
                txtNameFile.setText(SDCARD);
                readFile(FileManager.PATH);
                break;
            case R.id.img_paste:
                imgPaste.setVisibility(View.INVISIBLE);
                fileManager.copyDirectory(new File(pathCopy), new File(pathItem));
                readFile(pathItem);
                break;
            case R.id.floating_folder:
                createFolderDialog();
                break;
            case R.id.floating_file:
                createFileDialog();
                break;
            default:
                break;
        }
    }

    private void selectMore(PopupMenu popupMenu) {
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (arrayList.get(i).isFile() == false) {
            pathItem = arrayList.get(i).getPathFile();
            txtNameFile.setText(pathItem.substring(pathItem.lastIndexOf("/") + 1));
            readFile(pathItem);
        } else {
            String fileType = arrayList.get(i).getPathFile();
            if ((fileType).endsWith(".mp3") || (fileType).endsWith(".MP3")) {
                File file = new File(fileType);
                Intent intent = new Intent();
                intent.setDataAndType(Uri.fromFile(file), "audio/*");
                startActivity(intent);
            } else if (fileType.endsWith(".jpg")||fileType.endsWith(".png")) {
                File file = new File(fileType);
                Intent intent = new Intent();
                intent.setDataAndType(Uri.fromFile(file), "image/*");
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return true;
    }


    @Override
    public void onBackPressed() {
        int beforePath = pathItem.lastIndexOf("/");
        String url = pathItem.substring(0, beforePath);
        String name = url.substring(url.lastIndexOf("/") + 1);
        txtNameFile.setText(name);
        if (name.equals("0")) {
            txtNameFile.setText(SDCARD);
        }
        if (pathItem.equals(FileManager.PATH)) {
            super.onBackPressed();

        } else {
            arrayList.clear();
            readFile(url);
            pathItem = url;
        }
    }

    @Override
    public void OnClickItemNavigation(View view) {
        int position = listNavigation.getChildLayoutPosition(view);
        String nameItem=NavigationManager.getInstance().getNameNavigation(position);
        String itemNavigation = FileManager.PATH + "/" + nameItem;
        pathItem = itemNavigation;
        txtNameFile.setText(pathItem.substring(pathItem.lastIndexOf("/") + 1));

        if (nameItem.equals(SDCARD)) {
            readFile(FileManager.PATH);
        }
        else if (nameItem.equals(AUDIO_SDCARD)){
            arrayList.clear();
            arrayList.addAll(fileManager.getSong(this));
            if (arrayList!=null){
                itemAdapter.notifyDataSetChanged();
            }
        }
        else {
            readFile(itemNavigation);
        }
        drawerLayout.closeDrawers();
    }


    @Override
    public void onClickCreate(final int position, ImageView imgCreate) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, imgCreate);
        popupMenu.getMenuInflater().inflate(R.menu.select_file, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.copy:
                        copyFolder(position);
                        break;
                    case R.id.delete:
                        deleteFolder(position);
                        break;
                    case R.id.rename:
                        renameFile(position);
                        break;
                }
                return false;
            }
        });
    }

    private void copyFolder(int position) {
        imgPaste.setVisibility(View.VISIBLE);
        pathCopy = arrayList.get(position).getPathFile();
        Log.d("abc", pathCopy);
    }

    private void createFileDialog() {
        final CreateDialog createDialog = new CreateDialog(MainActivity.this);
        createDialog.getTxtNew().setText("New File");
        createDialog.setOnReceiveDataListener(new CreateDialog.OnReceiveDataListener() {
            @Override
            public void onReceiveData(String nameFile) {
                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    return;
                } else {
                    txtNameFile.setText(pathItem.substring(pathItem.lastIndexOf("/") + 1));
                    try {
                        File file = new File(pathItem, nameFile);
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    readFile(pathItem);
                }
                floatingActionMenu.close(false);
                createDialog.dismiss();
            }

            @Override
            public void onClickCancel() {
                floatingActionMenu.close(false);
                createDialog.dismiss();
            }
        });
        createDialog.show();
    }

    public void renameFile(final int position) {
        final CreateDialog createDialog = new CreateDialog(MainActivity.this);
        createDialog.getTxtNew().setText("Rename");
        createDialog.setOnReceiveDataListener(new CreateDialog.OnReceiveDataListener() {
            @Override
            public void onReceiveData(String nameFolder) {
                String path=arrayList.get(position).getPathFile();
                fileManager.renameFile(path,nameFolder);
                readFile(pathItem);
                createDialog.dismiss();
            }

            @Override
            public void onClickCancel() {
                floatingActionMenu.close(false);
                createDialog.dismiss();
            }
        });
        createDialog.show();
    }

    private void createFolderDialog() {
        final CreateDialog createDialog = new CreateDialog(MainActivity.this);
        createDialog.setOnReceiveDataListener(new CreateDialog.OnReceiveDataListener() {
            @Override
            public void onReceiveData(String nameFolder) {
                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    return;
                } else {
                    String directory = pathItem + File.separator + nameFolder;
                    txtNameFile.setText(pathItem.substring(pathItem.lastIndexOf("/") + 1));
                    File folder = new File(directory);
                    folder.mkdirs();
                    readFile(pathItem);
                }
                floatingActionMenu.close(false);
                createDialog.dismiss();
            }

            @Override
            public void onClickCancel() {
                floatingActionMenu.close(false);
                createDialog.dismiss();
            }
        });
        createDialog.show();
    }


    private void deleteFolder(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("Delete Folder")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String path = arrayList.get(position).getPathFile();
                        fileManager.deleteDirectory(path);
                        Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        int after = path.lastIndexOf("/");
                        String link = path.substring(0, after);
                        pathItem = link;
                        readFile(link);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setMessage("Do you want to delete?")
                .create();
        alertDialog.show();

    }

}
