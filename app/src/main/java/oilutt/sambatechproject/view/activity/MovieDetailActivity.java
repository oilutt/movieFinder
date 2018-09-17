package oilutt.sambatechproject.view.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import oilutt.sambatechproject.R;
import oilutt.sambatechproject.app.Constants;
import oilutt.sambatechproject.presentation.presenter.MovieDetailActivityPresenter;
import oilutt.sambatechproject.presentation.view.MovieDetailActivityPresenterView;
import oilutt.sambatechproject.view.adapter.AdapterGenres;
import oilutt.sambatechproject.view.dialog.ImageFullScreenDialog;

public class MovieDetailActivity extends BaseActivity implements MovieDetailActivityPresenterView {

    @BindView(R.id.img_movie)
    ImageView imgMovie;
    @BindView(R.id.txt_day)
    TextView txtDay;
    @BindView(R.id.txt_month)
    TextView txtMonth;
    @BindView(R.id.txt_year)
    TextView txtYear;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_tagline)
    TextView txtTagline;
    @BindView(R.id.txt_length)
    TextView txtLength;
    @BindView(R.id.txt_description)
    TextView txtDescription;
    @BindView(R.id.txt_rate)
    TextView txtRate;
    @BindView(R.id.txt_quantity_popularity)
    TextView txtPopularity;
    @BindView(R.id.rv_genres)
    RecyclerView rvGenres;

    @InjectPresenter
    MovieDetailActivityPresenter presenter;

    @ProvidePresenter
    MovieDetailActivityPresenter createPresenter() {
        return new MovieDetailActivityPresenter(getIntent(), this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie, menu);
        if(presenter.isFav()) {
            menu.getItem(0).setIcon(R.drawable.ic_fav_on);
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_fav);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fav:
                presenter.clickFav();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.img_movie)
    public void clickImage() {
        presenter.clickImage();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_movie_details;
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void setToolbarTitle(String movieName) {
        setUpToolbarText(movieName, true);
    }

    @Override
    public void setImage(String img) {
        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().centerCrop()).load(img).into(imgMovie);
    }

    @Override
    public void setTitle(String title) {
        txtTitle.setText(title);
    }

    @Override
    public void setDay(String day) {
        txtDay.setText(day);
    }

    @Override
    public void setMonth(String month) {
        txtMonth.setText(month);
    }

    @Override
    public void setYear(String year) {
        txtYear.setText(year);
    }

    @Override
    public void setTagline(String tagline) {
        txtTagline.setText(tagline);
    }

    @Override
    public void setDescription(String description) {
        txtDescription.setText(description);
    }

    @Override
    public void setLength(String length) {
        txtLength.setText(length);
    }

    @Override
    public void setRate(String rate) {
        txtRate.setText(rate);
    }

    @Override
    public void setPopularity(String popularity) {
        txtPopularity.setText(popularity);
    }

    @Override
    public void setGenres(AdapterGenres adapter) {
        rvGenres.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvGenres.setAdapter(adapter);
    }

    @Override
    public void showImage(String url) {
        ImageFullScreenDialog dialog = new ImageFullScreenDialog();
        Bundle args = new Bundle();
        args.putString(Constants.Extras.PATH_MOVIE, url);
        dialog.setArguments(args);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        dialog.show(ft, ImageFullScreenDialog.TAG);
    }

    @Override
    public void changeFavColor() {
        invalidateOptionsMenu();
    }
}
