package com.solicionio.essential.providers;

import com.solicionio.essential.response.enums.RequestType;
import com.solicionio.essential.response.modules.RequestModule;
import io.netty.channel.ChannelHandlerContext;
import org.json.JSONObject;

public class Login extends RequestModule {

    public Login() {
        super(RequestType.LOGIN);
    }

    @Override
    public void response(ChannelHandlerContext ctx, Object msg) {
        JSONObject object = new JSONObject();
        object.put("data", "login");
        object.put("status", true);

        //SAMPLE ERROR USE
        if(false) {
            error(ctx, object);
            return;
        }

        success(ctx, object);
    }
}
