package com.ssalog.config;

import java.util.ArrayList;
import java.util.List;

import net.bis5.mattermost.client4.hook.IncomingWebhookClient;
import net.bis5.mattermost.model.IncomingWebhookRequest;
import net.bis5.mattermost.model.SlackAttachment;

public class webhook {
		
		private final static String _WEBHOOK_URL = "https://meeting.ssafy.com/hooks/yqhe18memjdadyp49q7aukq7xc";
		
		public void send(String text) {
			IncomingWebhookClient client = new IncomingWebhookClient(_WEBHOOK_URL);
			IncomingWebhookRequest payload = new IncomingWebhookRequest();
			payload.setText("@wnw1102 \n");
			payload.setUsername("Override Username");
			SlackAttachment sa = new SlackAttachment();
			sa.setColor("#36a64f");
			sa.setText(""+text);
			sa.setAuthorName("Backend Exception!");
			sa.setAuthorIcon("https://www.epicentrofestival.com/wp-content/uploads/2019/12/Front-Facing-Baby-Chick-Icon-Icon.jpg");
			List<SlackAttachment> li = new ArrayList<>();
			li.add(sa);
			payload.setAttachments(li);
			client.postByIncomingWebhook(payload);
		}
}
