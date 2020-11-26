package com.hsd.contest.spain.view.sportprofile.serviceshuawei;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.hihealth.AutoRecorderController;
import com.huawei.hms.hihealth.HiHealthOptions;
import com.huawei.hms.hihealth.HuaweiHiHealth;
import com.huawei.hms.hihealth.data.DataCollector;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.data.DeviceInfo;
import com.huawei.hms.hihealth.data.Field;
import com.huawei.hms.hihealth.data.SamplePoint;
import com.huawei.hms.hihealth.options.OnSamplePointListener;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;

import java.util.concurrent.TimeUnit;

public class HiHealthBaseAdapter {
    private static final String TAG = "HiHealthBaseAdapter";

    private static final Object OBJECT = new Object();

    private static final Object PROCESS_OBJECT = new Object();

    public static final long SAMPLE_INTERVAL = 10 * 1000L;

    private static final long FIRST_DELAY = 1000L;

    private ISportListener mSportListener;

    private Context mContext;

    private AutoRecorderController autoRecorderController;

    private boolean mIsStopped;

    private long mBaseTimeStamp;

    private long mCurrentStartTime;

    private int mTempSetpValue;

    private int mSteps;

    private Handler mHandler;

    private Runnable mSampleHandler;

    private OnSamplePointListener mListener = new OnSamplePointListener() {
        @Override
        public void onSamplePoint(SamplePoint samplePoint) {
            Log.d(TAG, "on sample point received");
            processSamplePoint(samplePoint);
        }
    };


    public HiHealthBaseAdapter(Context context, ISportListener listener) {
        mContext = context;
        mSportListener = listener;
        mSteps = 0;
        mBaseTimeStamp = 0L;
        mCurrentStartTime = 0L;
        mTempSetpValue = 0;
        mIsStopped = false;

        HiHealthOptions options = HiHealthOptions.builder().build();
        AuthHuaweiId hwId = HuaweiIdAuthManager.getExtendedAuthResult(options);
        autoRecorderController = HuaweiHiHealth.getAutoRecorderController(mContext, hwId);

        setupSample();
    }

    private void processSamplePoint(SamplePoint samplePoint) {
        synchronized (PROCESS_OBJECT) {
            if (mIsStopped) {
                return;
            }
        }

        if (mCurrentStartTime == 0) {
            mCurrentStartTime = samplePoint.getEndTime(TimeUnit.MILLISECONDS);
            mTempSetpValue = samplePoint.getFieldValue(Field.FIELD_STEPS).asIntValue();
            return;
        }

        mBaseTimeStamp = samplePoint.getEndTime(TimeUnit.MILLISECONDS);
        mSteps = samplePoint.getFieldValue(Field.FIELD_STEPS).asIntValue() - mTempSetpValue;
        mTempSetpValue = samplePoint.getFieldValue(Field.FIELD_STEPS).asIntValue();
    }

    private void setupSample() {
        mHandler = new Handler();
        mSampleHandler = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "sample real time data");
                if (mBaseTimeStamp != 0L && mSteps != 0) {
                    WalkingSportData walkingSportData =
                            new WalkingSportData(mBaseTimeStamp - mCurrentStartTime, mSteps);
                    mSportListener.onRecvData(walkingSportData);
                    mSteps = 0;
                    Log.d(TAG, String.format("feed data: %s", walkingSportData.toString()));
                }

                mHandler.postDelayed(this, SAMPLE_INTERVAL);
            }
        };
    }

    public boolean start(ISportListener listener) {
        Log.i(TAG, "begin to access sensor");

        synchronized (OBJECT) {
            mIsStopped = false;
        }

        DataCollector dataCollector = new DataCollector.Builder().setDataType(DataType.DT_CONTINUOUS_STEPS_DELTA)
                .setDataGenerateType(DataCollector.DATA_TYPE_RAW)
                .setPackageName(this.mContext)
                .setDeviceInfo(new DeviceInfo("hw", "hw", "hw", 0))
                .build();

        autoRecorderController.startRecord(DataType.DT_CONTINUOUS_STEPS_TOTAL, mListener)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "success");
                        } else {
                            Log.i(TAG, "" + task.getException());
                        }
                    }
                });

        mHandler.postDelayed(mSampleHandler, FIRST_DELAY);
        mCurrentStartTime = 0L;

        return true;
    }

    public boolean stop() {
        Log.i(TAG, "stop to access sensor");

        synchronized (OBJECT) {
            mIsStopped = true;
        }

        autoRecorderController.stopRecord(DataType.DT_CONTINUOUS_STEPS_TOTAL, mListener)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "success");
                        } else {
                            Log.i(TAG, "" + task.getException());
                        }
                    }
                });
        mHandler.removeCallbacks(mSampleHandler);
        mBaseTimeStamp = 0L;
        mCurrentStartTime = 0L;
        mTempSetpValue = 0;
        mSteps = 0;

        return true;
    }
}
