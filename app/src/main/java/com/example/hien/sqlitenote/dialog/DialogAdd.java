package com.example.hien.sqlitenote.dialog;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hien.sqlitenote.MainActivity;
import com.example.hien.sqlitenote.R;
import com.example.hien.sqlitenote.commom.CONST;
import com.example.hien.sqlitenote.manager.ManagerNote;
import com.example.hien.sqlitenote.module.ItemNote;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hien on 8/8/17.
 */

public class DialogAdd extends Dialog implements View.OnClickListener {

    private EditText edtId, edtName;
    private ManagerNote mManagerNote;

    private List<Integer> listInteger;

    public DialogAdd(@NonNull Context context) {
        super(context);

        setContentView(R.layout.dialog_note_add);

        findViewByIds();

        initComponents();

        setEvents();
    }

    private void setEvents() {
        findViewById(R.id.btn_add_cancel).setOnClickListener(this);
        findViewById(R.id.btn_add_confirm).setOnClickListener(this);
    }

    private void initComponents() {

        mManagerNote = new ManagerNote(getContext());

    }

    private void findViewByIds() {

        edtId = findViewById(R.id.edt_add_id);
        edtName = findViewById(R.id.edt_add_name);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_cancel:
                dismiss();

                break;
            case R.id.btn_add_confirm:

                boolean check = addNote();

                if(check == true){

                    dismiss();

                }else {
                    show();
                }

                break;

            default: break;
        }
    }

    private boolean addNote() {

        mManagerNote.openDatabase();

        String id = edtId.getText().toString();
        String nameNote = edtName.getText().toString();

        if (id.equals("") || nameNote.equals("")) {
            showMess("Bạn cần nhập đầy đủ thông tin!!!");

        } else {

            int idNote = Integer.parseInt(id);

            ItemNote item = new ItemNote(idNote, nameNote);




            if (checkIdRepeat(idNote)) {
                showMess("Id da ton tai ban can nhap id moi!!!");

            }else {
                boolean insert = mManagerNote.insertNote(item);

                MainActivity.mNoteList.add(item);
                MainActivity.mAdapter.notifyDataSetChanged();


                if (insert) {
                    showMess("Thêm thành công!!!");
                    return true;
                }
            }
        }

        return false;

    }

    private boolean checkIdRepeat(int idAdd){
        listInteger = new ArrayList<>();

        String sql = CONST.showData;

        Cursor cursor = mManagerNote.showNote(sql);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){

            int id = cursor.getInt(0);

            listInteger.add(id);

            cursor.moveToNext();
        }

        for(int i = 0; i < listInteger.size(); i++){
            if(idAdd == listInteger.get(i)){
                return true;
            }
        }

        return false;
    }

    private void showMess(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
