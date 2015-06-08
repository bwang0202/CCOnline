package server.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * The server view of the program
 * @author Fabio-CSM
 *
 */
public class ServerView extends JFrame {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 811369936426289669L;
	private JPanel borderLayout;
	private final JPanel pnl_top = new JPanel();
	private final JPanel pnl_statusCode = new JPanel();
	private final JPanel pnl_console = new JPanel();
	private final JTextArea txa_console = new JTextArea();
	private final JScrollPane scrpnl_console = new JScrollPane(txa_console);
	private final JPanel pnl_connectedUsers = new JPanel();
	private final JLabel lblConnectedUsers = new JLabel("Connected Users:");
	private DefaultListModel<String> connectedModel = new DefaultListModel<String>();
	private final JList<String> list_connectedUsers = new JList<String>(connectedModel);
	private final JPanel pnl_parsedLanguages = new JPanel();
	private final JLabel lblParsedLanguages = new JLabel("Parsed Languages:");
	private DefaultListModel<String> languagesModel = new DefaultListModel<String>();
	private final JList<String> list_parsedLanguages = new JList<String>(languagesModel);
	private final JPanel pnl_receivedPrograms = new JPanel();
	private final JLabel lblReceivedPrograms = new JLabel("Received Programs:");
	private DefaultListModel<String> programsModel = new DefaultListModel<String>();
	private final JList<String> list_programs = new JList<String>(programsModel);
	private final JLabel lblConsole = new JLabel("Console");
	private final JLabel lblStatusCode = new JLabel("Status Code:");
	private final JComboBox<Integer> cmb_statusCode = new JComboBox<Integer>();
	private final JButton btnSendStatusInfo = new JButton("Send Status Info");
	private IServerViewToModelAdapter adapter;
	private String last_addr = null;
	/**
	 * Create view for server
	 * @param adapter adapter to server model
	 */
	public ServerView(IServerViewToModelAdapter adapter) {
		this.adapter = adapter;
		initGUI();
	}
	/**
	 * The method which initializes the view components
	 */
	private void initGUI() {
		setTitle("Server View");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 420);
		borderLayout = new JPanel();
		borderLayout.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(borderLayout);
		borderLayout.setLayout(new BorderLayout(0, 0));
		
		borderLayout.add(pnl_top, BorderLayout.NORTH);
		pnl_top.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		pnl_top.add(pnl_connectedUsers);
		GridBagLayout gbl_pnl_connectedUsers = new GridBagLayout();
		gbl_pnl_connectedUsers.columnWidths = new int[]{124, 0};
		gbl_pnl_connectedUsers.rowHeights = new int[] {24, 92, 0};
		gbl_pnl_connectedUsers.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnl_connectedUsers.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		pnl_connectedUsers.setLayout(gbl_pnl_connectedUsers);
		
		GridBagConstraints gbc_lblConnectedUsers = new GridBagConstraints();
		gbc_lblConnectedUsers.insets = new Insets(0, 0, 5, 0);
		gbc_lblConnectedUsers.anchor = GridBagConstraints.NORTH;
		gbc_lblConnectedUsers.gridx = 0;
		gbc_lblConnectedUsers.gridy = 0;
		pnl_connectedUsers.add(lblConnectedUsers, gbc_lblConnectedUsers);
		
		GridBagConstraints gbc_list_connectedUsers = new GridBagConstraints();
		gbc_list_connectedUsers.fill = GridBagConstraints.BOTH;
		gbc_list_connectedUsers.gridx = 0;
		gbc_list_connectedUsers.gridy = 1;
		pnl_connectedUsers.add(list_connectedUsers, gbc_list_connectedUsers);
		
		pnl_top.add(pnl_parsedLanguages);
		GridBagLayout gbl_pnl_parsedLanguages = new GridBagLayout();
		gbl_pnl_parsedLanguages.columnWidths = new int[] {124, 0};
		gbl_pnl_parsedLanguages.rowHeights = new int[] {24, 92, 0};
		gbl_pnl_parsedLanguages.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnl_parsedLanguages.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		pnl_parsedLanguages.setLayout(gbl_pnl_parsedLanguages);
		
		GridBagConstraints gbc_lblParsedLanguages = new GridBagConstraints();
		gbc_lblParsedLanguages.anchor = GridBagConstraints.NORTH;
		gbc_lblParsedLanguages.insets = new Insets(0, 0, 5, 0);
		gbc_lblParsedLanguages.gridx = 0;
		gbc_lblParsedLanguages.gridy = 0;
		pnl_parsedLanguages.add(lblParsedLanguages, gbc_lblParsedLanguages);
		
		GridBagConstraints gbc_list_parsedLanguages = new GridBagConstraints();
		gbc_list_parsedLanguages.fill = GridBagConstraints.BOTH;
		gbc_list_parsedLanguages.gridx = 0;
		gbc_list_parsedLanguages.gridy = 1;
		pnl_parsedLanguages.add(list_parsedLanguages, gbc_list_parsedLanguages);
		
		pnl_top.add(pnl_receivedPrograms);
		GridBagLayout gbl_pnl_receivedPrograms = new GridBagLayout();
		gbl_pnl_receivedPrograms.columnWidths = new int[] {124, 0};
		gbl_pnl_receivedPrograms.rowHeights = new int[] {24, 92, 0};
		gbl_pnl_receivedPrograms.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnl_receivedPrograms.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		pnl_receivedPrograms.setLayout(gbl_pnl_receivedPrograms);
		
		GridBagConstraints gbc_lblReceivedPrograms = new GridBagConstraints();
		gbc_lblReceivedPrograms.anchor = GridBagConstraints.NORTH;
		gbc_lblReceivedPrograms.insets = new Insets(0, 0, 5, 0);
		gbc_lblReceivedPrograms.gridx = 0;
		gbc_lblReceivedPrograms.gridy = 0;
		pnl_receivedPrograms.add(lblReceivedPrograms, gbc_lblReceivedPrograms);
		
