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
        String testString = "叶公子5386评论";

//        boolean match = resourceId.matches("^(com.jifen.qukan:id)");
//        boolean match = testString.matches("^[+]\\d{1,4}[步]$");
        boolean match = testString.matches(".*\\d{1,5}评论");

        System.out.println("match=" + match);
    }
}