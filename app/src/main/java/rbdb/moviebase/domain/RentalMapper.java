package rbdb.moviebase.domain;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Daniel on 18-6-2017.
 */

public class RentalMapper {
    // De JSON attributen die we uitlezen
    public static final String FILM_RESULT = "result";
    public static final String FILM_TITLE = "title";
    public static final String FILM_DESCRIPTION = "description";
    public static final String INVENTORY_ID = "inventory_id";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String RENTAL_ID = "rental_id";


    /**
     * Map het JSON response op een arraylist en retourneer deze.
     */
    public static ArrayList<Rental> mapRentalList(JSONObject response){

        ArrayList<Rental> result = new ArrayList<>();

        try{
            JSONArray jsonArray = response.getJSONArray(FILM_RESULT);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                Rental rental = new Rental(
                        jsonObject.getString(FILM_TITLE),
                        jsonObject.getString(FILM_DESCRIPTION),
                        jsonObject.getString(INVENTORY_ID),
                        jsonObject.getString(CUSTOMER_ID),
                        jsonObject.getString(RENTAL_ID)

                );
                // Log.i("ToDoMapper", "Film: " + film);
                result.add(rental);
            }
        } catch( JSONException ex) {
            Log.e("FilmMapper", "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        return result;
    }
}


