package com.juarez.demoappmelon.Controlador.fragment;


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
import com.juarez.demoappmelon.Controlador.Adapters.UsersAdapter;
import com.juarez.demoappmelon.R;
import com.juarez.demoappmelon.modelo.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {
    private static final String TAG = "UsersFragment" ;
    private RecyclerView recyclerUsers;
    private UsersAdapter mAdapter;
    private ArrayList<User> myDataSet;
    private ProgressBar progressBar;
    private  View view;


    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_users, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        progressBar.setVisibility(view.VISIBLE);

        //recyclerview
        recyclerUsers = (RecyclerView) view.findViewById(R.id.recyclerusers);
        recyclerUsers.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerUsers.setLayoutManager(linearLayoutManager);
        //dummyData();
        loadPhotosFromWeb();
        //UsersAdapter mAdapter  = new UsersAdapter(this.getContext().getApplicationContext(), myDataSet);


        return  view;


    }
    //metodo para actualizar el Layout
    private void refreshDataSet() {

        progressBar.setVisibility(view.GONE);
        if(recyclerUsers == null){
            return;
        }
        if(mAdapter == null){
            mAdapter = new UsersAdapter(this.getContext().getApplicationContext(), myDataSet);
            recyclerUsers.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

    //metodo para traer los datos de la web
    private void loadPhotosFromWeb() {

        //Hacemos uso de Volley para consumir el End-point
        myDataSet = new ArrayList<User>();

        //Definimos un String con la URL del End-point
        String url = "https://jsonplaceholder.typicode.com/users";

        //Instanciamos un objeto RequestQueue el cual se encarga de gestionar la cola de peticiones
        RequestQueue queue = Volley.newRequestQueue(this.getContext().getApplicationContext());

        //Armamos una peticion de tipo JSONArray por que es un JsonArray lo que obtendremos como resultado
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
                                    JSONObject objectUser = (JSONObject) response.get(i);
                                    JSONObject objectAddress = objectUser.getJSONObject("address");
                                    JSONObject objectCompany = objectUser.getJSONObject("company");

                                    if (objectUser != null){
                                        //Armamos un objeto con los atributos de cada JSONObject
                                        User user = new User();

                                        if (objectUser.has("name"))
                                            user.setNombre(objectUser.getString("name"));
                                        if (objectUser.has("username"))
                                            user.setNombreUsuario(objectUser.getString("username"));
                                            user.setSitioWeb(objectUser.getString("website"));
                                            user.setCorreo(objectUser.getString("email"));
                                            user.setTelefono(objectUser.getString("phone"));

                                            user.setDireccion(objectAddress.getString("street"));
                                            user.setEmpresa(objectCompany.getString("name"));

                                        //metodo random para asignar imagenes a los usuarios
                                        int[] elementos={R.drawable.girl_1,R.drawable.girl_2,R.drawable.man_1, R.drawable.man_2};
                                        int numRandon = (int) Math.round(Math.random() * 3 ) ;
                                        int num = elementos[numRandon];

                                            user.setFoto(num);
                                        //Agreagamos el objeto Photo al Dataset
                                        myDataSet.add(user);
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
