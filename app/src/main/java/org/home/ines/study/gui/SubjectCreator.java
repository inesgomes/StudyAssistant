package org.home.ines.study.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.home.ines.study.R;
import org.home.ines.study.logic.Epoch;
import org.home.ines.study.logic.SchoolYear;
import org.home.ines.study.logic.Subject;

/**
 * Created by Inês Gomes on 14/09/2016.
 */
public class SubjectCreator extends AppCompatActivity {

    private EditText nameTxt;
    private EditText ectTxt;
    private Spinner spinnerTerm;
    private Spinner spinnerColor;
    private Button deleteBtn;
    private Button cancelBtn;
    private Button saveBtn;
    private ArrayAdapter<Epoch> adapterTerm;
    private ArrayAdapter<String> adapterColor;

    private boolean problem;
    String[] colors = {"Black", "Blue","Cyan", "Dark gray", "Gray", "Green", "Ligth gray", "Magenta", "Red", "White", "Yellow"};

    private SchoolYear schoolYear;
    private Subject newSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjectcreator);

        problem = false;
        newSubject = new Subject();

        if(getIntent().hasExtra("schoolyear_2"))
            schoolYear = (SchoolYear)getIntent().getExtras().getParcelable("schoolyear_2");
        //else cria uma excecao

        nameTxt = (EditText) findViewById(R.id.nameEdit);
        ectTxt = (EditText) findViewById(R.id.ectEdit); //este só é usado se o booleano do SchoolYear for true

        //if(!schoolYear.hasDiffWeights())
        // colocar o editText inativo

        //spiner para escolher o semestre a que esta cadeira pertence
        spinnerTerm = (Spinner) findViewById(R.id.termSpinner);
        adapterTerm = new ArrayAdapter<Epoch>(SubjectCreator.this, android.R.layout.simple_list_item_1, schoolYear.getTerms() );
        adapterTerm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTerm.setAdapter(adapterTerm);

        //spiner para escolher a cor da cadeira
        spinnerColor = (Spinner) findViewById(R.id.colorSpinner);
        adapterColor = new ArrayAdapter<String>(SubjectCreator.this, android.R.layout.simple_list_item_1, colors );
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColor.setAdapter(adapterColor);

        saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //preencher as informacoes sobre a cadeira
                newSubject.setName(nameTxt.getText().toString());
                if (schoolYear.hasDiffWeights()) {
                    //verifica se é mesmo um numero, se não mostra um dialogo
                    int number = 0;
                    try{
                        problem = false;
                        number = Integer.parseInt(ectTxt.getText().toString());
                    }catch(NumberFormatException nfe){
                        problem = true;
                        AlertDialog.Builder builder = new AlertDialog.Builder(SubjectCreator.this);
                        builder.setMessage("aaaaaaaa")
                                .setCancelable(true)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    if(!problem)
                        newSubject.setEcts(number);
                }
                if(!problem) {
                    newSubject.setColor(spinnerColor.getSelectedItem().toString());
                    Epoch e = (Epoch) spinnerTerm.getSelectedItem(); //adicionar a cadeira ao semestre correto
                    e.addSubject(newSubject);
                    schoolYear.verifyEpoch(e);
                    finish();
                }
            }
        });

        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //abre dialogo para ter a certeza que é para sair
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SubjectCreator.this);
                builder.setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    /**
     * Envia o user para a atividade Study
     */
    @Override
    public void finish() {
        Intent data = new Intent(this, SubjectList.class);
        data.putExtra("schoolyear_2", schoolYear);
        setResult(RESULT_OK, data);
        super.finish();
    }
}
