package com.juarez.demoappmelon.Controlador.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.juarez.demoappmelon.R;
import com.juarez.demoappmelon.Controlador.fragment.AlbumesFragment;
import com.juarez.demoappmelon.Controlador.fragment.UsersFragment;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private ProgressBar progressBarPhoto;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Renderizando la toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbartitle);

        setSupportActionBar(toolbar);
        toolbarTitle.setText("Demo App");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //metodo que secciona el fragment inicial desde otra activity (DetailUserActivity)
        seleccionFragmentInicial();

        //BottomNavigationView onclick
        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottomnavigationview);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch (id){
                    case R.id.usersItem:
                        fragment = new UsersFragment();
                        //toolbarTitle.setText("Usuarios");
                        break;
                    case R.id.albumItem:
                        fragment = new AlbumesFragment();
                        //toolbarTitle.setText("Albumes");
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragment).commit();
                return true;
            }
        });
    }

    private void seleccionFragmentInicial() {
        //recibiendo datos de 2da pantalla
        int valor = getIntent().getIntExtra("valorFragment", 0);

        //if elegir fragments a iniciar
        fragmentManager = getSupportFragmentManager();
        if(valor == 0){
            fragment = new UsersFragment();
        }else if (valor == 2){
            fragment = new AlbumesFragment();
        }
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment).commit();
    }

}
