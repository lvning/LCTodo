package com.yiqivr.lctodo;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * @author lvning
 * @version create time:2015-4-17_下午5:41:49
 * @Description TODO
 */
@AVClassName("TodoItem")
public class TodoItem extends AVObject {

	private static final String CONTENT = "content";

	public String getTodoContent() {
		return getString(CONTENT);
	}

	public void putTodoContent(String content) {
		put(CONTENT, content);
	}
}
