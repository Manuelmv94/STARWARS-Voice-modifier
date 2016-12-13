package record;

import java.io.File;
import javax.sound.sampled.*;

public class Grabar {

	String[]palabras=new String [100];

	AudioFileFormat.Type aFF_T = AudioFileFormat.Type.WAVE;
	//static AudioFormat aF = new AudioFormat(16000.0F, 16, 1, true, true);
	static AudioFormat aF = new AudioFormat(44100.0F,16,1,true,false);
	TargetDataLine tD;

	File f = new File("Grabacion.wav");

	public Grabar() {
		try {
			DataLine.Info dLI = new DataLine.Info(TargetDataLine.class,aF);
			tD = (TargetDataLine)AudioSystem.getLine(dLI);
			new CapThread().start();
			System.out.println("Grabando durante 5s...");
			Thread.sleep(5000);
			tD.close();
		}catch (Exception e) {}
	}

	class CapThread extends Thread {
		public void run() {
			try {
				tD.open(aF);
				tD.start();
				AudioSystem.write(new AudioInputStream(tD), aFF_T, f);
			}catch (Exception e){}
		}
	}

	public static void main(String[] args) {
		new Grabar(); 
		System.out.println(aF.toString());
	}
}

