package cn.sensorsdata.demo.bean;

/**
 * Created by yang on 2017/12/15
 */

public class UserData {
    private String name;
    private String age;


    public UserData(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public String getAge() {
        return this.age;
    }


}
