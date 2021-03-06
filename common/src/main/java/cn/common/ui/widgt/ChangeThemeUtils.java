package cn.common.ui.widgt;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ChangeThemeUtils {
  private ChangeThemeUtils() {
  }

  public static void setStatusBarTextColor(Activity context) {
    if (TextUtils.equals(Build.BRAND, "Xiaomi") && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      int type = 1;
      Window window = context.getWindow();
      Class clazz = window.getClass();
      try {
        int tranceFlag = 0;
        int darkModeFlag = 0;
        Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
        Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
        tranceFlag = field.getInt(layoutParams);
        field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
        darkModeFlag = field.getInt(layoutParams);
        Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
        if (type == 0) {
          extraFlagField.invoke(window, tranceFlag, tranceFlag);//只需要状态栏透明
        } else if (type == 1) {
          extraFlagField.invoke(window, tranceFlag | darkModeFlag, tranceFlag | darkModeFlag);//状态栏透明且黑色字体
        } else {
          extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
        }
      } catch (Exception e) {
      }
    }
  }

  public static void adjustStatusBar(View view, Context context) {
    if (view == null) {
      return;
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      if (Build.VERSION.SDK_INT >= 21) {
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
      }
      view.setPadding(0, getStatusBarHeight(context), 0, 0);
    }
  }

  public static int getStatusBarHeight(Context context) {
    try {
      Class<?> clazz = Class.forName("com.android.internal.R$dimen");
      Object obj = clazz.newInstance();
      Field field = clazz.getField("status_bar_height");
      int id = Integer.parseInt(field.get(obj).toString());
      return context.getResources().getDimensionPixelSize(id);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, context.getResources().getDisplayMetrics());
  }

  public static void convertActivityFromTranslucent(Activity activity) {
    try {
      Method method = Activity.class.getDeclaredMethod("convertFromTranslucent");
      method.setAccessible(true);
      method.invoke(activity);
    } catch (Throwable t) {
    }
  }

  public static void convertActivityToTranslucent(Activity activity) {
    try {
      Class<?>[] classes = Activity.class.getDeclaredClasses();
      Class<?> translucentConversionListenerClazz = null;
      for (Class clazz : classes) {
        if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
          translucentConversionListenerClazz = clazz;
        }
      }
      if (Build.VERSION.SDK_INT < 21) {
        Method method = Activity.class.getDeclaredMethod("convertToTranslucent", translucentConversionListenerClazz);
        method.setAccessible(true);
        method.invoke(activity, new Object[]{null});
      } else {
        Method method = Activity.class.getDeclaredMethod("convertToTranslucent", translucentConversionListenerClazz, ActivityOptions.class);
        method.setAccessible(true);
        method.invoke(activity, new Object[]{null, null});
      }
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
