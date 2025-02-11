package edu.badpals.recyclerview.back;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import edu.badpals.recyclerview.R;
import edu.badpals.recyclerview.entities.Tarjeta;

public class TarjetaViewModel extends ViewModel {

    private final MutableLiveData<List<Tarjeta>> tarjetasLiveData = new MutableLiveData<>();

    public TarjetaViewModel() {
        cargarTarjetas();
    }

    private void cargarTarjetas() {
        List<Tarjeta> tarjetas = new ArrayList<>();

        tarjetas.add(new Tarjeta(R.drawable.aguila ,"Aguila, ave rapaz diurna de gran tamaño, con cabeza pequeña, pico ganchudo y fuertes garras, que se alimenta de presas vivas.", "Aguila"));
        tarjetas.add(new Tarjeta(R.drawable.ballena, "Ballena, mamífero marino de gran tamaño, con forma hidrodinámica, cabeza grande, aleta dorsal y cola horizontal.", "Ballena"));
        tarjetas.add(new Tarjeta(R.drawable.caballo, "Caballo, mamífero herbívoro de gran tamaño, con cuerpo esbelto, cabeza alargada, crin y cola largas, patas largas y fuerte.", "Caballo"));
        tarjetas.add(new Tarjeta(R.drawable.delfin, "Delfín, mamífero marino de tamaño mediano, con cuerpo hidrodinámico, cabeza redondeada, aleta dorsal y cola horizontal.", "Delfin"));
        tarjetas.add(new Tarjeta(R.drawable.canario, "Canario, es un PÁAAAAAAAAAAAAAJARO", "Canario"));
        tarjetas.add(new Tarjeta(R.drawable.gato, "Gato, mamífero carnívoro de tamaño mediano, con cuerpo ágil, cabeza redondeada, orejas puntiagudas, ojos grandes y cola larga.", "Gato"));
        tarjetas.add(new Tarjeta(R.drawable.perro, "Perro, mamífero carnívoro de tamaño mediano, con cuerpo robusto, cabeza redondeada, orejas caídas, ojos grandes y cola larga.", "Perro"));
        tarjetas.add(new Tarjeta(R.drawable.vaca, "Vaca, hace moo", "Vaca"));

        tarjetasLiveData.setValue(tarjetas);
    }

    public MutableLiveData<List<Tarjeta>> getTarjetasLiveData() {
        return tarjetasLiveData;
    }
}