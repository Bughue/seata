package org.apache.seata.serializer.seata.protocol.v2;

import io.netty.buffer.ByteBuf;
import org.apache.seata.core.protocol.AbstractIdentifyResponse;
import org.apache.seata.serializer.seata.protocol.AbstractResultMessageCodec;

import java.nio.ByteBuffer;

/**
 *  The type Abstract identify request codec.(v2)
 **/
public class AbstractIdentifyResponseCodecV2  extends AbstractResultMessageCodec {
    @Override
    public Class<?> getMessageClassType() {
        return AbstractIdentifyResponse.class;
    }

    @Override
    public <T> void encode(T t, ByteBuf out) {
        super.encode(t, out);
        AbstractIdentifyResponse abstractIdentifyResponse = (AbstractIdentifyResponse) t;
        boolean identified = abstractIdentifyResponse.isIdentified();
        String version = abstractIdentifyResponse.getVersion();

        out.writeByte(identified ? (byte) 1 : (byte) 0);
        if (version != null) {
            byte[] bs = version.getBytes(UTF8);
            out.writeShort((short) bs.length);
            if (bs.length > 0) {
                out.writeBytes(bs);
            }
        } else {
            out.writeShort((short) 0);
        }
    }

    @Override
    public <T> void decode(T t, ByteBuffer in) {
        super.decode(t, in);
        AbstractIdentifyResponse abstractIdentifyResponse = (AbstractIdentifyResponse) t;

        abstractIdentifyResponse.setIdentified(in.get() == 1);
        short len = in.getShort();
        if (len <= 0) {
            return;
        }
        if (in.remaining() < len) {
            return;
        }
        byte[] bs = new byte[len];
        in.get(bs);
        abstractIdentifyResponse.setVersion(new String(bs, UTF8));
    }

}
