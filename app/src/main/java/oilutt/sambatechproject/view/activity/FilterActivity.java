package oilutt.sambatechproject.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import oilutt.sambatechproject.R;
import oilutt.sambatechproject.app.Constants;
import oilutt.sambatechproject.presentation.presenter.FilterActivityPresenter;
import oilutt.sambatechproject.presentation.view.FilterActivityPresenterView;
import oilutt.sambatechproject.utils.AnimationUtils;

public class FilterActivity extends BaseActivity implements FilterActivityPresenterView {

    @BindView(R.id.root_layout)
    View rootLayout;
    @BindView(R.id.rg_order_by)
    RadioGroup rgOrderBy;
    @BindView(R.id.rb_fav)
    RadioButton rbFav;

    @InjectPresenter
    FilterActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setUpToolbarText(R.string.filter, true);
        AnimationUtils.circularRevealAnimation(rootLayout, getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear:
                Intent intent = new Intent();
                intent.putExtra(Constants.Extras.ORDER_BY, 0);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AnimationUtils.unRevealActivity(rootLayout, getIntent(), this);
        super.onBackPressed();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_filter;
    }

    @OnClick(R.id.btn_filter)
    public void onClickFilter() {
        Intent intent = new Intent();
        intent.putExtra(Constants.Extras.ORDER_BY, rgOrderBy.getCheckedRadioButtonId());
        intent.putExtra(Constants.Extras.FILTER_BY, rbFav.isChecked());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
