package rbdb.moviebase.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rbdb.moviebase.R;
import rbdb.moviebase.domain.Rental;
import rbdb.moviebase.domain.RentalMapper;

/**
 * Created by Operator on 6/20/2017.
 */

public class RentedFilmRequest {

    private Context context;
    public final String TAG = this.getClass().getSimpleName();

    // De aanroepende class implementeert deze interface.
    private RentedFilmRequest.RentedFilmListener listener;

    /**
     * Constructor
     *
     * @param context
     * @param listener
     */
    public RentedFilmRequest(Context context, RentedFilmRequest.RentedFilmListener listener) {
        this.context = context;
        this.listener = listener;
    }

    /**
     * Verstuur een GET request om alle ToDo's op te halen.
     */
    public void handleGetAllRentedFilms() {

        Log.i(TAG, "handleGetAllRentedFilms");

        // Haal het token uit de prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "token");


        Log.i(TAG, "Token gevonden, we gaan het request uitvoeren");

        // Haal het token uit de prefs
        SharedPreferences customerPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final int customerId = sharedPref.getInt("customer_id", 1);


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET,
                Config.URL_RENTAL + "/" + customerId,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Succesvol response
                        Log.i(TAG, response.toString());
                        ArrayList<Rental> result = RentalMapper.mapRentalList(response);
                        listener.onRentedFilmsAvailable(result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handleErrorResponse(error);
                        Log.e(TAG, error.toString());
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(context,
                                    context.getString(R.string.error_network_timeout),
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                        } else if (error instanceof ServerError) {
//                            try {
//                                TimeUnit.SECONDS.sleep(5);
//                                handleGetAllRentedFilms();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                        } else if (error instanceof NetworkError) {
                            //TODO
                        } else if (error instanceof ParseError) {
                            //TODO
                        }
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
    public interface RentedFilmListener {
        // Callback function to return a fresh list of Films
        void onRentedFilmsAvailable(ArrayList<Rental> rentals);

        // Callback function to handle a single added Film.
        void onRentedFilmAvailable(Rental rentals);

        // Callback to handle serverside API errors
        void onRentedFilmsError(String message);
    }

}


