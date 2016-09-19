package org.home.ines.study.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import org.home.ines.study.R;
import org.home.ines.study.client.Study;
import org.home.ines.study.logic.Epoch;
import org.home.ines.study.logic.SchoolYear;
import org.home.ines.study.logic.Subject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Inês Gomes on 14/09/2016.
 */
public class SubjectList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button addSubject;
    private ListView subjectList;
    private Spinner spinner;
    private ArrayAdapter<Subject> adapterS;
    private ArrayAdapter<String> adapterC;

    private ArrayList<String> options = new ArrayList<>();
    private SchoolYear schoolYear;

    private final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjectlist);

        if (getIntent().hasExtra("schoolyear_1")) {
            schoolYear = (SchoolYear) getIntent().getExtras().getParcelable("schoolyear_1");
            Log.d("aqui","primeiro");
        }else
            Log.d("aqui","segunda");
        //else cria uma excecao

        //spinner
        spinner = (Spinner) findViewById(R.id.choiceSpinner);
        createAdaptor();
        spinner.setAdapter(adapterC);
        spinner.setOnItemSelectedListener(this); // obriga a implementacao de OnItemSelectedListener
        spinner.setSelection(options.size()-1);

        subjectList = (ListView) findViewById(R.id.listSubject);
        updateAdapter(toPosition(spinner.getSelectedItem()));
        subjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //vai para uma activity que permite ver as disciplinas -> SubjectView -> envia o subject
            }
        });

        addSubject = (Button) findViewById(R.id.addSubject);
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SubjectList.this, SubjectCreator.class);
                i.putExtra("schoolyear_2", schoolYear);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("schoolyear_2")) {
                schoolYear = data.getExtras().getParcelable("schoolyear_2");
                updateAdapter(toPosition(spinner.getSelectedItem()));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void createAdaptor() {
        for(Epoch e : schoolYear.getTerms()){
            options.add(e.toString());
        }
        options.add("All terms");
        adapterC = new ArrayAdapter<String>(SubjectList.this, android.R.layout.simple_list_item_1, options);
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public void updateAdapter(int position){
        if(position != options.size()-1) {
            Epoch e = schoolYear.getTerms().get(position);
            Log.d("name",e.toString());
            adapterS = new ArrayAdapter<Subject>(this, android.R.layout.simple_list_item_1, e.getSubjects());
        }else{
            ArrayList<Subject> subjects = new ArrayList<>();
            for(Epoch e : schoolYear.getTerms()){
                subjects.addAll(e.getSubjects());
            }
            adapterS = new ArrayAdapter<Subject>(this, android.R.layout.simple_list_item_1, subjects);
        }
        subjectList.setAdapter(adapterS);
    }

    public int toPosition(Object selectedItem) {
        for(int i = 0; i < options.size(); i++){
            if(options.get(i) == (String) selectedItem)
                return i;
        }
        return options.size()-1; //todos os semestres
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateAdapter(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
