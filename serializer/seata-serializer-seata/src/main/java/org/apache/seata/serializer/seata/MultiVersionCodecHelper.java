package org.apache.seata.serializer.seata;

import org.apache.seata.core.protocol.IncompatibleVersionException;
import org.apache.seata.core.protocol.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * the type MultiVersionCodecHelper
 *
 **/
public class MultiVersionCodecHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiVersionCodecHelper.class);

    public static MessageSeataCodec match(String v, MessageSeataCodec messageCodec) {
        try {
            if (!(messageCodec instanceof MultiVersionCodec)) {
                return null;
            }
            Map<MultiVersionCodec.VersionRange, MessageSeataCodec> map = ((MultiVersionCodec) messageCodec).oldVersionCodec();
            long version = Version.convertVersion(v);
            for (MultiVersionCodec.VersionRange range : map.keySet()) {
                if (version > Version.convertVersion(range.getBegin()) &&
                        version <= Version.convertVersion(range.getEnd())) {
                    return map.get(version);
                }
            }
        } catch (IncompatibleVersionException e) {
            LOGGER.error("match version error", e);
        }
        return null;
    }
}
