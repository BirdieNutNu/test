package android.mytodo;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static final String TOPIC = "Topic";
    public static final String CONTENT = "Content";
    public static final String SAVETOPIC = "savetopic";
    public static final String SAVE_CONTENT = "saveContent";
    private String topic;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentContainer,new MainFragment())
                .commit();


    }
    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);

        Items saveItem = new Items(TOPIC,CONTENT);
        saveInstanceState.putString(SAVETOPIC,saveItem.getTopic());
        saveInstanceState.putString(SAVE_CONTENT,saveItem.getContent());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        topic = savedInstanceState.getString(SAVETOPIC);
        content = savedInstanceState.getString(SAVE_CONTENT);
    }
}
