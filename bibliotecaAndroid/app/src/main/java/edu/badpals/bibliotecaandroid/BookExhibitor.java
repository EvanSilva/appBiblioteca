package edu.badpals.bibliotecaandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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


    private final ActivityResultLauncher<Intent> qrLauncher = registerForActivityResult(

            new ActivityResultContracts.StartActivityForResult(),

            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        IntentResult scanResult = IntentIntegrator.parseActivityResult(result.getResultCode(), data);
                        if (scanResult != null && scanResult.getContents() != null) {
                            String qrData = scanResult.getContents();

                            SharedPreferences prefs = getSharedPreferences("MisDatosQR", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("ultimo_qr", qrData);
                            editor.apply();

                            Intent intent = new Intent(this, BookDetail.class);
                            intent.putExtra("id", qrData);
                            startActivity(intent);

                            Toast.makeText(this, "Código escaneado: " + qrData, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "No se obtuvo información del QR", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "No se recibieron datos del escáner", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show();
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
            finish();
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