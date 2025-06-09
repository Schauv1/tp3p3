package interfaz;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class MenuInicial {

	private JFrame frame;
	private JTextField textCeldasX;
	private JTextField textCeldasY;
	static MenuInicial window;
	private int sizeX;
	private int sizeY;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MenuInicial();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MenuInicial() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Menu");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textCeldasX = new JTextField();
		textCeldasX.setBounds(77, 121, 96, 19);
		frame.getContentPane().add(textCeldasX);
		textCeldasX.setColumns(10);
		
		textCeldasY = new JTextField();
		textCeldasY.setBounds(254, 121, 96, 19);
		frame.getContentPane().add(textCeldasY);
		textCeldasY.setColumns(10);
		
		JLabel lblCeldasY = new JLabel("Celdas Y: ");
		lblCeldasY.setHorizontalAlignment(SwingConstants.CENTER);
		lblCeldasY.setBounds(254, 96, 96, 13);
		frame.getContentPane().add(lblCeldasY);
		
		JLabel lblCeldasX = new JLabel("Celdas X:");
		lblCeldasX.setHorizontalAlignment(SwingConstants.CENTER);
		lblCeldasX.setBounds(77, 94, 96, 17);
		frame.getContentPane().add(lblCeldasX);
		
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.setBounds(168, 180, 85, 21);
		frame.getContentPane().add(btnIniciar);
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sizeX = Integer.parseInt(textCeldasX.getText());
					sizeY = Integer.parseInt(textCeldasY.getText());
				}
				catch(NumberFormatException f) {
					return;
				}
				window.frame.dispose();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Ventana ventana = new Ventana(sizeX, sizeY);
							ventana.frame.setVisible(true);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				});
			}
		});
	}
}
