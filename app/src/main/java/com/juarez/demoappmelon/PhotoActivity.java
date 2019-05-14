package com.juarez.demoappmelon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.juarez.demoappmelon.Adapters.PhotoAdapter;
import com.juarez.demoappmelon.model.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private TextView tituloAlbum;
    private static final String TAG = "PhotoActivity" ;
    private RecyclerView recyclerPhotos;
    private PhotoAdapter mAdapter;
    private ArrayList<Photo> myDataSet;
    private String AlbumId;
    private ProgressBar progressBarPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        showToolbar("Album", true);

        tituloAlbum = (TextView) findViewById(R.id.txtTituloAlbumPhoto);
        progressBarPhoto = (ProgressBar) findViewById(R.id.progressBarPhoto);
        progressBarPhoto.setVisibility(View.VISIBLE);

        //recibir  datos
        String DTituloAlbum = getIntent().getStringExtra("tituloAlbum");
        AlbumId = getIntent().getStringExtra("albumId");
        String DAlbumiD = getIntent().getStringExtra("albumId");


        //Mostrar valores a la pantalla
        tituloAlbum.setText(DAlbumiD+" "+ DTituloAlbum);



        //recyclerview
        recyclerPhotos = (RecyclerView) findViewById(R.id.recyclerphotos);
        recyclerPhotos.setHasFixedSize(true);
        recyclerPhotos.setLayoutManager(new GridLayoutManager(this,3));


        //llenar RecyclerPhotos
        loadPhotosFromAlbum();

        //menu botttomnavigationview
        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottomnavigationview);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch (id) {
                    case R.id.usersItem:
                        Intent IntentUser = new Intent(PhotoActivity.this, MainActivity.class);
                        IntentUser.putExtra("valorFragment",  0);
                        startActivity(IntentUser);
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
    //metodo para actualizar datos de web
    private void refreshDataSet() {
        progressBarPhoto.setVisibility(View.GONE);
        if(recyclerPhotos == null){
            return;
        }
        if(mAdapter == null){
            mAdapter = new PhotoAdapter(PhotoActivity.this, myDataSet);
            recyclerPhotos.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

    private void loadPhotosFromAlbum() {
        //Hacemos uso de Volley para consumir el End-point
        myDataSet = new ArrayList<Photo>();

        //Definimos un String con la URL del End-point
        String url = "https://jsonplaceholder.typicode.com/photos?albumId="+AlbumId;

        //Instanciamos un objeto RequestQueue el cual se encarga de gestionar la cola de peticiones
        RequestQueue queue = Volley.newRequestQueue(this);

        //Armamos una peticion de tipo JSONArray
        JsonArrayRequest aRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse");

                        //Obtenemos un JSONArray como respuesta
                        if (response != null && response.length() > 0){
                            //Iteramos son el JSONArray
                            for (int i=0; i <response.length(); i++){
                                try {
                                    JSONObject objectPhoto = (JSONObject) response.get(i);

                                    if (objectPhoto != null){
                                        //Armamos un objeto con los atributos de cada JSONObject
                                        Photo photo= new Photo();

                                        if (objectPhoto.has("url"))
                                        photo.setImageUrl(objectPhoto.getString("url"));
                                        photo.setIdPhoto(objectPhoto.getString("id"));
                                        photo.setAlbumId(objectPhoto.getString("albumId"));

                                        //Agreagamos el objeto al Dataset
                                        myDataSet.add(photo);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();


                                } finally {
                                    //Finalmente si hemos cargado datos en el Dataset
                                    // refrescamos
                                    if (myDataSet.size() > 0)
                                        refreshDataSet();
                                }
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse");
            }
        });

        //Agregamos la petición de tipo JSON a la cola
        queue.add(aRequest);
    }

    //Toolbar
    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }
}
