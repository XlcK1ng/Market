
package cn.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.common.ui.activity.BaseApplication;

/**
 * Created by jakechen on 2015/8/11.
 */
public class CommonUtil {
    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Activity context) {
        InputMethodManager manager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (context.getCurrentFocus() != null) {
            manager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    /**
     * 显示软键盘
     */

    public static void showSoftInput(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static int getAppVersion() {
        try {
            return BaseApplication.getInstance().getPackageManager().getPackageInfo(BaseApplication.getInstance().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static boolean isListAvailable(List<?> list) {
        return list != null && list.size() > 0;
    }

    public static boolean isSameObject(Object obj1, Object obj2) {
        return obj1 == obj2;
    }
}
