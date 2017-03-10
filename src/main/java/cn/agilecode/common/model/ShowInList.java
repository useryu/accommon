package cn.agilecode.common.model;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ShowInList {
	
	public String chname();

	public String property() default "";
	
	public boolean needConvert() default false;
}
