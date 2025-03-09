package edu.badpals.bibliotecaandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import edu.badpals.bibliotecaandroid.API.models.Book;
import edu.badpals.bibliotecaandroid.API.models.User;
import edu.badpals.bibliotecaandroid.API.repository.BookAdapter;
import edu.badpals.bibliotecaandroid.API.repository.BookViewModel;
import edu.badpals.bibliotecaandroid.API.repository.UserProvider;

public class HallBookstore extends AppCompatActivity {

    Button btnGoToBookExhibitor, btnPersonalData;
    BookViewModel vm;
    BookAdapter bookAdapter;
    TextView txtDatoCurioso;
    RecyclerView recyclerViewNovedades;

    List<String> datosCuriosos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        User usuarioLoggeado = UserProvider.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_bookstore);
        btnGoToBookExhibitor = findViewById(R.id.btnGotoBookExhibitor);

        btnGoToBookExhibitor.setOnClickListener(view -> {

            Intent bookExhibitor = new Intent(view.getContext(), BookExhibitor.class);
            startActivity(bookExhibitor);

        });

        recyclerViewNovedades = findViewById(R.id.rvNovedades);
        recyclerViewNovedades.setLayoutManager(new LinearLayoutManager(this));

        vm = new ViewModelProvider(this).get(BookViewModel.class);

        vm.getBooksLiveData().observe(this, books -> {
            if (books != null && !books.isEmpty()) {
                Collections.shuffle(books);

                List<Book> shuffledBooks = new ArrayList<>();
                shuffledBooks.add(books.get(0));
                shuffledBooks.add(books.get(1));

                bookAdapter = new BookAdapter(shuffledBooks);
                recyclerViewNovedades.setAdapter(bookAdapter);
            } else {
                Toast.makeText(this, "No recomendaciones diarias disponibles", Toast.LENGTH_SHORT).show();
            }
        });

        btnPersonalData = findViewById(R.id.btnGotoPersonalData);
        btnPersonalData.setOnClickListener(view -> {

            Intent personalData = new Intent(view.getContext(), PersonalData.class);
            startActivity(personalData);

        });

        txtDatoCurioso = findViewById(R.id.txtDatoCurioso);
        List<String> datosCuriosos = new ArrayList<>(
                Arrays.asList(
                        "Dato curioso #1: ¡En 1956, el primer lenguaje de programación, Fortran, era tan ineficiente que se tardaba más en compilar un programa que en ejecutarlo!",
                        "Dato curioso #2: ¡El código más corto jamás escrito para un 'Hello World' en un lenguaje de programación fue en Malbolge, un lenguaje esotérico, con solo 8 caracteres!",
                        "Dato curioso #3: ¡En 1969, los programadores usaban tarjetas perforadas que podían ser fácilmente arruinadas con un café derramado, causando grandes pérdidas de datos!",
                        "Dato curioso #4: ¡El lenguaje de programación más confuso y delirante es INTERCAL, diseñado para hacer que los programadores odien escribir código!",
                        "Dato curioso #5: ¡En los años 80, los programadores usaban cintas de casete para almacenar programas, lo que provocaba que a menudo los datos se desmagnetizaran por accidente!",
                        "Dato curioso #6: ¡El primer virus de computadora fue escrito en 1986 en un lenguaje llamado Brain, que estaba destinado a ser una broma y no un software malicioso!",
                        "Dato curioso #7: ¡El nombre de 'bug' para un error en el software proviene de un insecto real que se metió en una computadora en 1947, causando un fallo!",
                        "Dato curioso #8: ¡En 2006, se descubrió que el 70% de las líneas de código escritas en Java eran completamente innecesarias y solo servían para hacer que el código pareciera más complejo!",
                        "Dato curioso #9: ¡El lenguaje de programación Perl es tan flexible que tiene 7 formas diferentes de hacer la misma cosa, lo que a veces genera más confusión que soluciones!",
                        "Dato curioso #10: ¡La función 'goto' fue eliminada de muchos lenguajes de programación modernos porque su uso generaba programas difíciles de mantener y le daba caos a los desarrolladores!",
                        "Dato curioso #11: ¡En 1995, el famoso lenguaje de programación Java fue lanzado como 'escriba una vez, ejecute en cualquier lugar', pero en realidad, necesitaba miles de actualizaciones para funcionar bien!",
                        "Dato curioso #12: ¡En un experimento en 2011, los programadores encontraron que un error en un sistema de control de tráfico aéreo hizo que un avión pasara por encima de otro, debido a un pequeño error de codificación!",
                        "Dato curioso #13: ¡El lenguaje COBOL, que sigue siendo usado en sistemas bancarios, fue diseñado para ser tan simple que incluso un mono con un teclado podría escribir código funcional!",
                        "Dato curioso #14: ¡El término 'hackeo' proviene de los años 60, cuando los estudiantes de MIT hackeaban máquinas de escribir para escribir sus ensayos más rápido, ¡nada que ver con las intrusiones informáticas modernas!",
                        "Dato curioso #15: ¡La primera aplicación para teléfono móvil fue creada en 1994 en un lenguaje llamado 'C' y se trataba de un juego de ajedrez!",
                        "Dato curioso #16: ¡El lenguaje de programación Lisp, creado en 1958, se caracteriza por tener la menor cantidad de reglas sintácticas, lo que lo convierte en un dolor de cabeza para quienes intentan leer su código!",
                        "Dato curioso #17: ¡El peor error de programación en la historia fue el fallo de un sistema de control de misiles en 1991 que hizo que un misil se despidiera hacia un objetivo no deseado!",
                        "Dato curioso #18: ¡El lenguaje Assembly es tan cercano al lenguaje de la máquina que algunos programadores dicen que es como escribir en jeroglíficos digitales!",
                        "Dato curioso #19: ¡En 2012, un programador encontró que el error más común en su código provenía de una falta de paréntesis, ¡una simple comedia de errores de programación!",
                        "Dato curioso #20: ¡En 1993, el sistema operativo Windows 3.1 fue tan lleno de bugs que incluso el propio Bill Gates decía que se necesitaban 'mínimos 500 parches' para que funcionara correctamente!",
                        "Dato curioso #21: ¡El término 'stack overflow' no se refiere a un término matemático, sino a un error que ocurre cuando una función se llama a sí misma demasiadas veces!",
                        "Dato curioso #22: ¡En un experimento de 2015, se descubrió que los programadores pueden escribir más de 10.000 líneas de código en una semana, pero a menudo ese código nunca se usa en producción!",
                        "Dato curioso #23: ¡En 2016, un programador descubrió que un sistema de inteligencia artificial podía escribir código mejor que un ser humano, aunque terminó creando su propio conjunto de errores!",
                        "Dato curioso #24: ¡El famoso sistema operativo Unix fue creado en 1969 por un grupo de programadores que se encargaban de escribir código mientras estaban medio borrachos!",
                        "Dato curioso #25: ¡En 2017, un programador español ganó un concurso por escribir la mayor cantidad de código en la menor cantidad de tiempo, pero se olvidó de agregar comentarios en su código!",
                        "Dato curioso #26: ¡El lenguaje Python fue nombrado en honor al grupo de comedia Monty Python, no por la serpiente, ¡pero sigue siendo una de las opciones más populares por su sintaxis sencilla!",
                        "Dato curioso #27: ¡En 2018, un programador descubrió que su código estaba siendo alterado por un error de codificación que hacía que sus variables cambiaran por sí solas, creando caos en su proyecto!",
                        "Dato curioso #28: ¡En 1997, un error de codificación en el software de un avión hizo que el avión comenzara a perder altitud, lo que provocó un accidente que resultó en 150 personas muertas!",
                        "Dato curioso #29: ¡La primera línea de código de 'The Matrix' fue escrita por un programador conocido como 'El Arquitecto', ¡quien era famoso por escribir código que parecía no tener sentido!",
                        "Dato curioso #30: ¡En 2014, un programador descubrió que una página web de una tienda online no funcionaba porque el código CSS estaba tan desordenado que las imágenes se superponían!",
                        "Dato curioso #31: ¡El lenguaje C es tan antiguo que algunos programadores lo consideran un 'lenguaje de abuelos', pero sigue siendo el más utilizado en sistemas operativos!",
                        "Dato curioso #32: ¡El concepto de 'debugging' proviene de una historia en la que un ingeniero de software encontró un insecto real dentro de la computadora que causaba el fallo!",
                        "Dato curioso #33: ¡Los programadores se han pasado horas y horas escribiendo código sin que los resultados funcionen, solo para descubrir que se olvidaron de un simple punto y coma!",
                        "Dato curioso #34: ¡El término 'refactorizar' en programación se refiere a la acción de reescribir código para hacerlo más legible, pero no siempre es la solución a los problemas!",
                        "Dato curioso #35: ¡El primer fallo de seguridad conocido en la historia de la informática fue encontrado en 1966, y fue causado por una simple ejecución errónea de código!",
                        "Dato curioso #36: ¡En la programación funcional, las variables no pueden cambiar su valor, lo que hace que los programadores se vuelvan aún más paranoicos cuando tienen que manejar errores!",
                        "Dato curioso #37: ¡El lenguaje esotérico más difícil de aprender se llama 'Malbolge', y hasta los programadores expertos se rindieron después de intentar escribir un simple programa!",
                        "Dato curioso #38: ¡El primer 'bug' reportado en un sistema de software fue un insecto real que se encontró atrapado dentro de una computadora, causando un error de hardware!",
                        "Dato curioso #39: ¡En algunos lenguajes de programación, como JavaScript, es posible declarar variables sin asignarles valor, ¡lo que a veces resulta en resultados completamente aleatorios!",
                        "Dato curioso #40: ¡La cantidad de código basura que un programador escribe durante su carrera podría llenar una biblioteca entera, pero siempre se considera parte del proceso de aprendizaje!",
                        "Dato curioso #41: ¡El término 'algoritmo' proviene de un matemático persa del siglo IX llamado Al-Juarismi, quien lo usó para resolver ecuaciones!",
                        "Dato curioso #42: ¡El lenguaje Ruby fue creado para hacer que los programadores pudieran escribir código de forma divertida, ¡y hasta tiene un nombre inspirado en una piedra preciosa!",
                        "Dato curioso #43: ¡En 1998, un error de codificación en un sistema de votación electrónica hizo que una victoria electoral fuera alterada por un simple fallo de programación!",
                        "Dato curioso #44: ¡Los programadores de videojuegos a menudo escriben líneas de código para hacer que los personajes de los juegos puedan moverse, ¡pero algunos olvidan agregar un límite de velocidad!",
                        "Dato curioso #45: ¡El término 'hacker' originalmente no significaba una persona malintencionada, sino a alguien que podía modificar el código de un programa de manera eficiente!",
                        "Dato curioso #46: ¡La creación de un sistema operativo requiere tanto código que algunos programadores tienen que escribir durante años sin ver resultados!",
                        "Dato curioso #47: ¡En los primeros días de la programación, los programadores usaban lápices y papel para escribir sus programas, ¡y luego los pasaban a las computadoras usando tarjetas perforadas!",
                        "Dato curioso #48: ¡El lenguaje JavaScript fue creado en solo 10 días por un programador llamado Brendan Eich, ¡pero hoy en día es uno de los lenguajes más utilizados del mundo!",
                        "Dato curioso #49: ¡El nombre 'byte' proviene del término 'bite', y originalmente significaba una porción de memoria que podía ser procesada a la vez por una computadora!",
                        "Dato curioso #50: ¡El error más raro en programación se llama 'Heisenbug', que es un error que cambia su comportamiento cuando se intenta diagnosticar!"
                )
        );

        this.datosCuriosos = datosCuriosos;

        Collections.shuffle(datosCuriosos);
        txtDatoCurioso.setText(datosCuriosos.get(0));


        // AÑADIR LA TOOLBAR

        Toolbar tb = findViewById(R.id.toolbarMain);
        setSupportActionBar(tb);

        addMenuProvider(new MenuProvider() {

            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menuvarios, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.toolbarWhoami) {

                    Toast.makeText(HallBookstore.this, "Bienvenido, " + usuarioLoggeado.getName() + ", con el ID: " + usuarioLoggeado.getId() , Toast.LENGTH_SHORT).show();

                }
                if (id == R.id.toolbarExit) {

                    Intent newIntent = new Intent(HallBookstore.this, MainActivity.class);
                    startActivity(newIntent);
                    finish();
                }
                if (id == R.id.toolbarDatoCurioso) {

                    Collections.shuffle(datosCuriosos);
                    txtDatoCurioso.setText(datosCuriosos.get(0));
                    return true;

                }

                return false;
            }
        });


        TextView tv = findViewById(R.id.txtDatoCurioso);
        registerForContextMenu(tv);

    }

    // TOOLBAR CONTEXTUAL ANCLADA A UN OBJETO

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menudatocurioso, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.toolbarDatoCurioso) {

            Collections.shuffle(datosCuriosos);
            txtDatoCurioso.setText(datosCuriosos.get(0));
            return true;

        }

        return super.onContextItemSelected(item);
    }


}