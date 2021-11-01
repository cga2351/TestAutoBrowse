package com.example.test_auto_browse.task;

public abstract class TimedBaseTask extends BrowseBaseTask {
    protected long lastSuccessTime = 0;

    protected abstract int getExecInterval();

    @Override
    protected int getLeftExecCount() {
        if (System.currentTimeMillis() - lastSuccessTime > getExecInterval()) {
            return 1;
        } else {
            return 0;
        }
    }
}
