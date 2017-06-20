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
import java.util.ArrayList;

import rbdb.moviebase.R;
import rbdb.moviebase.Service.EditRequest;
import rbdb.moviebase.Service.RentRequest;
import rbdb.moviebase.domain.Rental;

public class CommitEditActivity extends AppCompatActivity implements
        EditRequest.EditListener, Serializable{

    public final String TAG = this.getClass().getSimpleName();

    // UI Elements
    private EditText editTextCustomerId;
    private TextView txtLoginErrorMsg;

    private String mCustomerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_edit);

        Intent intent = getIntent();
        Rental rental = (Rental) intent.getSerializableExtra("RENTAL_DATA");
        final String mInventoryId = rental.getInventoryID().toString(); intent.getSerializableExtra("ID");
        final String mRentalId = rental.getInventoryID().toString(); intent.getSerializableExtra("rID");

        System.out.println(mInventoryId);

        Log.d(TAG, "Token gevonden - Films ophalen!");

        editTextCustomerId = (EditText) findViewById(R.id.editTextCustomerId);
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

        EditRequest requestEdit = new EditRequest(getApplicationContext(), this);
        requestEdit.handleGetRentals();
    }

    @Override
    public void onRentalsAvailable(ArrayList<Rental> rentals) {

    }
}