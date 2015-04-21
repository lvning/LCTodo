package com.yiqivr.lctodo;

import java.util.List;

import lv.framework.adapter.ViewHolder;
import lv.framework.adapter.ViewHolderFactory;
import lv.framework.adapter.ViewHoldingListAdapter;
import lv.framework.adapter.ViewInflator;
import lv.framework.utils.Log;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.yiqivr.lctodo.LCTodoManager.ActionType;
import com.yiqivr.lctodo.LCTodoManager.LCTodoManagerActionListener;

public class TodoListActivity extends FragmentActivity implements LCTodoManagerActionListener {

	private LCTodoManager manager;
	private ListView listView;
	private List<TodoItem> items;
	private AddTodoDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list);

		listView = (ListView) findViewById(R.id.lv_todo_list);

		manager = LCTodoManager.getInstance();
		manager.setUpListener(this);

		manager.getItems();

		dialog = new AddTodoDialog(this);

		findViewById(R.id.iv_add_todo).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.show();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				new AlertDialog.Builder(TodoListActivity.this).setTitle("请选择操作：")
						.setPositiveButton("删除", new AlertDialog.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								manager.deleteItem(items.get(position));
							}

						}).setNegativeButton("标记完成", new AlertDialog.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								TodoItem item = items.get(position);
								manager.updateItem(item.getObjectId(), item.getTodoContent() + "----已完成");
							}

						}).show();
			}
		});
	}

	@Override
	public void onActionCallBack(ActionType type, List<TodoItem> items, AVException exp) {
		switch (type) {
		case ADD:
		case DELETE:
		case UPDATE:
			manager.getItems();
			if (exp != null)
				Log.e(exp.getLocalizedMessage());
			break;
		case QUERY:
			if (items != null) {
				if (items.size() > 0) {
					this.items = items;
					ViewHoldingListAdapter<TodoItem> adapter = new ViewHoldingListAdapter<TodoItem>(this, items,
							ViewInflator.viewInflatorFor(this, android.R.layout.simple_list_item_1),
							new ViewHolderFactory<TodoItem>() {

								@Override
								public ViewHolder<TodoItem> createViewHolderFor(View view) {
									return new TodoItemHolder(view);
								}

								@Override
								public Class<? extends ViewHolder<TodoItem>> getHolderClass() {
									return TodoItemHolder.class;
								}
							});
					listView.setAdapter(adapter);
				}
			} else {
				if (exp != null)
					Log.e(exp.getLocalizedMessage());
			}
			break;
		default:
			break;
		}

	}

}
