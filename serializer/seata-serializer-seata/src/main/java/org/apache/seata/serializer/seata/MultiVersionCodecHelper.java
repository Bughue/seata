package org.apache.seata.serializer.seata;

import org.apache.seata.core.protocol.IncompatibleVersionException;
import org.apache.seata.core.protocol.Version;

import java.util.Map;

/**
 * the type MultiVersionCodecHelper
 *
 * @author minghua.xie
 * @date 2024/11/6
 **/
public class MultiVersionCodecHelper {

    public static MessageSeataCodec match(String v, MessageSeataCodec messageCodec) {
        try {
            if (!(messageCodec instanceof MultiVersionCodec)) {
                return null;
            }
            Map<MultiVersionCodec.VersionRange, MessageSeataCodec> map = ((MultiVersionCodec) messageCodec).oldVersionCodec();
            long version = Version.convertVersion(v);
            for (MultiVersionCodec.VersionRange range : map.keySet()) {
                if (version > Version.convertVersion(range.begin) &&
                        version <= Version.convertVersion(range.end)) {
                    return map.get(version);
                }
            }
        } catch (IncompatibleVersionException e) {
            // todo 【我的todo】
        }
        return null;
    }
}
