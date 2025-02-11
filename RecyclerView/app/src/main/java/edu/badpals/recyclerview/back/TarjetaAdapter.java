package edu.badpals.recyclerview.back;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.nfc.cardemulation.CardEmulation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.badpals.recyclerview.Extension;
import edu.badpals.recyclerview.R;
import edu.badpals.recyclerview.entities.Tarjeta;

public class TarjetaAdapter extends RecyclerView.Adapter {

    List<Tarjeta> tarjetas;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_slides, parent, false);
        return new TarjetaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        TarjetaViewHolder tarjetaViewHolder = (TarjetaViewHolder) holder;
        Tarjeta tarjeta = tarjetas.get(position);
        tarjetaViewHolder.nombre.setText(tarjeta.getNombre());
        tarjetaViewHolder.descripcion.setText(tarjeta.getDescripcion());
        tarjetaViewHolder.img.setImageResource(tarjeta.getImg());

        if (tarjeta.isVisited()) {
            tarjetaViewHolder.imgIsvisited.setImageResource(R.drawable.color_verde);
        } else {
            tarjetaViewHolder.imgIsvisited.setImageResource(R.drawable.color_rosa);
        }

        tarjetaViewHolder.button.setOnClickListener(view -> {

            Intent intent = new Intent(view.getContext(), Extension.class);

            intent.putExtra("nombre", tarjeta.getNombre());
            intent.putExtra("descripcion", tarjeta.getDescripcion());
            intent.putExtra("img", tarjeta.getImg());

            startActivity(view.getContext(), intent, null);

        });



    }

    @Override
    public int getItemCount() {
        return tarjetas.size();
    }

    public TarjetaAdapter(List<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }







}
