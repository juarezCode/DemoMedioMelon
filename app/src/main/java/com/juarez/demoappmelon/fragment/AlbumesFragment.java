package com.juarez.demoappmelon.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.juarez.demoappmelon.Adapters.AlbumsAdapter;
import com.juarez.demoappmelon.R;
import com.juarez.demoappmelon.model.Album;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumesFragment extends Fragment {
    private static final String TAG = "AlbumesFragment" ;
    private RecyclerView recyclerAlbums;
    private AlbumsAdapter mAdapter;
    private ArrayList<Album> myDataSet;
    private ProgressBar progressBarAlbum;
    private View view;


    public AlbumesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_albumes, container, false);

        progressBarAlbum = (ProgressBar) view.findViewById(R.id.progressBarAlbum);
        progressBarAlbum.setVisibility(view.VISIBLE);

        //recyclerview
        recyclerAlbums = (RecyclerView) view.findViewById(R.id.recycleralbumes);
        recyclerAlbums.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerAlbums.setLayoutManager(linearLayoutManager);
        loadAlbumFromWeb();

        return  view;

    }
    ////metodo para actualizar el Layout
    private void refreshDataSet() {
        progressBarAlbum.setVisibility(view.GONE);
        if(recyclerAlbums == null){
            return;
        }
        if(mAdapter == null){
            mAdapter = new AlbumsAdapter(this.getContext().getApplicationContext(), myDataSet);
            recyclerAlbums.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

    //metodo para traer los datos de la web
    private void loadAlbumFromWeb() {

        //Hacemos uso de Volley para consumir el End-point
        myDataSet = new ArrayList<Album>();

        //Definimos un String con la URL del End-point
        String url = "https://jsonplaceholder.typicode.com/albums";

        //Instanciamos un objeto RequestQueue el cual se encarga de gestionar la cola de peticiones
        RequestQueue queue = Volley.newRequestQueue(this.getContext().getApplicationContext());

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
                                    JSONObject objectAlbum = (JSONObject) response.get(i);

                                    if (objectAlbum != null){
                                        //Armamos un objeto con los atributos de cada JSONObject
                                        Album album= new Album();

                                        if (objectAlbum.has("id"))
                                            album.setId(objectAlbum.getString("id"));
                                            album.setTitulo(objectAlbum.getString("title"));

                                        //Agreagamos el objeto Photo al Dataset
                                        myDataSet.add(album);
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

}
