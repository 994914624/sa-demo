/*
 * Copyright © 2016 Yan Zhenjie.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.sensorsdata.demo.yang;

import com.yanzhenjie.andserver.RequestHandler;
import com.yanzhenjie.andserver.RequestMethod;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.util.HttpRequestParser;

import org.apache.httpcore.HttpException;
import org.apache.httpcore.HttpRequest;
import org.apache.httpcore.HttpResponse;
import org.apache.httpcore.entity.StringEntity;
import org.apache.httpcore.protocol.HttpContext;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * <p>Login Handler.</p>
 * Created by Yan Zhenjie on 2016/6/13.
 */
public class LoginHandler implements RequestHandler {

    @RequestMapping(method = {RequestMethod.GET})
    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        //Map<String, String> params = HttpRequestParser.parseParams(request);

       // String userName = URLDecoder.decode(params.get("username"), "utf-8");

            StringEntity stringEntity = new StringEntity("Login Succeed !!! alert(\"dd\");", "utf-8");


        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            response.setStatusCode(666);
        }
        response.setStatusCode(200);

    }
}
