package ui.controls;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * User interface control that allows the user to enter and edit a simulation time either as a number (of seconds) or as a real time expressed as HH:mm
 */
public class SimTimeEdit extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 1L;

	private JSpinner spnRealTime = null;

	private JSpinner spnSimTime = null;

	/**
	 * The value that is assigned to the control
	 */
	private int value = 0;

	private JLabel lblRealTime = null;

	private JLabel lblSimTime = null;

	private List<ChangeListener> changeListeners = new ArrayList<ChangeListener>();

	public void addChangeListener(ChangeListener c) {
		if (!changeListeners.contains(c)) {
			changeListeners.add(c);
		}
	}

	public void removeChangeListener(ChangeListener c) {
		if (changeListeners.contains(c)) {
			changeListeners.remove(c);
		}
	}

	private void NotifyValueChanged() {
		for (ChangeListener c : changeListeners) {
			c.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * Returns the time value
	 * @return
	 * @uml.property  name="value"
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the specified value to the control.
	 * @param  value
	 * @uml.property  name="value"
	 */
	@SuppressWarnings("deprecation")
	public void setValue(int value) {
		this.value = value;

		//Create the date to be shown on the real time control
		Date d = new Date();
		d.setHours(value / 3600);
		d.setMinutes((value % 3600) / 60);
		d.setSeconds((value % 3600) % 60);

		//Set the values
		spnRealTime.setValue(d);
		spnSimTime.setValue(value);

		spnSimTime.addChangeListener(this);
		spnRealTime.addChangeListener(this);
	}

	/**
	 * This is the default constructor
	 */
	public SimTimeEdit() {
		super();
		initialize();

		setValue(0);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints2.insets = new Insets(0, 0, 0, 5);
		gridBagConstraints2.gridy = 1;
		lblSimTime = new JLabel();
		lblSimTime.setText("Simulation time:");
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints11.gridy = 0;
		lblRealTime = new JLabel();
		lblRealTime.setText("Real time:");
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.weightx = 1.0D;
		gridBagConstraints1.weighty = 1.0D;
		gridBagConstraints1.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints1.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints1.gridy = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.weighty = 1.0D;
		gridBagConstraints.weightx = 1.0D;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.gridy = 0;
		this.setSize(163, 49);
		this.setLayout(new GridBagLayout());
		this.add(getSpnRealTime(), gridBagConstraints);
		this.add(getSpnSimTime(), gridBagConstraints1);
		this.add(lblRealTime, gridBagConstraints11);
		this.add(lblSimTime, gridBagConstraints2);
	}

	/**
	 * This method initializes spnRealTime
	 * @return  javax.swing.JSpinner
	 * @uml.property  name="spnRealTime"
	 */
	private JSpinner getSpnRealTime() {
		if (spnRealTime == null) {
			spnRealTime = new JSpinner();
			spnRealTime.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
			JSpinner.DateEditor de = new JSpinner.DateEditor(spnRealTime, "HH:mm");
			spnRealTime.setEditor(de);
			spnRealTime.setPreferredSize(new Dimension(60, 20));
		}
		return spnRealTime;
	}

	/**
	 * This method initializes spnSimTime
	 * @return  javax.swing.JSpinner
	 * @uml.property  name="spnSimTime"
	 */
	private JSpinner getSpnSimTime() {
		if (spnSimTime == null) {
			spnSimTime = new JSpinner();
			spnSimTime.setModel(new SpinnerNumberModel(0, 0, 86400, 60));
			spnSimTime.setPreferredSize(new Dimension(60, 20));
		}
		return spnSimTime;
	}

	@SuppressWarnings("deprecation")
	public void stateChanged(ChangeEvent e) {
		//Remove listeners to avoid a loop
		spnSimTime.removeChangeListener(this);
		spnRealTime.removeChangeListener(this);

		//Depending on the source of the event, update the other control
		if (e.getSource().equals(spnRealTime)) {
			Date d = (Date) spnRealTime.getValue();
			value = d.getHours() * 3600 + d.getMinutes() * 60 + d.getSeconds();
			spnSimTime.setValue(value);
		} else if (e.getSource().equals(spnSimTime)) {
			value = (Integer) spnSimTime.getValue();

			Date d = new Date();
			d.setHours(value / 3600);
			d.setMinutes((value % 3600) / 60);
			d.setSeconds((value % 3600) % 60);

			spnRealTime.setValue(d);
		}

		//Re-register the listeners
		spnSimTime.addChangeListener(this);
		spnRealTime.addChangeListener(this);

		//Notify any registered listeners that the value has changed
		NotifyValueChanged();
	}

	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);

		//Enable / disable child controls
		spnRealTime.setEnabled(b);
		spnSimTime.setEnabled(b);

		lblSimTime.setEnabled(b);
		lblRealTime.setEnabled(b);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
