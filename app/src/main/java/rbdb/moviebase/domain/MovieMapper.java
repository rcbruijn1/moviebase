package rbdb.moviebase.domain;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ruben on 15-6-2017.
 */

public class MovieMapper {

    // De JSON attributen die we uitlezen
    public static final String MOVIE_RESULT = "result";
    public static final String MOVIE_TITLE = "Titel";


    /**
     * Map het JSON response op een arraylist en retourneer deze.
     */
    public static ArrayList<MovieItem> mapMovieList(JSONObject response){

        ArrayList<MovieItem> result = new ArrayList<>();

        try{
            JSONArray jsonArray = response.getJSONArray(MOVIE_RESULT);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Convert stringdate to Date
              //  String timestamp = jsonObject.getString(MOVIE_UPDATED_AT);
              //  DateTime todoDateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(timestamp);

                MovieItem movieItem = new MovieItem(
                        jsonObject.getString(MOVIE_TITLE)
                );
                // Log.i("ToDoMapper", "Movie: " + Movie);
                result.add(movieItem);
            }
        } catch( JSONException ex) {
            Log.e("MovieMapper", "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        return result;
    }
}
