package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Driver5;


public class PanelVoces extends JPanel implements ActionListener{
	private JButton grabar;
	//private JRadioButton sel1, sel2, sel3, sel4, sel5;
	private JButton b1,b2,b3,b4,b5;
	private JLabel labelMensaje;
	private String mensaje;
	protected ImageIcon i1, i2, i3, i4, i5;
	private Driver5 driver;
	private Graph orGraph, modGraph;
	Graph2 timGraph;
	
	
	public PanelVoces(){
		driver = new Driver5();
		
		i1 = new ImageIcon("darth.png"); 
		i2 = new ImageIcon("storm.png");      
		i3 = new ImageIcon("cepo.png"); 
		i4 = new ImageIcon("artu.png"); 
		i5 = new ImageIcon("yoda.png"); 
		
		b1 = new JButton(i1);
		b2 = new JButton(i2);
		b3 = new JButton(i3);
		b4 = new JButton(i4);
		b5 = new JButton(i5);
		
		
		this.labelMensaje = new JLabel("    dfs               ");
		this.mensaje = "Grabando por 5 segundos";
		
		this.grabar = new JButton(new ImageIcon("recordd.png"));
		
		this.grabar.addActionListener(this);
		this.b1.addActionListener(this);
		this.b2.addActionListener(this);
		this.b3.addActionListener(this);
		this.b4.addActionListener(this);
		this.b5.addActionListener(this);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth=1; 
		c.gridy = 0;
		c.gridx = 0; 
		//c.gridheight = 2;
		this.add(b1, c);
		//c.gridheight = 1;
		c.gridx = 1; this.add(grabar,c);
		c.gridx = 2; this.add(b2, c);
		c.gridy = 1; c.gridx = 0; this.add(b3, c);
		c.gridx = 1; this.add(b4, c);
		c.gridx = 2; this.add(b5, c);
		
		//this.add(labelMensaje,c);
		
		 c.gridy = 2;
		
		
		 
		c.gridy = 3; c.gridx = 0; 
		c.gridx = 2; 
		
		
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==grabar){
			driver.setSel(0);
			driver.grabarAudio();
			this.modGraph.setColor(Color.WHITE);
			this.timGraph.setColor(Color.WHITE);
		}
		else{
			if(e.getSource()==b1){
				driver.setSel(1);
				this.modGraph.setColor(Color.red);
				this.timGraph.setColor(Color.red);
			}
			else if(e.getSource()==b2){
				driver.setSel(2);
				this.modGraph.setColor(Color.LIGHT_GRAY);
				this.timGraph.setColor(Color.LIGHT_GRAY);
			}
			else if(e.getSource()==b3){
				driver.setSel(3);
				this.modGraph.setColor(Color.YELLOW);
				this.timGraph.setColor(Color.YELLOW);
			}
			else if(e.getSource()==b4){
				driver.setSel(4);
				this.modGraph.setColor(Color.BLUE);
				this.timGraph.setColor(Color.BLUE);
			}
			else if(e.getSource()==b5){
				driver.setSel(5);
				this.modGraph.setColor(Color.GREEN);
				this.timGraph.setColor(Color.GREEN);
			}
		}
		driver.reproducirFourier();
		
		this.orGraph.setArr(this.getdriver().getOrig()[10]);
		this.modGraph.setArr(this.getdriver().getMod()[10]);
		this.timGraph.setArr(this.getdriver().getOrigT()[10],this.getdriver().getmodT()[10]);
		
		this.orGraph.repaint();
		this.modGraph.repaint();
		this.timGraph.repaint();

	}
	
	public Driver5 getdriver(){
		return this.driver;
	}
	
	public void setOrGraph(Graph orGraph){
		this.orGraph=orGraph;
	}
	
	public void setModGraph(Graph modGraph){
		this.modGraph=modGraph;
	}
	
	public void setTimGraph(Graph2 timGraph){
		this.timGraph=timGraph;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		setBackground(Color.white);
	}
}
