package com.example.test_auto_browse.task;

public abstract class TimedTaskAvailableChecker {
    protected long lastSuccessTime = 0;

    protected abstract int getExecInterval();

    public long getLastSuccessTime() {
        return lastSuccessTime;
    }

    public void setLastSuccessTime(long lastSuccessTime) {
        this.lastSuccessTime = lastSuccessTime;
    }

    public boolean isTimedTaskAvailable() {
        if (System.currentTimeMillis() - lastSuccessTime >= getExecInterval()) {
            return true;
        } else {
            return false;
        }
    }
}
