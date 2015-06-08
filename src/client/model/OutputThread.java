package client.model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
 
/**
 * Thread to handle displaying output 
 * @author Bojun
 *
 */
public class OutputThread extends Thread {
	/**
	 * server socket
	 */
    private Socket socket = null;
    /**
     * adapter to client's view
     */
    private IModelToViewAdapter adapter;
    /**
     * 
     * @param socket server socket
     * @param viewAdapter adapter to client's view
     */
    public OutputThread(Socket socket, IModelToViewAdapter viewAdapter) {
        super("OutputThread");
        this.socket = socket;
        adapter = viewAdapter;
    }
    /**
     * display all received messages to client's view.
     */
    public void run() {
 
        try (
        	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
        	String inputLine;
        	while (true) {
	            while ((inputLine = in.readLine()) != null){
	            	JSONObject map = (JSONObject)(JSONValue.parse(inputLine));
					if (map.get(hw03Common.defs.IMessageFields.IStatusInfo.STATUS_CODE) != null) {
						adapter.outputText("status code : " + (Long)map.get(hw03Common.defs.IMessageFields.IStatusInfo.STATUS_CODE));
					}
					if (map.get(hw03Common.defs.IMessageFields.IStatusInfo.STATUS_MSG) != null) {
						adapter.outputText("status_msg : " + (String)map.get(hw03Common.defs.IMessageFields.IStatusInfo.STATUS_MSG));
					}
					if (map.get(hw03Common.defs.IMessageFields.IStatusInfo.ORIGINAL_MSG) != null) {
						try {
							adapter.outputText("original_msg : " + (String)map.get(hw03Common.defs.IMessageFields.IStatusInfo.ORIGINAL_MSG));
						} catch (Exception e) {
							adapter.outputText("original_msg : " + (JSONObject)map.get(hw03Common.defs.IMessageFields.IStatusInfo.ORIGINAL_MSG));
						}
					}
	            }
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}