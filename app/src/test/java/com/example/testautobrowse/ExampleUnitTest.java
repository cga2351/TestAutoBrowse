package com.example.testautobrowse;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        String resourceId = "com.jifen.qukan:id/b0q";

//        boolean match = resourceId.matches("^(com.jifen.qukan:id)");
        boolean match = resourceId.matches("^com.jifen.qukan:id.*");
        System.out.println("match=" + match);

    }

    @Test
    public void testRegExpress() {
//        String testString = "+1250步";
//        String testString = "叶公子5386评论";
//        String testString = "18元宝12500步";
        String testString = "188元宝500步";

//        boolean match = resourceId.matches("^(com.jifen.qukan:id)");
//        boolean match = testString.matches("^[+]\\d{1,4}[步]$");
//        boolean match = testString.matches(".*\\d{1,5}评论");
        boolean match = testString.matches("^(\\d{1,3}元宝)\\d{1,5}[步]$");

        System.out.println("match=" + match);
    }
}