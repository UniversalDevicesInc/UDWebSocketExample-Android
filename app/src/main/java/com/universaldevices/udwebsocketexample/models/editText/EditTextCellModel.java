/*
 * Copyright (c) 2021. Javier Refuerzo. All rights reserved.
 */

package com.universaldevices.udwebsocketexample.models.editText;


import android.content.Context;

import com.universaldevices.udwebsocketexample.R;


public class EditTextCellModel {

    public String label = null;
    public String editTextValue = null;
    public String editTextHint = null;
    public int drawableResourceId = 0;
    public boolean isSecureEntry = false;
    public boolean isNumericalEntry = false;
    public boolean isImageButtonVisible = false;


    public void setIsPasswordType(boolean isPasswordType){
        if (isPasswordType){
            isSecureEntry = true;
            drawableResourceId = R.drawable.hide;
        }else {
            isSecureEntry = false;
            drawableResourceId = 0;
        }
    }


    ////////////////////////////Get Instance Methods///////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////

    public static EditTextCellModel getInstance(String value, String label, String hint, boolean isPassword){
        EditTextCellModel viewModel = new EditTextCellModel();
        viewModel.label = label;
        viewModel.editTextHint = hint;
        viewModel.editTextValue = value;
        viewModel.isImageButtonVisible = isPassword;
        viewModel.isSecureEntry = isPassword;
        return viewModel;
    }



}
