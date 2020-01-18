package com.company.forturetelling.view.calendar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.company.forturetelling.R;


/**
 * Created by carbs on 2016/7/12.
 */

public class DialogGLC extends Dialog implements View.OnClickListener, IndicatorView.OnIndicatorChangedListener {

    private Context mContext;
    private IndicatorView mIndicatorView;
    private GregorianLunarCalendarView mGLCView;
    private Button mButtonGetData;
    private onCilckListener listener;

    public DialogGLC(Context context, onCilckListener listener) {
        super(context, R.style.dialog);
        mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_glc);
        initWindow();

        mGLCView = (GregorianLunarCalendarView) this.findViewById(R.id.calendar_view);
        mIndicatorView = (IndicatorView) this.findViewById(R.id.indicator_view);
        mIndicatorView.setOnIndicatorChangedListener(this);

        mButtonGetData = (Button) this.findViewById(R.id.button_get_data);
        mButtonGetData.setOnClickListener(this);
    }

    public void initCalendar() {
        mGLCView.init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_get_data:
                listener.onGetDataResult(mGLCView.getCalendarData().getCalendar());
                break;
        }
    }

    @Override
    public void onIndicatorChanged(int oldSelectedIndex, int newSelectedIndex) {
        if (newSelectedIndex == 0) {
            toGregorianMode();
        } else if (newSelectedIndex == 1) {
            toLunarMode();
        }
    }

    private void toGregorianMode() {
        mGLCView.toGregorianMode();
    }

    private void toLunarMode() {
        mGLCView.toLunarMode();
    }

    private void initWindow() {
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = (int) (0.90 * getScreenWidth(getContext()));
        if (lp.width > dp2px(getContext(), 480)) {
            lp.width = dp2px(getContext(), 480);
        }
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        win.setAttributes(lp);
    }

    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    private int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public interface onCilckListener {

        void onGetDataResult(ChineseCalendar calendar);
    }
}