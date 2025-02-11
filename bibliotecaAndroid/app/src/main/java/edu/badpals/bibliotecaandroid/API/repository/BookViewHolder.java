package edu.badpals.bibliotecaandroid.API.repository;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.badpals.bibliotecaandroid.R;

public class BookViewHolder extends RecyclerView.ViewHolder{

    ImageView imgBook;
    TextView tvTitulo, tvIsbn, tvAutor;

    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        imgBook = itemView.findViewById(R.id.imgBook);
        tvTitulo = itemView.findViewById(R.id.txtBookTitle);
        tvIsbn = itemView.findViewById(R.id.txtBookIsbn);
        tvAutor = itemView.findViewById(R.id.txtBookAuthor);
    }
}
