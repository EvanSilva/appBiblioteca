package edu.badpals.bibliotecaandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import edu.badpals.bibliotecaandroid.API.models.Book;
import edu.badpals.bibliotecaandroid.API.models.BookLendingForm;
import edu.badpals.bibliotecaandroid.API.models.User;
import edu.badpals.bibliotecaandroid.API.repository.BookLendingRepository;
import edu.badpals.bibliotecaandroid.API.repository.BookRepository;
import edu.badpals.bibliotecaandroid.API.repository.BookViewModel;
import edu.badpals.bibliotecaandroid.API.repository.ImageRepository;
import edu.badpals.bibliotecaandroid.API.repository.UserProvider;
import okhttp3.ResponseBody;

public class BookDetail extends AppCompatActivity {

    BookViewModel vm;

    BookRepository br = new BookRepository();
    ImageRepository ir = new ImageRepository();
    BookLendingRepository blr = new BookLendingRepository();

    User usuarioLoggeado = UserProvider.getInstance();


    Button volver, reservar;
    ImageView imageView;
    TextView titulo, autor, isbn, reservado;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        vm = new ViewModelProvider(this).get(BookViewModel.class);

        volver = findViewById(R.id.btnReturn);
        reservar = findViewById(R.id.btnLendBook);
        imageView = findViewById(R.id.imgBook);
        titulo = findViewById(R.id.txtTitulo);
        autor = findViewById(R.id.txtAuthor);
        isbn = findViewById(R.id.txtIsbn);
        reservado = findViewById(R.id.txtDisponible);

        br.getBookById(getIntent().getIntExtra("id", 1), new BookRepository.ApiCallback<Book>() {
            @Override
            public void onSuccess(Book result) {
                if (result.getBookPicture().equals("")) {
                    imageView.setImageResource(R.drawable.missin);
                } else {
                    ir.getImage(result.getBookPicture(), new BookRepository.ApiCallback<ResponseBody>() {
                        @Override
                        public void onSuccess(ResponseBody result) {
                            try {
                                Bitmap bitmap = BitmapFactory.decodeStream(result.byteStream());
                                imageView.setImageBitmap(bitmap);
                            } catch (Exception e) {
                                imageView.setImageResource(R.drawable.missin);
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            imageView.setImageResource(R.drawable.missin);
                        }
                    });
                }
                titulo.setText(result.getTitle());
                autor.setText(result.getAuthor());
                isbn.setText(result.getIsbn());
                reservado.setText(result.isAvailable() ? "Disponible" : "No disponible");
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        volver.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), BookExhibitor.class);
            startActivity(intent);
        });

        reservar.setOnClickListener(view -> {

            BookLendingForm blf = new BookLendingForm(getIntent().getIntExtra("id", 1), usuarioLoggeado.getId());

            blr.lendBook(blf, new BookRepository.ApiCallback<Boolean>() {

                @Override
                public void onSuccess(Boolean result) {

                    Toast.makeText(BookDetail.this, "Libro reservado", Toast.LENGTH_SHORT).show();

                    Log.d("UWU", result.toString());
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(BookDetail.this, "Error al reservar el libro", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}

