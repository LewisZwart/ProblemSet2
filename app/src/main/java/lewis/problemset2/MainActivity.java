package lewis.problemset2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/*
 * Name: Lewis Zwart
 * Student number: 10251057
 *
 * Implements the main screen of the Madlibs game. Here the user can choose the theme of the story.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    String currentSelection;

    /*
     * Creates a spinner showing the different themes.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.storySpinner);
        spinner.setOnItemSelectedListener(this);

        // create ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.stories_array, android.R.layout.simple_spinner_item);

        // specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        // apply adapter to spinner
        spinner.setAdapter(adapter);
    }

    /*
     * Sets the theme according to the user's choice.
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currentSelection = (String) parent.getItemAtPosition(position);
    }

    /*
     * Takes a Simple theme if user chooses nothing.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        currentSelection = "Simple";
    }

    /*
    * When user decides to start a game, goes to next activity (filling the blanks).
    */
    public void startStory(View view) {
        Intent intent = new Intent(this, FillBlanksActivity.class);
        intent.putExtra("storyChoice", currentSelection);
        startActivity(intent);
        finish();
    }
}
