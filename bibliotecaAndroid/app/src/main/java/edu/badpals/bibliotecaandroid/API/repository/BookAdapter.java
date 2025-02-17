package edu.badpals.bibliotecaandroid.API.repository;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import edu.badpals.bibliotecaandroid.API.models.Book;
import edu.badpals.bibliotecaandroid.BookDetail;
import edu.badpals.bibliotecaandroid.R;
import okhttp3.ResponseBody;

public class BookAdapter extends RecyclerView.Adapter{

    List<Book> books = new ArrayList<>();
    ImageRepository ir = new ImageRepository();

    BookRepository bookRepository = new BookRepository();

    public BookAdapter(List<Book> books) {
        this.books = books;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_book, parent, false);
        return new BookViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        BookViewHolder viewHolder = (BookViewHolder) holder;
        Book book = books.get(position);

        viewHolder.tvTitulo.setText(book.getTitle());
        viewHolder.tvIsbn.setText("ISBN: " + book.getIsbn());
        viewHolder.tvAutor.setText("Autor: " + book.getAuthor());

        viewHolder.btnInformacion.setOnClickListener(view -> {

                Intent intent = new Intent(view.getContext(), BookDetail.class);
                intent.putExtra("id",  book.getId());
                view.getContext().startActivity(intent);

        });




        if (viewHolder.imgBook.equals("")) {

            viewHolder.imgBook.setImageResource(R.drawable.missin);

        } else {

            ir.getImage(book.getBookPicture(), new BookRepository.ApiCallback<ResponseBody>() {
                @Override
                public void onSuccess(ResponseBody result) {
                    try {

                        Bitmap bitmap = BitmapFactory.decodeStream(result.byteStream());
                        viewHolder.imgBook.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        viewHolder.imgBook.setImageResource(R.drawable.missin);
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    viewHolder.imgBook.setImageResource(R.drawable.missin);
                }
            });
        }

    }

    @Override
    public int getItemCount() {

        if(books == null) return 0;
        return books.size();
    }
}
