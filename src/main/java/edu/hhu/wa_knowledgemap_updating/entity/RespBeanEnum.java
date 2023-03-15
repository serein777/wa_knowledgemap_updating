package edu.hhu.wa_knowledgemap_updating.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 公共返回对象枚举
 * 乐字节：专注线上IT培训
 * 答疑老师微信：lezijie
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
	//通用
	SUCCESS(200, "SUCCESS"),
	ERROR(500, "服务端异常"),
	;
	private final Integer code;
	private final String message;
}