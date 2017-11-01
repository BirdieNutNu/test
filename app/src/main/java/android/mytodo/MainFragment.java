package android.mytodo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;

/**
 * Created by TNGP17-001 on 24-Oct-17.
 */

public class MainFragment extends Fragment implements View.OnClickListener {

    public static final String TOPICVIEW = "topicview";
    public static final String CONTENTVIEW = "contentview";

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
//                Log.d("positionMF", "" + position);
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

            case 2:  //add
                if (resultCode == Activity.RESULT_OK) {
                    itemsArrayList.add(new Items(data.getStringExtra(TOPIC), data.getStringExtra(CONTENT)));
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();

                    for (int j = 0; j < itemsArrayList.size(); j++) {
                        editor.putString("items" + j, itemsArrayList.get(j).getTopic());
                        editor.putString("content" + j, itemsArrayList.get(j).getTopic());
                    }
                    editor.putInt("size", itemsArrayList.size());
                    editor.commit();
                    listAdapter.notifyDataSetChanged();
                    break;
                }
            case 3:   //delete
                if (resultCode == Activity.RESULT_OK) {
                    int position = data.getIntExtra("position", -1);
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();

                    itemsArrayList.remove(position);
                    for (int j = 0; j < itemsArrayList.size(); j++) {
                        editor.putString("items" + j, itemsArrayList.get(j).getTopic());
                        editor.putString("content" + j, itemsArrayList.get(j).getTopic());
                    }
                    editor.putInt("size", itemsArrayList.size());
                    editor.commit();
                    listAdapter.notifyDataSetChanged();
                    break;
                }

            case 4:   //edit
                if (resultCode == Activity.RESULT_OK) {


                    int position = data.getIntExtra("position", -2);
                    String topic = data.getStringExtra("topic"); //pass by ref as topic from editDialog
                    String content = data.getStringExtra("content"); // pass by ref as content

//                    Items items1 = new Items(topic,content);   //create new Items and set its position, replace it topic and content
//                    itemsArrayList.set(position,items1);
//                    items.setTopic("xxxxx");
                    Log.d("position", "positionMF" + position);
                    Items items = itemsArrayList.get(position);
                    items.setTopic(topic);
                    items.setContent(content);

                    listAdapter.notifyDataSetChanged();
                    break;
                }
            case 5:
                if (resultCode == Activity.RESULT_OK) {
                    int position1 = data.getIntExtra("position1", -3);
                    itemsArrayList.remove(position1);
                    listAdapter.notifyDataSetChanged();
                    break;
                } else if (resultCode == Activity.RESULT_FIRST_USER) {

                    int position = data.getIntExtra("position", -4);
                    String topic = data.getStringExtra("topic");
                    String content = data.getStringExtra("content");
//                    Toast.makeText(getContext(), "position : " + position + "topic : " + topic + "content : " + content, Toast.LENGTH_LONG).show();
                    Items items = itemsArrayList.get(position);
                    items.setTopic(topic);
                    items.setContent(content);
                    break;
                }
        }


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("hi","hi test");
//        itemsArrayList.add();
        for (int i = 0; i < itemsArrayList.size(); i++) {
            editor.remove("items" + i);
            editor.putString("items" + i, itemsArrayList.get(i).getTopic());
            editor.putString("content" + i, itemsArrayList.get(i).getContent());
            listAdapter.notifyDataSetChanged();
        }
        editor.putInt("size", itemsArrayList.size());

        editor.commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        if (savedInstanceState != null) {
//            String hi = sharedPreferences.getString("hi", "0");

            ArrayList<String> itemsArrayList = new ArrayList<>();
            int size = sharedPreferences.getInt("size", 0);
            Log.d("size", " what to show " + size);
            for (int j = 0; j < size; j++) {
                if (sharedPreferences.getString("items" + j, "") == "") {
                    break;
                }
                itemsArrayList.add(sharedPreferences.getString("items" + j, ""));
                Log.d("size", "item" + sharedPreferences.getString("items" + j, ""));
                itemsArrayList.add(sharedPreferences.getString("content" + j, "0"));
                Log.d("size", "item" + sharedPreferences.getString("content" + j, ""));
                itemsArrayList.remove(sharedPreferences.getString("items" + j, ""));
                itemsArrayList.remove(sharedPreferences.getString("content" + j, ""));

                listAdapter.notifyDataSetChanged();
            }

        }
        listView = getActivity().findViewById(R.id.list_item);

    }
}


//       @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Intent intent = new Intent();
//        topic = intent.getStringExtra("topic");
//        content = intent.getStringExtra("content");
//        position = intent.getIntExtra("position", 99);
//
//        outState.putInt("position", position);
//         outState.putString("topic",topic);
//        outState.putString("content",content);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        topic = savedInstanceState.getString("topic");
//        content = savedInstanceState.getString("content");
//        position = savedInstanceState.getInt("position");


