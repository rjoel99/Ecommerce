package com.joel.message;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

/**
 * 
 * @author joel.rubio
 *
 */
@Builder
@Getter
public class ErrorMessage {

	private int status;
	private String message;
	private LocalDateTime datetime;
	private String path;
}
