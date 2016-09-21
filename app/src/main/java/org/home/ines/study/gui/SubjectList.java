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

        if (getIntent().hasExtra("schoolyear_1"))
            schoolYear = (SchoolYear) getIntent().getExtras().getParcelable("schoolyear_1");
        //else cria uma excecao

        //spinner
        spinner = (Spinner) findViewById(R.id.choiceSpinner);
        createAdaptor();
        spinner.setAdapter(adapterC);
        spinner.setOnItemSelectedListener(this); // obriga a implementacao de OnItemSelectedListener
        spinner.setSelection(options.size()-1);

        subjectList = (ListView) findViewById(R.id.listSubject);
        updateAdapter(toPosition(spinner.getSelectedItem().toString()));
        subjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SubjectList.this, SubjectView.class);
                i.putExtra("subject", adapterS.getItem(position));
                startActivityForResult(i, REQUEST_CODE);
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
                updateAdapter(toPosition(spinner.getSelectedItem().toString()));
            }if(data.hasExtra("subject")){
                //procura o subject na lista de subjects, elimina o que está nessa lista e põe este
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void createAdaptor() {
        for(Epoch e : schoolYear.getTerms()){
            options.add(e.toString());
        }
        options.add("All terms");
        adapterC = new ArrayAdapter<String>(SubjectList.this, android.R.layout.simple_spinner_item, options);
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public void updateAdapter(int position){
        if(position != options.size()-1) {
            Epoch e = schoolYear.getTerms().get(toPosition(options.get(position)));
            adapterS = new ArrayAdapter<Subject>(this, android.R.layout.simple_list_item_1, e.getSubjects());
        }else{
            ArrayList<Subject> subjects = new ArrayList<>();
            for(Epoch e : schoolYear.getTerms()){
                subjects.addAll(e.getSubjects());
            }
            adapterS = new ArrayAdapter<Subject>(this, android.R.layout.simple_list_item_1, subjects);
        }
        subjectList.setAdapter(adapterS);
        adapterS.notifyDataSetChanged();
    }

    /**
     * Transforma o item selecionado numa posicao das opcoes
     * @param selectedItem
     * @return
     */
    public int toPosition(String selectedItem) {
        ArrayList<Epoch> terms = schoolYear.getTerms();
        for(int i = 0; i < terms.size(); i++){
            if(terms.get(i).getName().equals(selectedItem))
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
