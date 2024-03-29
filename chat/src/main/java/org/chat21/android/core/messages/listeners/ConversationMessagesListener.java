package org.chat21.android.core.messages.listeners;

import org.chat21.android.core.exception.ChatRuntimeException;
import org.chat21.android.core.messages.models.Message;

/**
 * Created by andrealeo on 06/12/17.
 */

public interface ConversationMessagesListener {

    void onConversationMessageReceived(Message message, ChatRuntimeException e);

    void onConversationMessageChanged(Message message, ChatRuntimeException e);

}

