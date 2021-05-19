package com.edw;

import org.apache.activemq.artemis.api.core.ActiveMQBuffer;
import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.Interceptor;
import org.apache.activemq.artemis.api.core.Message;
import org.apache.activemq.artemis.core.protocol.core.Packet;
import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionReceiveMessage;
import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionSendMessage;
import org.apache.activemq.artemis.spi.core.protocol.RemotingConnection;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     com.edw.SimpleInterceptor
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 18 Mei 2021 20:14
 */
public class SimpleInterceptor implements Interceptor {
    public boolean intercept(final Packet packet, final RemotingConnection connection) throws ActiveMQException {

        try {
            if (packet instanceof SessionSendMessage) {
                SessionSendMessage realPacket = (SessionSendMessage) packet;
                Message msg = realPacket.getMessage();

                if((msg.getTimestamp()>0) && msg.getUserID()!=null) {
                    ActiveMQBuffer activeMQBuffer = realPacket.getMessage().getBodyBuffer();

                    List<String> lines = Arrays.asList( "***** msg in *****", msg.toString(), activeMQBuffer.readNullableSimpleString().toString(), "*****");
                    Files.write(Paths.get("D:\\tmp\\amq.out"), lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
            }
            else if (packet instanceof SessionReceiveMessage) {
                SessionReceiveMessage realPacket = (SessionReceiveMessage) packet;
                Message msg = realPacket.getMessage();

                if((msg.getTimestamp()>0) && msg.getUserID()!=null) {

                    List<String> lines = Arrays.asList( "***** msg out *****", msg.toString(), "*****");
                    Files.write(Paths.get("D:\\tmp\\amq.out"), lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return true;
    }
}
