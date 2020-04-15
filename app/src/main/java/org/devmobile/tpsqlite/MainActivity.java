package org.devmobile.tpsqlite;

import android.app.ListActivity;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends ListActivity implements View.OnClickListener {
     Button btnAdd;
     Button btnDel;
     Button btnRes;
     EditText nom;
     EditText tel;

    private ArrayAdapter<Student> myAdapder;
    private StudentDataSource studentDataSource;
    private Student student = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //// initialize component
        nom = findViewById(R.id.nom_field);
        tel = findViewById(R.id.tel_field);
        btnAdd = findViewById(R.id.btnAdd);
        btnDel = findViewById(R.id.btnDel);
        btnRes = findViewById(R.id.btnRst);
        // events
        btnAdd.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnRes.setOnClickListener(this);

        studentDataSource = new StudentDataSource(this);
        try {
            studentDataSource.open();
            List<Student> studentList = studentDataSource.getAllAdherants();
            myAdapder = new ArrayAdapter<Student>(this, R.layout.row_layout, R.id.nomView, studentList);
            setListAdapter(myAdapder);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAdd) {
            if (nom.getText().length() > 0) {
                if (student != null) {
                    student.setNom(nom.getText().toString());
                    student.setTel(tel.getText().toString());
                    studentDataSource.updateAdherant(student);
                } else {
                    myAdapder.add(studentDataSource.creerAdherant(nom.getText().toString(),tel.getText().toString()));
                    nom.setText("");
                    tel.setText("");
                }
                myAdapder.notifyDataSetChanged();
            }
        }

        if (v.getId() == R.id.btnDel) {
            if (student != null) {
                nom.setText("");
                tel.setText("");
                studentDataSource.deleteAdherant(student);
                myAdapder.remove(student);
                myAdapder.notifyDataSetChanged();
                student = null;
            }


        }

        if (v.getId() == R.id.btnRst){
            if (student != null) {
                nom.setText("");
                tel.setText("");
                student = null;
            }
        }
    }
    @Override
    protected void onListItemClick(ListView l, View view, int position, long id) {
        super.onListItemClick(l, view, position, id);
        student = (Student) getListView().getItemAtPosition(position);
        nom.setText(student.getNom());
        tel.setText(student.getTel());

    }
    @Override
    protected void onResume() {
        try {
            studentDataSource.open();
        }catch (SQLException e){
            e.printStackTrace();
        }
        super.onResume();
    }
    @Override
    protected void onPause() {
        studentDataSource.close();
        super.onPause();
    }
}
