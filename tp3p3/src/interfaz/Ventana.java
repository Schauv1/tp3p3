package interfaz;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import system.BruteForce;
import system.InstructionSet;
import system.MatrizValores;
import system.Step;
import system.Value;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.Font;

public class Ventana {

	JFrame frame;
	private int sizeX;
	private int sizeY;
	private JLabel[][] _displayNumeros;
	private int[][] _matrizEnteros;
	String[] nombreColumnas = {"Variable" , "Valores"};
	DefaultTableModel datos = new DefaultTableModel(nombreColumnas,0);
	private double tdeSinPodas = 0;
	private double tdeConPodas = 0;
	private int caminosExplorados = 0;
	
	
	MatrizValores matriz;
	BruteForce fuerzaBruta;
	/**
	 * @wbp.nonvisual location=63,94
	 */
	private JPanel panelGrilla;
	private JTable table;
	private JTable tabla;
	private JButton btnNewButton;
	
	public Ventana(int celdasX, int celdasY) {
		sizeX = celdasX;
		sizeY = celdasY;
		initialize();

	}


	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Backtracking");
		frame.setBounds(400, 200, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		fuerzaBruta = new BruteForce();
		matriz = new MatrizValores(sizeX, sizeY);
		
//		sizeX = 4;
//		sizeY = 4;s
		convertirMatriz(matriz.devolverMatriz());
		
		panelGrilla = new JPanel();
		panelGrilla.setBounds(10, 11, 342, 442);
		frame.getContentPane().add(panelGrilla);
		panelGrilla.setLayout(new GridLayout(_matrizEnteros.length, _matrizEnteros[0].length));
		

			
		
		generarBotones();
		
		
		
		
		JButton btnVerificarCamino = new JButton("Verificar");
		btnVerificarCamino.setBounds(461, 354, 137, 31);
		frame.getContentPane().add(btnVerificarCamino);
		
		tabla = new JTable(datos);
		tabla.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tabla.setBounds(381, 71, 271, 64);
		frame.getContentPane().add(tabla);
		
		
		datos.addRow(new Object[] {"Tamaño Grilla", "" + sizeY + " x " + sizeX});
		datos.addRow(new Object[] {"TdE sin podas", "" + tdeSinPodas});
		datos.addRow(new Object[] {"TdE con podas", "" + tdeConPodas});
		datos.addRow(new Object[] {"Caminos Posibles", "" + caminosExplorados});
		btnVerificarCamino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fuerzaBruta.isVerifiable(matriz)) {
					fuerzaBruta.buildAllPaths_Backtrack(matriz);
					caminosExplorados = fuerzaBruta.caminosExplorados();
					datos.setValueAt(caminosExplorados, 3, 1);;
					if (fuerzaBruta.getSoluciones().size() > 0) {
					LinkedList<Step> solucionAleatoria = fuerzaBruta.returnRandomSolution();
					int valorX = 0;
					int valorY = 0;
	
					_displayNumeros[0][0].setBackground(Color.YELLOW);
					for(int i=0; i<solucionAleatoria.size(); i++) {
						if(solucionAleatoria.get(i).equals(Step.Down)) {
							valorY+=1;
						}
						else if(solucionAleatoria.get(i).equals(Step.Right)){
							valorX+=1;
						}
						_displayNumeros[valorY][valorX].setBackground(Color.YELLOW);
					}
					}
					else {
						System.out.println("No tiene camino Neutro");
						return;
					}
				}
				else {
					System.out.println("No tiene camino posible");
					return;
				}
			}
		});
		
		
		;
		
		
	}





	private void convertirMatriz(Value[][] _matrizCopiada) {
		_matrizEnteros = new int[_matrizCopiada.length][_matrizCopiada[0].length];
		for(int x=0; x<_matrizCopiada.length; x++) {
			for(int y=0; y<_matrizCopiada[0].length; y++) {
				int valorEntero = 0;
				if(_matrizCopiada[x][y].equals(Value.plusOne))
					valorEntero = 1;
				else
					valorEntero = -1;
				_matrizEnteros[x][y] = valorEntero;
			}
		}
		
	}
	
	private void generarBotones() {
		_displayNumeros = new JLabel[_matrizEnteros.length][_matrizEnteros[0].length];
		for(int x=0; x<_matrizEnteros.length; x++) {
			for(int y=0; y<_matrizEnteros[x].length; y++) {
				_displayNumeros[x][y] = new JLabel("" ,SwingConstants.CENTER);
				_displayNumeros[x][y].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
				_displayNumeros[x][y].setText(_matrizEnteros[x][y] + "");
				_displayNumeros[x][y].setOpaque(true);
				panelGrilla.add(_displayNumeros[x][y]);
				
			}
		}
		
	}
}
