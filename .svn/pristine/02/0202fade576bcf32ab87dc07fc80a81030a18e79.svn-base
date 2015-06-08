package client.view;

import hw03Common.defs.INetworkConfig;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.model.FileWrapper;

/**
 * The client view
 * @author Fabio-CSM
 *
 */
public class ClientViewNew extends JFrame {

	/**
	 * The unique serial ID
	 */
	private static final long serialVersionUID = 1433155985163558062L;
	private JPanel borderLayout;
	private final JPanel pnl_top = new JPanel();
	private final JPanel pnl_bottom = new JPanel();
	private final JPanel pnl_servers = new JPanel();
	private final JLabel lblConnectedServers = new JLabel("Connected Servers:");
	private DefaultListModel<String> serversModel = new DefaultListModel<String>();
	private final JList<String> list_servers = new JList<String>(serversModel);
	private final JPanel pnl_languages = new JPanel();
	private final JLabel lblLanguages = new JLabel("Loaded Languages:");
	private DefaultListModel<FileWrapper> languagesModel = new DefaultListModel<FileWrapper>();
	private final JList<FileWrapper> list_languages = new JList<FileWrapper>(languagesModel);
	private final JPanel pnl_programs = new JPanel();
	private DefaultListModel<FileWrapper> programsModel = new DefaultListModel<FileWrapper>();
	private final JLabel lblLoadedPrograms = new JLabel("Loaded Programs:");
	private final JList<FileWrapper> list_programs = new JList<FileWrapper>(programsModel);
	private final JPanel pnl_opMaps = new JPanel();
	private final JLabel lblLoadedOpMaps = new JLabel("Loaded Op. Maps:");
	private DefaultListModel<FileWrapper> opMapsModel = new DefaultListModel<FileWrapper>();
	private final JList<FileWrapper> list_opMaps = new JList<FileWrapper>(opMapsModel);
	private final JPanel pnl_center = new JPanel();
	private final JTextArea txa_console = new JTextArea();
	private final JScrollPane scrpnl_console = new JScrollPane(txa_console);
	private final JLabel lblConsole = new JLabel("Console");
	private final JPanel pnl_left = new JPanel();
	private final JLabel lblIpAddress = new JLabel("IP Address:");
	private final JTextField txf_ipAddress = new JTextField();
	private final JButton btnConnect = new JButton("Connect");
	private final JLabel lblLanguage = new JLabel("Language:");
	private final JTextField txf_language = new JTextField();
	private final JButton btn_loadLanguage = new JButton("Load");
	private final JLabel lblProgram = new JLabel("Program:");
	private final JTextField txf_program = new JTextField();
	private final JButton btn_loadProgram = new JButton("Load");
	private final JLabel lblOpMap = new JLabel("Op. Map:");
	private final JTextField txf_opMap = new JTextField();
	private final JButton btn_loadOpMap = new JButton("Load");
	private final JPanel pnl_right = new JPanel();
	private final JLabel lblInitialization = new JLabel("Initialization Code");
	private final JTextArea txa_init = new JTextArea();
	private final JScrollPane scrpnl_init = new JScrollPane(txa_init);
	private final JButton btn_sendLanguage = new JButton("Send Language");
	private final JButton btn_sendProgram = new JButton("Send Program");
	private final JButton btn_SendInitialization = new JButton("Send Initialization");
	private IViewToModelAdapterNew adapter;

	/**
	 * The method which starts the client view and make it visible.
	 */
	public void start(){
		this.setVisible(true);
	}
	
	/**
	 * create client view
	 * @param adapter adapter to client model.
	 */
	public ClientViewNew(IViewToModelAdapterNew adapter) {
		this.adapter = adapter;
		initGUI();
	}
	
	/**
	 * The method which prints the 
	 * @param text text to output to gui.
	 */
	public void outputText(String text){
		txa_console.append(text + "\n");
		int len = txa_console.getDocument().getLength();
		txa_console.setCaretPosition(len);
	}
	
	/**
	 * The GUI initialization
	 */
	private void initGUI() {
		txf_opMap.setColumns(10);
		txf_program.setColumns(10);
		txf_language.setColumns(10);
		txf_ipAddress.setColumns(10);
		list_servers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_languages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_opMaps.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setTitle("Client View");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 420);
		borderLayout = new JPanel();
		borderLayout.setBorder(new EmptyBorder(5, 5, 5, 5));
		borderLayout.setLayout(new BorderLayout(0, 0));
		setContentPane(borderLayout);
		
		borderLayout.add(pnl_top, BorderLayout.NORTH);
		
