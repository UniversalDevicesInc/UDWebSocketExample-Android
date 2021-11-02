/*
 * Copyright (c) 2021. Javier Refuerzo. All rights reserved.
 */

package com.universaldevices.udwebsocketexample.models.textCenteredCell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.universaldevices.udwebsocketexample.R;
import com.universaldevices.udwebsocketexample.objects.IndexPath;


public class TextCellHolder extends RecyclerView.ViewHolder {


    private final TextView centerText;



    public TextCellHolder(@NonNull View view){
        super(view);
        centerText = view.findViewById(R.id.text_view_name);
    }


    public static TextCellHolder getInstance(ViewGroup viewGroup){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cell_text_centered, viewGroup, false);
        return new TextCellHolder(view);
    }

    public void bindData(final TextCellModel viewModel, IndexPath indexPath, View.OnClickListener clickListener){
        //set the click listener
        itemView.setOnClickListener(clickListener);
        itemView.setTag(indexPath);
        itemView.setEnabled(viewModel.isEnabled);

        centerText.setText(viewModel.label);
    }

}
