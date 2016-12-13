package main;

import io.*;
import record.Grabar;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JFrame;

import analysis.*;
import visualization.*;

public class Driver6 {
	private int sel;
	public float[][]originalspect=new float[204][1024];
	public float[][]modspect= new float[204][1024];
	private JFrame Ventana;
	private Plot plot0,plot1;
	private Color mycolor;
	
	public Driver6(int sel){
		//seleccionar efecto, 
		this.sel = sel;
	}
	
	public Driver6(){
		this.sel=1;
	}
	
	public Driver6(JFrame ventana){
		this.sel=1;
		this.Ventana=ventana;
	}
	
	
	public void reproducirFourier(){
		try {
			WaveDecoder decoder = new WaveDecoder(new FileInputStream("Grabacion.wav"));//Abrir archivo de audio grabado
			FFT fft = new FFT(1024, 44100);

			float[][] samples = new float[204][1024];
			float[] temp=new float[513];
			float[][] inversa=new float[204][1024];
			
			

			for(int i=0;i<204;i++){
				
				decoder.readSamples( samples[i] );						//Pasar audio a un arreglo
				
				fft.forward(samples[i]);								//Hacer Fourier a dicho arreglo
				for(int j=0;j<fft.getSpectrum().length;j++){
					this.originalspect[i][j]=fft.getSpectrum()[j];
				}
				
				
				for (int j=fft.freqToIndex(0);j<fft.freqToIndex(100);j++){
					fft.scaleBand(j, 0);											//Modificar banda de frecuencias
				}
				
				if(sel==1){//Darth Vader
					for (int j=0;j<fft.specSize();j++){
						float ampli=fft.getBand(j);
						fft.setBand((int)(j*0.7), ampli);
					}
				}else if(sel==2){//Storm Trooper
					for (int j=0;j<fft.specSize();j++){
						float ampli=fft.getBand(j);
						fft.setBand((int)(j*0.9), ampli);
					}
				}else if(sel==3){//C3P0
					for (int j=(int)(fft.specSize()/2.5);j>fft.specSize()/40;j--){
						float ampli=fft.getBand(j);
						fft.setBand((int)(j*2.4), ampli);
					}
				}else if(sel==4){//R2D2
					for (int j=(int)(fft.specSize()/5);j>fft.specSize()/100;j--){
						float ampli=fft.getBand(j);
						fft.setBand((int)(j*4.9), ampli);
					}
				}else{//YODA
					for (int j=(int)(fft.specSize()/10);j>fft.specSize()/100;j--){
						float ampli=fft.getBand(j);
						fft.setBand((int)(j*2), ampli);
					}
				}
				
				for(int j=0;j<fft.getSpectrum().length;j++){
					this.modspect[i][j]=fft.getSpectrum()[j];
				}
				
				temp = fft.getSpectrum();
				//Guardar espectro de frecuencias en arreglo
				for(int j=0;j<temp.length;j++){		//Guardar espectro de frecuencias en arreglo
					inversa[i][j]=temp[j];			
				}			
				
				fft.inverse(inversa[i]);			//Transformada Inversa de Fourier(Regresar al dominio del tiempo)

			}
			
			
			switch(sel){
			case 1:	
				mycolor=Color.RED;
				break;
			case 2:	
				mycolor=Color.WHITE;
				break;
			case 3:	
				mycolor=Color.YELLOW;
				break;
			case 4:	
				mycolor=Color.BLUE;
				break;
			default:	
				mycolor=Color.green;
				break;
			}
			
			//plot0.clear();
		//	plot0.plot( this.originalspect[10], 1, Color.GRAY );
		//	plot0.plot( this.modspect[10], 1, mycolor );
			
			
			
			//Reproducir audio modificado
			AudioDevice device=new AudioDevice();
			for(int i=0;i<204;i++){
				device.writeSamples(inversa[i]);
			}


		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	public void grabarAudio(){
		new Grabar(); //Grabar archivo de audio localmente(Grabacion.wav)
		
	}
	
	
	public int getSel() {
		return sel;
	}
	
	public void show(int spect){
		
		//Plot plot0 = new Plot( "Samples", 512, 350 );  // descomentar si se desea ver la gr�fica
		plot0=new Plot(Ventana);
		//plot1=new Plot(Ventana);
		//Plot plot1 = new Plot( "Samples", 512, 350 );  // descomentar si se desea ver la gr�fica
		//plot1.plot( this.modspect[spect], 1, mycolor );
	}
	
	public void setSel(int sel) {
		this.sel = sel;
	}

	
	public static void main(String[] argv){
		Driver5 d = new Driver5();
		//d.grabarAudio();
		d.reproducirFourier();
		// sel puede ser 1,2,3,4,o 5.
		
		
	}
}
