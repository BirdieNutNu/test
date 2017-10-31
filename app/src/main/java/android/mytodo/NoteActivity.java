package android.mytodo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {
    public static final String TOPICVIEW = "topicview";
    public static final String CONTENTVIEW = "contentview";
    public static final String VIEW_TOPIC = "viewTopic";
    public static final String VIEW_CONTENT = "viewContent";
//    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Intent i = getIntent();
        String top = i.getStringExtra(TOPICVIEW);
        String con = i.getStringExtra(CONTENTVIEW);
        int position = i.getIntExtra("position", -123);

        NoteFragment noteFragment = new NoteFragment(); // create fragment
        Bundle bundle = new Bundle();
        bundle.putString("topic", top);
        bundle.putString("content", con);
        bundle.putInt("position", position);
        noteFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentContainer1, noteFragment)
                .commit();

        setUpView();
    }

    public void setUpView() { // set up toolbar press back
        setTitle("Note Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public boolean onSupportNavigateUp() { // press to go back
        finish();
        return false;
    }

}
