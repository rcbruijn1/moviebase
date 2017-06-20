package rbdb.moviebase.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.andexert.library.RippleView;

import java.io.Serializable;

import rbdb.moviebase.R;
import rbdb.moviebase.Service.RentRequest;
import rbdb.moviebase.domain.Film;

public class CommitAddActivity extends AppCompatActivity implements
        RentRequest.RentListener, Serializable{

    public final String TAG = this.getClass().getSimpleName();

    // UI Elements
    private EditText editTextCustomerId;
    private TextView txtLoginErrorMsg;

    private String mCustomerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_add);

        Intent intent = getIntent();
        Film film = (Film) intent.getSerializableExtra("FILM_DATA");
        final String mInventoryId = film.getInventoryID().toString(); intent.getSerializableExtra("ID");
        // final String mCustomerId = film.getCustomerID().toString(); intent.getSerializableExtra("cID")

        System.out.println(mInventoryId);

        Log.d(TAG, "Token gevonden - Films ophalen!");

        editTextCustomerId = (EditText) findViewById(R.id.edittextCustomerId);
        txtLoginErrorMsg = (TextView) findViewById(R.id.txtLoginErrorMessage);
        final RippleView rippleViewRent = (RippleView) findViewById(R.id.btnRent);

        rippleViewRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomerId = editTextCustomerId.getText().toString();



                Context context = getApplicationContext();
                SharedPreferences sharedPref = context.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.customer_id), mCustomerId);
                editor.putString(getString(R.string.inventory_id), mInventoryId);
                editor.commit();

                // TODO Checken of username en password niet leeg zijn
                // momenteel checken we nog niet

                rentFilm();
            }
        });




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








    /**
     * Start the activity to GET all Films from the server.
     */
    private void rentFilm(){

        RentRequest request = new RentRequest(getApplicationContext(), this);
        request.handleRentFilm();
    }

}