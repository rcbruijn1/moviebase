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
 * Created by Ruben on 15-6-2017.
 */

public class MovieAdapter extends BaseAdapter{

    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private LayoutInflater mInflator;
    private ArrayList<MovieItem> moviesArrayList;

    public MovieAdapter(Context context, LayoutInflater layoutInflater, ArrayList<MovieItem> moviesArrayList) {
        this.mContext = context;
        this.mInflator = layoutInflater;
        this.moviesArrayList = moviesArrayList;
    }

    @Override
    public int getCount() {
        int size = moviesArrayList.size();
        Log.i(TAG, "getCount() = " + size);
        return size;
    }

    @Override
    public Object getItem(int position) {
        Log.i(TAG, "getItem() at " + position);
        return moviesArrayList.get(position);
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
            convertView = mInflator.inflate(R.layout.movie_listview_row, null);

            // Maak een ViewHolder en koppel de schermvelden aan de velden uit onze eigen row.
            viewHolder = new ViewHolder();
            viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.movieRowTitle);
            // viewHolder.textViewContents = (TextView) convertView.findViewById(R.id.rowToDoDate);

            // Sla de viewholder op in de convertView
            convertView.setTag(viewHolder);
        } else {
            Log.i(TAG, "convertView BESTOND AL - hergebruik");
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MovieItem movie = moviesArrayList.get(position);
        viewHolder.textViewTitle.setText(movie.getTitle());

        return convertView;
    }

    private static class ViewHolder {
        public TextView textViewTitle;
        // public TextView textViewContents;
    }
}

