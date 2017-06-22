package rbdb.moviebase.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import java.io.Serializable;

import es.dmoral.toasty.Toasty;
import rbdb.moviebase.R;
import rbdb.moviebase.Service.RentRequest;
import rbdb.moviebase.domain.Film;

public class RentActivity extends AppCompatActivity implements
        RentRequest.RentListener, Serializable{

    public final String TAG = this.getClass().getSimpleName();

    // UI Elements

    private TextView txtLoginErrorMsg;

    private String mCustomerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        Intent intent = getIntent();
        Film film = (Film) intent.getSerializableExtra("FILM_DATA");
        final String mInventoryId = film.getInventoryID().toString(); intent.getSerializableExtra("ID");


        System.out.println(mInventoryId);

        Log.d(TAG, "Token gevonden - Films ophalen!");


        txtLoginErrorMsg = (TextView) findViewById(R.id.txtLoginErrorMessage);
        final RippleView rippleViewRent = (RippleView) findViewById(R.id.btnRent);

        rippleViewRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Context context = getApplicationContext();
                SharedPreferences sharedPref = context.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.customer_id), mCustomerId);
                editor.putString(getString(R.string.inventory_id), mInventoryId);
                editor.commit();



                rentFilm();
                Toasty.success(getApplicationContext(), "Rented!", Toast.LENGTH_LONG, true).show();
                Intent back = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(back);
                finish();
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