		GridBagConstraints gbc_list_programs = new GridBagConstraints();
		gbc_list_programs.fill = GridBagConstraints.BOTH;
		gbc_list_programs.gridx = 0;
		gbc_list_programs.gridy = 1;
		pnl_receivedPrograms.add(list_programs, gbc_list_programs);
		
		borderLayout.add(pnl_console, BorderLayout.CENTER);
		pnl_console.setLayout(new BorderLayout(0, 0));
		pnl_console.add(scrpnl_console);
		lblConsole.setHorizontalAlignment(SwingConstants.CENTER);
		pnl_console.add(lblConsole, BorderLayout.NORTH);
		
		borderLayout.add(pnl_statusCode, BorderLayout.SOUTH);
		
		pnl_statusCode.add(lblStatusCode);
		
		pnl_statusCode.add(cmb_statusCode);
		
		pnl_statusCode.add(btnSendStatusInfo);
		cmb_statusCode.addItem(0); //ok
		cmb_statusCode.addItem(100); // lang ok
		cmb_statusCode.addItem(200); // program ok
		cmb_statusCode.addItem(300); //init ok
		cmb_statusCode.addItem(400); //execution ok
		btnSendStatusInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adapter.sendStatusMsg(cmb_statusCode.getItemAt(cmb_statusCode.getSelectedIndex()));
			}
		});
	}
	
	
	/**
	 * The method which starts the view and print the current ip address to the console.
	 */
	public void start(){
		setVisible(true);
		String IP = null;
		try {
			IP = getLocalAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
		outputText("The server IP address is: "+ IP + "\n");
	}
	
	/**
	 * Method which add text to the console
	 * @param text The text to be added
	 */
	public void outputText(String text){
		txa_console.append(text + "\n");
		int len = txa_console.getDocument().getLength();
		txa_console.setCaretPosition(len);
	}
	/**
	 * The method which added in the list the connected clients
	 * @param ipAddress The client IP address
	 */
	public void addConnectedClient(String ipAddress) {
		connectedModel.addElement(ipAddress);
	}
	/**
	 * The method which add to the list the lang name received
	 * @param langName The name of the language
	 */
	public void addLanguage(String langName) {
		languagesModel.addElement(langName);
	}
	/**
	 * The method which added to the list the program received.
	 * @param progName The name of the program
	 */
	public void addProgram(String progName) {
		programsModel.addElement(progName);
	}
	
	/**
	 * This method is designed to reliably return the actual local IP address
	 * across multiple platforms, particularly Linux. This method is a
	 * replacement for "java.net.InetAddress.getLocalHost().getHostAddress()"
	 * which will return the loopback address in Linux, not the actual IP
	 * address. Also, Java tends to report many virtual network adapters as 
	 * "non-virtual".  This method only returns IPv4 addresses, not IPv6 addresses,
	 * which are non-loopback, operational and non-virtual, as reported by Java.   
	 * By default, privateAddrOnly is set to true, so only private IP addresses are 
	 * detected.   If privateAddr = false (having used the auxiliary constructor)
	 * then all valid addresses are detected. If multiple potentially valid addresses are 
	 * detected, then a dialog box will pop up to ask the user to select the proper address.
	 * The selected address is cached so the next call to getLocalAddress() will simply return
	 * the cached address value.
	 * 
	 * @return A non-loopback, non-virtual IPv4 address found for the system.
	 * @throws SocketException
	 *             thrown when there is a problem retrieving the network
	 *             interfaces.
	 * @throws UnknownHostException
	 *             thrown when the local host address cannot be found.
	 * @author Dr. Wong in RMI Utils class
	 */
	public String getLocalAddress() throws SocketException,
	UnknownHostException {
		if (last_addr == null) {  // Only look for the address once

			// The code below is needed for Linux to find the host's real
			// (non-loopback) IP address.
			Enumeration<NetworkInterface> nics = NetworkInterface
					.getNetworkInterfaces();
			ArrayList<String> addr_choices = new ArrayList<String>();  // list of possible addresses
			while (nics.hasMoreElements()) {
				NetworkInterface nic = nics.nextElement();
				if (nic.isUp() && !nic.isVirtual()) {
					Enumeration<InetAddress> addrs = nic.getInetAddresses();
					while (addrs.hasMoreElements()) {
						InetAddress addr = addrs.nextElement();
						if (!addr.isLoopbackAddress() && (addr instanceof Inet4Address)) {
							outputText("Found address = " + addr.getHostAddress() + "\n");
								// Always add the address
								addr_choices.add(addr.getHostAddress());								
						}
					}
				}
			}

			switch(addr_choices.size()) {
			case 0:  // couldn't find an address in the above search process, so go with Java's default address result if there is one.
				outputText("The potentially filtered address search returned no results.  Defaulting to Java's default address, if it exists.\n");
				last_addr =  java.net.InetAddress.getLocalHost().getHostAddress();
				break;

			case 1:  // only one address found
				last_addr = addr_choices.get(0);
				break;

			default:  // Multiple potential addresses found
				String[] addr_array = addr_choices.toArray(new String[addr_choices.size()]);

				int addrIdx = JOptionPane.showOptionDialog(null, "Select the IP address of the physical network adapter:","Multiple IP Addresses Found!",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,  addr_array, addr_array[0]); 
				if (addrIdx == JOptionPane.CLOSED_OPTION) {
					addrIdx = 0;   // Just take the first address in this case.
				}
				last_addr = addr_array[addrIdx];
				break;

			}
		}
		return last_addr;
	}
}
