package main;

import io.*;
import record.Grabar;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.JFileChooser;

import analysis.*;
import visualization.*;

public class Driver {
	public static void main(String[] argv){
		//new Grabar();
		int select=1;
		int freq1,freq2;
		//1=graves,2=medios,3==agudos
		
		
		
		try {
			if(select==1){
				freq1=250;
				freq2=20000;
			}else{
				freq1=500;
				freq2=1000;
			}
			
			
			WaveDecoder decoder = new WaveDecoder(new FileInputStream("Grabacion.wav"));
			AudioDevice device=new AudioDevice();
			FFT fft = new FFT(1024, 44100);

			float[] samples = new float[1024];
			float[] spectrum = new float[1024 / 2 + 1];
			float[] lastSpectrum = new float[1024 / 2 + 1];
			List<Float> spectralFlux = new ArrayList<Float>();

			while (decoder.readSamples(samples) > 0) {
				fft.forward(samples);
				System.arraycopy(spectrum, 0, lastSpectrum, 0, spectrum.length);
				System.arraycopy(fft.getSpectrum(), 0, spectrum, 0, spectrum.length);

				float flux = 0;
				for (int i = 0; i < spectrum.length; i++)
					flux += (spectrum[i] - lastSpectrum[i]);
				spectralFlux.add(flux);
				
				Plot plot0 = new Plot( "Samples", 1024, 512 );
				plot0.plot( samples, 1f, Color.YELLOW );
			}
			
			//raw data
			Plot plot0 = new Plot( "Samples", 1024, 512 );
			plot0.plot( samples, 1, Color.YELLOW );
			
			//Spectral flux
			Plot plot = new Plot( "Spectral Flux", 1024, 512 );
			plot.plot( spectralFlux, 1, Color.red );
			
			//Espectro de frecuencias
			Plot plot2 = new Plot( "Frequency Spectrum", 512, 512);
			plot2.plot(fft.getSpectrum(), 1, Color.WHITE );
			
			
			for (int i=fft.freqToIndex(0);i<fft.freqToIndex(80);i++){
				fft.scaleBand(i, 0.1f);
			}
			
			for (int i=fft.freqToIndex(500);i<fft.freqToIndex(1000);i++){
				fft.scaleBand(i, 0.1f);
			}
			
			//Espectro de frecuencias modificado
			Plot plot3 = new Plot( "Modified Frequency Spectrum", 512, 512);
			plot3.plot(fft.getSpectrum(), 1, Color.WHITE );
			
			
			//raw data modified
			float[] inverso=new float[1024];
			for(int i=0;i<fft.getSpectrum().length;i++){
				inverso[i]=fft.getSpectrum()[i];
			}
			fft.inverse(inverso);
			
			System.out.println(fft.getSpectrum().length);
			Plot plot4 = new Plot( "Signal modified", 1024, 512 );
			plot4.plot( inverso, 1, Color.BLUE );
			
			float[] spect=new float[spectralFlux.size()];
			for(int i=0;i<spect.length;i++){
				spect[i]=spectralFlux.get(i);
			}
		     
	     
			
			
			
			//new PlaybackVisualizer(plot2,1,decoder);
			//new PlaybackVisualizer(plot2,1,decoder);
			
			
			/*byte byteArray[] = new byte[inverso.length*4];

			// wrap the byte array to the byte buffer
			ByteBuffer byteBuf = ByteBuffer.wrap(byteArray);

			// create a view of the byte buffer as a float buffer
			FloatBuffer floatBuf = byteBuf.asFloatBuffer();

			// now put the float array to the float buffer,
			// it is actually stored to the byte array
			floatBuf.put (inverso);
			
			File out = new File("Grabacionmod.wav");
		
			AudioFormat format = new AudioFormat((float)44100, 16, 1, true, false);
		    ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
		    AudioInputStream audioInputStream;
		    audioInputStream = new AudioInputStream(bais, format,inverso.length);
		    while( audioInputStream.read()>0 )
			{
		    	 
			}
		   
		    audioInputStream.close();
			*/
			
			
			
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		} catch (Exception e) {
			System.out.println(e);
		}
		



	}
}
