/*
 * Copyright (c) 2021. Javier Refuerzo. All rights reserved.
 */

package com.universaldevices.udwebsocketexample.models.editText;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.universaldevices.udwebsocketexample.R;
import com.universaldevices.udwebsocketexample.objects.IndexPath;


public class EditTextCellHolder extends RecyclerView.ViewHolder {

    private final String LOG_TAG = EditTextCellHolder.class.getSimpleName();

    private final TextView label;
    private final EditText editText;
    private final ImageButton imageButton;

    private TextWatcher textWatcher;
    private final int inputTypePassword = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
    private final int inputTypeText = InputType.TYPE_CLASS_TEXT;
    private final int inputTypeNumber = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL;

    public interface TextChangeCompletionHandler {
        void onTextChanged(IndexPath indexPath, CharSequence charSequence);
    }

    public EditTextCellHolder(@NonNull View view){
        super(view);
        label = view.findViewById(R.id.text_view);
        editText = view.findViewById(R.id.edit_text);
        imageButton = view.findViewById(R.id.image_button);
    }

    public static EditTextCellHolder getInstance(ViewGroup viewGroup){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.edittext_cell, viewGroup, false);
        return new EditTextCellHolder(view);
    }


    public void bindData(final EditTextCellModel viewModel, IndexPath indexPath, TextChangeCompletionHandler completionHandler){
        //remove the text watcher as it may be linked to another model when this is recycled
        if (textWatcher != null){
            editText.removeTextChangedListener(textWatcher);
        }
        imageButton.setOnClickListener(null);

        label.setText(viewModel.label);
        editText.setHint(viewModel.editTextHint);
        editText.setText(viewModel.editTextValue);
        if (viewModel.isSecureEntry){
            editText.setInputType(inputTypePassword);
        }else {
            editText.setInputType(inputTypeText);
        }

        if (viewModel.isNumericalEntry){
            editText.setInputType(inputTypeNumber);
        }


        if (viewModel.isImageButtonVisible){
            imageButton.setVisibility(View.VISIBLE);
        }else {
            imageButton.setVisibility(View.GONE);
        }
        if (viewModel.drawableResourceId != 0){
            imageButton.setImageResource(viewModel.drawableResourceId);
        }

        //Set this last to prevent the watcher being called during bind
        imageButton.setOnClickListener(showHidePasswordClickListener);
        onTextChange(editText, indexPath, completionHandler);
    }

    public void onTextChange(EditText editText, IndexPath indexPath, TextChangeCompletionHandler completionHandler){


        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                completionHandler.onTextChanged(indexPath, charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        editText.addTextChangedListener(textWatcher);

    }

    View.OnClickListener showHidePasswordClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (editText.getInputType() == inputTypePassword ){
                editText.setInputType(inputTypeText);
                imageButton.setImageResource(R.drawable.show);
            }else {
                editText.setInputType(inputTypePassword);
                imageButton.setImageResource(R.drawable.hide);
            }
        }
    };


}