		pnl_top.add(pnl_servers);
		GridBagLayout gbl_pnl_servers = new GridBagLayout();
		gbl_pnl_servers.columnWidths = new int[] {0, 10};
		gbl_pnl_servers.rowHeights = new int[] {24, 92, 0};
		gbl_pnl_servers.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnl_servers.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		pnl_servers.setLayout(gbl_pnl_servers);
		
		GridBagConstraints gbc_lblConnectedServers = new GridBagConstraints();
		gbc_lblConnectedServers.insets = new Insets(0, 0, 5, 0);
		gbc_lblConnectedServers.gridx = 0;
		gbc_lblConnectedServers.gridy = 0;
		pnl_servers.add(lblConnectedServers, gbc_lblConnectedServers);
		
		GridBagConstraints gbc_list_servers = new GridBagConstraints();
		gbc_list_servers.fill = GridBagConstraints.BOTH;
		gbc_list_servers.gridx = 0;
		gbc_list_servers.gridy = 1;
		pnl_servers.add(list_servers, gbc_list_servers);
		
		pnl_top.add(pnl_languages);
		GridBagLayout gbl_pnl_languages = new GridBagLayout();
		gbl_pnl_languages.columnWidths = new int[] {0, 10};
		gbl_pnl_languages.rowHeights = new int[] {24, 92};
		gbl_pnl_languages.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnl_languages.rowWeights = new double[]{0.0, 1.0};
		pnl_languages.setLayout(gbl_pnl_languages);
		
		GridBagConstraints gbc_lblLanguages = new GridBagConstraints();
		gbc_lblLanguages.insets = new Insets(0, 0, 5, 0);
		gbc_lblLanguages.gridx = 0;
		gbc_lblLanguages.gridy = 0;
		pnl_languages.add(lblLanguages, gbc_lblLanguages);
		
		GridBagConstraints gbc_list_languages = new GridBagConstraints();
		gbc_list_languages.fill = GridBagConstraints.BOTH;
		gbc_list_languages.gridx = 0;
		gbc_list_languages.gridy = 1;
		pnl_languages.add(list_languages, gbc_list_languages);
		
		pnl_top.add(pnl_opMaps);
		GridBagLayout gbl_pnl_opMaps = new GridBagLayout();
		gbl_pnl_opMaps.columnWidths = new int[] {0, 10};
		gbl_pnl_opMaps.rowHeights = new int[] {24, 92, 0};
		gbl_pnl_opMaps.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnl_opMaps.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		pnl_opMaps.setLayout(gbl_pnl_opMaps);
		
		GridBagConstraints gbc_lblLoadedOpMaps = new GridBagConstraints();
		gbc_lblLoadedOpMaps.insets = new Insets(0, 0, 5, 0);
		gbc_lblLoadedOpMaps.gridx = 0;
		gbc_lblLoadedOpMaps.gridy = 0;
		pnl_opMaps.add(lblLoadedOpMaps, gbc_lblLoadedOpMaps);
		
		GridBagConstraints gbc_list_opMaps = new GridBagConstraints();
		gbc_list_opMaps.fill = GridBagConstraints.BOTH;
		gbc_list_opMaps.gridx = 0;
		gbc_list_opMaps.gridy = 1;
		pnl_opMaps.add(list_opMaps, gbc_list_opMaps);
		list_programs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		pnl_top.add(pnl_programs);
		GridBagLayout gbl_pnl_programs = new GridBagLayout();
		gbl_pnl_programs.columnWidths = new int[] {0, 10};
		gbl_pnl_programs.rowHeights = new int[] {24, 92, 0};
		gbl_pnl_programs.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnl_programs.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		pnl_programs.setLayout(gbl_pnl_programs);
		
		GridBagConstraints gbc_lblLoadedPrograms = new GridBagConstraints();
		gbc_lblLoadedPrograms.insets = new Insets(0, 0, 5, 0);
		gbc_lblLoadedPrograms.gridx = 0;
		gbc_lblLoadedPrograms.gridy = 0;
		pnl_programs.add(lblLoadedPrograms, gbc_lblLoadedPrograms);
		
		GridBagConstraints gbc_list_programs = new GridBagConstraints();
		gbc_list_programs.fill = GridBagConstraints.BOTH;
		gbc_list_programs.gridx = 0;
		gbc_list_programs.gridy = 1;
		pnl_programs.add(list_programs, gbc_list_programs);
		@SuppressWarnings("unused")
		FlowLayout flowLayout = (FlowLayout) pnl_bottom.getLayout();
		
