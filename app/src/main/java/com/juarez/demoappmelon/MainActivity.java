package com.juarez.demoappmelon;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.juarez.demoappmelon.fragment.AlbumesFragment;
import com.juarez.demoappmelon.fragment.UsersFragment;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Renderizando la toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Demo App");




        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottomnavigationview);
        //bottomNavigation.inflateMenu(R.menu.bottomnavigationview_menu);
        fragmentManager = getSupportFragmentManager();
        fragment = new UsersFragment();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment).commit();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                switch (id){
                    case R.id.usersItem:
                        fragment = new UsersFragment();
                        getSupportActionBar().setTitle("Usuarios");
                        break;
                    case R.id.albumItem:
                        fragment = new AlbumesFragment();
                        getSupportActionBar().setTitle("Albumes");
                        break;



                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragment).commit();
                return true;
            }
        });





    }

}
