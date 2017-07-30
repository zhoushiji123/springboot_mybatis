package com.zsj.action;

import com.zsj.nettys.TcpServer;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by zsj on 2017/7/30.
 */
@Controller
public class TestAction {

    @Resource
    private TcpServer tcpServer;

    public TestAction(){
        execute();
    }


    private void execute(){
        try {
            tcpServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
