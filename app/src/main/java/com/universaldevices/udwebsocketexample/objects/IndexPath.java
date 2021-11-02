/*
 * Copyright (c) 2021. Javier Refuerzo. All rights reserved.
 */

package com.universaldevices.udwebsocketexample.objects;


import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.universaldevices.udwebsocketexample.models.editText.EditTextCellHolder;
import com.universaldevices.udwebsocketexample.models.textCenteredCell.TextCellHolder;

public class IndexPath {

    private final String LOG_TAG = IndexPath.class.getSimpleName();

    private final int position;
    public int section;
    public int row;
    public ViewTypeEnum type;

    public IndexPath(int position, int section, int row, ViewTypeEnum type){
        this.position = position;
        this.section = section;
        this.row = row;
        this.type = type;
    }

    public int getPosition() {
        return position;
    }


    public enum ViewTypeEnum {
        editTextCell(0),
        textCenteredCell(1);

        public final int rawValue;

        ViewTypeEnum(int rawValue) {
            this.rawValue = rawValue;
        }

        public static ViewTypeEnum getEnum(int rawValue) {
            for (ViewTypeEnum e : ViewTypeEnum.values()) {
                if (e.rawValue == rawValue) {
                    return e;
                }
            }
            throw new IllegalArgumentException("Could not match type enum");
        }

        public static RecyclerView.ViewHolder getViewHolder(int viewType, ViewGroup viewGroup) {
            ViewTypeEnum viewTypeEnum = ViewTypeEnum.getEnum(viewType);
            switch (viewTypeEnum) {
                case editTextCell:
                    return EditTextCellHolder.getInstance(viewGroup);
                case textCenteredCell:
                    return TextCellHolder.getInstance(viewGroup);
                default:
                    throw new IllegalArgumentException("Could not match viewTypeEnum");
            }
        }
    }



}
