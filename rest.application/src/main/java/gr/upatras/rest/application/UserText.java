package gr.upatras.rest.application;

public class UserText {
	public String message;
	
	public UserText(String message) {
		 this.message = message;
		 }
	
	public void publishText() {
		 MQTTClient client = new MQTTClient();
		 client.runClient(this);
		}
}

