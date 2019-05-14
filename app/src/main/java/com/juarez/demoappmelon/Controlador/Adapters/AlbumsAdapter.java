package com.juarez.demoappmelon.Controlador.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.juarez.demoappmelon.Controlador.Activity.PhotoActivity;
import com.juarez.demoappmelon.R;
import com.juarez.demoappmelon.modelo.Album;

import java.util.ArrayList;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<Album> mDataset;

    // viewholder accede a todas las vistas
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtAlbumId;
        TextView txtAlbumTitulo;

        ViewHolder(View v) {
            super(v);
            txtAlbumId = (TextView) v.findViewById(R.id.textViewNumber);
            txtAlbumTitulo = (TextView) v.findViewById(R.id.textViewNombreAlbum);

        }
    }

    // constructor
    public AlbumsAdapter(Context context, ArrayList<Album> myDataset) {
        mDataset = myDataset;
        mContext = context;
    }

    // Crea nuevas vistas (invocadas pot el layout manager)
    @Override
    public AlbumsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // crear una nueva vista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_albumes, parent, false);

        return new AlbumsAdapter.ViewHolder(v);
    }

    // Reemplazar el contenido del layout manager
    @Override
    public void onBindViewHolder(final AlbumsAdapter.ViewHolder holder, final int position) {
        // - obtiene los elementos del dataset en una posicion definida
        holder.txtAlbumId.setText(mDataset.get(position).getId()+".");
        holder.txtAlbumTitulo.setText(mDataset.get(position).getTitulo());


        //metodoOnclick() para cada elemento del recyclerview
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //envio de datos
                Intent intent = new Intent(mContext, PhotoActivity.class);
                intent.putExtra("tituloAlbum",  mDataset.get(position).getTitulo());
                intent.putExtra("albumId",  mDataset.get(position).getId());
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
