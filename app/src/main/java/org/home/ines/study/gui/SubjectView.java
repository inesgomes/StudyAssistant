package org.home.ines.study.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.home.ines.study.R;
import org.home.ines.study.client.Study;
import org.home.ines.study.logic.Evaluation;
import org.home.ines.study.logic.Schedule;
import org.home.ines.study.logic.SchoolYear;
import org.home.ines.study.logic.Subject;

/**
 * Created by Inês Gomes on 21/09/2016.
 */
public class SubjectView extends AppCompatActivity {

    private Subject subject;
    private TextView name;
    private ListView evaluationList;
    private ArrayAdapter<Evaluation> adapterE;
    private ListView scheduleList;
    private ArrayAdapter<Schedule> adapterS;
    private Button addEvaluation;
    private Button addSchedule;
    private Button editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjectview);

        //recebe um subject
        if (getIntent().hasExtra("subject"))
            subject = (Subject) getIntent().getExtras().getParcelable("subject");

        //set name
        name = (TextView) findViewById(R.id.subjectName);
        name.setText(subject.getName());

        //lista das avaliacoes ---> fazer depois
        evaluationList = (ListView) findViewById(R.id.evalutionList);
        adapterE = new ArrayAdapter<Evaluation>(this, android.R.layout.simple_list_item_1, subject.getEvaluations());
        evaluationList.setAdapter(adapterE);
        evaluationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // abre uma caixa de dialogo
            }
        });

        addEvaluation = (Button) findViewById(R.id.addEvaluation);
        addEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abrir um dialogo que é uma listView com os tipos de avaliacoes
            }
        });

        scheduleList = (ListView) findViewById(R.id.scheduleList);
        adapterS = new ArrayAdapter<Schedule>(this, android.R.layout.simple_list_item_1, subject.getSchedule());
        scheduleList.setAdapter(adapterS);
        scheduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               //ainda não sei o que é que isto vai fazer -> edit dos horarios ? it could be...
            }
        });

        addSchedule = (Button) findViewById(R.id.addSchedule);
        addSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abre uma nova activity para criar um horario
            }
        });

        editBtn = (Button) findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vai para SubjectCreator (com as coisas preenchidas)
            }
        });
    }
    /**
     * Envia o user para a atividade SUbjectList
     */
    @Override
    public void finish() {
        Intent data = new Intent(this, SubjectList.class);
        data.putExtra("subject", subject);
        setResult(RESULT_OK, data);
        super.finish();
    }

}
