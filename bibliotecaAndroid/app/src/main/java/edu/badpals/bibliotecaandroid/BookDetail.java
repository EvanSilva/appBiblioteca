package edu.badpals.bibliotecaandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.badpals.bibliotecaandroid.API.models.Book;
import edu.badpals.bibliotecaandroid.API.models.BookLending;
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
    TextView titulo, autor, isbn, reservado, tvfechaDevolucion;

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
        tvfechaDevolucion = findViewById(R.id.txtFechaDevolucion);

        int idBookActual = getIntent().getIntExtra("id", 1);

        br.getBookById(idBookActual, new BookRepository.ApiCallback<Book>() {

            @Override
            public void onSuccess(Book result) {
                reservar.setText(result.isAvailable() ? "Reservar" : "Devolver");

                if (result.getBookPicture().isEmpty()) {
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

                List<BookLending> prestamosHistorico = result.getBookLendings();
                BookLending prestamoADevolver = null;

                for (BookLending prestamo : prestamosHistorico) {
                    if (prestamo.getReturnDate() == null) {
                        prestamoADevolver = prestamo;
                        break;
                    }
                }

                if (prestamoADevolver != null) {
                    int idPrestatario = prestamoADevolver.getUserId();
                    int idUsuarioLogueado = usuarioLoggeado.getId();

                    Log.d("DEBUG", "Libro en préstamo. Prestatario: " + idPrestatario + ", Usuario logueado: " + idUsuarioLogueado);

                    if (idUsuarioLogueado != idPrestatario) {
                        Log.d("DEBUG", "Ocultando botón: El usuario actual no es el prestatario");
                        reservar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        volver.setOnClickListener(view -> {
            finish();
        });

        br.getBookById(idBookActual, new BookRepository.ApiCallback<Book>() {
            @Override
            public void onSuccess(Book bookActual) {

                BookLending prestamoADevolver = getBookLending(bookActual);

                if (prestamoADevolver != null) {

                    calcularFechaDevolucion(prestamoADevolver.getLendDate());

                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });


        reservar.setOnClickListener(view -> {

            br.getBookById(idBookActual, new BookRepository.ApiCallback<Book>() {
                @Override
                public void onSuccess(Book bookActual) {

                    if (bookActual.isAvailable()) {

                        blr.lendBook(usuarioLoggeado.getId(), idBookActual, new BookRepository.ApiCallback<Boolean>() {
                            @Override
                            public void onSuccess(Boolean result) {

                                reservar.setText("Devolver");
                                reservado.setText("No Disponible");

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                String fechaPrestamo = sdf.format(new Date());
                                calcularFechaDevolucion(fechaPrestamo);

                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(BookDetail.this, "Error al reservar el libro", Toast.LENGTH_SHORT).show();
                            }
                        });


                    } else {

                        BookLending prestamoADevolver = getBookLending(bookActual);
                        if (prestamoADevolver != null) {

                            int idPrestatario = prestamoADevolver.getUserId();
                            int idUsuarioLogueado = usuarioLoggeado.getId();

                            if (idUsuarioLogueado != idPrestatario) {
                                Toast.makeText(BookDetail.this, "No puedes devolver un libro que no has prestado", Toast.LENGTH_SHORT).show();
                                return;
                            }


                            blr.returnBook(prestamoADevolver.getId(), new BookRepository.ApiCallback<Boolean>() {
                                @Override
                                public void onSuccess(Boolean result) {

                                    Toast.makeText(BookDetail.this, "Se ha regresado el libro", Toast.LENGTH_SHORT).show();
                                    reservar.setText("Reservar");
                                    reservado.setText("Disponible");
                                    tvfechaDevolucion.setText("");

                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    Toast.makeText(BookDetail.this, "Error al devolver el libro", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                }
            });
        });

    }

    private static @Nullable BookLending getBookLending(Book bookActual) {

        List<BookLending> prestamosHistorico = bookActual.getBookLendings();
        BookLending prestamoADevolver = null;

        for (BookLending prestamo : prestamosHistorico) {
            if (prestamo.getReturnDate() == null) {
                prestamoADevolver = prestamo;
            }
        }
        return prestamoADevolver;
    }

    private void calcularFechaDevolucion(String fechaPrestamo) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat sdfFormatoFinal = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {

            Date date = sdf.parse(fechaPrestamo);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 14); // Sumar 14 días

            String fechaDevolucion = sdfFormatoFinal.format(calendar.getTime());
            tvfechaDevolucion.setText("Fecha de devolución: " + fechaDevolucion);


        } catch (ParseException e) {

            System.out.println("Error al convertir la fecha: " + e.getMessage());

        }
    }

}
