package android.mytodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by TNGP17-001 on 24-Oct-17.
 */

public class MainFragment extends Fragment implements View.OnClickListener {

    public static final String TOPICVIEW = "topicview";
    public static final String CONTENTVIEW = "contentview";
    public static final String SAVETOPIC = "savetopic";
    public static final String SAVE_CONTENT = "saveContent";

    private ImageButton addButton;
    private ListView listView;
    private ItemsListAdapter listAdapter;
    private ArrayList<Items> itemsArrayList;
    public static final String TOPIC = "Topic";
    public static final String CONTENT = "Content";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_note, container, false);
        initInstances(rootView);
        return rootView;
    }


    public void initInstances(View v) {
        addButton = v.findViewById(R.id.add_button);
        addButton.setOnClickListener(this);
        itemsArrayList = new ArrayList<>();
        listView = v.findViewById(R.id.list_item);
        itemsArrayList.add(new Items("Test 1", "write Something 1"));
        itemsArrayList.add(new Items("Test 2", "write Something 2"));
        itemsArrayList.add(new Items("Test 3", "write Something 3"));
        itemsArrayList.add(new Items("Test 4", "write Something 4"));
        itemsArrayList.add(new Items("Test 5", "write Something 5"));
        listAdapter = new ItemsListAdapter(itemsArrayList, getContext(), this, getFragmentManager());
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Items i = itemsArrayList.get(position);
                Intent intent = new Intent(getContext(), NoteActivity.class);
                intent.putExtra(TOPICVIEW, i.getTopic());
                intent.putExtra(CONTENTVIEW, i.getContent());
                intent.putExtra("position", position);
                Log.d("positionMF", "" + position);
                startActivityForResult(intent, 5);

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_button) {
            DialogFragment dialogFragment = MyToDoDialogFragment.newInstances();
            dialogFragment.setTargetFragment(this, 2);
            dialogFragment.show(getFragmentManager(), "My To Do Dialog");
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 5:
                if(resultCode == Activity.RESULT_OK) {
                    int position1 = data.getIntExtra("position1", -12);
                    itemsArrayList.remove(position1);
                    listAdapter.notifyDataSetChanged();
                    break;
                }
               else if (resultCode == Activity.RESULT_FIRST_USER) {
                    int position = data.getIntExtra("position1", -3);
                    String topic = data.getStringExtra("topic1");
                    String content = data.getStringExtra("content1");
                    Toast.makeText(getContext(), "position : " + position + "topic : " + topic + "content : " + content, Toast.LENGTH_LONG).show();
                    Items items = itemsArrayList.get(position);
                    items.setTopic(topic);
                    items.setContent(content);
                    break;
                }
            case 2:  //add
                if (resultCode == Activity.RESULT_OK) {
                    itemsArrayList.add(new Items(data.getStringExtra(TOPIC), data.getStringExtra(CONTENT)));
                    listAdapter.notifyDataSetChanged();
                    break;
                }
            case 3:   //delete
                if (resultCode == Activity.RESULT_OK) {
                    int position = data.getIntExtra("position", -2);
                    itemsArrayList.remove(position);
                    listAdapter.notifyDataSetChanged();
                    break;
                }
            case 4:   //edit
                if (resultCode == Activity.RESULT_OK) {

                    int position = data.getIntExtra("position", -13);
                    String topic = data.getStringExtra("topic"); //pass by ref as topic from editDialog
                    String content = data.getStringExtra("content"); // pass by ref as content

//                    Items items1 = new Items(topic,content);   //create new Items and set its position, replace it topic and content
//                    itemsArrayList.set(position,items1);
//                    items.setTopic("xxxxx");

                    Items items = itemsArrayList.get(position);
                    items.setTopic(topic);
                    items.setContent(content);

                    listAdapter.notifyDataSetChanged();
                    break;
                }
        }

    }
}
