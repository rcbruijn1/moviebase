package rbdb.moviebase.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;
import rbdb.moviebase.Service.Config;
import rbdb.moviebase.R;
import rbdb.moviebase.Service.VolleyRequestQueue;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView txtLoginErrorMsg;

    private EditText editTextFirstName;
    private EditText editTextLastName;

    private LinearLayout signupLayout;

    private String mUsername;
    private String mPassword;
    private String mFirstName;
    private String mLastName;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_login);

        editTextUsername = (EditText) findViewById(R.id.edittextUsername);
        editTextPassword = (EditText) findViewById(R.id.edittextPassword);
        txtLoginErrorMsg = (TextView) findViewById(R.id.txtLoginErrorMessage);
        signupLayout = (LinearLayout) findViewById(R.id.signupLayout);
        editTextFirstName = (EditText) findViewById(R.id.edittextFirstName);
        editTextLastName = (EditText) findViewById(R.id.edittextLastName);
        final RippleView rippleViewSignUp = (RippleView) findViewById(R.id.btnSignUp);
        final RippleView rippleViewBack = (RippleView) findViewById(R.id.btnBack);
        final RippleView rippleViewSignIn = (RippleView) findViewById(R.id.btnSignIn);
        final RippleView rippleViewSubmit = (RippleView) findViewById(R.id.btnSubmit);


        rippleViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsername = editTextUsername.getText().toString();
                mPassword = editTextPassword.getText().toString();
                txtLoginErrorMsg.setText("");

                // TODO Checken of username en password niet leeg zijn
                // momenteel checken we nog niet

                handleLogin(mUsername, mPassword);
            }
        });

        rippleViewSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUsername = editTextUsername.getText().toString();
                mPassword = editTextPassword.getText().toString();
                mFirstName = editTextFirstName.getText().toString();
                mLastName = editTextLastName.getText().toString();
                txtLoginErrorMsg.setText("");

                // TODO Checken of username en password niet leeg zijn
                // momenteel checken we nog niet

                handleSignUp(mUsername, mPassword, mFirstName, mLastName);

            }
        });

        rippleViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rippleViewSignIn.setVisibility(View.VISIBLE);
                rippleViewSignUp.setVisibility(View.VISIBLE);
                rippleViewSubmit.setVisibility(View.GONE);
                signupLayout.setVisibility(View.GONE);
                rippleViewBack.setVisibility(View.GONE);

            }
        });

        rippleViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rippleViewSignIn.setVisibility(View.GONE);
                rippleViewSignUp.setVisibility(View.GONE);
                rippleViewSubmit.setVisibility(View.VISIBLE);
                signupLayout.setVisibility(View.VISIBLE);
                rippleViewBack.setVisibility(View.VISIBLE);


            }
        });




        ImageView logo =(ImageView)findViewById(R.id.logo);
        logo.animate()
                .setDuration(8000)
                .alpha(1);



    }

    private void handleLogin(String username, String password) {
        //
        // Maak een JSON object met username en password. Dit object sturen we mee
        // als request body (zoals je ook met Postman hebt gedaan)
        //
        String body = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        Log.i(TAG, "handleLogin - body = " + body);

        try {
            JSONObject jsonBody = new JSONObject(body);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, Config.URL_LOGIN, jsonBody, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response - dat betekent dat we een geldig token hebben.
                            // txtLoginErrorMsg.setText("Response: " + response.toString());
                            Toasty.success(getApplicationContext(), "Succesvol ingelogd!", Toast.LENGTH_LONG, true).show();

                            // We hebben nu het token. We kiezen er hier voor om
                            // het token in SharedPreferences op te slaan. Op die manier
                            // is het token tussen app-stop en -herstart beschikbaar -
                            // totdat het token expired.
                            try {
                                String token = response.getString("id_token");

                                Integer customerId = response.getJSONArray("result").getJSONObject(0).getInt("customer_id");
                                Context context = getApplicationContext();

                                SharedPreferences customerPref = context.getSharedPreferences(
                                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                SharedPreferences.Editor customerEditor = customerPref.edit();
                                customerEditor.putInt("customer_id", customerId);
                                customerEditor.commit();


                                SharedPreferences sharedPref = context.getSharedPreferences(
                                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString(getString(R.string.saved_token), token);
                                editor.commit();

                                // Start the main activity, and close the login activity
                                Intent main = new Intent(getApplicationContext(), MenuActivity.class);
                                startActivity(main);
                                // Close the current activity
                                finish();

                            } catch (JSONException e) {
                                // e.printStackTrace();
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleErrorResponse(error);
                        }
                    });

            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1500, // SOCKET_TIMEOUT_MS,
                    2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(this).addToRequestQueue(jsObjRequest);
        } catch (JSONException e) {
            txtLoginErrorMsg.setText(e.getMessage());
            // e.printStackTrace();
        }
        return;
    }

    private void handleSignUp(final String username, final String password, String firstName, String lastName) {
        //
        // Maak een JSON object met username en password. Dit object sturen we mee
        // als request body (zoals je ook met Postman hebt gedaan)
        //
        String body = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"firstName\":\"" + firstName + "\",\"lastName\":\"" + lastName + "\"}";
        Log.i(TAG, "handleLogin - body = " + body);

        try {
            JSONObject jsonBody = new JSONObject(body);

            final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, Config.URL_REGISTER, jsonBody, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response - dat betekent dat we een geldig token hebben.
                            // txtLoginErrorMsg.setText("Response: " + response.toString());
                            Toasty.success(getApplicationContext(), "Succesvol geregistreerd!", Toast.LENGTH_LONG, true).show();

                            // We hebben nu het token. We kiezen er hier voor om
                            // het token in SharedPreferences op te slaan. Op die manier
                            // is het token tussen app-stop en -herstart beschikbaar -
                            // totdat het token expired.
                            try {


                                String token = response.getString("id_token");

                                Context context = getApplicationContext();
                                SharedPreferences sharedPref = context.getSharedPreferences(
                                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString(getString(R.string.saved_token), token);
                                editor.commit();


                                handleLogin(username, password);


                            } catch (JSONException e) {
                                // e.printStackTrace();
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleErrorResponse(error);
                        }
                    });

            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1500, // SOCKET_TIMEOUT_MS,
                    2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(this).addToRequestQueue(jsObjRequest);
        } catch (JSONException e) {
            txtLoginErrorMsg.setText(e.getMessage());
            // e.printStackTrace();
        }
        return;
    }

    /**
     * Handel Volley errors op de juiste manier af.
     *
     * @param error Volley error
     */
    public void handleErrorResponse(VolleyError error) {
        Log.e(TAG, "handleErrorResponse");

        if(error instanceof com.android.volley.AuthFailureError) {
            String json = null;
            NetworkResponse response = error.networkResponse;
            if (response != null && response.data != null) {
                json = new String(response.data);
                json = trimMessage(json, "error");
                if (json != null) {
                    json = "Error " + response.statusCode + ": " + json;
                    displayMessage(json);
                }
            } else {
                Log.e(TAG, "handleErrorResponse: kon geen networkResponse vinden.");
            }
        } else if(error instanceof com.android.volley.NoConnectionError) {
            Log.e(TAG, "handleErrorResponse: server was niet bereikbaar");
            txtLoginErrorMsg.setText(getString(R.string.error_server_offline));
        } else {
            Log.e(TAG, "handleErrorResponse: error = " + error);
        }
    }

    public String trimMessage(String json, String key){
        Log.i(TAG, "trimMessage: json = " + json);
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }
        return trimmedString;
    }


    public void displayMessage(String toastString){
        Toasty.error(getApplicationContext(), toastString, Toast.LENGTH_LONG, true).show();
    }
}
