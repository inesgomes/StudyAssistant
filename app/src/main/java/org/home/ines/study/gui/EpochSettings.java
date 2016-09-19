package org.home.ines.study.gui;

import org.home.ines.study.R;
import org.home.ines.study.logic.Epoch;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Inês Gomes on 11/09/2016.
 *
 * FALTA:
 * - botao delete inativo quando é uma nova epoca
 * - excecao para o caso de nao receber uma epoca
 */
public class EpochSettings extends AppCompatActivity {

    private EditText nameTxt;
    private DatePicker initDate;
    private DatePicker endDate;
    private Button saveBtn;
    private Button cancelBtn;
    private Button deleteBtn;

    private Epoch epoch;
    private boolean delete;

    /**
     * Iniciliaza a atividade
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epoch);

        delete = false;

        //deve sempre receber uma epoca
        if(getIntent().hasExtra("epoch"))
            epoch = getIntent().getExtras().getParcelable("epoch");
        else
            epoch = null; //devera lançar uma excecao --->>>

        //trata do layout
        nameTxt = (EditText) findViewById(R.id.nameEpoch);
        nameTxt.setText(epoch.getName());
        initDate = (DatePicker) findViewById(R.id.datePickerInit);
        initDate.init(epoch.getInit().get(Calendar.YEAR), epoch.getInit().get(Calendar.MONTH), epoch.getInit().get(Calendar.DAY_OF_MONTH), null);
        endDate = (DatePicker) findViewById(R.id.datePickerEnd);
        endDate.init(epoch.getEnd().get(Calendar.YEAR), epoch.getEnd().get(Calendar.MONTH), epoch.getEnd().get(Calendar.DAY_OF_MONTH), null);

        //botoes : save, cancel and delete
        saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //enviar a nova Epoca para o SchoolYearSettings
                int day = initDate.getDayOfMonth();
                int month = initDate.getMonth();
                int year = initDate.getYear();
                Calendar c1 = new GregorianCalendar(year, month, day);

                day = endDate.getDayOfMonth();
                month = endDate.getMonth();
                year = endDate.getYear();
                Calendar c2 = new GregorianCalendar(year, month, day);

                epoch.setName(nameTxt.getText().toString());
                epoch.setInit(c1);
                epoch.setEnd(c2);

                finish();
            }
        });

        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //o botao de delete abre uma caixa de dialogo
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EpochSettings.this);
                builder.setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        delete = true;
                        finish();
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
     * Metodo da atividade
     * Este metodo serve para poder enviar objetos entre classes
     */
    @Override
    public void finish() {
        Intent data = new Intent(this, SchoolYearSettings.class); // from -> to (activities)
        data.putExtra("epoch", epoch); //envia a epoca
        data.putExtra("deleteE",delete); //envia um booleno para saber se a epoca deve ou nao ser eliminada
        setResult(RESULT_OK, data);
        super.finish();
    }

    /**
     * Metodo da atividade
     */
    @Override
    public void onBackPressed() {
        delete = true;
        super.onBackPressed();
    }
}
