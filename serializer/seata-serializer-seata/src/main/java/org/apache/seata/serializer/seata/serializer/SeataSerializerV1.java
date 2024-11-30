package org.apache.seata.serializer.seata.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.seata.common.util.BufferUtils;
import org.apache.seata.core.protocol.AbstractMessage;
import org.apache.seata.core.protocol.ProtocolConstants;
import org.apache.seata.core.serializer.Serializer;
import org.apache.seata.serializer.seata.MessageCodecFactory;
import org.apache.seata.serializer.seata.MessageSeataCodec;
import org.apache.seata.serializer.seata.SeataSerializer;

import java.nio.ByteBuffer;

/**
 * SeataSerializer of V1
 **/
public class SeataSerializerV1 implements Serializer {

    private static volatile SeataSerializerV1 instance;

    protected SeataSerializerV1() {
    }

    public static SeataSerializerV1 getInstance() {
        if (instance == null) {
            synchronized (SeataSerializerV1.class) {
                if (instance == null) {
                    instance = new SeataSerializerV1();
                }
            }
        }
        return instance;
    }

    @Override
    public <T> byte[] serialize(T t) {
        if (!(t instanceof AbstractMessage)) {
            throw new IllegalArgumentException("AbstractMessage isn't available.");
        }
        AbstractMessage abstractMessage = (AbstractMessage) t;
        //type code
        short typecode = abstractMessage.getTypeCode();
        //msg codec
        MessageSeataCodec messageCodec = MessageCodecFactory.getMessageCodec(typecode, protocolVersion());
        //get empty ByteBuffer
        ByteBuf out = Unpooled.buffer(1024);
        //msg encode
        messageCodec.encode(t, out);
        byte[] body = new byte[out.readableBytes()];
        out.readBytes(body);

        ByteBuffer byteBuffer;

        //typecode + body
        byteBuffer = ByteBuffer.allocate(2 + body.length);
        byteBuffer.putShort(typecode);
        byteBuffer.put(body);

        BufferUtils.flip(byteBuffer);
        byte[] content = new byte[byteBuffer.limit()];
        byteBuffer.get(content);
        return content;
    }

    @Override
    public <T> T deserialize(byte[] bytes) {
        return SeataSerializer.deserializeByVersion(bytes, protocolVersion());
    }

    public byte protocolVersion(){
        return ProtocolConstants.VERSION_1;
    }
}
