package edu.badpals.bibliotecaandroid.API.repository;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import edu.badpals.bibliotecaandroid.API.models.Book;
public class BookAdapter extends RecyclerView.Adapter{

    List<Book> books;

    BookRepository bookRepository = new BookRepository();


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_slides, parent, false);
        return new BookViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        BookViewHolder viewHolder = (BookViewHolder) holder;
        Book book = books.get(position);
        viewHolder.tvTitulo.setText(book.getTitle());
        viewHolder.tvIsbn.setText(book.getIsbn());
        viewHolder.tvAutor.setText(book.getAuthor());

        bookRepository.getBookById(book.getId(), new BookRepository.ApiCallback<Book>() {
            @Override
            public void onSuccess(Book book) {


                viewHolder.imgBook.setImageResource(book.getBookPicture());

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("BookAdapter", "Error fetching book", t);
            }
        });



    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
