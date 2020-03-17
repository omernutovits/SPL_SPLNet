package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.MessageEncoderDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class StompMessageEncoderDecoder implements MessageEncoderDecoder<String> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k;
    private int len = 0;
    private static final String ENDLINE = "\u0000";
    private String message = "";


    @Override
    public String decodeNextByte(byte nextByte) {

        if(nextByte == '\u0000') return popString();

        pushByte(nextByte);
        return null;
    }

    private String getLine(byte nextByte){
        if (nextByte == '\u0000') {
            return popString();
        }

        pushByte(nextByte);
        return null;
    }

    @Override
    public byte[] encode(String message) {
        System.out.println(message);
        return (message).getBytes(); //uses utf8 by default
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    private String popString() {
        String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
        len = 0;
        return result;
    }
}
