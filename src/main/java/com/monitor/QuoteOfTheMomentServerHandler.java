package com.monitor;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Random;


public class QuoteOfTheMomentServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final Random random = new Random();

    // Quotes from Mohandas K. Gandhi:
    private static final String[] quotes = {
            "Where there is love there is life.",
            "First they ignore you, then they laugh at you, then they fight you, then you win.",
            "Be the change you want to see in the world.",
            "The weak can never forgive. Forgiveness is the attribute of the strong.",
    };

    private static String nextQuote() {
        int quoteId;
        synchronized (random) {
            quoteId = random.nextInt(quotes.length);
        }
        return quotes[quoteId];
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        System.err.println(packet);
        String aim = packet.content().toString(CharsetUtil.UTF_8);

        String regex = "([0-9])";//"^<([0-9])>\\s+(\\d{4}-\\d{2}-\\d{2}+\\s+\\d{2}:\\d{2}:\\d{2})\\s+(\\S+)\\s+(\\w+)\\s+(\\S+)\\s+(\\S+)\\s+(.*)\\|(\\d+\\.\\d+\\.\\d+\\.\\d+)&(\\d+\\.\\d+\\.\\d+\\.\\d+)$";
        match(regex, aim);
        //System.out.println(packet.content().toString(CharsetUtil.UTF_8));
        /*
        if ("QOTM?".equals(packet.content().toString(CharsetUtil.UTF_8))) {
            ctx.write(new DatagramPacket(
                    Unpooled.copiedBuffer("QOTM: " + nextQuote(), CharsetUtil.UTF_8), packet.sender()));
        }
        */
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
       cause.printStackTrace();
        // We don't close the channel because we can keep serving requests.
    }

    public boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        System.out.println( matcher.group());
        return matcher.matches();
    }
}
