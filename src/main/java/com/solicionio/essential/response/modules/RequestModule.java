package com.solicionio.essential.response.modules;

import com.solicionio.essential.adapters.PacketAdapter;
import com.solicionio.essential.response.ResponseDataProvider;
import com.solicionio.essential.response.enums.PacketType;
import com.solicionio.essential.response.enums.RequestType;
import com.solicionio.essential.response.handlers.IMessageHandler;
import com.solicionio.essential.response.handlers.IRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@AllArgsConstructor
@Getter
@Setter
public abstract class RequestModule implements IRequest {

    private String callName;
    private boolean sync;

    @Override
    public void error(ChannelHandlerContext ctx, Object object) {
        flush(ctx, ResponseDataProvider.errorData(object));
    }

    @Override
    public void success(ChannelHandlerContext ctx, Object object) {
        flush(ctx, ResponseDataProvider.successData(object));
    }

    @Override
    public void constantMessage(ChannelHandlerContext ctx, IMessageHandler message) {
        flush(ctx, ResponseDataProvider.constantMessage(message));
    }

    @Override
    public void flush(ChannelHandlerContext ctx, JSONObject object){
        ctx.writeAndFlush(new TextWebSocketFrame(object.toString()));
    }

    @Override
    public void onAction(ChannelHandlerContext ctx, Object msg) {
        if(sync){
            PacketAdapter.addRequestQueue(() -> { response(ctx, msg); });
            return;
        }

        response(ctx, msg);
    }

}
