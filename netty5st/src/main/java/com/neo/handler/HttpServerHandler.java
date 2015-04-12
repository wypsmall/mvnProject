package com.neo.handler;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.EndOfDataDecoderException;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/** 
 * desc:
 * <p>创建人：wangyunpeng 创建日期：2015年4月12日</p>
 * @version V1.0  
 * Netty处理HTTP之GET,POST请求
 * http://www.open-open.com/lib/view/1401241181246
 * Netty实现Http服务器端（二）
 * http://my.oschina.net/xinxingegeya/blog/295408
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); //Disk
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		String method = msg.getMethod().name();
		System.out.println("method==>" + method);
		Map<String, String> param = new HashMap<String, String>();
		//获取POST请求参数
		if(HttpMethod.POST.equals(msg.getMethod())) {
			HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(factory, msg);
			try {
				while (decoder.hasNext()) {
	                InterfaceHttpData data = decoder.next();
	                if (data != null) {
                    	if (data.getHttpDataType() == HttpDataType.Attribute) {
                    		Attribute attribute = (Attribute) data;
                    		param.put(attribute.getName(), attribute.getValue());
                    	}
                        data.release();
	                }
	            }
			} catch (EndOfDataDecoderException e) {
				System.out.println("END OF POST CONTENT");
			}
			
		//获取GET请求参数
		} else if(HttpMethod.GET.equals(msg.getMethod())) {
			QueryStringDecoder decoderQuery = new QueryStringDecoder(msg.getUri());
            Map<String, List<String>> uriAttributes = decoderQuery.parameters();
            for (Entry<String, List<String>> attr : uriAttributes.entrySet()) {
                for (String attrVal : attr.getValue()) {
                    param.put(attr.getKey(), attrVal);
                }
            }
		}
		System.out.println("class : " + msg.getClass().getName());
		
		//发送成功响应
		sendSucceedClose(ctx, param);
	}
	

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		sendFailedClose(ctx);
	}
	
	private void sendSucceedClose(ChannelHandlerContext ctx, Map<String, String> param) {
		System.out.println("param is " + param.toString());
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.copiedBuffer(param.toString(), CharsetUtil.UTF_8));
		response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		System.out.println("sendSucceedClose");
		
	}
	
	private void sendSucceedClose(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.copiedBuffer("Succeed: " + OK + "\r\n", CharsetUtil.UTF_8));
		response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		System.out.println("sendSucceedClose");
	}

	private void sendFailedClose(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST, Unpooled.copiedBuffer("Failure: " + BAD_REQUEST + "\r\n", CharsetUtil.UTF_8));
		response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		System.err.println("sendFailedClose");
	}
}
