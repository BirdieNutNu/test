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
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by TNGP17-001 on 24-Oct-17.
 */

public class EditDialog extends DialogFragment implements View.OnClickListener {

//    public static final String TOPIC_CHANGE = "TopicChange";
//    public static final String CONTETN_CHANGE = "ContetnChange";
//    public static final String VIEW_TOPIC = "viewTopic";
//    public static final String VIEW_CONTENT = "viewContent";
    private Button saveChangeButton;
    private Button cancelChangeButton;
    private EditText topicChange;
    private EditText contentChange;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_edit, container, false);
        initInstancec(rootView);
        return rootView;
    }


    public static EditDialog newInstances() {
        EditDialog editDialog = new EditDialog();
        return editDialog;
    }

    public void initInstancec(View v) {
        saveChangeButton = v.findViewById(R.id.button_saveChanged);
        saveChangeButton.setOnClickListener(this);
        cancelChangeButton = v.findViewById(R.id.button_cancelChanged);
        cancelChangeButton.setOnClickListener(this);
        topicChange = v.findViewById(R.id.inputTopicChange);
        contentChange = v.findViewById(R.id.inputContentChange);


        Bundle bundle = getArguments();
        String topic = bundle.getString("topic");
        String content = bundle.getString("content");


        topicChange.setText(topic);
        contentChange.setText(content);
        Log.d("topic","topic"+ topic + " content " + content);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_saveChanged) {
            Bundle bundle = getArguments();
            String topic = bundle.getString("topic");
            String content = bundle.getString("content");
            int position = bundle.getInt("position");

            Intent intent = getActivity().getIntent();
             intent.getIntExtra("position",position);
            Log.d("position","positionED" + position);
             intent.getStringExtra(topic);
            intent.getStringExtra(content);

//            Bundle bundle = getArguments();
//            int position = bundle.getInt("position");
//            String topic = bundle.getString("topic");
//            String content = bundle.getString("content");

            intent.putExtra("content", contentChange.getText().toString());
            intent.putExtra("topic", topicChange.getText().toString());
            intent.putExtra("position", position);
            Log.d("position","position"+ position + "topic"+ topic + "content"+ content);

            if (topicChange.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please Fill Up Topic", Toast.LENGTH_SHORT).show();
            } else if (contentChange.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please Fill Up Content", Toast.LENGTH_SHORT).show();
            } else {
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();
            }
        } else if (view.getId() == R.id.button_cancelChanged) {
            dismiss();
        }
    }
}
