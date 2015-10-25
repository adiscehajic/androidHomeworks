package ba.bitcamp.android.homeworkandroid;

import android.text.Editable;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Adis Cehajic on 10/25/2015.
 */
public class Person {

    private UUID id;
    private String name;
    private String surname;
    private Date inputDate;

    public Person(Editable name, Editable surname) {
        this.id = UUID.randomUUID();
        this.name = name.toString();
        this.surname = surname.toString();
        this.inputDate = new Date();
    }

    public static void updatePersonList(List<Person> persons, Person person) {
        for (Person p : persons) {
            if (p.getId().equals(person.getId())) {
                p.setName(person.getName());
                p.setSurname(person.getSurname());
            }
        }
    }

    public static boolean isFieldFilled(Editable name, Editable surname) {
        return name.toString().equals("") || surname.toString().equals("");
    }


    public static void sortByName(List<Person> persons) {
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getName().compareToIgnoreCase(p2.getName());
            }
        });
    }

    public static void sortBySurname(List<Person> persons) {
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getSurname().compareToIgnoreCase(p2.getSurname());
            }
        });
    }

    public static void sortByInputDate(List<Person> persons) {
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getInputDate().compareTo(p2.getInputDate());
            }
        });
    }

    public UUID getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }
}
