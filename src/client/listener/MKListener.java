package client.listener;

import base.json.MessageJson;

public interface MKListener {
    void onReceiveMessage(MessageJson message);
}
