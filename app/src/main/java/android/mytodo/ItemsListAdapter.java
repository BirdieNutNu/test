package android.mytodo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by TNGP17-001 on 24-Oct-17.
 */

public class ItemsListAdapter extends BaseAdapter {

    private ArrayList<Items> arrayList;
    private Context mContext;
    private Fragment fragment;
    private FragmentManager mFragmentManager;
    private TextView topic;
    private TextView content;

    public ItemsListAdapter(ArrayList<Items> arrayList, Context mContext, Fragment fragment, FragmentManager mFragmentManager) {
        this.arrayList = arrayList;
        this.mContext = mContext;
        this.fragment = fragment;
        this.mFragmentManager = mFragmentManager;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(R.layout.items, viewGroup, false);
        }
        //set topic and content
        topic = view.findViewById(R.id.textTopic);
        content = view.findViewById(R.id.textContent);
        topic.setText(arrayList.get(position).getTopic());
        content.setText(arrayList.get(position).getContent());

        final ImageButton deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = DeleteDialog.newInstances();

                Bundle bundle = new Bundle(); // to get position
                bundle.putInt("position", position); //send bundle to delete dialog
                dialogFragment.setArguments(bundle);
                dialogFragment.setTargetFragment(fragment, 3);
                dialogFragment.show(mFragmentManager, "Delete Dialog");
            }
        });

        ImageButton editButton = view.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = EditDialog.newInstances();

                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putString("topic", topic.getText().toString());
                bundle.putString("content", content.getText().toString());
                dialogFragment.setArguments(bundle);
                dialogFragment.setTargetFragment(fragment, 4);
                dialogFragment.show(mFragmentManager, "Edit Dialog");
            }
        });


        return view;
    }
}
