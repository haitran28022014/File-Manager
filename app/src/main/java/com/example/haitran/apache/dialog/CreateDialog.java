package com.example.haitran.apache.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.haitran.apache.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hai Tran on 10/4/2016.
 */

public class CreateDialog extends Dialog implements View.OnClickListener {
    private OnReceiveDataListener onReceiveDataListener;

    @Bind(R.id.txt_new_file_folder)
    TextView txtNew;

    @Bind(R.id.edt_create)
    EditText edtCreate;

    @Bind(R.id.btn_cancel)
    TextView txtCancel;

    @Bind(R.id.btn_create)
    TextView txtCreate;

    public CreateDialog(Context context) {
        super(context);
        setContentView(R.layout.create_file_folder);
        ButterKnife.bind(this);
        initializeListener();
    }

    private void initializeListener() {
        txtCreate.setOnClickListener(this);
        txtCancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_create:
                String folder=edtCreate.getText().toString();
                if (folder.equals("")){
                    return;
                }
                onReceiveDataListener.onReceiveData(folder);
                break;
            case R.id.btn_cancel:
                onReceiveDataListener.onClickCancel();
                break;
            default:
                break;
        }
    }

    public TextView getTxtNew() {
        return txtNew;
    }

    public interface OnReceiveDataListener{
        void onReceiveData(String nameFolder);
        void onClickCancel();
    }

    public void setOnReceiveDataListener(OnReceiveDataListener onReceiveDataListener) {
        this.onReceiveDataListener = onReceiveDataListener;
    }
}
