package app.com.example.mustafacan.movieapp.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mustafa Can on 20.04.2016.
 */
public class MovieFragment extends Fragment {
    private MovieAdapter mMovieAdapter;
    private GridView mGridView;
    ArrayList<Movie> movies=new ArrayList<Movie>();;

    public MovieFragment(){

    }

    @Override
    public void onStart() {
        super.onStart();
        new FetchMovieTask().execute();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {


        mMovieAdapter=new MovieAdapter(getActivity().getApplicationContext(),movies);
        View rootView=inflater.inflate(R.layout.fragment_main,container,false);
        mGridView=(GridView)rootView.findViewById(R.id.gridView_movie);
        mGridView.setAdapter(mMovieAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie m=mMovieAdapter.getItem(position);
                Intent i = new Intent(getActivity(),DetailActivity.class).putExtra("Movie",m);
                startActivity(i);
            }
        });
        return rootView;
    }



    private ArrayList<Movie> getMovieDataFromJson(String movieJsonStr) throws JSONException{



        final String TMDB_RESULTS="results";
        final String TMDB_OVERVIEW="overview";
        final String TMDB_RELEASEDATE="release_date";
        final String TMDB_TITLE="title";
        final String TMDB_IMAGEURL="poster_path";
        final String TMDB_USERRATING="vote_average";
        final String baseIMAGEURL="http://image.tmdb.org/t/p/w185";
        JSONObject movieJson=new JSONObject(movieJsonStr);
        JSONArray movieArray=movieJson.getJSONArray(TMDB_RESULTS);


        ArrayList<Movie> nmovie=new ArrayList<Movie>();
        for(int i=0;i<movieArray.length();i++){
            String imageUrl;
            String title;
            String overview;
            String userRate;
            String releaseDate;

            JSONObject movieObject=movieArray.getJSONObject(i);

            title=movieObject.getString(TMDB_TITLE);
            imageUrl=movieObject.getString(TMDB_IMAGEURL);
            overview=movieObject.getString(TMDB_OVERVIEW);
            userRate=movieObject.getString(TMDB_USERRATING);
            releaseDate=movieObject.getString(TMDB_RELEASEDATE);
            String imageUrlFinal=baseIMAGEURL+imageUrl;

            nmovie.add(new Movie(imageUrlFinal,title,overview,userRate,releaseDate));

        }

        return nmovie;

    }


    public class FetchMovieTask extends AsyncTask<Void,Void,ArrayList<Movie>>{

        private final String LOG_TAG=FetchMovieTask.class.getSimpleName();

        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {

            HttpURLConnection urlConnection=null;
            BufferedReader reader=null;

            String movieJsonStr=null;



            try {
                String Movie_Popular_Base_Url="http://api.themoviedb.org/3/movie/popular?";
                String Movie_Highest_Rate_Base_Url="http://api.themoviedb.org/3/movie/top_rated?";
                final String APIKEY_PARAM="api_key";
                Uri buildUri;
                SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
                String sortBy=sharedPreferences.getString(getString(R.string.pref_sort_key),getString(R.string.pref_sort_default));
                if(sortBy.equals("popular")){
                    buildUri=Uri.parse(Movie_Popular_Base_Url).buildUpon()
                            .appendQueryParameter(APIKEY_PARAM,BuildConfig.THE_MOVIE_DB_API_KEY).build();
                }
                else {
                    buildUri=Uri.parse(Movie_Highest_Rate_Base_Url).buildUpon()
                            .appendQueryParameter(APIKEY_PARAM,BuildConfig.THE_MOVIE_DB_API_KEY).build();
                }





                URL url=new URL(buildUri.toString());

                urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream=urlConnection.getInputStream();
                StringBuffer buffer=new StringBuffer();
                if(inputStream==null){
                    return null;
                }

                reader=new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line=reader.readLine())!=null){
                    buffer.append(line+"\n");
                }
                movieJsonStr=buffer.toString();


            }catch (IOException e){
                Log.e(LOG_TAG,"Error :",e);
                return null;
            }finally {
                if(urlConnection!=null){
                    urlConnection.disconnect();
                }
                if(reader!=null){
                    try {
                        reader.close();
                    }catch (final IOException e){
                        Log.e(LOG_TAG,"Error closing stream",e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(movieJsonStr);
            }catch (JSONException e){
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }
            return null;
        }
        //nmov = newmovies
        @Override
        protected void onPostExecute(ArrayList<Movie> nmov) {
            if(nmov!=null){

                mMovieAdapter.clear();
                for(Movie movie:nmov){
                    //honeycomb ve üstü android sürümlerinde tek tek eklemek yerine addAll metodunu kullanabiliriz.

                    mMovieAdapter.add(movie);

                }



            }
        }

    }
}
