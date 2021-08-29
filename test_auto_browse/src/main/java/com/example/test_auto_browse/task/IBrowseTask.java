package com.example.test_auto_browse.task;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2021/5/19
 * description :
 */

public interface IBrowseTask {
    boolean initTask() throws InterruptedException;
    boolean runTask() throws InterruptedException;
    void endTask() throws InterruptedException;

    // auto browse task max run count per day
    boolean isTaskAvailable();
    void increaseExecCount();
    int waitTaskEndMaxTime();

    void setForceStop(boolean forceStop);
    boolean getForceStop();
    String getTargetAppPackageName();
}
