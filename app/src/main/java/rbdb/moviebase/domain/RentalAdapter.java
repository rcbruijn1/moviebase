package rbdb.moviebase.domain;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import rbdb.moviebase.R;

/**
 * Created by Daniel on 18-6-2017.
 */

public class RentalAdapter extends BaseAdapter {

    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private LayoutInflater mInflator;
    private ArrayList<Rental> rentalArrayList;

    public RentalAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Rental> rentalArrayList) {
        this.mContext = context;
        this.mInflator = layoutInflater;
        this.rentalArrayList = rentalArrayList;
    }

    @Override
    public int getCount() {
        int size = rentalArrayList.size();
        Log.i(TAG, "getCount() = " + size);
        return size;
    }

    @Override
    public Object getItem(int position) {
        Log.i(TAG, "getItem() at " + position);
        return rentalArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG, "getView at " + position);

        ViewHolder viewHolder;

        if(convertView == null){

            Log.i(TAG, "convertView is NULL - nieuwe maken");

            // Koppel de convertView aan de layout van onze eigen row
            convertView = mInflator.inflate(R.layout.list_film_row, null);

            // Maak een ViewHolder en koppel de schermvelden aan de velden uit onze eigen row.
            viewHolder = new ViewHolder();
            viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.rowFilmTitle);
            viewHolder.textViewDescription = (TextView) convertView.findViewById(R.id.rowFilmDescription);
            viewHolder.textViewInventoryID = (TextView) convertView.findViewById(R.id.rowInventoryID);
            viewHolder.textViewCustomerID = (TextView) convertView.findViewById(R.id.rowCustomerID);

            // Sla de viewholder op in de convertView
            convertView.setTag(viewHolder);
        } else {
            Log.i(TAG, "convertView BESTOND AL - hergebruik");
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Rental rental = rentalArrayList.get(position);
        viewHolder.textViewTitle.setText(rental.getTitle());
        viewHolder.textViewDescription.setText(rental.getDescription());
        viewHolder.textViewInventoryID.setText(rental.getInventoryID());
        viewHolder.textViewInventoryID.setText(rental.getCustomerID());

        return convertView;
    }

    private static class ViewHolder {
        public TextView textViewTitle;
        public TextView textViewDescription;
        public TextView textViewInventoryID;
        public TextView textViewCustomerID;
    }

}


