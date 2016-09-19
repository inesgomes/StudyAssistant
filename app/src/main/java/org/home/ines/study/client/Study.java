package org.home.ines.study.client;

import org.home.ines.study.R;
import org.home.ines.study.gui.EpochSettings;
import org.home.ines.study.gui.Setting;
import org.home.ines.study.gui.SubjectList;
import org.home.ines.study.logic.SchoolYear;
import org.home.ines.study.logic.Term;
import org.home.ines.study.logic.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Study extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int REQUEST_CODE = 1;
    private User user;

    private Menu myMenu;
    private MenuItem name;
    private MenuItem course;
    private MenuItem college;
    private MenuItem year;
    private Button subjects;

    /**
     * Inicializacao da atividade
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        user = new User();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //menu de lado
        myMenu = navigationView.getMenu();
        name = myMenu.findItem(R.id.nameText);
        college = myMenu.findItem(R.id.collegeText);
        course = myMenu.findItem(R.id.courseText);
        year = myMenu.findItem(R.id.yearNameText);
        populateFields();

        //menu principal
        subjects = (Button)findViewById(R.id.subjectmanager);
        subjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getAllYears().size() == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Study.this);
                    builder.setMessage("You can not continue until you fill out the school year. First, go to settings.")
                            .setCancelable(true)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }else {
                    Intent i = new Intent(Study.this, SubjectList.class);
                    i.putExtra("schoolyear_1", user.getAllYears().get(user.getCurrentYear()));
                    startActivityForResult(i, REQUEST_CODE);
                }
            }
        });

    }

    /**
     * Metodo da atividade
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Metodo da atividade
     * Inflate the menu; this adds items to the action bar if it is present.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.study, menu);
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
        int id = item.getItemId();

        //abre os settings
        if (id == R.id.action_settings) {
            Intent i = new Intent(Study.this, Setting.class);
            i.putExtra("user", user);
            startActivityForResult(i, REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo da atividade.
     * Handle navigation view item clicks here.
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            // Handle the camera action
        } else if (id == R.id.nav_help) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Metodo da atividade.
     * Receive extras from other activities and handles them.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            //recebe o objeto User da atividade Setting
            if (data.hasExtra("user")) {
                user = data.getExtras().getParcelable("user");
                populateFields();
                if(user.getAllYears().size() != 0) //so executa quando existem anos letivos
                    year.setTitle(user.getAllYears().get(user.getCurrentYear()).toString());
            }if(data.hasExtra("schoolyear_1")){
                user.update((SchoolYear) data.getExtras().getParcelable("schoolyear_1"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Metodo auxiliar que preenche os campos name, college e course dependendo do objeto User
     */
    public void populateFields(){
        name.setTitle(user.getName());
        college.setTitle(user.getCollege());
        course.setTitle(user.getCourse());
    }
}
