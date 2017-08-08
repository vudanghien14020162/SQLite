package com.example.hien.sqlitenote;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hien.sqlitenote.adpater.RcAdapter;
import com.example.hien.sqlitenote.dialog.DialogAdd;
import com.example.hien.sqlitenote.manager.ManagerNote;
import com.example.hien.sqlitenote.module.ItemNote;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RcAdapter.IAdapter {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ManagerNote mManager;
    private RecyclerView rcNote;
    public static RcAdapter mAdapter;
    public static List<ItemNote> mNoteList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewByIds();
        initComponents();
        setEvents();
    }

    private void findViewByIds() {
        rcNote = (RecyclerView) findViewById(R.id.rc_note);
    }

    private void initComponents() {

        mNoteList = new ArrayList<>();

        //add databses
        mManager = new ManagerNote(this);

        mManager.openDatabase();

        mAdapter = new RcAdapter(this, this);

        LinearLayoutManager llManager = new LinearLayoutManager(this);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, llManager.getOrientation());
        Drawable drawable = getResources().getDrawable(R.drawable.shape_divider_item);
        itemDecoration.setDrawable(drawable);

        rcNote.addItemDecoration(itemDecoration);
        rcNote.setLayoutManager(llManager);
        rcNote.setAdapter(mAdapter);

        showDataInTable();


    }

    private void setEvents() {

    }


    //show data in table
    public void showDataInTable(){

        String sql = "select * from note";

        Cursor cursor = mManager.showNote(sql);

        mNoteList.clear();

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){

            int idNote = cursor.getInt(0);

            String nameNote = cursor.getString(1);


            mNoteList.add(new ItemNote(idNote, nameNote));


            Log.d(TAG, "Id Note: " + idNote + "-----------" + "Name Note: " + nameNote);

            cursor.moveToNext();
        }



    }


    @Override
    public int getSize() {
        return mNoteList.size();
    }

    @Override
    public ItemNote getItemNote(int position) {
        return mNoteList.get(position);
    }

    @Override
    public void clickImgEdit(int position) {



    }

    @Override
    public void clickImgDelete(final int position) {

        deleteNote(position);
    }

    //xoa note
    private void deleteNote(final int position) {

        Log.d(TAG, "----Delete!!");

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

        dialog.setTitle("Delete data!!!");
        dialog.setMessage("Remove Data From Table!");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ItemNote note = mNoteList.get(position);

                String sql = "delete from note where id = '" + note.getId() + "'";
                mManager.deleteDatabases(sql);

                mAdapter.notifyDataSetChanged();
                showDataInTable();

                showMsg("Xóa thành công!!!");
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        
        dialog.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:

                DialogAdd add = new DialogAdd(this);
                //hien thi
                add.show();
                Log.d(TAG, "Size: " + mNoteList.size() + "");

                showDataInTable();

                mAdapter.notifyDataSetChanged();

                break;

            default:
                break;
        }
        return true;
    }

    private void showMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

}
