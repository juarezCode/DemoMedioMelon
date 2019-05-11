package com.juarez.demoappmelon;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailUserActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private TextView DUserNombre;
    private TextView DUserNombreUsuario;
    private TextView DUserSitioWeb;
    private TextView DUserCorreo;
    private TextView DUserTel;
    private TextView DUserDireccion;
    private TextView DUserEmpresa;
    private ImageView DUserFoto;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        DUserNombre        = (TextView) findViewById(R.id.txtUserDetailNombre);
        DUserNombreUsuario = (TextView) findViewById(R.id.txtUserDetailNombreUsuario);
        DUserSitioWeb      = (TextView) findViewById(R.id.txtUserDetailSitioWeb);
        DUserCorreo        = (TextView) findViewById(R.id.txtUserDetailEmail);
        DUserTel           = (TextView) findViewById(R.id.txtUserDetailTel);
        DUserDireccion     = (TextView) findViewById(R.id.txtUserDetailDomicilio);
        DUserEmpresa     = (TextView) findViewById(R.id.txtUserDetailNombreEmpresa);
        DUserFoto     = (ImageView) findViewById(R.id.imageViewDetailUsers);


        showToolbar("Detalles de Usuario", true);

        //recibir  datos
        String DNombre = getIntent().getStringExtra("nombre");
        String DNombreUsuario = getIntent().getStringExtra("nombreUsuario");
        String DSitioWeb = getIntent().getStringExtra("sitioWeb");
        String DCorreo = getIntent().getStringExtra("correo");
        String DTel = getIntent().getStringExtra("tel");
        String DDireccion = getIntent().getStringExtra("direccion");
        String DEmpresa = getIntent().getStringExtra("empresa");
        int DFoto  = getIntent().getIntExtra("foto",R.drawable.man_1);


        //Mostrar valores a la pantalla
        DUserNombre.setText(DNombre);
        DUserNombreUsuario.setText(DNombreUsuario);
        DUserSitioWeb.setText(DSitioWeb);
        DUserCorreo.setText(DCorreo);
        DUserTel.setText(DTel);
        DUserDireccion.setText(DDireccion);
        DUserEmpresa.setText(DEmpresa);
        DUserFoto.setImageResource(DFoto);




        //menu botttomnavigationview
        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottomnavigationview);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                switch (id) {
                    case R.id.usersItem:
                        finish();

                        break;
                    case R.id.albumItem:
                        finish();
                        break;


                }

                return true;
            }
        });

    }
    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }
}
