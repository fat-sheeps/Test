package com.example;

import lombok.Data;

import java.time.LocalDateTime;

@Data
class MinuteValue {
    private LocalDateTime minute = LocalDateTime.now();
    private int value = 0;
}
