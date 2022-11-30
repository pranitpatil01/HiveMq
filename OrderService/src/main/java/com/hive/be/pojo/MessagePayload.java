package com.hive.be.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagePayload implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String topic;
	private String message;

}
