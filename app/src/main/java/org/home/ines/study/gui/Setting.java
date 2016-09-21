package org.home.ines.study.gui;

import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.home.ines.study.R;
import org.home.ines.study.client.Study;
import org.home.ines.study.logic.SchoolYear;
import org.home.ines.study.logic.User;

import java.util.ArrayList;


/**
 * Created by Inês Gomes on 16/07/2016.
 *
 * FALTA
 * - excecao para o caso de nao receber um User
 *
 * IDEIA
 * - sempre que existe um novo SchoolYear colocar num vetor privado (pode ser só com os nomes, ou id's).
 * - Se fizermos cancel, todos os anos escolares desse vetor têm de ser eliminados do User.
 */
public class Setting extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText nameTxt;
    private EditText collegeTxt;
    private EditText courseTxt;
    private Button saveBtn;
    private Button cancelBtn;
    private Button addYear;
    private ListView list;
    private Spinner spinner;
    ArrayAdapter<SchoolYear> adapter ;

    private User u;
    private User oldUser;
    private boolean cancel;
    private final int REQUEST_CODE = 1;

    /**
     * Inicializacao da atividade
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        cancel = false;

        if(getIntent().hasExtra("user")) {
            oldUser = getIntent().getExtras().getParcelable("user");
            u = getIntent().getExtras().getParcelable("user");
            Log.d("aaaaa","chegou");
        }//else
           // u = new User(); // deve criar uma excecao

        //EditText (compor o layout)
        nameTxt = (EditText)findViewById(R.id.nameText);
        collegeTxt = (EditText)findViewById(R.id.collegeText);
        courseTxt = (EditText) findViewById(R.id.courseText);

        nameTxt.setText(u.getName());
        courseTxt.setText(u.getCourse());
        collegeTxt.setText(u.getCollege());

        //listView com todos os anos escolares
        list = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<SchoolYear>(Setting.this, android.R.layout.simple_list_item_1, u.getAllYears());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Setting.this, SchoolYearSettings.class);
                i.putExtra("schoolyear",(SchoolYear) adapter.getItem(position));
                startActivityForResult(i,REQUEST_CODE );
            }
        });

        //spiner para escolher o anos escolar atual
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this); // obriga a implementacao de OnItemSelectedListener

        //adiciona um novo ano escolar
        addYear = (Button)findViewById(R.id.yearBtn);
        addYear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Setting.this, SchoolYearSettings.class);
                i.putExtra("schoolyear", new SchoolYear());
                startActivityForResult(i,REQUEST_CODE);
            }
        });

        saveBtn = (Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                u.setCollege(collegeTxt.getText().toString());
                u.setCourse(courseTxt.getText().toString());
                u.setName(nameTxt.getText().toString());
                finish();
            }
        });

        cancelBtn = (Button)findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cancel = true;
                onBackPressed();
            }
        });
    }

    /**
     * Metodo da atividade.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data.hasExtra("schoolyear") && data.hasExtra("deleteSY")) {
                SchoolYear sy =(SchoolYear) data.getExtras().getParcelable("schoolyear");
                if(data.getBooleanExtra("deleteSY",true))
                    adapter.remove(sy);
                else
                    add(sy);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Envia o user para a atividade Study
     */
    @Override
    public void finish() {
        Intent data = new Intent(this, Study.class);
        if(cancel) {
            Log.d("aaaaa","cancel");
            data.putExtra("user", oldUser);
        }else {
            Log.d("aaaaa","save");
            data.putExtra("user", u);
        }
        setResult(RESULT_OK, data);
        super.finish();
    }

    /**
     * Metodo da atividade. Serve para saber qual dos itens do adapter foi escolhido no spinner.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        u.setCurrentYear(parent.getSelectedItemPosition());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        return;
    }

    /**
     * Adiciona o ano letivo à lista de anos letivos caso ainda nao exista, se não é modificado
     * @param sy
     */
    public void add(SchoolYear sy){
        if(u.verifyAllYears(sy))
            adapter.notifyDataSetChanged();
        else
            adapter.add(sy);
    }
}
