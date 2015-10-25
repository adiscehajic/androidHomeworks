package ba.bitcamp.android.homeworkandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Adis Cehajic on 10/25/2015.
 */
public class EditActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editSurname;
    private Button saveButton;
    private Button backButton;
    private static Person editingPerson;
    private static List<Person> personList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_person);

        editName = (EditText) findViewById(R.id.editPersonName);
        editSurname = (EditText) findViewById(R.id.editPersonSurname);
        backButton = (Button) findViewById(R.id.backToMain);
        saveButton = (Button) findViewById(R.id.saveEditedPerson);

        editName.setText(editingPerson.getName());
        editSurname.setText(editingPerson.getSurname());

        final Editable personName = editName.getText();
        final Editable personSurname = editSurname.getText();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editingPerson.setName(personName.toString());
                editingPerson.setSurname(personSurname.toString());

                if (Person.isFieldFilled(personName, personSurname)) {
                    Toast.makeText(EditActivity.this, "Please input both fields.", Toast.LENGTH_SHORT).show();
                } else {
                    Person.updatePersonList(personList, editingPerson);

                    Toast.makeText(EditActivity.this, "Person edited", Toast.LENGTH_SHORT).show();

                    onBackPressed();
                }
            }
        });

    }

    public static Intent newIntent(Context packageContext, Person person, List<Person> persons) {
        editingPerson = person;
        personList = persons;
        Intent i = new Intent(packageContext, EditActivity.class);
        return i;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.updateUI();
    }
}
