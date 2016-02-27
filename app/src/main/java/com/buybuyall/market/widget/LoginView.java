
package com.buybuyall.market.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.buybuyall.market.R;
import com.buybuyall.market.logic.BroadcastActions;
import com.buybuyall.market.logic.UrlManager;
import com.buybuyall.market.logic.http.HttpRequest;
import com.buybuyall.market.logic.http.response.SpecialResponse;
import com.buybuyall.market.ui.ForgetPwActivity;
import com.buybuyall.market.ui.RegisterActivity;
import com.buybuyall.market.utils.ToastUtil;

import cn.common.exception.AppException;
import cn.common.utils.DisplayUtil;

/**
 * 描述:登录页面
 *
 * @author jakechen
 * @since 2016/1/28 17:54
 */
public class LoginView extends LinearLayout implements View.OnClickListener, Handler.Callback {
    private static final int MSG_LOGIN_SUCCESS = 0;
    private static final int MSG_LOGIN_FAIL = 1;
    private EditText evAccount;
    private EditText evPw;
    private Button btnLogin;
    private Handler handler;

    public LoginView(Context context) {
        this(context, null);
    }

    public LoginView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handler = new Handler(this);
        inflate(context, R.layout.view_login, this);
        setOrientation(VERTICAL);
        setBackgroundColor(getResources().getColor(R.color.white));
        setPadding(DisplayUtil.dip(10), 0, DisplayUtil.dip(10), DisplayUtil.dip(10));
        evAccount = (EditText) findViewById(R.id.ev_account);
        evPw = (EditText) findViewById(R.id.ev_pw);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        findViewById(R.id.tv_forget_pw).setOnClickListener(this);
        findViewById(R.id.tv_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_login) {
            login();
        } else if (id == R.id.tv_forget_pw) {
            goActivity(ForgetPwActivity.class);
        } else if (id == R.id.tv_register) {
            goActivity(RegisterActivity.class);
        }
    }

    private void login() {
        if (TextUtils.isEmpty(evAccount.getText())) {
            ToastUtil.show("请输入登录账号");
            return;
        }
        if (TextUtils.isEmpty(evPw.getText())) {
            ToastUtil.show("请输入登录密码");
            return;
        }
        changeLoginState(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                loginTask();
            }
        }).start();
    }

    private void loginTask() {
        HttpRequest<SpecialResponse> request = new HttpRequest<>(UrlManager.SPECIAL, SpecialResponse.class);
        request.setIsGet(true);
        request.addParam("account", evAccount.getText().toString());
        request.addParam("pw", evPw.getText().toString());
        try {
            SpecialResponse response = request.request();
            if (response != null && response.isOk()) {
                handler.sendEmptyMessage(MSG_LOGIN_SUCCESS);
            } else {
                handler.sendEmptyMessage(MSG_LOGIN_SUCCESS);
            }
        } catch (AppException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(MSG_LOGIN_SUCCESS);
        }
    }

    private void changeLoginState(boolean isLogin) {
        if (isLogin) {
            btnLogin.setText("登录中...");
            btnLogin.setEnabled(false);
            evAccount.setEnabled(false);
            evPw.setEnabled(false);
        } else {
            btnLogin.setText("登录");
            btnLogin.setEnabled(true);
            evAccount.setEnabled(true);
            evPw.setEnabled(true);
        }
    }

    private void goActivity(Class<?> clazz, Bundle bundle) {
        Intent it = new Intent();
        it.setClass(getContext(), clazz);
        if (bundle != null) {
            it.putExtras(bundle);
        }
        getContext().startActivity(it);
    }

    private void goActivity(Class<?> clazz) {
        goActivity(clazz, null);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_LOGIN_SUCCESS:
                if (getContext() != null) {
                    getContext().sendBroadcast(new Intent(BroadcastActions.ACTION_LOGIN_SUCCESS));
                }
                changeLoginState(false);
                break;
            case MSG_LOGIN_FAIL:
                changeLoginState(false);
                break;
        }
        return true;
    }
}