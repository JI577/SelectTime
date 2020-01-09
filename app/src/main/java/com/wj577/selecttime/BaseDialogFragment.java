package com.wj577.selecttime;




import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;


public abstract class BaseDialogFragment extends DialogFragment {
    //DialogFragment可能引发内存泄漏

    public View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1 通过样式定义,DialogFragment.STYLE_NORMAL这个很关键的
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogFragment);
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setWindowAnimations(getAimate());
        mView = inflater.inflate(getContentView(), container, false);

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //注意下面这个方法会将布局的根部局忽略掉，所以需要嵌套一个布局
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y=100;  lp.x=100; /改变上下左右具体的位置
        lp.gravity = getLocation();//改变在屏幕中的位置
        dialogWindow.setAttributes(lp);

        getDialog().setCancelable(getCancel());
        getDialog().setCanceledOnTouchOutside(getCancel());
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {//可以在这拦截返回键啊home键啊事件
                if (getCancel()) {
                    dialog.dismiss();
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    protected abstract int getContentView();

    protected abstract int getLocation();

    protected abstract int getAimate();

    public boolean getCancel() {
        return true;
    }

    //重写show方法解决异常Can not perform this action after onSaveInstanceState with DialogFragment
    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            Class<?> c = DialogFragment.class;
            Field dismissed = c.getDeclaredField("mDismissed");
            Field shownByMe = c.getDeclaredField("mShownByMe");
            dismissed.setAccessible(true);
            shownByMe.setAccessible(true);
            Object obj = c.getConstructor().newInstance();
            dismissed.set(obj, false);
            shownByMe.set(obj, true);
        } catch (Exception e) {
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        // 这里吧原来的commit()方法换成了commitAllowingStateLoss()
        ft.commitAllowingStateLoss();
    }
}