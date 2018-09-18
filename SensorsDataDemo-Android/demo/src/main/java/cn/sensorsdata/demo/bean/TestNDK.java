package cn.sensorsdata.demo.bean;

/**
 * Created by yang on 2017/9/6.
 */

public class TestNDK {
    public static native String myMethodString() ;

    static {
        System.loadLibrary("native-lib");
    }
}
