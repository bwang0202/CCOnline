package server.controller;

import server.model.IServerModelToViewAdapter;
import server.model.ServerModel;
import server.view.IServerViewToModelAdapter;
import server.view.ServerView;
import debug.Logger;

/**
 * The controller that implements the adapters between the server view and model
 * @author Fabio-CSM
 *
 */
public class ServerController {

	 /**
	 * The server model
	 */
	private ServerModel model;
	 
	/**
	 * The server view
	 */
	private ServerView view;
	  
	/**
	 * The main method to run the server application
	 * @param args args
	 */
	public static void main(String[] args) {
		Logger.setOut();
		new ServerController().start();
	}
	 
	/**
	 * The server constructor
	 */
	public ServerController() {
		model = new ServerModel(new IServerModelToViewAdapter() {
			public void outputText(String text) {
				view.outputText(text);
			}

			/* (non-Javadoc)
			 * @see server.model.IServerModelToViewAdapter#addConnectedClient(java.lang.String)
			 */
			@Override
			public void addConnectedClient(String ipAddress) {
				view.addConnectedClient(ipAddress);
			}

			/* (non-Javadoc)
			 * @see server.model.IServerModelToViewAdapter#addLanguage(java.lang.String)
			 */
			@Override
			public void addLanguage(String langName) {
				view.addLanguage(langName);
			}

			/* (non-Javadoc)
			 * @see server.model.IServerModelToViewAdapter#addProgram(java.lang.String)
			 */
			@Override
			public void addProgram(String progName) {
				view.addProgram(progName);
			}
		});
		view = new ServerView(new IServerViewToModelAdapter() {
			
			/* (non-Javadoc)
			 * @see server.view.IServerViewToModelAdapter#sendStatusMsg(java.lang.Integer)
			 */
			@Override
			public void sendStatusMsg(Integer msg) {
				model.sendStatusMsg(msg);
			}
			
		});
			
	}

	/**
	 * The method which starts the server view and the model.
	 */
	private void start() {
		view.start();
		model.start();
	}
	
}
