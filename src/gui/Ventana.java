package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;

import javax.swing.SpringLayout.Constraints;

public class Ventana extends JFrame{

	PanelVoces panelvoces;
	Graph original;
	Graph modificada;
	Graph2 tiempo;
	
	public Ventana(){
		super("Transforma tu voz");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		this.setSize(new Dimension(1480, 720)); 
		
		this.panelvoces = new PanelVoces();
	
		
		this.original=new Graph(this.panelvoces.getdriver().getOrig()[10],"Original");
		this.panelvoces.setOrGraph(original);
		
		this.modificada=new Graph(this.panelvoces.getdriver().getMod()[10],"Desplazada");
		this.panelvoces.setModGraph(modificada);
		
		this.tiempo=new Graph2(this.panelvoces.getdriver().getOrigT()[10],this.panelvoces.getdriver().getmodT()[10],"Tiempo");
		this.panelvoces.setTimGraph(tiempo);
		
		this.buildLayout2();

		this.setVisible(true);	
	}
	
	public void buildLayout(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridheight=2; 
		c.gridy = 0; c.gridx = 0; 
		this.add(panelvoces, c);
		c.gridheight = 1;
		c.gridx = 1;
		this.add(this.original, c);
		c.gridy = 1;
		this.add(this.modificada, c);
		
	}
	
	public void buildLayout2(){
		this.setLayout(new GridLayout(0,2));
		this.add(this.original);
		this.add(this.panelvoces);
		this.add(this.modificada);
		this.add(this.tiempo);
	}
	
	public static void main(String[] args) {
		Ventana v = new Ventana();
	}


}
