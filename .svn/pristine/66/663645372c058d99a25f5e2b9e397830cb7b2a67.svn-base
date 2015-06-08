package view;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;


public class RDPFrame extends JFrame {
	private static final long serialVersionUID = 2007111140354L;
	/**
	 * Content pane.
	 */
	private JPanel contentPane;

	/**
	 * Top panel.
	 */
	private JPanel topPanel = new JPanel();

	/**
	 * Bottom scroll pane.
	 */
	private JScrollPane scrollPane = new JScrollPane();

	/**
	 * File name input field.
	 */
	private JTextField inpFileTF = new JTextField();

	/**
	 * File name input field.
	 */
	private JTextField inpFile2TF = new JTextField();

	/**
	 * Button to parse original grammar file.
	 */
	private JButton parseOrigBtn = new JButton();

	/**
	 * Button to parse XML file.
	 */
	private JButton parseXMLBtn = new JButton();

	/**
	 * Button to semantically check the parsed file.
	 */
	private JButton checkBtn = new JButton();

	/**
	 * Button to parse BNF file.
	 */
	private JButton parseBNFBtn = new JButton();

	/**
	 * Button to semantically check the parsed BNF file.
	 */
	private JButton checkBNFBtn = new JButton();
	
	private JTextArea outputTA = new JTextArea();

	/**
	 * The adapter to the model
	 */
	private IModelAdapter model;



	/**
	 * The constructor for the frame
	 * @param closeOp  The default window closing operation to use.   Use either WindowConstants.EXIT_ON_CLOSE or HIDE_ON_CLOSE
	 * @param model   The adapter to the model
	 */
	public RDPFrame(int closeOp, IModelAdapter model) {
		this.model = model;
		try {
			this.setDefaultCloseOperation(closeOp);
			initGUI();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		validate();
		//Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		setVisible(true);
	}

	/**
	 * Initialize GUI components.
	 */
	private void initGUI() {
		//setIconImage(Toolkit.getDefaultToolkit().createImage(RDPFrame.class.getResource("[Your Icon]")));
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		this.setSize(new Dimension(889, 401));
		this.setTitle("YAK (Yet Another Kooprey)");
		inpFileTF.setToolTipText("File to be parsed by hard-coded grammars");
		inpFileTF.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "File Input 1", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		inpFileTF.setMinimumSize(new Dimension(100, 21));
		inpFileTF.setPreferredSize(new Dimension(100, 35));
		inpFileTF.setText("bnfbnf4.txt");
		inpFile2TF.setToolTipText("The file to parse using a dynamically generated parser.");
		inpFile2TF.setBorder(new TitledBorder(null, "File Input 2", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		inpFile2TF.setMinimumSize(new Dimension(100, 21));
		inpFile2TF.setPreferredSize(new Dimension(100, 35));
		inpFile2TF.setText("bnf1.txt");
		parseOrigBtn.setToolTipText("Use the \"original\" grammar to parse Input File 1 ");

		parseOrigBtn.setText("Parse Orig");
		parseOrigBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputTA.setText(model.parseOrig(inpFileTF.getText()));
			}
		});
		parseXMLBtn.setToolTipText("Use XML grammar to parse Input File 1");

		parseXMLBtn.setText("Parse XML");
		parseXMLBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputTA.setText(model.parseXML(inpFileTF.getText()));
			}
		});
		checkBtn.setToolTipText("Check that the XML parsed file has matching tags.");

		checkBtn.setText("Check XML result");
		checkBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputTA.append(model.check());
			}
		});
		parseBNFBtn.setToolTipText("Use the BNF grammar to parse Input File 1");

		parseBNFBtn.setText("Parse BNF");
		parseBNFBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputTA.setText(model.parseBNF(inpFileTF.getText()));
			}
		});
		checkBNFBtn.setToolTipText("Use the parsed BNF from Input File 1 to parse Input File 2 using the selected tokenizer. ");

		checkBNFBtn.setText("Check BNF");
		checkBNFBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputTA.append(model.checkBNF( inpFile2TF.getText()));
			}
		});
		topPanel.setLayout(new WrapLayout());
		topPanel.add(inpFileTF, null);
		topPanel.add(parseOrigBtn, null);
		topPanel.add(parseXMLBtn, null);
		topPanel.add(checkBtn, null);
		topPanel.add(parseBNFBtn, null);
		topPanel.add(checkBNFBtn, null);
		topPanel.add(inpFile2TF, null);

		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		scrollPane.setViewportView(outputTA);
		contentPane.add(topPanel, BorderLayout.NORTH);
	}

}