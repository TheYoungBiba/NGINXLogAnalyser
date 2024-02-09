package edu.project;

import java.net.InetAddress;
import java.time.ZonedDateTime;

public record LogRecord(
    InetAddress remoteAddress,
    String remoteUserLogin,
    ZonedDateTime dateOfRequest,
    Request request,
    int httpStatusCode,
    int byteSize,
    String refererLink,
    String userAgent
) {
    public record Request(ConnectionType connectionType, String resource, ProtocolVersion version) {}

    public enum ConnectionType {
        GET,
        POST,
        PUT,
        DELETE,
        HEAD,
        CONNECT,
        PRI
    }

    public enum ProtocolVersion {
        HTTP_0_9,
        HTTP_1_0,
        HTTP_1_1,
        HTTP_2_0,
        HTTP_3_0;
    }

    public enum HttpStatusCode {
        CONTINUE(100),
        SWITCHING_PROTOCOL(101),
        PROCESSING(102),
        EARLY_HINTS(103),
        OK(200),
        CREATED(201),
        ACCEPTED(202),
        NON_AUTHORITATIVE_INFORMATION(203),
        NO_CONTENT(204),
        RESET_CONTENT(205),
        ALREADY_REPORTED(208),
        MULTIPLE_CHOICES(300),
        MOVED_PERMANENTLY(301),
        MOVED_TEMPORARILY(302),
        SEE_OTHER(303),
        NOT_MODIFIED(304),
        USE_PROXY(305),
        TEMPORARY_REDIRECT(307),
        PERMANENT_REDIRECT(308),
        BAD_REQUEST(400),
        UNAUTHORIZED(401),
        PAYMENT_REQUIRED(402),
        FORBIDDEN(403),
        NOT_FOUND(404),
        METHOD_NOT_ALLOWED(405),
        NOT_ACCEPTABLE(406),
        PROXY_AUTHENTICATION_REQUIRED(407),
        REQUEST_TIMEOUT(408),
        PAYLOAD_TOO_LARGE(413),
        URI_TOO_LONG(414),
        TOO_MANY_REQUESTS(429),
        CLIENT_CLOSED_REQUEST(499),
        INTERNAL_SERVER_ERROR(500),
        NOT_IMPLEMENTED(501),
        BAD_GATEWAY(502),
        SERVICE_UNAVAILABLE(503),
        GATEWAY_TIMEOUT(504),
        INSUFFICIENT_STORAGE(507),
        LOOP_DETECTED(508),
        BANDWIDTH_LIMIT_EXCEEDED(509),
        UNKNOWN_ERROR(520),
        WEB_SERVER_IS_DOWN(521),
        CONNECTION_TIMED_OUT(522),
        ORIGIN_IS_UNREACHABLE(523),
        TIMEOUT_OCCURRED(524),
        SSL_HANDSHAKE_FAILED(525),
        INVALID_SSL_CERTIFICATE(526);

        private final int code;

        HttpStatusCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
