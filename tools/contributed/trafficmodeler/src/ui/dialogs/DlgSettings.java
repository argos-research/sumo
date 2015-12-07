package ui.dialogs;

import helpers.ApplicationSettings;
import java.awt.Frame;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Dialog for modifying the application settings
 */
public class DlgSettings extends JDialog {

	private static final long serialVersionUID = 1L;

	private JButton btnBrowseDUARouter = null;

	private JButton btnBrowseNetConvert = null;

	private JButton btnCancel = null;

	private JButton btnOK = null;

	private JPanel jContentPane = null;

	private JLabel lblDUARouterPath = null;

	private JLabel lblNetConvertPath = null;

	private JTextField txtDUARouterPath = null;

	private JTextField txtNetConvertPath = null;

	/**
	 * @param owner
	 */
	public DlgSettings(Frame owner) {
		super(owner);
		initialize();

		// Load the paths
		txtDUARouterPath.setText(ApplicationSettings.getDUARouterPath());
		txtNetConvertPath.setText(ApplicationSettings.getNetConvertPath());
	}

	/**
	 * This method initializes btnBrowseDUARouter
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnBrowseDUARouter"
	 */
	private JButton getBtnBrowseDUARouter() {
		if (btnBrowseDUARouter == null) {
			btnBrowseDUARouter = new JButton();
			btnBrowseDUARouter.setBounds(new Rectangle(444, 29, 87, 22));
			btnBrowseDUARouter.setText("Browse...");
			btnBrowseDUARouter.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String selectedPath = BrowseForFile();

					if (selectedPath != null) {
						txtDUARouterPath.setText(selectedPath);
					}
				}
			});
		}
		return btnBrowseDUARouter;
	}

	/**
	 * This method initializes btnBrowseNetConvert
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnBrowseNetConvert"
	 */
	private JButton getBtnBrowseNetConvert() {
		if (btnBrowseNetConvert == null) {
			btnBrowseNetConvert = new JButton();
			btnBrowseNetConvert.setBounds(new Rectangle(444, 77, 87, 22));
			btnBrowseNetConvert.setText("Browse...");
			btnBrowseNetConvert.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String selectedPath = BrowseForFile();

					if (selectedPath != null) {
						txtNetConvertPath.setText(selectedPath);
					}
				}
			});
		}
		return btnBrowseNetConvert;
	}

	/**
	 * This method initializes btnCancel
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnCancel"
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(458, 112, 73, 26));
			btnCancel.setText("Cancel");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes btnOK
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnOK"
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(new Rectangle(379, 112, 73, 26));
			btnOK.setText("OK");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SaveSettings();

					setVisible(false);
				}
			});
		}
		return btnOK;
	}

	/**
	 * This method initializes jContentPane
	 * @return  javax.swing.JPanel
	 * @uml.property  name="jContentPane"
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lblNetConvertPath = new JLabel();
			lblNetConvertPath.setBounds(new Rectangle(7, 58, 189, 16));
			lblNetConvertPath.setText("Net converter executable path:");
			lblDUARouterPath = new JLabel();
			lblDUARouterPath.setBounds(new Rectangle(7, 10, 189, 16));
			lblDUARouterPath.setText("DUA router executable path:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lblDUARouterPath, null);
			jContentPane.add(getTxtDUARouterPath(), null);
			jContentPane.add(getBtnBrowseDUARouter(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(lblNetConvertPath, null);
			jContentPane.add(getTxtNetConvertPath(), null);
			jContentPane.add(getBtnBrowseNetConvert(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes txtDUARouterPath
	 * @return  javax.swing.JTextField
	 * @uml.property  name="txtDUARouterPath"
	 */
	private JTextField getTxtDUARouterPath() {
		if (txtDUARouterPath == null) {
			txtDUARouterPath = new JTextField();
			txtDUARouterPath.setBounds(new Rectangle(7, 31, 430, 20));
		}
		return txtDUARouterPath;
	}

	/**
	 * This method initializes txtNetConvertPath
	 * @return  javax.swing.JTextField
	 * @uml.property  name="txtNetConvertPath"
	 */
	private JTextField getTxtNetConvertPath() {
		if (txtNetConvertPath == null) {
			txtNetConvertPath = new JTextField();
			txtNetConvertPath.setBounds(new Rectangle(6, 79, 430, 20));
		}
		return txtNetConvertPath;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(545, 178);
		this.setModal(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Settings");
		this.setContentPane(getJContentPane());
	}

	protected String BrowseForFile() {
		JFileChooser f = new JFileChooser();

		f.setMultiSelectionEnabled(false);
		f.setFileSelectionMode(JFileChooser.FILES_ONLY);
		f.setDialogTitle("Select executable");
		f.setAcceptAllFileFilterUsed(true);
		f.setApproveButtonText("Select");

		if (f.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			return f.getSelectedFile().getAbsolutePath();
		} else {
			return null;
		}
	}

	/**
	 * Validates and saves the settings entered by the user
	 * 
	 */
	protected void SaveSettings() {
		File dua = new File(txtDUARouterPath.getText());
		File netconvert = new File(txtNetConvertPath.getText());

		if (!dua.exists()) {
			JOptionPane.showMessageDialog(this, "Specified file does not exist");
			return;
		}

		if (!netconvert.exists()) {
			JOptionPane.showMessageDialog(this, "Specified file does not exist");
			return;
		}
		
		ApplicationSettings.setDUARouterPath(txtDUARouterPath.getText());
		ApplicationSettings.setNetConvertPath(txtNetConvertPath.getText());
	}

} // @jve:decl-index=0:visual-constraint="10,10"
