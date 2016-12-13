package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JPanel;

public class Graph2 extends JPanel {
	private float[] arr;
	private float[]arrmod;
	private Color myColor;
	String mensaje;
	
	public Graph2(){
		super();
	//	this.setSize(new Dimension(512,350));
		this.setPreferredSize(new Dimension(512,320));
		this.setBackground(Color.BLACK);
		myColor=Color.WHITE;
	}
	
	public Graph2(float[] arr,float[]arrmod,String mensaje){
		super();
	//	this.setSize(new Dimension(512,350));
		this.setPreferredSize(new Dimension(512,300));
		this.setBackground(Color.BLACK);
		this.arr=arr;
		this.arrmod=arrmod;
		myColor=Color.WHITE;
		this.mensaje=mensaje;
	}
	
	public void setArr(float[] arr, float[] arrmod){
		this.arr=arr;
		this.arrmod=arrmod;
	}
	
	public void setColor(Color color){
		this.myColor=color;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(myColor);
		if(arr!=null){
			for(int i=0;i<700;i++){
				g.setColor(Color.WHITE);
				g.drawLine(i, this.getHeight()/2-(int)(arr[i]*70000), i+1, this.getHeight()/2-(int)(arr[i+1]*70000));
				g.setColor(myColor);
				g.drawLine(i, this.getHeight()/2-(int)(arrmod[i]*70000), i+1, this.getHeight()/2-(int)(arrmod[i+1]*70000));

			}
		}
		g.setColor(Color.magenta);
		g.drawLine(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
		g.drawLine(0, 0,0, this.getHeight());
		
		for(int i=0;i<21;i++){
			g.drawLine(i*this.getWidth()/20, this.getHeight()/2-3,i*this.getWidth()/20, this.getHeight()/2+3);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.BOLD,20));
		g.drawString(this.mensaje, 20, 30);
		g.setFont(new Font("Arial",Font.BOLD,15));
		g.drawString("t(ms)", this.getWidth()-50, this.getHeight()/2-15);
		
		g.setFont(new Font("Arial",Font.PLAIN,11));
		float k=0;
		for(float i=0;i<20;i++){
			g.drawString(String.format("%.1f", k),(int) i*this.getWidth()/20-10, this.getHeight()/2+15);
			k+=2.4;
		}
	}
}
