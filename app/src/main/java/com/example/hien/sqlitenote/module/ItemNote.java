package com.example.hien.sqlitenote.module;

import java.io.Serializable;

/**
 * Created by hien on 8/8/17.
 */

public class ItemNote implements Serializable {

    private int mId;

    private String mNameNote;

    public ItemNote(int mId, String mNameNote) {
        this.mId = mId;
        this.mNameNote = mNameNote;
    }

    public int getId() {
        return mId;
    }

    public String getNameNote() {
        return mNameNote;
    }

    @Override
    public String toString() {
        return mId + "_____" + mNameNote;
    }
}
