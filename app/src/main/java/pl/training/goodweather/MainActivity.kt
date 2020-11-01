package pl.training.goodweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

}

/*
TODO:
 - Brak prawidłowego podziału odpowiedzialności i silne sprzężenie komponentów (kłopoty z utrzymaniem, rozwojem i testowaniem kodu)
 - Brak podziału domenowego
 - Brak testów
 - Brak architektury zapewniającej sensowne zarządzanie stanem aplikacji (wielokrotne ładowanie danych, przekazywanie i synchronizacja stanu między widokami)
 - Bark dedykowanego modelu danych (aplikacja wykorzystuje model z zewnętrznego API)
 - Konfiguracja zmieszana z logiką
 - Złożoność (łatwo popełnić błąd np. w zmieniając wątek / kontekst wykonania, doprowadzić do wycieku pamięci, nie przewidzieć kolejności wykonania, konsekwencji wyjątku)
*/