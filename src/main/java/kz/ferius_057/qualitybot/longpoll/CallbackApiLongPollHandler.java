package kz.ferius_057.qualitybot.longpoll;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.events.EventsHandler;
import com.vk.api.sdk.events.longpoll.GroupLongPollApi;
import com.vk.api.sdk.objects.groups.LongPollEvents;
import com.vk.api.sdk.objects.messages.Message;

public class CallbackApiLongPollHandler extends GroupLongPollApi {

    public CallbackApiLongPollHandler(final VkApiClient client, final GroupActor actor, final int waitTime) {
        super(client, actor, waitTime);
    }

    @Override
    public void messageNew(Integer groupId, final Message message) {
        System.out.println(message.getText());
    }
}