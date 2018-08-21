package com.monitor;

import io.netty.util.CharsetUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regexTest {
    public  static void main(String[] args){
        String aim = "<3> 2010-01-11 19:57:08 192.168.1.233 SR 0 3 Interface 9371662 is Up, ifAdminStatus is 1, ifOperStatus is 1  |192.168.1.233&192.168.4.252";
        String regex = "^<([0-9])>\\s+(\\d{4}-\\d{2}-\\d{2}+\\s+\\d{2}:\\d{2}:\\d{2})\\s+(\\S+)\\s+(\\w+)\\s+(\\S+)\\s+(\\S+)\\s+(.*)\\|(\\d+\\.\\d+\\.\\d+\\.\\d+)&(\\d+\\.\\d+\\.\\d+\\.\\d+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(aim);

       if (matcher.find()){
           System.out.println(matcher.group(4));
           System.out.println(matcher.group(5));
           System.out.println(matcher.group(6));
        }
        //System.out.println( matcher.matches());
    }

}
