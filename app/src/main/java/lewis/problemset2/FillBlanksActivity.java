package lewis.problemset2;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/*
 * Prompts the user for words to fill the gaps in the story.
 */
public class FillBlanksActivity extends AppCompatActivity {

    // global variables
    EditText userInput;
    InputStream storyInputStream;
    int placeholderTotal;
    Story story;
    TextView progressTextView, wordtypeTextView;

    /*
     * Creates a new story and adjusts the text on screen according to that story.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_blanks);

        // assign variables to layout elements
        progressTextView = (TextView) findViewById(R.id.placeholdersFilledIn);
        wordtypeTextView = (TextView) findViewById(R.id.wordType);
        userInput = (EditText) findViewById(R.id.submittedWord);

        // get filename of story from main activity
        Intent oldIntent = getIntent();
        String storyName = oldIntent.getStringExtra("storyChoice");
        String storyFileName = storyName + ".txt";

        // open file; upon failure, display error message and return to main activity
        AssetManager assetManager = this.getAssets();
        try {
            storyInputStream = assetManager.open(storyFileName);
        }
        catch (IOException e) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            displayMessage(getString(R.string.fileNotFound));
            finish();
        }

        // initialize story and placeholder count
        story = new Story(storyInputStream);
        placeholderTotal = story.getPlaceholderCount();

        // display story if it is already filled in
        if (story.isFilledIn()) {
            displayStory();
        }

        // inform user about progress and desired input
        displayProgress();
        String wordtype = story.getNextPlaceholder() + ":";
        wordtypeTextView.setText(wordtype);
    }

    /*
     * Saves the story state in case user rotates the device.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("story", story);
    }

    /*
     * Retrieves story state if device has been rotated.
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        // retrieve story
        super.onRestoreInstanceState(savedInstanceState);
        story = (Story) savedInstanceState.getSerializable("story");

        // set text according to story state
        displayProgress();
        String placeHolder = story.getNextPlaceholder();
        wordtypeTextView.setText(placeHolder);
    }

    /*
     * If user clicks SEND button, fills in user input in story. If story is complete, goes to
     * next activity (displaying the story).
     */
    public void sendWord(View view) {

        // fill in word
        String userWord = userInput.getText().toString();
        if (!userWord.equals("")) {
            story.fillInPlaceholder(userWord);

            // upon completion of story, display story
            if (story.isFilledIn()) {
                displayStory();
            }

            // otherwise adjust text and ask for new input
            displayProgress();
            String placeholder = story.getNextPlaceholder();
            wordtypeTextView.setText(placeholder);
            userInput.setText("");
        }
        // if user typed nothing, inform user of this and ask again
        else {
            String placeholder = story.getNextPlaceholder();
            displayMessage("Please type a/an " + placeholder);
        }
    }

    /*
     * Displays how many words remain to be filled in.
     */
    private void displayProgress() {
        String progress = story.getPlaceholderRemainingCount() + "/" + placeholderTotal;
        progressTextView.setText(progress);
    }

    /*
     * Displays the story in the next activity.
     */
    private void displayStory() {
        Intent intent = new Intent(this, DisplayStoryActivity.class);

        // send story text to next activity
        String storyString = story.toString();
        intent.putExtra("story", storyString);
        startActivity(intent);
        finish();
    }

    /*
     * Displays the given message in a toast form
     */
    private void displayMessage(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
