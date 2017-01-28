package app.com.example.mustafacan.movieapp.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,new DetailFragment())
                    .commit();
        }
    }

    public static class DetailFragment extends Fragment{


        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView=inflater.inflate(R.layout.fragment_detail,container,false);

            Movie m=(Movie)getActivity().getIntent().getExtras().getSerializable("Movie");
            if(m!=null){
                TextView title=(TextView)rootView.findViewById(R.id.title);
                TextView overview=(TextView)rootView.findViewById(R.id.overview);
                TextView voteAverage=(TextView)rootView.findViewById(R.id.voteAverage);
                TextView releaseDate=(TextView)rootView.findViewById(R.id.releaseDate);
                ImageView imageView=(ImageView)rootView.findViewById(R.id.imageView);

                title.setText(m.getTitle());
                overview.setText(m.getOverview());
                voteAverage.setText("User Rating:"+m.getUserRating());
                releaseDate.setText("Release Date:"+m.getReleaseDate());
                Picasso.with(getActivity().getApplicationContext()).load(m.getImageUrl()).into(imageView);
            }
            return rootView;
        }
    }

}
