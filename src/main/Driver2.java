package main;

import io.*;
import record.Grabar;


import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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

public class Driver2 {
	public static void main(String[] argv){
		//new Grabar();



		try {
			WaveDecoder decoder = new WaveDecoder(new FileInputStream("Grabacion.wav"));


			FFT fft = new FFT(262144, 44100);

			float[] samples = new float[262144];
			int size=decoder.readSamples( samples );


			//muestras de audio
			/*Plot plott = new Plot( "Samples of audio", 1024, 512);
			plott.plot(samples, 1f, Color.YELLOW );*/

			fft.forward(samples);

			//Espectro de frecuencias
			/*Plot plot2 = new Plot( "Frequency spectrum", 512, 512);
			plot2.plot(fft.getSpectrum(), 1, Color.WHITE );*/

			//Escalar amplitud en rango de frecuencias
			for (int i=fft.freqToIndex(0);i<fft.freqToIndex(80);i++){
				fft.scaleBand(i, 1f);
			}

			/*Plot plo4 = new Plot( "Frequency spectrum modified", 512, 512 );
			plo4.plot( fft.getSpectrum(), 1, Color.BLUE );*/

			//Transformada inversa
			float[] inverso=new float[131073];
			for(int i=0;i<fft.getSpectrum().length;i++){
				inverso[i]=fft.getSpectrum()[i];
			}
			//float[] inverso=fft.getSpectrum();
			fft.inverse(inverso);

			/*	Plot plo3 = new Plot( "audio modificado", 512, 512 );
			plo3.plot( inverso, 1, Color.BLUE );*/



			ByteBuffer buffer = ByteBuffer.allocate(4 * inverso.length);
			for (float value : inverso){
				buffer.putFloat(value);
			}
			byte[] newInverso=buffer.array();

			File out = new File("Grabacionmod.wav");

	        ByteArrayInputStream bais = new ByteArrayInputStream(newInverso);
	        AudioFormat format = new AudioFormat(44100.f, 16, 1, true, false);
	        long length = (long)(newInverso.length / format.getFrameSize());
	        AudioInputStream audioInputStream = new AudioInputStream(bais, format, length);
			AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out);
			//new PlaybackVisualizer(plot2,1,decoder);

			




		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		} catch (Exception e) {
			System.out.println(e);
		}



	}

}
