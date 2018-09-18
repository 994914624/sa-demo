package cn.sensorsdata.demo.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by yang on 2018/1/11
 */

public class EmulatorUtils {


    /**
     * 是否为模拟器
     * @return true 表示为模拟器
     */
    public static Boolean isEmulator(Context context){
        return !existLightSensor(context);
    }

    /**
     * 模拟器目前不存在光感模拟器
     * 部分真机也不存在温度和压力传感器。
     * @return false 不存光感模拟器
     */
    private static Boolean existLightSensor(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        return sensor!=null;
    }
}
