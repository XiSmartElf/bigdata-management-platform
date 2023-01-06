package org.example.api.utils.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.MessageFormat;

@Data
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(Status.SUCCESS.getCode(), Status.SUCCESS.getMsg(), data);
    }

    public boolean isSuccess() {
        return this.code == Status.SUCCESS.getCode();
    }

    public static <T> Result<T> error(Status status, Object... args) {
        String msg;
        if (args.length > 0) {
            msg = MessageFormat.format(status.getMsg(), args);
        } else {
            msg = status.getMsg();
        }
        return new Result<>(status.getCode(), msg, null);
    }

    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }
}
