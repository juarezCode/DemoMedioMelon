package com.juarez.demoappmelon.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.juarez.demoappmelon.Adapters.UsersAdapter;
import com.juarez.demoappmelon.R;
import com.juarez.demoappmelon.model.User;

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


    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_users, container, false);
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
    private  void dummyData(){

        myDataSet = new ArrayList<User>();
        int i = 0;
        while(i < 50){
            User user = new User();
            user.setNombre(" Roberto" +  String.valueOf(i));
            user.setCorreo(" juarez@gmail.com" +  String.valueOf(i));
            myDataSet.add(user);
            i++;

        }
        refreshDataSet();
    }

    private void refreshDataSet() {
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
    private void loadPhotosFromWeb() {
        //Hacemos uso de Volley para consumir el End-point
        myDataSet = new ArrayList<User>();
        //myDataset = new ArrayList<User>();

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
                                    JSONObject p = (JSONObject) response.get(i);
                                    if (p != null){
                                        //Armamos un objeto Photo con el Title y la URL de cada JSONObject
                                        User user = new User();
                                        if (p.has("name"))
                                            user.setNombre(p.getString("name"));
                                        if (p.has("email"))
                                            user.setCorreo(p.getString("email"));
                                        //if (p.has("url"))
                                        //    photo.setImageUrl(p.getString("url"));
                                        //Agreagamos el objeto Photo al Dataset
                                        myDataSet.add(user);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();


                                } finally {
                                    //Finalmente si hemos cargado datos en el Dataset
                                    // entonces refrescamos
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
