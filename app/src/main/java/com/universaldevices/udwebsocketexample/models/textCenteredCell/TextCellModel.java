/*
 * Copyright (c) 2021. Javier Refuerzo. All rights reserved.
 */

package com.universaldevices.udwebsocketexample.models.textCenteredCell;


public class TextCellModel {

    public String label;
    public boolean isEnabled = true;


    ////////////////////////////Creation Methods////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////


    public static TextCellModel getInstance(String object){
        TextCellModel viewModel = new TextCellModel();
        viewModel.label = object;
        viewModel.isEnabled = false;
        return viewModel;
    }


}
