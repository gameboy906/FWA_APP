package com.wolfs.fwa.fwa2_0;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class GeneralSshStuff {
    private static Session getSession(String ip) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession("pi",  ip, 22);
        session.setPassword("raspberry");
        session.setConfig(getConfiguration());
        session.connect();
//		System.out.println("Connected");
        return session;
    }

    private static Properties getConfiguration() {
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        return config;
    }

    public static String executeCommand(String command, String ip) throws JSchException {
        Session session = getSession(ip);
//		System.out.println("Channel opened");
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);

        return getAnswer(channel);
    }

    public static String executeCommands(List<String> commands, String ip) throws JSchException {
        Session session = getSession(ip);
        Channel channel = session.openChannel("exec");
        for (String commandLine : commands) {
            ((ChannelExec) channel).setCommand(commandLine);
            channel.connect();
            channel.disconnect();
        }
        return "";
    }

    private static String getAnswer(Channel channel) throws JSchException {
        channel.setInputStream(null);
        String answer = null;
        // StringBuilder test = new StringBuilder();
        try {
            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    // System.out.print(new String(tmp, 0, i));
                    // test.append(new String(tmp, 0, i));
                    answer = new String(tmp, 0, i);

                }
                if (channel.isClosed()) {
//					log.warn("Channel closed");
                    break;
                }
                Thread.sleep(1000);
            }
            channel.disconnect();
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
        // return test.toString();
        return answer;
    }
}
