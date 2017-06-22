package rbdb.moviebase.Service;

/**
 * Bevat o.a. URL definities.
 */
public class Config {

    private static final String BASIC_URL = "https://moviebase-server.herokuapp.com";

    public static final String URL_LOGIN = BASIC_URL + "/api/v1/login";
    public static final String URL_REGISTER = BASIC_URL + "/api/v1/register";
    public static final String URL_FILMS = BASIC_URL + "/api/v1/films";
    public static final String URL_RENTAL = BASIC_URL + "/api/v1/rentals";



}
