package com.ether.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sde")
public class SdeProperties {
    private String path;
    private Cbor cbor = new Cbor();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Cbor getCbor() {
        return cbor;
    }

    public void setCbor(Cbor cbor) {
        this.cbor = cbor;
    }

    public static class Cbor {
        private Cache cache = new Cache();

        public Cache getCache() {
            return cache;
        }

        public void setCache(Cache cache) {
            this.cache = cache;
        }
        public static class Cache {
            private String path;

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }
        }
    }
}
