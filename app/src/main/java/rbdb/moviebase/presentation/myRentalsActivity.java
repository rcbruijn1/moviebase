package rbdb.moviebase.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import rbdb.moviebase.R;
import rbdb.moviebase.Service.RentedFilmRequest;
import rbdb.moviebase.domain.Rental;
import rbdb.moviebase.domain.RentalAdapter;

public class myRentalsActivity extends AppCompatActivity implements OnItemClickListener,
        RentedFilmRequest.RentedFilmListener{

    public final String TAG = this.getClass().getSimpleName();

    // UI Elements
    private ListView listViewRentals;
    private BaseAdapter rentalAdapter;
    private ArrayList<Rental> rentals = new ArrayList<>();

    // The name for communicating Intents extras
    public final static String FILM_DATA = "FILMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_rentals);


        listViewRentals   = (ListView) findViewById(R.id.listViewRentals);
        rentalAdapter = new RentalAdapter(this, getLayoutInflater(), rentals);
        listViewRentals.setAdapter(rentalAdapter);
        listViewRentals.setOnItemClickListener(this);

        rentalAdapter.notifyDataSetChanged();

        //
        // We hebben een token. Je zou eerst nog kunnen valideren dat het token nog
        // geldig is; dat doen we nu niet.
        // Vul de lijst met Films
        //
        Log.d(TAG, "Token gevonden - Films ophalen!");
        getRentedFilms();


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




        Intent intent = new Intent(getApplicationContext(), ReturnRentalActivity.class);
        Rental rental = rentals.get(position);
        String inventoryID = rental.getInventoryID().toString();
        String rentalID = rental.getRentalID().toString();


        intent.putExtra("ID", inventoryID);
        intent.putExtra("rID", rentalID);
        intent.putExtra("RENTAL_DATA", rental);
        startActivity(intent);
    }

    /**
     * Callback function - handle an ArrayList of ToDos
     *
     * @param rentalArrayList
     */
    @Override
    public void onRentedFilmsAvailable(ArrayList<Rental> rentalArrayList) {

        Log.i(TAG, "We hebben " + rentalArrayList.size() + " items in de lijst");

        rentals.clear();
        for(int i = 0; i < rentalArrayList.size(); i++) {
            rentals.add(rentalArrayList.get(i));
        }
        rentalAdapter.notifyDataSetChanged();
    }

    /**
     * Callback function - handle a single ToDo
     *
     * @param rental
     */
    @Override
    public void onRentedFilmAvailable(Rental rental) {
        rentals.add(rental);
        rentalAdapter.notifyDataSetChanged();
    }

    /**
     * Callback function
     *
     * @param message
     */
    @Override
    public void onRentedFilmsError(String message) {
        Log.e(TAG, message);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * Start the activity to GET all Films from the server.
     */
    private void getRentedFilms(){
        RentedFilmRequest request = new RentedFilmRequest(getApplicationContext(), this);
        request.handleGetAllRentedFilms();
    }

}
