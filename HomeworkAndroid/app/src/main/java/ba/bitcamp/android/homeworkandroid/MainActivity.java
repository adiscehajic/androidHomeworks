package ba.bitcamp.android.homeworkandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText surnameInput;
    private Button addPerson;
    private RadioButton sortName;
    private RadioButton sortSurname;
    private RadioButton sortDate;
    private static RecyclerView recyclerView;

    private static List<Person> persons = new ArrayList<>();

    private static PersonAdapter personAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = (EditText) findViewById(R.id.personName);
        surnameInput = (EditText) findViewById(R.id.personSurname);
        addPerson = (Button) findViewById(R.id.addPerson);
        sortName = (RadioButton) findViewById(R.id.sortByName);
        sortSurname = (RadioButton) findViewById(R.id.sortBySurname);
        sortDate = (RadioButton) findViewById(R.id.sortByDate);
        recyclerView = (RecyclerView) findViewById(R.id.personList);

        addPerson.setOnClickListener(new ButtonClick());
        sortName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person.sortByName(persons);
                updateUI();
            }
        });

        sortSurname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person.sortBySurname(persons);
                updateUI();
            }
        });

        sortDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person.sortByInputDate(persons);
                updateUI();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        personAdapter = new PersonAdapter(persons);
        recyclerView.setAdapter(personAdapter);

    }

    private class ButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Editable personName = nameInput.getText();
            Editable personSurname = surnameInput.getText();

            if (Person.isFieldFilled(personName, personSurname)) {
                Toast.makeText(MainActivity.this, "Please input both fields.", Toast.LENGTH_SHORT).show();
            } else {
                persons.add(0, new Person(personName, personSurname));

                Toast.makeText(MainActivity.this, "Person added", Toast.LENGTH_SHORT).show();

                personAdapter.notifyDataSetChanged();

                nameInput.setText("");
                surnameInput.setText("");
            }
        }
    }

    private class PersonHolder extends RecyclerView.ViewHolder {

        private TextView nameView;
        private TextView surnameView;
        private TextView dateView;
        private Button removePerson;
        private Button editPerson;

        public PersonHolder(View itemView) {
            super(itemView);

            nameView = (TextView) itemView.findViewById(R.id.personNameText);
            surnameView = (TextView) itemView.findViewById(R.id.personSurnameText);
            dateView = (TextView) itemView.findViewById(R.id.personInputDate);
            removePerson = (Button) itemView.findViewById(R.id.removePerson);
            editPerson = (Button) itemView.findViewById(R.id.editPerson);
        }
    }

    private class PersonAdapter extends RecyclerView.Adapter<PersonHolder> {

        private List<Person> personList;

        public PersonAdapter(List<Person> personList) {
            this.personList = personList;
        }

        @Override
        public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);

            View view = layoutInflater.inflate(R.layout.person_layout, parent, false);

            return new PersonHolder(view);
        }

        @Override
        public void onBindViewHolder(PersonHolder holder, final int position) {

            final Person person = personList.get(position);

            holder.nameView.setText(person.getName());
            holder.surnameView.setText(person.getSurname());
            holder.dateView.setText(person.getInputDate().toString());
            holder.removePerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder confirmRemove = new AlertDialog.Builder(MainActivity.this);
                    confirmRemove.setTitle("Are you sure?");

                    confirmRemove.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            personList.remove(position);
                            updateUI();
                            Toast.makeText(MainActivity.this, "Person removed", Toast.LENGTH_SHORT).show();
                        }
                    });

                    confirmRemove.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    confirmRemove.show();
                }
            });
            holder.editPerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = EditActivity.newIntent(MainActivity.this, person, personList);
                    startActivityForResult(i, 0);
                }
            });
        }

        @Override
        public int getItemCount() {
            return persons.size();
        }
    }

    public static void updateUI(){
        personAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(personAdapter);
    }
}
