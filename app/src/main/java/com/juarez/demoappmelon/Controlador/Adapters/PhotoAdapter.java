package com.juarez.demoappmelon.Controlador.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.juarez.demoappmelon.R;
import com.juarez.demoappmelon.modelo.Photo;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<Photo> mDataset;

    // viewholder accede a todas las vistas
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        ImageView photo;

        ViewHolder(View v) {
            super(v);
            photo = (ImageView) v.findViewById(R.id.imgPhoto);

        }
    }

    // constructor
    public PhotoAdapter(Context context, ArrayList<Photo> myDataset) {
        mDataset = myDataset;
        mContext = context;
    }

    // Crea nuevas vistas (invocadas pot el layout manager)
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // crear una nueva vista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_photos, parent, false);

        return new PhotoAdapter.ViewHolder(v);
    }

    // Reemplazar el contenido del layout manager
    @Override
    public void onBindViewHolder(final PhotoAdapter.ViewHolder holder, final int position) {
        // - obtiene los elementos del dataset en una posicion definida
        Glide.with(mContext)
                .load(mDataset.get(position).getImageUrl())
                .placeholder(R.drawable.ic_download)
                .into(holder.photo);
        //Glide.with(mContext).load("http://goo.gl/gEgYUd").into(holder.mImgtView);
        //holder.photo.setImageResource(Integer.parseInt(mDataset.get(position).getImageUrl()));

    }

    // Devuelve el tamaño de tu dataset
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
