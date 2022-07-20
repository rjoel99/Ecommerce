package com.joel.message;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SuccessMessage {

	private int status;
	private String message;
	private LocalDateTime datetime;
}
