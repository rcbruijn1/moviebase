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
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import rbdb.moviebase.R;
import rbdb.moviebase.Service.RentalReturnRequest;
import rbdb.moviebase.domain.Rental;

public class ReturnRentalActivity extends AppCompatActivity implements
        RentalReturnRequest.EditListener, Serializable{

    public final String TAG = this.getClass().getSimpleName();

    // UI Elements

    private TextView txtLoginErrorMsg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_rental);

        Intent intent = getIntent();
        Rental rental = (Rental) intent.getSerializableExtra("RENTAL_DATA");
        final String mInventoryId = rental.getInventoryID().toString();
        final String mRentalId = rental.getRentalID().toString();


        System.out.println(mInventoryId);

        Log.d(TAG, "Token gevonden - Films ophalen!");


        txtLoginErrorMsg = (TextView) findViewById(R.id.txtLoginErrorMessage);
        final RippleView rippleViewEdit = (RippleView) findViewById(R.id.btnEdit);

        rippleViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Context context = getApplicationContext();
                SharedPreferences sharedPref = context.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.rental_id), mRentalId);
                editor.putString(getString(R.string.inventory_id), mInventoryId);
                editor.commit();

                // TODO Checken of username en password niet leeg zijn
                // momenteel checken we nog niet

                editRental();
                Toasty.success(getApplicationContext(), "Returned!", Toast.LENGTH_LONG, true).show();
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
    private void editRental(){

        RentalReturnRequest requestEdit = new RentalReturnRequest(getApplicationContext(), this);
        requestEdit.handleGetRentals();
    }

    @Override
    public void onRentalsAvailable(ArrayList<Rental> rentals) {

    }
}