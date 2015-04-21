package com.yiqivr.lctodo;

import java.util.List;

import lv.framework.utils.Log;
import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

/**
 * @author lvning
 * @version create time:2015-4-17_下午5:36:25
 * @Description TODO
 */
public class LCTodoManager {

	private volatile static LCTodoManager manager;
	private LCTodoManagerActionListener listener;

	public enum ActionType {
		ADD, DELETE, UPDATE, QUERY
	}

	private LCTodoManager() {
	};

	public static LCTodoManager getInstance() {
		if (manager == null) {
			synchronized (LCTodoManager.class) {
				if (manager == null) {
					manager = new LCTodoManager();
				}
			}
		}
		return manager;
	}

	public void init(Context context) {

		AVObject.registerSubclass(TodoItem.class);
		AVOSCloud.initialize(context, "n7z4z290pdyn5tf9b3bmoisdzsg2olm4hn5qumfr7n49zg8p",
				"g9l8tkpqg2ye48ry3ul6593bi7n08oq4h8trmvledutit8ph");

		Log.d("inited...");
	}

	public void setUpListener(LCTodoManagerActionListener listener) {
		this.listener = listener;
	}

	public void addTodoItem(TodoItem item) {
		item.saveInBackground(new SaveCallback() {

			@Override
			public void done(AVException arg0) {
				if (listener != null) {
					listener.onActionCallBack(ActionType.ADD, null, arg0);
				}
			}
		});
	}

	public void deleteItem(TodoItem item) {
		item.deleteInBackground(new DeleteCallback() {

			@Override
			public void done(AVException arg0) {
				if (listener != null) {
					listener.onActionCallBack(ActionType.DELETE, null, arg0);
				}
			}
		});
	}

	public void updateItem(String objectId, final String newContent) {
		AVQuery<TodoItem> avq = AVQuery.getQuery(TodoItem.class);
		avq.whereEqualTo("objectId", objectId);
		avq.findInBackground(new FindCallback<TodoItem>() {

			@Override
			public void done(List<TodoItem> arg0, AVException arg1) {
				TodoItem oldItem = arg0.get(0);
				oldItem.putTodoContent(newContent);
				oldItem.saveInBackground(new SaveCallback() {

					@Override
					public void done(AVException arg0) {
						if (listener != null) {
							listener.onActionCallBack(ActionType.UPDATE, null, arg0);
						}
					}
				});
			}
		});
	}

	public void getItems() {
		AVQuery<TodoItem> avq = AVQuery.getQuery(TodoItem.class);
		avq.setLimit(10);
		avq.orderByDescending("updatedAt");
		avq.findInBackground(new FindCallback<TodoItem>() {

			@Override
			public void done(List<TodoItem> arg0, AVException arg1) {
				if (listener != null) {
					listener.onActionCallBack(ActionType.QUERY, arg0, arg1);
				}
			}
		});
	}

	public interface LCTodoManagerActionListener {
		void onActionCallBack(ActionType actionType, List<TodoItem> items, AVException exp);
	}
}
