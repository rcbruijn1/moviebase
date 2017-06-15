package rbdb.moviebase.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rbdb.moviebase.R;
import rbdb.moviebase.domain.MovieItem;
import rbdb.moviebase.domain.MovieMapper;

/**
 * Created by Ruben on 15-6-2017.
 */

public class MovieRequest {

    private Context context;
    public final String TAG = this.getClass().getSimpleName();

    // De aanroepende class implementeert deze interface.
    private MovieRequest.MovieListener listener;

    /**
     * Constructor
     *
     * @param context
     * @param listener
     */
    public MovieRequest(Context context, MovieRequest.MovieListener listener) {
        this.context = context;
        this.listener = listener;
    }

    /**
     * Verstuur een GET request om alle Movies op te halen.
     */
    public void handleGetAllMovies() {

        Log.i(TAG, "handleGetAllMovies");

        // Haal het token uit de prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
        if(token != null && !token.equals("dummy default token")) {

            Log.i(TAG, "Token gevonden, we gaan het request uitvoeren");
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    Config.URL_MOVIES,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response
                            Log.i(TAG, response.toString());
                            ArrayList<MovieItem> result = MovieMapper.mapMovieList(response);
                            listener.moviesAvailable(result);
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
        }
    }

    //
    // Callback interface - implemented by the calling class (MainActivity in our case).
    //
    public interface MovieListener {
        // Callback function to return a fresh list of Movie
        void moviesAvailable(ArrayList<MovieItem> movieItems);

        // Callback function to handle a single added Movie
        void moviesAvailable(MovieItem movieItem);

        // Callback to handle serverside API errors
        void moviesError(String message);
    }

}

