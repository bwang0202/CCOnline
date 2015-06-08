package client.controller;

import java.io.File;

import client.model.ClientModel;
import client.model.FileWrapper;
import client.model.IModelToViewAdapter;
import client.view.ClientViewNew;
import client.view.IViewToModelAdapterNew;
import debug.Logger;

/**
 * Class which controls the model and the view of the client side and implements the both adapters.
 *
 */
public class ClientController {

	 /**
	 * The client model
	 */
	private ClientModel model;
	 
	/**
	 * The client view
	 */
	private ClientViewNew view;
	 
	/**
	 * The main method which runs the client application
	 * @param args	code
	 */
	public static void main(String[] args) {
		Logger.setOut();
		new ClientController().start();
	}
	 
	/**
	 * The constructor if the client controller which implements the both adapters
	 */
	public ClientController() {
		model = new ClientModel(new IModelToViewAdapter() {
			public void outputText(String text) {
				view.outputText(text);
			}
		});
		view = new ClientViewNew(new IViewToModelAdapterNew() {
			
			/* (non-Javadoc)
			 * @see client.view.IViewToModelAdapterNew#parseProgram(java.io.File, java.lang.String, java.lang.String)
			 */
			@Override
			public void parseProgram(File file, String langName, String ipAddress) {
				model.parseProgram(file, langName, ipAddress);
				
			}
			
			/* (non-Javadoc)
			 * @see client.view.IViewToModelAdapterNew#parseLanguage(java.io.File, java.lang.String, java.lang.String)
			 */
			@Override
			public void parseLanguage(File file, String ipAddress, String opMapFileName) {
				model.parseLanguage(file, ipAddress, opMapFileName);
				
			}
			
			/* (non-Javadoc)
			 * @see client.view.IViewToModelAdapterNew#loadProgram(java.lang.String)
			 */
			@Override
			public FileWrapper loadProgram(String filename) {
				return model.loadFile(filename);
			}
			
			/* (non-Javadoc)
			 * @see client.view.IViewToModelAdapterNew#loadOpMap(java.lang.String)
			 */
			@Override
			public FileWrapper loadOpMap(String filename) {
				return model.loadFile(filename);
			}
			
			/* (non-Javadoc)
			 * @see client.view.IViewToModelAdapterNew#loadLanguage(java.lang.String)
			 */
			@Override
			public FileWrapper loadLanguage(String filename) {
				return model.loadFile(filename);
			}
			
			@Override
			public void executeProgram(String langName, String progName, String init,
					String ipAddress) {
				// TODO executeProgram with init code
				model.executeProgram(langName, progName, init, ipAddress);
				
			}
			
			@Override
			public String connectSocket(String hostName, int portNumber) {
				return model.connectSocket(hostName, portNumber);
			}
		});
	}

	/**
	 * The method which starts the model and the view
	 */
	private void start() {
		model.start();
		view.start();
	}
	
}