		borderLayout.add(pnl_bottom, BorderLayout.SOUTH);
		btn_sendLanguage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (list_languages.getSelectedValue() == null) {
					outputText("choose a listed language");
					return;
				}
				if (list_servers.getSelectedValue() == null) {
					outputText("choose a listed server");
					return;
				}
				if (list_opMaps.getSelectedValue() == null) {
					outputText("choose a listed op maps");
				}
				adapter.parseLanguage(list_languages.getSelectedValue().getFile(), list_servers.getSelectedValue(), list_opMaps.getSelectedValue().toString());
			}
		});
		
		pnl_bottom.add(btn_sendLanguage);
		btn_sendProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list_programs.getSelectedValue() == null) {
					outputText("choose a listed program");
					return;
				}
				if (list_languages.getSelectedValue() == null) {
					outputText("choose a listed language");
					return;
				}
				if (list_servers.getSelectedValue() == null) {
					outputText("choose a listed server");
					return;
				}
				adapter.parseProgram(list_programs.getSelectedValue().getFile(), list_languages.getSelectedValue().toString(), list_servers.getSelectedValue());
			}
		});
		
		pnl_bottom.add(btn_sendProgram);
		btn_SendInitialization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list_programs.getSelectedValue() == null) {
					outputText("choose a listed program");
					return;
				}
				if (list_languages.getSelectedValue() == null) {
					outputText("choose a listed language");
					return;
				}
				if (list_servers.getSelectedValue() == null) {
					outputText("choose a listed server");
					return;
				}
				if (list_opMaps.getSelectedValue() == null) {
					outputText("choose a listed op maps");
				}
				adapter.executeProgram(list_languages.getSelectedValue().toString(), list_programs.getSelectedValue().toString(), txa_init.getText(), list_servers.getSelectedValue());
			}
		});
		
		pnl_bottom.add(btn_SendInitialization);
		
		borderLayout.add(pnl_center, BorderLayout.CENTER);
		pnl_center.setLayout(new BorderLayout(0, 0));
		lblConsole.setHorizontalAlignment(SwingConstants.CENTER);
		pnl_center.add(lblConsole, BorderLayout.NORTH);
		pnl_center.add(scrpnl_console);
		
		borderLayout.add(pnl_left, BorderLayout.WEST);
		GridBagLayout gbl_pnl_left = new GridBagLayout();
		gbl_pnl_left.columnWidths = new int[] {0, 30, 10};
		gbl_pnl_left.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pnl_left.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_pnl_left.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnl_left.setLayout(gbl_pnl_left);
		
		GridBagConstraints gbc_lblIpAddress = new GridBagConstraints();
		gbc_lblIpAddress.anchor = GridBagConstraints.WEST;
		gbc_lblIpAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblIpAddress.gridx = 0;
		gbc_lblIpAddress.gridy = 0;
		pnl_left.add(lblIpAddress, gbc_lblIpAddress);
		
		GridBagConstraints gbc_txf_ipAddress = new GridBagConstraints();
		gbc_txf_ipAddress.insets = new Insets(0, 0, 5, 5);
		gbc_txf_ipAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_txf_ipAddress.gridx = 0;
		gbc_txf_ipAddress.gridy = 1;
		pnl_left.add(txf_ipAddress, gbc_txf_ipAddress);
		
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.anchor = GridBagConstraints.WEST;
		gbc_btnConnect.insets = new Insets(0, 0, 5, 0);
		gbc_btnConnect.gridx = 1;
		gbc_btnConnect.gridy = 1;
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				serversModel.addElement(txf_ipAddress.getText());
				adapter.connectSocket(txf_ipAddress.getText(), INetworkConfig.SERVER_PORT);
			}
		});
		pnl_left.add(btnConnect, gbc_btnConnect);
		
		GridBagConstraints gbc_lblLanguage = new GridBagConstraints();
		gbc_lblLanguage.anchor = GridBagConstraints.WEST;
		gbc_lblLanguage.insets = new Insets(0, 0, 5, 5);
		gbc_lblLanguage.gridx = 0;
		gbc_lblLanguage.gridy = 2;
		pnl_left.add(lblLanguage, gbc_lblLanguage);
		
		GridBagConstraints gbc_txf_language = new GridBagConstraints();
		gbc_txf_language.insets = new Insets(0, 0, 5, 5);
		gbc_txf_language.fill = GridBagConstraints.HORIZONTAL;
		gbc_txf_language.gridx = 0;
		gbc_txf_language.gridy = 3;
		pnl_left.add(txf_language, gbc_txf_language);
		
		GridBagConstraints gbc_btn_loadLanguage = new GridBagConstraints();
		gbc_btn_loadLanguage.anchor = GridBagConstraints.EAST;
		gbc_btn_loadLanguage.insets = new Insets(0, 0, 5, 0);
		gbc_btn_loadLanguage.gridx = 1;
		gbc_btn_loadLanguage.gridy = 3;
		btn_loadLanguage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileWrapper loadedLangFile = adapter.loadLanguage(txf_language.getText());
				if (loadedLangFile != null) {
					languagesModel.addElement(loadedLangFile);
					byte[] encoded = null;
					try {
						encoded = Files.readAllBytes(loadedLangFile.getFile().toPath());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					  outputText("Language loaded: \n" + new String(encoded, Charset.defaultCharset()));
				}
			}
		});
		pnl_left.add(btn_loadLanguage, gbc_btn_loadLanguage);
		
		GridBagConstraints gbc_lblOpMap = new GridBagConstraints();
		gbc_lblOpMap.anchor = GridBagConstraints.WEST;
		gbc_lblOpMap.insets = new Insets(0, 0, 5, 5);
		gbc_lblOpMap.gridx = 0;
		gbc_lblOpMap.gridy = 4;
		pnl_left.add(lblOpMap, gbc_lblOpMap);
		
		GridBagConstraints gbc_txf_opMap = new GridBagConstraints();
		gbc_txf_opMap.insets = new Insets(0, 0, 5, 5);
		gbc_txf_opMap.fill = GridBagConstraints.HORIZONTAL;
		gbc_txf_opMap.gridx = 0;
		gbc_txf_opMap.gridy = 5;
		pnl_left.add(txf_opMap, gbc_txf_opMap);
		GridBagConstraints gbc_btn_loadOpMap = new GridBagConstraints();
		gbc_btn_loadOpMap.anchor = GridBagConstraints.EAST;
		gbc_btn_loadOpMap.insets = new Insets(0, 0, 5, 0);
		gbc_btn_loadOpMap.gridx = 1;
		gbc_btn_loadOpMap.gridy = 5;
		pnl_left.add(btn_loadOpMap, gbc_btn_loadOpMap);
		btn_loadOpMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileWrapper loaded = adapter.loadOpMap(txf_opMap.getText());
				if (loaded != null){
					opMapsModel.addElement(loaded);
					byte[] encoded = null;
					try {
						encoded = Files.readAllBytes(loaded.getFile().toPath());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					  outputText("Operation Map loaded: \n" + new String(encoded, Charset.defaultCharset()));
				}
			}
		});
		
		GridBagConstraints gbc_lblProgram = new GridBagConstraints();
		gbc_lblProgram.anchor = GridBagConstraints.WEST;
		gbc_lblProgram.insets = new Insets(0, 0, 5, 5);
		gbc_lblProgram.gridx = 0;
		gbc_lblProgram.gridy = 6;
		pnl_left.add(lblProgram, gbc_lblProgram);
		
		GridBagConstraints gbc_btn_loadProgram = new GridBagConstraints();
		gbc_btn_loadProgram.anchor = GridBagConstraints.EAST;
		gbc_btn_loadProgram.gridx = 1;
		gbc_btn_loadProgram.gridy = 7;
		btn_loadProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileWrapper loaded = adapter.loadProgram(txf_program.getText());
				if (loaded != null) {
					programsModel.addElement(loaded);
					byte[] encoded = null;
					try {
						encoded = Files.readAllBytes(loaded.getFile().toPath());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					  outputText("Program loaded: \n" + new String(encoded, Charset.defaultCharset()));
				}
			}
		});
		

		
		GridBagConstraints gbc_txf_program = new GridBagConstraints();
		gbc_txf_program.insets = new Insets(0, 0, 0, 5);
		gbc_txf_program.fill = GridBagConstraints.HORIZONTAL;
		gbc_txf_program.gridx = 0;
		gbc_txf_program.gridy = 7;
		pnl_left.add(txf_program, gbc_txf_program);
		
		
		pnl_left.add(txf_program, gbc_txf_program);
		pnl_left.add(btn_loadProgram, gbc_btn_loadProgram);
		pnl_left.add(btn_loadProgram, gbc_btn_loadProgram);
		
		borderLayout.add(pnl_right, BorderLayout.EAST);
		pnl_right.setLayout(new BorderLayout(0, 0));
		lblInitialization.setHorizontalAlignment(SwingConstants.CENTER);
		pnl_right.add(lblInitialization, BorderLayout.NORTH);
		pnl_right.add(scrpnl_init);
	}

}
