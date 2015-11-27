package com.kii.blacbot.Ochat.ui.loader;

import java.util.List;

import android.content.Context;

import com.kii.blacbot.Ochat.model.ChatStamp;

/**
 * A Loader to get {@link ChatStamp} from chat_stamps bucket.
 * 
 * @author noriyoshi.fukuzaki@kii.com
 */
public class ChatStampListLoader extends AbstractAsyncTaskLoader<List<ChatStamp>> {

	public ChatStampListLoader(Context context) {
		super(context);
	}
	@Override
	public List<ChatStamp> loadInBackground() {
		return ChatStamp.listOrderByNewly();
	}

}
