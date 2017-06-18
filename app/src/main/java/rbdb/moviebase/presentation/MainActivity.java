package rbdb.moviebase.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import rbdb.moviebase.R;
import rbdb.moviebase.Service.FilmRequest;
import rbdb.moviebase.domain.Film;
import rbdb.moviebase.domain.FilmAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        FilmRequest.FilmListener{

    public final String TAG = this.getClass().getSimpleName();

    // UI Elements
    private ListView listViewFilms;
    private BaseAdapter filmAdapter;
    private ArrayList<Film> films = new ArrayList<>();

    // The name for communicating Intents extras
    public final static String FILM_DATA = "FILMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(tokenAvailable()){
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_main);

            listViewFilms = (ListView) findViewById(R.id.listViewFilms);
            listViewFilms.setOnItemClickListener(this);
            filmAdapter = new FilmAdapter(this, getLayoutInflater(), films);
            listViewFilms.setAdapter(filmAdapter);
            //
            // We hebben een token. Je zou eerst nog kunnen valideren dat het token nog
            // geldig is; dat doen we nu niet.
            // Vul de lijst met Films
            //
            Log.d(TAG, "Token gevonden - Films ophalen!");
            getFilms();
        } else {
            //
            // Blijkbaar was er geen token - eerst inloggen dus
            //
            Log.d(TAG, "Geen token gevonden - inloggen dus");
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(login);
            // Sluit de huidige activity. Dat voorkomt dat de gebuiker via de
            // back-button zonder inloggen terugkeert naar het homescreen.
            finish();
        }


    }

    private boolean tokenAvailable() {
        boolean result = false;

        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.saved_token), "token");
        if (token != null && !token.equals("token")) {
            result = true;
        }
        return result;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "Position " + position + " is geselecteerd");

        Film film = films.get(position);
        Intent intent = new Intent(getApplicationContext(), FilmDetailActivity.class);
        intent.putExtra(FILM_DATA, film);
        startActivity(intent);
    }

    /**
     * Callback function - handle an ArrayList of ToDos
     *
     * @param filmArrayList
     */
    @Override
    public void onFilmsAvailable(ArrayList<Film> filmArrayList) {

        Log.i(TAG, "We hebben " + filmArrayList.size() + " items in de lijst");

        films.clear();
        for(int i = 0; i < filmArrayList.size(); i++) {
            films.add(filmArrayList.get(i));
        }
        filmAdapter.notifyDataSetChanged();
    }

    /**
     * Callback function - handle a single ToDo
     *
     * @param film
     */
    @Override
    public void onFilmAvailable(Film film) {
        films.add(film);
        filmAdapter.notifyDataSetChanged();
    }

    /**
     * Callback function
     *
     * @param message
     */
    @Override
    public void onFilmsError(String message) {
        Log.e(TAG, message);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * Start the activity to GET all Films from the server.
     */
    private void getFilms(){
        FilmRequest request = new FilmRequest(getApplicationContext(), this);
        request.handleGetAllFilms();
    }

}
