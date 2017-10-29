package webby;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends readAllLinks implements ActionListener, ItemListener {

	static JFrame _window;

	public PrintWriter outStream;
	readAllLinks obj = new readAllLinks();
	String ROOTSITE = "https://www.cvs.com/store-locator/cvs-pharmacy-locations";	//Static Rootsite var
	ArrayList<String> stateList = obj.getStates(ROOTSITE);							//State List
	String STATE = "Alabama";  						//initializing global var for state
	final JComboBox listOfStates = new JComboBox(new Vector(stateList));

	public GUI() throws IOException {
		//Frame
		JFrame frame = new JFrame("WebScraper");
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int height = 250, width = 500;
		frame.setSize(width, height);
		frame.setLocation(430, 100);

		//panel
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		frame.add(panel);

		//Labeling
		JLabel label = new JLabel("Store to target: CVS - Select state and click GO");
		label.setAlignmentX((Component.CENTER_ALIGNMENT));
		label.setVisible(true);
		panel.add(label);


		//Combo box, starts at index 0
		listOfStates.setVisible(true);
		///listOfStates.setSelectedIndex(0);
		listOfStates.addItemListener(this);
		listOfStates.setAlignmentX((Component.CENTER_ALIGNMENT));
		panel.add(listOfStates);

		//Go button
		JButton button = new JButton("GO");
		button.addActionListener(this);
		button.setAlignmentX((Component.CENTER_ALIGNMENT));
		panel.add(button);
			
		///seprate GO and reset button
		JLabel lableSeprate = new JLabel("----------------");
		lableSeprate.setAlignmentX((Component.CENTER_ALIGNMENT));
		lableSeprate.setVisible(true);
		panel.add(lableSeprate);
                //reset button
                JButton btnReset = new JButton("reset");
                btnReset.addActionListener(null);
                btnReset.setAlignmentX((Component.CENTER_ALIGNMENT));
                panel.add(btnReset);


		//frame.pack();
		frame.setVisible(true);
		_window.add(frame);
		_window.add(panel);
		_window.pack();
	}

		public void itemStateChanged(ItemEvent e) {
			//gets state selected from dropdown menu
			JComboBox box = (JComboBox)e.getSource();
			STATE = (String)box.getSelectedItem();
		}

		public void actionPerformed(ActionEvent e) {
			JFrame waitWin = new JFrame("Gathering addresses, store numbers and phone numbers from "+ STATE+", please wait.");
			waitWin.setLayout(new GridBagLayout());
			JPanel panel2 = new JPanel();
			//JLabel jlabel2 = new JLabel("Gathering addresses, store numbers and phone numbers from "+ STATE+". Please wait, or close this window to quit.");
			//jlabel2.setFont(new Font("Verdana",1,12));
			//panel2.add(jlabel2);
			waitWin.add(panel2, new GridBagConstraints());
			waitWin.setSize(760, 150);
			waitWin.setLocationRelativeTo(null);
			waitWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			waitWin.setVisible(true);

				String filename = "gatheredData.txt";
				try {
					outStream = new PrintWriter(new FileWriter(filename, true));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				readAllLinks obj = new readAllLinks();
				rootsite = "https://www.cvs.com/store-locator/cvs-pharmacy-locations/";
				obj.get_links(ROOTSITE+'/'+STATE);


				Iterator<String> it1 = addresses.iterator();
				Iterator<String> it2 = phoneAndStoreNumbers.iterator();

				while(it1.hasNext() && it2.hasNext()){
					outStream.println(it1.next());
					outStream.println(it2.next());
				}

				outStream.close();
				JFrame frame1 = new JFrame();
				frame1.setLayout(new GridBagLayout());
				JPanel panel1 = new JPanel();
				JLabel jlabel1 = new JLabel("CVS addresses, store numbers and phone numbers gathered from "+ STATE +". You may now close this window.");
				jlabel1.setFont(new Font("Verdana",1,12));
				panel1.add(jlabel1);
				frame1.add(panel1, new GridBagConstraints());
				frame1.setSize(750, 150);
				frame1.setLocationRelativeTo(null);
				frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame1.setVisible(true);
				waitWin.setVisible(false);
			}
}
