package com.yiqivr.lctodo;

import lv.framework.adapter.ViewHolder;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * @author lvning
 * @version create time:2015-4-17_下午6:33:41
 * @Description TODO
 */
public class TodoItemHolder implements ViewHolder<TodoItem> {

	private TextView content;

	public TodoItemHolder(View view) {
		content = (TextView) view.findViewById(android.R.id.text1);
	}

	@Override
	public void updateViewFor(Context context, int index, TodoItem item) {
		content.setText(item.getTodoContent());
	}

}
