# Nao esquecer:#

- SubjectList : onActivityResult() 
- SubjectView : 
	* lista de avaliacoes ( com dialogo ) => criar objecto Evaluation
	* botao editar => SubjectCreator : colocar coisas para poder editar e botao delete
	* acrescentar atividade dos horarios
	* acrescentar atividade das avaliacoes


- botão delete inativo no caso de não ser uma modificação
- verificar que as datas finais devem ser maiores que as datas inicias -> com caixa de dialogo
- criar excecoes

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