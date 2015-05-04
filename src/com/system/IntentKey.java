package com.system;

/**
 * intent跳转key值的枚举类
 * */
public enum IntentKey 
{
	/**
	 * 说明：标记是否为左片<br>
	 * 用法：验证值为true或false
	 * */
	IsLeftImage,
	/**
	 * 说明：标记传递的值为拍摄参数<br>
	 * 用法：默认取值使用
	 * */
	PhotoParam, 
	/**
	 * 说明：标记传递的值为缩略图路径<br>
	 * 用法：默认取值使用
	 * */
	PhotoThumbnailPath,
	/**
	 * 说明：标记是否为存储模式<br>
	 * 用法：验证intent中是否包含此key
	 * */
	IsSave,
	/**
	 * 说明：标记是否为需要绘制角点<br>
	 * 用法：验证intent中是否包含此key
	 * */
	DrawHarris,
	/**
	 * 说明：标记传递的值为标定结果<br>
	 * 用法：默认取值使用
	 * */
	CalibrationResult,
	/**
	 * 说明：标记是否为重采样模式<br>
	 * 用法：验证值为true或false
	 * */
	Isremap,
	/**
	 * 说明：标记是否为标定模式<br>
	 * 用法：验证值为true或false
	 * */
	IsCalibration;
}
