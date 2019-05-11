package com.juarez.demoappmelon.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juarez.demoappmelon.DetailUserActivity;
import com.juarez.demoappmelon.R;
import com.juarez.demoappmelon.model.User;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<User> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
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

    // Provide a suitable constructor (depends on the kind of dataset)
    public UsersAdapter(Context context, ArrayList<User> myDataset) {
        mDataset = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_users, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txtnombre.setText(mDataset.get(position).getNombre());
        holder.txtcorreo.setText(mDataset.get(position).getCorreo());
        holder.foto.setImageResource(mDataset.get(position).getFoto());

        //metodoOnclick() para cada elemento del recyclerview
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Bundle nombre = new Bundle();
                //User user = new User();

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

        //Glide.with(mContext).load(mDataset.get(position).getImageUrl()).into(holder.mImgtView);
        //Glide.with(mContext).load("http://goo.gl/gEgYUd").into(holder.mImgtView);
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
