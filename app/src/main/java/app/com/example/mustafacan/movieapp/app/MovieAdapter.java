package app.com.example.mustafacan.movieapp.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mustafa Can on 23.04.2016.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {


    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Movie movie= getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movie, parent, false);
        }
        // Lookup view for data population
        ImageView imageView= (ImageView) convertView.findViewById(R.id.grid_item_movie_image);
    //.resize(500,800)
        Picasso.with(getContext()).load(movie.getImageUrl()).resize(500,800).into((ImageView)convertView);
        // Return the completed view to render on screen
        return convertView;
    }

}
