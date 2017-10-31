package android.mytodo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by TNGP17-001 on 25-Oct-17.
 */

public class NoteFragment extends Fragment {

    private TextView topicView;
    private TextView contentView;
    public static final String VIEW_TOPIC = "viewTopic";
    public static final String VIEW_CONTENT = "viewContent";
    private ItemsListAdapter itemsListAdapter;
    private ArrayList<Items> itemsArrayList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.show_note, container, false);
        initInstances(rootView);
        setHasOptionsMenu(true); // show menu option
        return rootView;
    }

    public void initInstances(View v) {
        topicView = v.findViewById(R.id.viewTopic);
        contentView = v.findViewById(R.id.viewContent);
        itemsArrayList = new ArrayList<>();
        itemsListAdapter = new ItemsListAdapter(itemsArrayList, getContext(), this, getFragmentManager());

        Bundle bundle = getArguments();
        String topic = bundle.getString("topic");
        String content = bundle.getString("content");

        topicView.setText(topic);
        contentView.setText(content);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_note_option, menu);
        menu.findItem(R.id.action_edit).setVisible(true);
        menu.findItem(R.id.action_delete).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_delete) {
            delete();
            return true;
        } else if (item.getItemId() == R.id.action_edit) {
            editNote();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editNote() {
        DialogFragment dialogFragment = new EditDialog();
        Bundle arg = getArguments();
        int position1 = arg.getInt("position");
        String topic1 = arg.getString("topic");
        String content1 = arg.getString("content");

        Intent intent = new Intent();
        intent.putExtra("position",position1);
        intent.putExtra("content",content1);
        intent.putExtra("topic",topic1);

        dialogFragment.setArguments(arg);
        dialogFragment.setTargetFragment(this, 8);
        dialogFragment.setCancelable(true);
        dialogFragment.show(getFragmentManager(), "Edit Dialog");

    }


    private void delete() {
        DialogFragment dialogFragment = new DeleteDialog();
        Bundle arg = getArguments();
        arg.getInt("position");
//        Log.d("position","position of items to be delete:"+position);
        dialogFragment.setArguments(arg);
        dialogFragment.setTargetFragment(this, 7);
        dialogFragment.setCancelable(true);
        dialogFragment.show(getFragmentManager(), "Delete Dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        switch (requestCode) {
            case 8:
                if (resultCode == Activity.RESULT_OK) {

                    Intent intent = getActivity().getIntent();
                    int position1 = intent.getIntExtra("position", 0);
                    String topic1 = intent.getStringExtra("topic");
                    String content1 = intent.getStringExtra("content");

//                    Bundle arg = getArguments();
//                    int position = arg.getInt("position");
//                    String content = arg.getString("content");
//                    String topic = arg.getString("topic");


                    intent.putExtra("position", position1);
                    intent.putExtra("topic", topic1);
                    intent.putExtra("content", content1);
                    getActivity().setResult(Activity.RESULT_FIRST_USER, intent);
                    getActivity().finish();

                }
                break;
            case 7:
                if (resultCode == Activity.RESULT_OK) {
//                    Toast.makeText(getContext(), "Test", Toast.LENGTH_SHORT).show();
                    int position = data.getIntExtra("position", -2);

                    Intent intent = new Intent();
                    intent.putExtra("position1", position);
                    getActivity().setResult(Activity.RESULT_OK, intent);

                    getActivity().finish();


//                    Fragment fragment = new MainFragment();
//                    topicView.clearComposingText();
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                    transaction.replace(R.id.contentContainer1, this);
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                    transaction.commitAllowingStateLoss();

                }
                break;
        }
    }
}
