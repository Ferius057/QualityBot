package kz.ferius_057.qualitybot;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import kz.ferius_057.qualitybot.data.Config;
import kz.ferius_057.qualitybot.longpoll.CallbackApiLongPollHandler;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author Charles_Grozny
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Config config = Config.load(Paths.get("config.properties"));

        if (config.getToken().isEmpty()) {
            System.err.println("Установите токен.");
            return;
        }

        VkApiClient client = new VkApiClient(new HttpTransportClient());
        GroupActor actor = new GroupActor(config.getGroupId(), config.getToken());




        // SDK кидает непонятные ошибки ежедневно, приходится юзать такой метод фикса
        boolean start = true;
        while (start) {
            try {
                run(client, actor);
            } catch (Exception e) {
                start = true;
                Thread.sleep(10000);
                continue;
            }
            start = false;
        }
    }

    private static void run(final VkApiClient client, final GroupActor actor) throws ClientException, ApiException {
        CallbackApiLongPollHandler handler = new CallbackApiLongPollHandler(client, actor, 25);
        handler.run();
    }
}