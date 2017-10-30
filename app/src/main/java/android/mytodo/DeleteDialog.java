package android.mytodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by TNGP17-001 on 24-Oct-17.
 */

public class DeleteDialog extends DialogFragment implements View.OnClickListener {

    private Button cancelButton;
    private Button okButton;
    private int position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_delete,container,false);
        initInstances(rootView);
        return rootView;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private void initInstances(View v) {
        cancelButton = v.findViewById(R.id.cancleButton);
        cancelButton.setOnClickListener(this);
        okButton = v.findViewById(R.id.okButton);
        okButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.cancleButton){
            dismiss();
        }
        else if(view.getId()==R.id.okButton){

            Bundle bundle = getArguments();  //to receive bundle
            int position = bundle.getInt("position");
            Intent intent = new Intent();
            intent.putExtra ("position", position);

            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
            dismiss();
        }
    }

    public static DeleteDialog newInstances() {
        DeleteDialog deleteDialog = new DeleteDialog();

        return deleteDialog;
    }
}
