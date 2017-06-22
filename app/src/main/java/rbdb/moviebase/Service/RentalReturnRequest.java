package rbdb.moviebase.Service;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rbdb.moviebase.R;
import rbdb.moviebase.domain.Rental;
import rbdb.moviebase.domain.RentalMapper;

/**
 * Created by Daniel on 18-6-2017.
 */

public class RentalReturnRequest {

    private Context context;
    public final String TAG = this.getClass().getSimpleName();

    // De aanroepende class implementeert deze interface.
    private RentalReturnRequest.EditListener listener;

    /**
     * Constructor
     *
     * @param context
     * @param listener
     */
    public RentalReturnRequest(Context context, RentalReturnRequest.EditListener listener) {
        this.context = context;
        this.listener = listener;
    }

    /**
     * Verstuur een GET request om alle ToDo's op te halen.
     */
    public void handleGetRentals() {

        Log.i(TAG, "handleGetRentals");

        // Haal het token uit de prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "token");
     //   final String cusId = sharedPref.getString(context.getString(R.string.inventory_id), "Id");
        final String invId = sharedPref.getString(context.getString(R.string.rental_id), "invId");
        //  if(token != null && !token.equals("token")) {

        Log.i(TAG, "Token gevonden, we gaan het request uitvoeren");
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.PUT,
                Config.URL_RENTAL + "/" + invId,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Succesvol response
                        Log.i(TAG, response.toString());
                        ArrayList<Rental> result = RentalMapper.mapRentalList(response);
                        listener.onRentalsAvailable(result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handleErrorResponse(error);
                        Log.e(TAG, error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        // Access the RequestQueue through your singleton class.
        VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
        //   }
    }


    //
    // Callback interface - implemented by the calling class (RentableMovieActivity in our case).
    //
    public interface EditListener {
        // Callback function to return a fresh list of Films
        void onRentalsAvailable(ArrayList<Rental> rentals);
    }

}



