package edu.badpals.recyclerview.back;

import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import edu.badpals.recyclerview.R;

public class TarjetaViewHolder extends RecyclerView.ViewHolder{

    ImageView img, imgIsvisited;
    TextView nombre;
    TextView descripcion;
    Button button;

    public TarjetaViewHolder(@NonNull View view) {
        super(view);
        img = view.findViewById(R.id.imgvSlider);
        imgIsvisited = view.findViewById(R.id.imgMainVisited);
        nombre = view.findViewById(R.id.txtvTitulo);
        descripcion = view.findViewById(R.id.txtvDescripcion);
        button = view.findViewById(R.id.btnGotoExtendedView);
    }

}
