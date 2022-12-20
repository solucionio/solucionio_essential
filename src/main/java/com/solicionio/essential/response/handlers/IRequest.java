package com.solicionio.essential.response.handlers;

import io.netty.channel.ChannelHandlerContext;
import org.json.JSONObject;

public interface IRequest {

    void onAction(ChannelHandlerContext ctx, Object msg);
    void response(ChannelHandlerContext ctx, Object msg);
    void flush(ChannelHandlerContext ctx, JSONObject object);
    void error(ChannelHandlerContext ctx, Object object);
    void success(ChannelHandlerContext ctx, Object object);
    void constantMessage(ChannelHandlerContext ctx, IMessageHandler message);
}
