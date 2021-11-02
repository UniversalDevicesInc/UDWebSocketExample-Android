package com.universaldevices.udwebsocketexample.ui;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.universaldevices.udwebsocketexample.models.editText.EditTextCellHolder;
import com.universaldevices.udwebsocketexample.models.editText.EditTextCellModel;
import com.universaldevices.udwebsocketexample.models.textCenteredCell.TextCellHolder;
import com.universaldevices.udwebsocketexample.models.textCenteredCell.TextCellModel;
import com.universaldevices.udwebsocketexample.objects.IndexPath;

import java.util.ArrayList;

public class MainActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final String LOG_TAG = MainActivityAdapter.class.getSimpleName();


    private ArrayList<IndexPath> indexPaths;
    private final MainActivity activity;

    public MainActivityAdapter(MainActivity activity){
        this.activity = activity;
    }


    public enum SectionEnum {

        IP_Address(0, "192.168.1.20", IndexPath.ViewTypeEnum.editTextCell),
        Port(1, "80", IndexPath.ViewTypeEnum.editTextCell),
        Username(2, "admin", IndexPath.ViewTypeEnum.editTextCell),
        Password(3, "admin", IndexPath.ViewTypeEnum.editTextCell),
        Messages(4, "", IndexPath.ViewTypeEnum.textCenteredCell);

        public final int section;
        public final String hint;
        public final IndexPath.ViewTypeEnum viewTypeEnum;

        SectionEnum(int section, String hint, IndexPath.ViewTypeEnum viewTypeEnum) {
            this.section = section;
            this.viewTypeEnum = viewTypeEnum;
            this.hint = hint;
        }

        public static SectionEnum getEnum(int sectionNumber){
            for(SectionEnum e : SectionEnum.values()){
                if (e.section == sectionNumber){
                    return e;
                }
            }
            throw new IllegalArgumentException("Could not match section");
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return IndexPath.ViewTypeEnum.getViewHolder(viewType, viewGroup);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final IndexPath indexPath = indexPaths.get(position);
        SectionEnum sectionEnum = SectionEnum.getEnum(indexPath.section);
        EditTextCellModel editTextCellModel;
        String value;
        String label;
        switch (sectionEnum){
            case IP_Address:
                value = activity.credential.ipAddress;
                label = sectionEnum.toString().replace("_", " ");
                editTextCellModel = EditTextCellModel.getInstance(value, label, sectionEnum.hint, false);
                ((EditTextCellHolder) holder).bindData(editTextCellModel, indexPath, textChangeCompletionHandler);
                return;
            case Port:
                value = activity.credential.port;
                label = sectionEnum.toString().replace("_", " ");
                editTextCellModel = EditTextCellModel.getInstance(value, label, sectionEnum.hint, false);
                ((EditTextCellHolder) holder).bindData(editTextCellModel, indexPath, textChangeCompletionHandler);
                return;
            case Username:
                value = activity.credential.userName;
                label = sectionEnum.toString().replace("_", " ");
                editTextCellModel = EditTextCellModel.getInstance(value, label, sectionEnum.hint, false);
                ((EditTextCellHolder) holder).bindData(editTextCellModel, indexPath, textChangeCompletionHandler);
                return;
            case Password:
                value = activity.credential.password;
                label = sectionEnum.toString().replace("_", " ");
                editTextCellModel = EditTextCellModel.getInstance(value, label, sectionEnum.hint, false);
                ((EditTextCellHolder) holder).bindData(editTextCellModel, indexPath, textChangeCompletionHandler);
                return;
            case Messages:
                String item = indexPath.row + ". " + activity.messages.get(indexPath.row);
                TextCellModel model = TextCellModel.getInstance(item);
                ((TextCellHolder) holder).bindData(model, indexPath, null);
                return;
            default:
                Log.v(LOG_TAG, "Could not match holder Type");
        }
    }



    @Override
    public int getItemCount() {
        indexPaths = new ArrayList<>();
        //Log.v(LOG_TAG, "getCount");
        int position = 0;
        for (SectionEnum sectionEnum : SectionEnum.values()){
            int row = 0;
            int numberOfRows = numberOfRowsInSection(sectionEnum);
            while (row != numberOfRows){
                indexPaths.add(new IndexPath(position, sectionEnum.section, row, sectionEnum.viewTypeEnum));
                row = row + 1;
                position = position + 1;
            }
        }
        return indexPaths.size();
    }


    private  int numberOfRowsInSection(SectionEnum section){
        //Log.v(LOG_TAG, "numberOfRowsInSection");
        switch (section){
            case IP_Address:
            case Port:
            case Username:
            case Password:
                if (!activity.isConnected){
                    return 1;
                }
                return 0;
            case Messages:
                if (activity.messages != null){
                    return activity.messages.size();
                }
                return 0;
            default:
                throw new IllegalArgumentException("could not match section");
        }
    }




    @Override
    public int getItemViewType(int position) {
        final IndexPath indexPath = indexPaths.get(position);
        //Log.v(LOG_TAG, "getItemViewType: " + indexPath.section);
        return indexPath.type.rawValue;
    }


    EditTextCellHolder.TextChangeCompletionHandler textChangeCompletionHandler = new EditTextCellHolder.TextChangeCompletionHandler() {
        @Override
        public void onTextChanged(IndexPath indexPath, CharSequence charSequence) {
            SectionEnum sectionEnum = SectionEnum.getEnum(indexPath.section);
            switch (sectionEnum){
                case IP_Address:
                    activity.credential.ipAddress = charSequence.toString();
                    break;
                case Port:
                    activity.credential.port = charSequence.toString();
                    break;
                case Username:
                    activity.credential.userName = charSequence.toString();
                    break;
                case Password:
                    activity.credential.password = charSequence.toString();
                    break;
                default:
                    Log.v(LOG_TAG, "Could not match section enum");
            }
        }
    };




}
