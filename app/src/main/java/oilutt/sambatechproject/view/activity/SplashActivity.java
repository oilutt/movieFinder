package oilutt.sambatechproject.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import oilutt.sambatechproject.R;
import oilutt.sambatechproject.presentation.presenter.SplashActivityPresenter;
import oilutt.sambatechproject.presentation.view.SplashActivityPresenterView;

public class SplashActivity extends BaseActivity implements SplashActivityPresenterView {

    @BindView(R.id.txt_app_name)
    TextView txtAppName;

    @InjectPresenter
    SplashActivityPresenter presenter;

    @ProvidePresenter
    SplashActivityPresenter createPresenter() {
        return new SplashActivityPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        fadeInFadeOut();
        setIsFinish();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    public void goToMain() {
        new Handler().postDelayed(() -> {
            if (!runningBackground) {
                goToActivity(SplashActivity.this, MainActivity.class);
            }
        }, 3000);
    }
}
