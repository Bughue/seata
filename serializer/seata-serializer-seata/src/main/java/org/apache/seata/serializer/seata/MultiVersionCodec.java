package org.apache.seata.serializer.seata;


import java.util.Map;

public interface MultiVersionCodec {

    Map<VersionRange, MessageSeataCodec> oldVersionCodec();

    /**
     * version range (begin, end]
     */
    class VersionRange {
        private String begin;
        private String end;

        public VersionRange(String begin, String end) {
            this.begin = begin;
            this.end = end;
        }

        public VersionRange(String end) {
            this.begin = "0";
            this.end = end;
        }

        public String getBegin() {
            return begin;
        }

        public String getEnd() {
            return end;
        }
    }
}
