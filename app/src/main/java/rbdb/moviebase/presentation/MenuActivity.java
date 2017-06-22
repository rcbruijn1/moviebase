package rbdb.moviebase.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.andexert.library.RippleView;

import rbdb.moviebase.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final RippleView rippleViewAddRental = (RippleView) findViewById(R.id.btnAddRental);
        rippleViewAddRental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(getApplicationContext(), RentableMovieActivity.class);
                startActivity(add);
            }
        });

        final RippleView rippleViewEditRental = (RippleView) findViewById(R.id.btnManageRental);
        rippleViewEditRental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(getApplicationContext(), myRentalsActivity.class);
                startActivity(edit);
            }
        });

    }
}
