package server.model;

import hw03Common.defs.IMessageFields;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONValue;

import server.MultiUserThread;
/**
 * The server model class
 *
 */
public class ServerModel {
	
	/**
	 * The sockets that the server is connected to.
	 */
	private Set<Socket> sockets = new HashSet<Socket>();
	/**
	 * The adapter to the server view
	 */
	private IServerModelToViewAdapter adapter;

	/**
	 * The constructor to the server model
	 * @param adapter The adapter between the model and the view
	 */
	public ServerModel(IServerModelToViewAdapter adapter) {
		this.adapter = adapter;
	}
	
	/**
	 * The method which starts the server model
	 */
	public void start() {
		try (ServerSocket _serverSocket = new ServerSocket(hw03Common.defs.INetworkConfig.SERVER_PORT)) {
			while (true) {
				Socket _socket = _serverSocket.accept();
				sockets.add(_socket);
				adapter.addConnectedClient(_socket.getInetAddress().getHostAddress());
				new MultiUserThread(_socket, adapter).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * The method which sends the message status code manually.
	 * @param msgCode The integer message status code
	 */
	public void sendStatusMsg(Integer msgCode){
		for (Socket socket : sockets) {
		PrintWriter out;
		Map<String, Object> status_msg;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			status_msg = new LinkedHashMap<String, Object>();
			status_msg.put(IMessageFields.IStatusInfo.STATUS_CODE, msgCode);
			status_msg.put(IMessageFields.IStatusInfo.STATUS_MSG, "testing msg\n");
			status_msg.put(IMessageFields.IStatusInfo.ORIGINAL_MSG, "N/A \n");
			out.println(JSONValue.toJSONString(status_msg));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		}
	}
}
