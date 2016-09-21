# Nao esquecer:#

- delete de subjects -> quando criar o edit

- botão delete inativo no caso de não ser uma modificação
- verificar que as datas finais devem ser maiores que as datas inicias -> com caixa de dialogo
- criar excecoes

- eventualmente, verificar se não há uma maneira mais pratica de por os displays dos semestres no SubjectList mais simples.

# ////////////////////////////////////////////////////////////////////////////////////////// #
para se algum dia precisar

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
		
		    <android.support.design.widget.NavigationView
        android:id="@+id/nav_view"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:fitsSystemWindows="true"
        app:headerLayout="@layout/nav_header_study"
        app:menu="@menu/activity_study_drawer" />