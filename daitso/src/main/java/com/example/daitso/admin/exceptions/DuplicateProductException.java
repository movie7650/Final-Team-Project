package com.example.daitso.admin.exceptions;

// 예외 메시지를 포함하여 예외 객체를 생성할 수 있도록 함
public class DuplicateProductException extends RuntimeException {
    public DuplicateProductException(String message) {
        super(message);
    }
}
