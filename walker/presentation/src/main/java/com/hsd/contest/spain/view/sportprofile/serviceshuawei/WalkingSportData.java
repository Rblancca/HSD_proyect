package com.hsd.contest.spain.view.sportprofile.serviceshuawei;

import com.hsd.contest.spain.view.sportprofile.serviceshuawei.BaseSportData;

public class WalkingSportData extends BaseSportData {
    private long mTimestamp;
    private long mStepDelta;

    public WalkingSportData(long timeStamp, long stepDelta) {
        mTimestamp = timeStamp;
        mStepDelta = stepDelta;
    }

    public long getStepDelta() {
        return mStepDelta;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public String toString() {
        return String.format("timeStamp: %d, value: %d", mTimestamp, mStepDelta);
    }
}
