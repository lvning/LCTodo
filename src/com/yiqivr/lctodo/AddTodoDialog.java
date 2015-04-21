package com.yiqivr.lctodo;

import lv.framework.widget.BaseDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

/**
 * @author lvning
 * @version create time:2015-4-18_下午5:43:48
 * @Description TODO
 */
public class AddTodoDialog extends BaseDialog {
	
	private EditText edit;

	public AddTodoDialog(Context context) {
		super(context);
	}

	@Override
	protected int getContentView() {
		return R.layout.input_todo;
	}

	@Override
	protected void initializeViews() {
		super.initializeViews();
		edit = (EditText) findViewById(R.id.et_input_content);
		setClickListener(R.id.btn_input_add, this);
		setClickListener(R.id.btn_input_cancle, this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_input_add:
			String content = edit.getText().toString();
			if(TextUtils.isEmpty(content)){
				return;
			}
			TodoItem item = new TodoItem();
			item.putTodoContent(content);
			LCTodoManager.getInstance().addTodoItem(item);
			edit.setText("");
			dismiss();
			break;
		case R.id.btn_input_cancle:
			dismiss();
			break;
		default:
			break;
		}
	}
}
