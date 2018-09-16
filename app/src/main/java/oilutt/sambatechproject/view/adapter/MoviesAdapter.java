package oilutt.sambatechproject.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import oilutt.sambatechproject.R;
import oilutt.sambatechproject.app.Constants;
import oilutt.sambatechproject.model.Configuration;
import oilutt.sambatechproject.model.Movie;
import oilutt.sambatechproject.utils.PreferencesManager;
import oilutt.sambatechproject.view.activity.MovieDetailActivity;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolder> {

    private List<Movie> mListMovies;
    private Context mContext;

    public MoviesAdapter(List<Movie> listMovies, Context context) {
        this.mListMovies = listMovies;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_movies, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie = mListMovies.get(position);
        if (movie.getPosterPath() != null) {
            Configuration.ConfigImages configImages = PreferencesManager.getInstance().getConfiguration().getConfigImages();
            String urlImage = configImages.getSecureBaseUrl() + configImages.getPosterSizes().get(4) + "/" + movie.getPosterPath();
            Glide.with(mContext)
                    .applyDefaultRequestOptions(new RequestOptions())
                    .load(urlImage)
                    .into(holder.imgMovie);
        }

        holder.imgMovie.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, MovieDetailActivity.class);
            intent.putExtra(Constants.Extras.ID_MOVIE, String.valueOf(movie.getId()));
            intent.putExtra(Constants.Extras.NAME_MOVIE, movie.getTitle());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mListMovies.size();
    }

    public void addMovies(List<Movie> listMovies) {
        mListMovies.addAll(listMovies);
        notifyItemRangeInserted(mListMovies.size() - listMovies.size(), mListMovies.size());
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        ImageView imgMovie;

        public MovieHolder(View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.img_movie);
        }
    }
}
