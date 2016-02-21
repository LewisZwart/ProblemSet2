package lewis.problemset2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/*
 * Shows the story that has been created FillBlanksActivity, and the option to start a new game.
 */
public class DisplayStoryActivity extends AppCompatActivity {

    /*
     * Upon creation, displays the given story.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_story);

        // retrieve story
        Intent oldIntent = getIntent();
        String storyString = oldIntent.getStringExtra("story");

        // display story
        TextView storyTextView = (TextView) findViewById(R.id.storyTextView);
        storyTextView.setText(storyString);
    }

    /*
     * Goes to the main activity to start a new story.
     */
    public void startStory(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
