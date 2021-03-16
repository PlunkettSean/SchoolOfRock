package tk.ghostffilms.schoolofrock;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // Declare a DBHandeler
    DBHandler dbHandler;

    // Declare EditTexts
    EditText voterEditText;

    // declare spinner
    Spinner songsSpinner;

    // declare a String to store the selected Spinner Value
    String selectedSong;

    /**
     * This method initializes the Action Bar and View of the activity.
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // initialize the View and Action bar of the MainActivity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Initialize DBHandler
        dbHandler = new DBHandler(this, null);

        // Initialize EditTexts
        voterEditText = (EditText) findViewById(R.id.voterEditText);

        // Initialize Spinner
        songsSpinner = (Spinner) findViewById(R.id.songsSpinner);

        // Initialize ArrayAdapter with values in quantities String-Array
        // and stylize it with style defined by simple_spinner_item
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.songs, android.R.layout.simple_spinner_item);

        // further stylize the Array Adapter
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // Set the ArrayAdapter on Spinner
        songsSpinner.setAdapter(adapter);

        // Register an OnItemSelectListener to the Spinner
        songsSpinner.setOnItemSelectedListener(this);
    }

    /**
     * This method further initializes the Action Bar of the activity.
     * It gets the code (XML) in the menu resource file and incorporates it into the Action Bar.
     * @param menu menu resource file for the activity.
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This method gets called when a menu item in the overflow menu is selected and it
     * controls what happens when the menu item is selected.
     * @param item selected item in the overflow menu.
     * @return true if the menu item is selected, else false.
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String voter;
        // get the id of the menu item selected
        switch(item.getItemId()) {
            // get data in EditTexts and store it in strings
            case R.id.add_like :
                // get data in EditTexts and store it in strings
                voter = voterEditText.getText().toString();

                // trim Strings ans see if they're equal to empy strings
                if (voter.trim().equals("") || selectedSong.trim().equals("")) {
                    // Display a Toast "Please select a song and enter your name!" when any var is empty
                    Toast.makeText(this, "Please select a song and enter your name!",
                            Toast.LENGTH_LONG).show();
                } else {
                    // add item into the database
                    dbHandler.addSchoolOfRockVote(selectedSong, voter, 1);
                    // display Toast saying "Item Added!" when all var are non-Nul or empty
                    Toast.makeText(this, "Vote Cast!",
                            Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.add_dislike :
                // get data in EditTexts and store it in strings
                voter = voterEditText.getText().toString();

                // trim Strings ans see if they're equal to empty strings
                if (voter.trim().equals("") || selectedSong.trim().equals("")) {
                    // Display a Toast "Please select a song and enter your name!" when any var is empty
                    Toast.makeText(this, "Please select a song and enter your name!",
                            Toast.LENGTH_LONG).show();
                } else {
                    // add item into the database
                    dbHandler.addSchoolOfRockVote(selectedSong, voter, 0);
                    // display Toast saying "Item Added!" when all var are non-Nul or empty
                    Toast.makeText(this, "Vote Cast!",
                            Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.action_show_count :
                String toast = dbHandler.getSchoolOfRockVotes();

                // display Toast saying "Item Added!" when all var are non-Nul or empty
                Toast.makeText(this, toast,
                        Toast.LENGTH_LONG).show();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p>
     * Implementers can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedSong = parent.getItemAtPosition(position).toString();
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}