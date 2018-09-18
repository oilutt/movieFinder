package oilutt.sambatechproject.presentation.view;

import com.arellomobile.mvp.MvpView;

public interface FilterActivityPresenterView extends MvpView {

    void clickFilter();

    void setOrderBySelected(int rbId);

    void setFavSelected();
}
