package com.mvp.fractal.hadar.danny.monom.graph;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.mvp.fractal.hadar.danny.monom.R;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.util.Objects;

public class GraphActivity extends AppCompatActivity implements GraphContract.View {

    // Views
    private EditText mPolynomialEditText;
    private FloatingActionButton mDrawFab;
    private FloatingActionButton mClearFab;
    private GraphView mGraphView;
    // Presenter
    private GraphPresenter mGraphPresenter;
    // Fabs Listener
    private FabsListener mFabsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPresenter();
        findAllViewsById();
        setListeners();
    }

    private void setPresenter() {
        mGraphPresenter = new GraphPresenter();
        mGraphPresenter.takeView(this);
    }

    private void findAllViewsById() {
        // Views
        mPolynomialEditText = findViewById(R.id.et_polynomial);
        mDrawFab = findViewById(R.id.fab_draw);
        mClearFab = findViewById(R.id.fab_clear);
        mGraphView = findViewById(R.id.graph_view);
        // Listener
        mFabsListener = new FabsListener();
    }

    private void setListeners() {
        mDrawFab.setOnClickListener(mFabsListener);
        mClearFab.setOnClickListener(mFabsListener);

        KeyboardVisibilityEvent.setEventListener(this, isOpen -> {
            mGraphPresenter.keyBoardStateChanged();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGraphPresenter.dropView();
    }

    @Override
    public void displayErrorInput() {
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void drawGraph(PolynomialPainter polynomialPainter) {
        mGraphView.setPolynomialPainter(polynomialPainter);
        mPolynomialEditText.setText(polynomialPainter.toString());
    }

    @Override
    public void hideSoftKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(mPolynomialEditText.getWindowToken(), 0);
    }

    @Override
    public void updateDrawing() {
        mGraphView.invalidate();
    }

    private class FabsListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab_clear:
                    mGraphPresenter.onClearButtonClicked();
                    break;
                case R.id.fab_draw:
                    mGraphPresenter.onDrawButtonClicked(mPolynomialEditText.getText().toString());
                    break;
            }
        }
    }

}
