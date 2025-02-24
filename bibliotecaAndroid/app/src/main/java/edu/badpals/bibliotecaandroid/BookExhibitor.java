package edu.badpals.bibliotecaandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.badpals.bibliotecaandroid.API.models.Book;
import edu.badpals.bibliotecaandroid.API.repository.BookAdapter;
import edu.badpals.bibliotecaandroid.API.repository.BookRepository;
import edu.badpals.bibliotecaandroid.API.repository.BookViewModel;

public class BookExhibitor extends AppCompatActivity {

    RecyclerView recyclerViewBookExhibitor;
    BookViewModel vm;
    EditText etFltrado;
    BookAdapter bookAdapter;
    Button btnQrSeach, btnGoBackToHall;
    BookRepository br = new BookRepository();


    private final ActivityResultLauncher<Intent> qrLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(
                            result.getResultCode(), result.getResultCode(), data);

                    if (scanResult != null && scanResult.getContents() != null) {
                        String qrContenido = scanResult.getContents();

                            Intent intent = new Intent(this, BookDetail.class);
                            intent.putExtra("id", Integer.parseInt(qrContenido));

                        Toast.makeText(this, "Código QR: " + qrContenido, Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(this, "No se escaneó ningún código", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_exhibitor_bookstore);

        etFltrado = findViewById(R.id.etFiltrado);
        btnQrSeach = findViewById(R.id.btnQrSearch);
        btnGoBackToHall = findViewById(R.id.btnBackToHall);

        recyclerViewBookExhibitor = findViewById(R.id.rvBookExhibitor);
        recyclerViewBookExhibitor.setLayoutManager(new LinearLayoutManager(this));

        vm = new ViewModelProvider(this).get(BookViewModel.class);
        vm.cargarBooks();

        bookAdapter = new BookAdapter(new ArrayList<>());
        recyclerViewBookExhibitor.setAdapter(bookAdapter);

        vm.getBooksLiveData().observe(this, books -> {

            if (books != null) {
                bookAdapter = new BookAdapter(books);
                recyclerViewBookExhibitor.setAdapter(bookAdapter);
            }

        });

        btnGoBackToHall.setOnClickListener(view -> {
            Intent hallBookstore = new Intent(view.getContext(), HallBookstore.class);
            startActivity(hallBookstore);
        });

        etFltrado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String filtro = s.toString().toLowerCase();
                List<Book> books = vm.getBooksLiveData().getValue();
                if (books != null) {
                    List<Book> booksFiltrados = books.stream()
                            .filter(book -> book.getTitle().toLowerCase().contains(filtro) ||
                                    book.getAuthor().toLowerCase().contains(filtro))
                            .collect(Collectors.toList());

                    bookAdapter = new BookAdapter(booksFiltrados);
                    recyclerViewBookExhibitor.setAdapter(bookAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnQrSeach.setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(BookExhibitor.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            integrator.setPrompt("Escanea un código QR");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(true);

            Intent scanIntent = integrator.createScanIntent();
            qrLauncher.launch(scanIntent);
        });
    }
}