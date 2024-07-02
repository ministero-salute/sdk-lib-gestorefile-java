/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.gestorefile.exception;

public class XSDNonSupportedException extends RuntimeException {

    public XSDNonSupportedException() {
        super();
    }

    public XSDNonSupportedException(String message) {
        super(message);
    }

    public XSDNonSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public XSDNonSupportedException(Throwable cause) {
        super(cause);
    }

    protected XSDNonSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
