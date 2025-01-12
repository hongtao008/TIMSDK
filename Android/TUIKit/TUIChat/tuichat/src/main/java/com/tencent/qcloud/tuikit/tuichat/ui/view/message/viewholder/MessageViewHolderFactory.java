package com.tencent.qcloud.tuikit.tuichat.ui.view.message.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.TUIChatService;
import com.tencent.qcloud.tuikit.tuichat.bean.message.TipsMessageBean;
import com.tencent.qcloud.tuikit.tuichat.ui.interfaces.ICommonMessageAdapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class MessageViewHolderFactory {

    public static RecyclerView.ViewHolder getInstance(ViewGroup parent, ICommonMessageAdapter adapter, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder holder = null;
        View view = null;

        // 头部的holder
        if (viewType == MessageBaseHolder.MSG_TYPE_HEADER_VIEW) {
            view = inflater.inflate(R.layout.loading_progress_bar, parent, false);
            holder = new MessageHeaderHolder(view);
            return holder;
        }

        // 具体消息holder
        if (viewType == TUIChatService.getInstance().getViewType(TipsMessageBean.class)) {
            view = inflater.inflate(R.layout.message_adapter_item_empty, parent, false);
            holder = new TipsMessageHolder(view);
        } else {
            view = inflater.inflate(R.layout.message_adapter_item_content, parent, false);
            holder = getViewHolder(view, viewType);
        }

        if (holder == null) {
            holder = new TextMessageHolder(view);
        }
        ((MessageBaseHolder) holder).setAdapter(adapter);

        return holder;
    }

    private static RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
        Class<? extends MessageBaseHolder> messageHolderClazz = TUIChatService.getInstance().getMessageViewHolderClass(viewType);;
        if (messageHolderClazz != null) {
            Constructor<? extends MessageBaseHolder> constructor;
            try {
                constructor = messageHolderClazz.getConstructor(View.class);
                return constructor.newInstance(view);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
