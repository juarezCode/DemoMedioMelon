package com.juarez.demoappmelon.Controlador.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juarez.demoappmelon.Controlador.Activity.DetailUserActivity;
import com.juarez.demoappmelon.R;
import com.juarez.demoappmelon.modelo.User;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<User> mDataset;

    // viewholder accede a todas las vistas
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView txtnombre;
        TextView txtcorreo;
        ImageView foto;
        ViewHolder(View v) {
            super(v);
            txtnombre = (TextView) v.findViewById(R.id.txtxUserNombre);
            txtcorreo = (TextView) v.findViewById(R.id.txtxUserCorreo);
            foto = (ImageView)v.findViewById(R.id.imageViewUsers);
        }
    }

    // constructor
    public UsersAdapter(Context context, ArrayList<User> myDataset) {
        mDataset = myDataset;
        mContext = context;
    }

    // Crea nuevas vistas (invocadas pot el layout manager)
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // crear una nueva vista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_users, parent, false);

        return new ViewHolder(v);
    }

    // Reemplazar el contenido del layout manager
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - obtiene los elementos del dataset en una posicion definida
        holder.txtnombre.setText(mDataset.get(position).getNombre());
        holder.txtcorreo.setText(mDataset.get(position).getCorreo());
        holder.foto.setImageResource(mDataset.get(position).getFoto());

        //metodoOnclick() para cada elemento del recyclerview
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //envio de datos
                Intent intent = new Intent(mContext, DetailUserActivity.class);
                intent.putExtra("nombre",  mDataset.get(position).getNombre());
                intent.putExtra("nombreUsuario",  mDataset.get(position).getNombreUsuario());
                intent.putExtra("sitioWeb",  mDataset.get(position).getSitioWeb());
                intent.putExtra("correo",  mDataset.get(position).getCorreo());
                intent.putExtra("tel",  mDataset.get(position).getTelefono());
                intent.putExtra("direccion",  mDataset.get(position).getDireccion());
                intent.putExtra("empresa",  mDataset.get(position).getEmpresa());
                intent.putExtra("foto", mDataset.get(position).getFoto() );

                mContext.startActivity(intent);
            }
        });

    }

    // Devuelve el tamaño de tu dataset
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
