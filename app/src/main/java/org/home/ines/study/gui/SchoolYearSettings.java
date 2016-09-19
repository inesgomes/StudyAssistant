package org.home.ines.study.gui;

import org.home.ines.study.R;
import org.home.ines.study.logic.Epoch;
import org.home.ines.study.logic.Holiday;
import org.home.ines.study.logic.SchoolYear;
import org.home.ines.study.logic.Term;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

/**
 * FALTA:
 * - botao delete inativo quando é uma nova epoca
 * - excecao para o caso de nao receber uma epoca
 */
public class SchoolYearSettings extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    //The ViewPager that will host the section contents.
    private ViewPager mViewPager;

    private final int REQUEST_CODE = 1;
    private boolean delete;

    private SchoolYear newSchoolYear;
    private ArrayAdapter<Epoch> adapterT;
    private ArrayAdapter<Epoch> adapterH;
    private ArrayAdapter<String> adapterC;
    private ListView listViewT;
    private ListView listViewH;
    private ListView listViewC;

    /**
     * Inicializacao da atividade
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_year_settings);

        delete = false;

        //recebe um objeto do tipo SchoolYear da activity Setting
        if(getIntent().hasExtra("schoolyear"))
            newSchoolYear = getIntent().getExtras().getParcelable("schoolyear");
        else
            newSchoolYear = new SchoolYear(); // deve criar uma excecao

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    /**
     * Metodo da atividade.
     * Inflate the menu; this adds items to the action bar if it is present.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * Metodo da atividade.
     * Handle action bar item clicks here. The action bar will automatically handle clicks on the
     * Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo da atividade. Recebe dados de outras atividades.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //recebe uma epoca, e de acordo com o booleano pode adiciona-la ou remove-la
            if (data.hasExtra("epoch") && data.hasExtra("deleteE")){
                Epoch e = (Epoch)data.getExtras().getParcelable("epoch");
                if(data.getBooleanExtra("deleteE",true)){
                    if(e instanceof Term) adapterT.remove(e);
                    else adapterH.remove(e);
                }
                else add(e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Envia dados para outras atividades, neste caso envia um SchoolYear e a informacao se deve ou nao ser apagado
     */
    @Override
    public void finish() {
        Intent data = new Intent(this, Setting.class);
        data.putExtra("schoolyear", newSchoolYear);
        data.putExtra("deleteSY",delete);
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

    /**
     * Adiciona uma epoch ao respetivo adapter, verificando se é uma modificação ou uma nova Epoch
     * @param e
     */
    public void add(Epoch e) {
        if(!newSchoolYear.verifyEpoch(e)){  // se não se verifica a epoca, é porque é nova
            if(e instanceof Term) adapterT.add(e);
            else adapterH.add(e);
        }else{ //no caso de ser uma modificação é necessário atualizar os adapters
            adapterH.notifyDataSetChanged();
            adapterT.notifyDataSetChanged();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     * Inner class
     */
    public class PlaceholderFragment extends Fragment  {

        // The fragment argument representing the section number for this fragment
        private static final String ARG_SECTION_NUMBER = "section_number";

        private EditText nameTxt;
        private CheckBox weights;
        private Button saveBtn;
        private Button cancelBtn;
        private Button deleteBtn;
        private Button addTermBtn;
        private Button addHolidayBtn;
        private Button addClassBtn;

        /**
         * Construtor
         */
        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section number.
         */
        public PlaceholderFragment newInstance(int sectionNumber){
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * Construcao da view. Depende do fragmento.
         * @param inflater
         * @param container
         * @param savedInstanceState
         * @return
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            final View rootView;

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                rootView = inflater.inflate(R.layout.fragment_school_year_settings, container, false);

                //nome
                nameTxt = (EditText) rootView.findViewById(R.id.nameYearTxt);
                nameTxt.setText(newSchoolYear.getName());
                //checkbox com os pesos
                weights = (CheckBox) rootView.findViewById(R.id.checkBox);
                weights.setChecked(newSchoolYear.hasDiffWeights());

                //botoes : save, cancel, delete, add
                saveBtn = (Button) rootView.findViewById(R.id.saveBtn);
                saveBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        newSchoolYear.setName(nameTxt.getText().toString());
                        newSchoolYear.setDiffWeights(weights.isChecked());
                        finish();
                    }
                });

                cancelBtn = (Button) rootView.findViewById(R.id.cancelBtn);
                cancelBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });

                //abre dialogo para ter a certeza que é para sair
                deleteBtn = (Button) rootView.findViewById(R.id.deleteBtn);
                deleteBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SchoolYearSettings.this);
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

                //adiciona novos tipos de aulas
                addClassBtn = (Button)rootView.findViewById(R.id.addClassBtn);
                addClassBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //cria um dialogo com espaço para digitar um texto, esse texto é então passado para um arrayccom todos os tipos de aulas
                        AlertDialog.Builder builder = new AlertDialog.Builder(SchoolYearSettings.this);
                        final EditText input = new EditText(SchoolYearSettings.this);
                        builder.setTitle("New Class Type")
                                .setCancelable(true)
                                .setView(input)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                adapterC.add(input.getText().toString());
                            }
                        })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });

                //list view com os tipos de classes
                listViewC = (ListView) rootView.findViewById(R.id.listTypeClass);
                adapterC = new ArrayAdapter<String>(rootView.getContext(),android.R.layout.simple_list_item_1, newSchoolYear.getClassTypes());
                listViewC.setAdapter(adapterC);
                listViewC.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        //no caso de clicar na lista abre um dialogo que permite eliminar o item escolhido
                        AlertDialog.Builder builder = new AlertDialog.Builder(SchoolYearSettings.this);
                        builder.setTitle("Delete entry")
                                .setMessage("Are you sure you want to delete this entry?")
                                .setCancelable(true)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                adapterC.remove(adapterC.getItem(position));
                                dialog.cancel();
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

            }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                rootView = inflater.inflate(R.layout.fragment_school_year_settings2, container, false);

                //botao adiconar Semestre
                addTermBtn = (Button)rootView.findViewById(R.id.buttonAdd);
                addTermBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //nova atividade EpochSettings
                        Intent i = new Intent(SchoolYearSettings.this, EpochSettings.class);
                        i.putExtra("epoch",new Term());
                        startActivityForResult(i,REQUEST_CODE);
                    }
                });

                //listView com os nomes dos semestres
                listViewT = (ListView) rootView.findViewById(R.id.listViewTerms);
                adapterT = new ArrayAdapter<Epoch>(rootView.getContext(),android.R.layout.simple_list_item_1, newSchoolYear.getTerms());
                listViewT.setAdapter(adapterT);
                listViewT.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //no caso de clicar num item abre a sua atividade de modo a puder modifica-lo
                        Intent i = new Intent(SchoolYearSettings.this, EpochSettings.class);
                        i.putExtra("epoch",(Term) adapterT.getItem(position));
                        startActivityForResult(i,REQUEST_CODE );
                    }
                });

            }else{
                rootView = inflater.inflate(R.layout.fragment_school_year_settings3, container, false);

                //botao adicionar ferias
                addHolidayBtn = (Button)rootView.findViewById(R.id.buttonAdd2);
                addHolidayBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //nova atividade EpochSettings
                        Intent i = new Intent(SchoolYearSettings.this, EpochSettings.class);
                        i.putExtra("epoch",new Holiday());
                        startActivityForResult(i,REQUEST_CODE );
                    }
                });

                //listView com as ferias
                listViewH = (ListView) rootView.findViewById(R.id.listViewHolidays);
                adapterH = new ArrayAdapter<Epoch>(rootView.getContext(),android.R.layout.simple_list_item_1, newSchoolYear.getHolidays());
                listViewH.setAdapter(adapterH);
                listViewH.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //no caso de clicar num item este pode ser modificado
                        Intent i = new Intent(SchoolYearSettings.this, EpochSettings.class);
                        i.putExtra("epoch",(Holiday) adapterH.getItem(position));
                        startActivityForResult(i,REQUEST_CODE );
                    }
                });

            }
            return rootView;
        }
    }

    /**
     * =============================================================================================
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     * Inner class
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * getItem is called to instantiate the fragment for the given page.
         * Return a PlaceholderFragment (defined as a static inner class below).
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            PlaceholderFragment p = new PlaceholderFragment();
            return p.newInstance(position+1);
        }

        /**
         * Show 3 total pages.
         * @return
         */
        @Override
        public int getCount() {
            return 3;
        }

        /**
         * Titulo de cada tab
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "School Year";
                case 1:
                    return "Terms";
                case 2:
                    return "Holidays";
            }
            return null;
        }
    }
}
