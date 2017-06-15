package rbdb.moviebase.Service;

/**
 * Bevat o.a. URL definities.
 */
public class Config {

    private static final String BASIC_URL = "https://ticketflowserver.herokuapp.com";

    public static final String URL_LOGIN = BASIC_URL + "/api/v1/login";
    public static final String URL_REGISTER = BASIC_URL + "/api/v1/register";
    public static final String URL_MOVIES = BASIC_URL + "/api/v1/movies";

}
