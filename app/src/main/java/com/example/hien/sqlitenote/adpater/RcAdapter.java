package com.example.hien.sqlitenote.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hien.sqlitenote.R;
import com.example.hien.sqlitenote.module.ItemNote;

/**
 * Created by hien on 8/8/17.
 */

public class RcAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private IAdapter mAdapter;
    private Context mContext;

    public RcAdapter(IAdapter mAdapter, Context mContext) {
        this.mAdapter = mAdapter;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_note, parent, false);
        ViewItemNote viewItemNote = new ViewItemNote(view, this);

        return viewItemNote;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewItemNote viewItemNote = (ViewItemNote) holder;

        ItemNote itemNote = mAdapter.getItemNote(position);

        viewItemNote.tvName.setText(itemNote.toString());

        viewItemNote.imgEdit.setImageResource(R.drawable.pencil);
        viewItemNote.imgDelete.setImageResource(R.drawable.delete);

    }

    @Override
    public int getItemCount() {
        return mAdapter.getSize();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_edit_note:

                IGetPosition iGetPosition = (IGetPosition) view.getTag();

                int position = iGetPosition.getPosition();

                mAdapter.clickImgEdit(position);

                break;

            case R.id.img_delete_note:
                iGetPosition = (IGetPosition) view.getTag();
                position = iGetPosition.getPosition();

                mAdapter.clickImgDelete(position);

                break;
            default:

                break;
        }
    }



    private class ViewItemNote extends RecyclerView.ViewHolder{
        private TextView tvName;
        private ImageView imgEdit;
        private ImageView imgDelete;

        public ViewItemNote(View itemView, View.OnClickListener onClickListener) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name_note);
            imgEdit = itemView.findViewById(R.id.img_edit_note);
            imgDelete = itemView.findViewById(R.id.img_delete_note);

            IGetPosition iGetPosition = new IGetPosition() {
                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }
            };

            itemView.setTag(iGetPosition);
            imgEdit.setTag(iGetPosition);
            imgDelete.setTag(iGetPosition);

            Animation animation =
                    AnimationUtils.loadAnimation(mContext, R.anim.translate_note);

            tvName.startAnimation(animation);
            imgDelete.startAnimation(animation);
            imgEdit.startAnimation(animation);

            itemView.setOnClickListener(onClickListener);
            imgEdit.setOnClickListener(onClickListener);
            imgDelete.setOnClickListener(onClickListener);



        }


    }
    public interface IAdapter{
        int getSize();

        ItemNote getItemNote(int position);

        void clickImgEdit(int position);

        void clickImgDelete(int position);

    }
}
