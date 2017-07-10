package util.exception;

/**
 * 系统运行时异常父类.
 * @author plz
 */
public class BizRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;

    public BizRuntimeException() {
    }

    public BizRuntimeException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BizRuntimeException(ResCode resCode) {
        this(resCode.getRetCode(), resCode.getDesc());
    }

    public BizRuntimeException(String message) {
        super(message);
        this.code = ResCode.SERVER_ERROR.getRetCode();
        this.message = message;
    }

    public BizRuntimeException(Throwable cause) {
        super(cause);
        this.code = ResCode.SERVER_ERROR.getRetCode();
        this.message = cause.getMessage();
    }

    public BizRuntimeException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResCode.SERVER_ERROR.getRetCode();
        this.message = message;
    }

    public BizRuntimeException(String message, Throwable cause,
                               boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = ResCode.SERVER_ERROR.getRetCode();
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
