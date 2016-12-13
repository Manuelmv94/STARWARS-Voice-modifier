package main;

import io.*;
import record.Grabar;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import analysis.*;
import visualization.*;

public class Driver3 {
	public static void main(String[] argv){
		//new Grabar();//Grabar archivo de audio localmente(Grabacion.wav)
		
		//seleccionar efecto, agregar más dentro de switch statement
		int sel=1;
		int freq1,freq2,freq3,freq4,freq5,freq6;
		float perc1,perc2,perc3;
		
		switch(sel){
		case 1:	
			freq1=0;		freq2=250;		perc1=1;
			freq3=250;		freq4=1200;		perc2=0.5f;
			freq5=1200;		freq6=40000;	perc3=1f;
			break;
		default:	
			freq1=0;		freq2=0;		perc1=1f;
			freq3=0;		freq4=0;		perc2=1f;
			freq5=0;		freq6=0;		perc3=1f;
			break;
		}


		try {
			WaveDecoder decoder = new WaveDecoder(new FileInputStream("Grabacion.wav"));//Abrir archivo de audio grabado
			FFT fft = new FFT(1024, 44100);

			float[][] samples = new float[204][1024];
			float[] temp=new float[513];
			float[][] inversa=new float[204][1024];


			for(int i=1;i<204;i++){
				
				decoder.readSamples( samples[i] );						//Pasar audio a un arreglo
				fft.forward(samples[i]);								//Hacer Fourier a dicho arreglo
				
				for (int j=fft.freqToIndex(freq1);j<fft.freqToIndex(freq2);j++){
					fft.scaleBand(j, perc1);											//Modificar banda de frecuencias
				}
				for (int j=fft.freqToIndex(freq3);j<fft.freqToIndex(freq4);j++){
					fft.scaleBand(j,perc2);												//Modificar banda de frecuencias
				}
				for (int j=fft.freqToIndex(freq5);j<fft.freqToIndex(freq6);j++){
					fft.scaleBand(j, perc3);											//Modificar banda de frecuencias
				}
				
				temp = fft.getSpectrum();
				for(int j=0;j<temp.length;j++){		//Guardar espectro de frecuencias en arreglo
					inversa[i][j]=temp[j];			
				}
				fft.inverse(inversa[i]);			//Transformada Inversa de Fourier(Regresar al dominio del tiempo)

				
				/*Plot plot0 = new Plot( "Samples", 1024, 512 );  // descomentar si se desea ver la gráfica
				plot0.plot( samples[i], 1, Color.YELLOW );
				plot0.plot( inversa[i], 1, Color.BLUE );

				System.out.println(i);*/
			}
			
			
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
}
