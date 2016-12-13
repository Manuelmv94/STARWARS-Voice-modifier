package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Graph extends JPanel {
	private float[] arr;
	private Color myColor;
	String mensaje;
	
	public Graph(){
		super();
	//	this.setSize(new Dimension(512,350));
		this.setPreferredSize(new Dimension(512,320));
		this.setBackground(Color.BLACK);
		myColor=Color.WHITE;
	}
	
	public Graph(float[] arr,String mensaje){
		super();
	//	this.setSize(new Dimension(512,350));
		this.setPreferredSize(new Dimension(512,300));
		this.setBackground(Color.BLACK);
		this.arr=arr;
		myColor=Color.WHITE;
		this.mensaje=mensaje;
	}
	
	public void setArr(float[] arr){
		this.arr=arr;
	}
	
	public void setColor(Color color){
		this.myColor=color;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(myColor);
		if(arr!=null){
			for(int i=0;i<512;i++){
				g.drawLine(this.getWidth()/2+i, this.getHeight()-20-(int)(arr[i]*4000), this.getWidth()/2+i+1, this.getHeight()-20-(int)(arr[i+1]*4000));
				g.drawLine(this.getWidth()/2-i, this.getHeight()-20-(int)(arr[i]*4000), this.getWidth()/2-i-1, this.getHeight()-20-(int)(arr[i+1]*4000));
			}
		}
		g.setColor(Color.magenta);
		g.drawLine(0, this.getHeight()-20, this.getWidth(), this.getHeight()-20);
		g.drawLine(this.getWidth()/2, 0, this.getWidth()/2, this.getHeight());
		
		for(int i=0;i<21;i++){
			g.drawLine(i*this.getWidth()/20, this.getHeight()-17,i*this.getWidth()/20, this.getHeight()-23);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.BOLD,20));
		g.drawString(this.mensaje, 20, 30);
		g.setFont(new Font("Arial",Font.BOLD,15));
		g.drawString("f(KHz)", this.getWidth()-50, this.getHeight()-25);
		
		g.setFont(new Font("Arial",Font.PLAIN,11));
		float k=-13.5f;
		for(float i=1;i<20;i++){
			g.drawString(k+"",(int) i*this.getWidth()/20-15, this.getHeight()-5);
			k+=1.5;
		}
	}
}
