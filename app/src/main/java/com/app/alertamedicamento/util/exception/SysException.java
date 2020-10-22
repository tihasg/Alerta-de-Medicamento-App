package com.app.alertamedicamento.util.exception;

@SuppressWarnings("serial")
public class SysException extends RuntimeException {

    public SysException() {
        super();
    }

    public SysException(final String message) {
        super(message);
    }

    public SysException(final Throwable cause) {
        super(cause);
    }

    public SysException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
