package com.app.alertamedicamento.util.net;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

/**
 * @author Desconhecido
 * @company Desconhecido
 */
public class ProxyConfig {
    private String host;
    private int port;
    private Proxy proxy;

    public ProxyConfig(final String host, final int port, final Type proxyType) {
        this.host = host;
        this.port = port;
        this.proxy = new Proxy(proxyType, new InetSocketAddress(host, port));
    }

    public Proxy getProxy() {
        return proxy;
    }

    public void setProxy(final Proxy proxy) {
        this.proxy = proxy;
    }

    public String getHost() {
        return host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(final int port) {
        this.port = port;
    }

}