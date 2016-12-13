package main;

import io.*;
import record.Grabar;


import java.io.FileInputStream;
import java.io.FileNotFoundException;



import analysis.*;


public class Driver5 {
	private int sel;
	private float[][]originalspect=new float[204][1024];
	private float[][]modspect= new float[204][1024];
	private float[][] samples = new float[204][1024];
	private float[] temp=new float[513];
	private float[][] inversa=new float[204][1024];

	
	public Driver5(int sel){
		//seleccionar efecto, 
		this.sel = sel;
	}
	
	public Driver5(){
		this.sel=0;
	}
	

	
	
	public void reproducirFourier(){
		try {
			WaveDecoder decoder = new WaveDecoder(new FileInputStream("Grabacion.wav"));//Abrir archivo de audio grabado
			FFT fft = new FFT(1024, 44100);

			

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
				}else if(sel==5){//YODA
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
			
			if(sel>=1 && sel<=5){
				//Reproducir audio modificado
				AudioDevice device=new AudioDevice();
				for(int i=0;i<204;i++){
					device.writeSamples(inversa[i]);
				}
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
	
	
	public void setSel(int sel) {
		this.sel = sel;
	}
	
	public float[][] getOrig(){
		return this.originalspect;
	}
	
	public float[][]getMod(){
		return this.modspect;
	}
	
	public float[][]getOrigT(){
		return this.samples;
	}
	
	public float[][]getmodT(){
		return this.inversa;
	}
}
