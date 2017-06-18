package rbdb.moviebase.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import rbdb.moviebase.R;
import rbdb.moviebase.domain.Film;

public class MainActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();

    // UI Elements
    private ListView listViewFilms;
    private BaseAdapter filmAdapter;
    private ArrayList<Film> films = new ArrayList<>();

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
            // Vul de lijst met ToDos
            //
            Log.d(TAG, "Token gevonden - ToDos ophalen!");
            getToDos();
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
}
