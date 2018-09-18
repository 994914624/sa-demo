package cn.sensorsdata.demo.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by yang on 2017/5/27
 */

public class WeChatMessage extends DataSupport{

    @Column(nullable = false)
    public String nick;
    public String message;

    public WeChatMessage(String nick, String message) {
        this.nick = nick;
        this.message = message;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
