package com.java.IosebiGames.backend;

import javax.sound.sampled.*;
import com.java.IosebiGames.App.Main;
import java.io.File;

public class VoiceSaving {
	private Thread recordingThread;
	private AudioFormat aFormat;
	private DataLine.Info micInfo;
	private TargetDataLine microphone;
	private File storedFile;
	private Clip clip;
	private Main main;
	private AudioInputStream ais;
	
	public VoiceSaving(Main main) {
		this.main = main;
		this.storedFile = new File("sample.wav");
		this.aFormat = new AudioFormat(44100.0F, 16, 1, true, false);
		this.micInfo = new DataLine.Info(TargetDataLine.class, aFormat);
		try {
			this.microphone = (TargetDataLine) AudioSystem.getLine(micInfo);
		}catch(LineUnavailableException e) {
			System.out.println("Can't find the line supported: " + e.getMessage());
		}
		createSaving();
	}
	private void createSaving() {
		recordingThread = new Thread(() -> {
			try {
				if(!AudioSystem.isLineSupported(micInfo)) {
					System.out.println("Unable to find Microphone!");
					return;
				}
				microphone.open(aFormat);
				microphone.start();
				AudioSystem.write(new AudioInputStream(microphone), AudioFileFormat.Type.WAVE, storedFile);
			}catch(Exception e) {
				System.out.println("Voice File saving has failed: " + e.getMessage());
			}
		});
	}
	public void startSaving() {
		recordingThread.start();		
	}
	public void stopSaving() {
		microphone.close();
	}
	public void listen(String path) {
		try {
			clip = AudioSystem.getClip();
			ais = AudioSystem.getAudioInputStream(new File(path));
			clip.open(ais);
			clip.start();
			
			clip.addLineListener(new LineListener() {
				@Override
				public void update(LineEvent e) {
				  if(e.getType() == LineEvent.Type.STOP) {
				    if(clip.getMicrosecondPosition() >= clip.getMicrosecondLength()) {
				    	clip.close();
				    	try {
				    		ais.close();
				    	} catch(Exception ex) {
				    		System.out.println(ex.getMessage());
				    	}
				    	main.Listen.setEnabled(true);
				        if(new File("sample.wav").exists()) {
				        	main.Delete.setEnabled(true);
				            main.window.dispose();
				        	new Main();
				        }
				    }
				  }					
				}
			});
		}catch(Exception e) {
			System.out.println("Can't listen: " + e.getMessage());
		}
	}
}