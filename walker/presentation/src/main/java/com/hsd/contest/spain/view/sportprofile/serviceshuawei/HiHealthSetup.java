package com.hsd.contest.spain.view.sportprofile.serviceshuawei;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.annotation.Nullable;

import com.hsd.contest.spain.view.sportprofile.ProfileViewModel;
import com.huawei.hms.hihealth.data.Scopes;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.hwid.HuaweiIdAuthAPIManager;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;
import com.huawei.hms.support.hwid.result.HuaweiIdAuthResult;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HiHealthSetup {
    private static final String TAG = "HiHealthSetup";

    private static final int REQUEST_CODE_LOGIN = 3624;

    private static final int REQUEST_CODE_PERMISSION = 4727;

    private static final int MESSAGE_REQUEST_PERMISSION = 1;

    private static HuaweiIdAuthService nHuaweiIdAuthService;

    private static AuthHuaweiId mAccount;

    private static Activity mActivity;

    private static ProfileViewModel mViewModel;

    @SuppressLint("HandlerLeak")
    private static final Handler mHandler = new Handler() {
        public void handleMessage(@NotNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == MESSAGE_REQUEST_PERMISSION) {
                requestPermission();
            } else {
                Log.e(TAG, "invalid msg");
            }
        }

    };

    /**
     * login with huawei id.
     *
     * @param activity Represent the activity object.
     */
    public static void login(Activity activity, ProfileViewModel viewModel) {
        Log.d(TAG, "login");

        mActivity = activity;
        mViewModel = viewModel;

        List<Scope> list = new ArrayList<>();
        String[] maxScopes = Scopes.getMaxScopes();
        Scope[] allRequiredScopes = new Scope[maxScopes.length];
        for (int i = 0; i < allRequiredScopes.length; i++) {
            allRequiredScopes[i] = new Scope(maxScopes[i]);
        }
        Collections.addAll(list, allRequiredScopes);
        HuaweiIdAuthParamsHelper authParamsHelper = new HuaweiIdAuthParamsHelper();
        HuaweiIdAuthParams authParams = authParamsHelper.setIdToken().setScopeList(list).createParams();

        nHuaweiIdAuthService = HuaweiIdAuthManager.getService(activity.getApplicationContext(), authParams);
        mAccount = HuaweiIdAuthManager.getAuthResult();
        if (mAccount == null) {
            Intent signInIntent = nHuaweiIdAuthService.getSignInIntent();
            activity.startActivityForResult(signInIntent, REQUEST_CODE_LOGIN);
        }

        if (mAccount != null) {
            viewModel.onConnect();
        }

    }

    public static void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_LOGIN:
                handleSignInResult(data);
                break;
            case REQUEST_CODE_PERMISSION:
                break;
            default:
                Log.e(TAG, "invalid request code");
        }
    }

    private static void handleSignInResult(Intent data) {
        HuaweiIdAuthResult result = HuaweiIdAuthAPIManager.HuaweiIdAuthAPIService.parseHuaweiIdFromIntent(data);
        Log.d(TAG, "result=" + result.getStatus() + " code= " + result.isSuccess());

        if (!result.isSuccess()) {
            Log.e(TAG, "failed to sign in");
            return;
        }

        Log.d(TAG, "success to sign in");

        mAccount = HuaweiIdAuthAPIManager.HuaweiIdAuthAPIService.parseHuaweiIdFromIntent(data).getHuaweiId();
        if (mAccount == null) {
            return;
        }

        mViewModel.onConnect();
        mHandler.sendEmptyMessageDelayed(MESSAGE_REQUEST_PERMISSION, 1000);
    }

    /**
     * Request permissions of accessing HiHealthCore.
     */
    private static void requestPermission() {
        Log.d(TAG, "request permissions");

        String[] maxScopes = Scopes.getMaxScopes();
        Scope[] allRequiredScopes = new Scope[maxScopes.length];

        for (int i = 0; i < allRequiredScopes.length; i++) {
            allRequiredScopes[i] = new Scope(maxScopes[i]);
        }

        List<Scope> scopeList = new ArrayList<>();
        Collections.addAll(scopeList, allRequiredScopes);

        HuaweiIdAuthManager.addAuthScopes(mActivity, REQUEST_CODE_PERMISSION, scopeList);

    }
}
