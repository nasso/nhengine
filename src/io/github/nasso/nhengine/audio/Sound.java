package io.github.nasso.nhengine.audio;

import java.io.IOException;
import java.nio.ByteBuffer;

import io.github.nasso.nhengine.data.AudioDataLoader;
import io.github.nasso.nhengine.event.Observable;

/**
 * A <code>Sound</code> represents a sound resource.
 * 
 * @author nasso
 */
public class Sound extends Observable {
	private ByteBuffer data;
	private int channels;
	private int sampleRate;
	private int sampleCount;
	private int bitsPerSample;
	
	/**
	 * Constructs a sound from the given raw data and with the specified properties.
	 * 
	 * @param data
	 *            The PCM data
	 * @param channels
	 *            The number of channels (1 = mono; 2 = stereo)
	 * @param sampleRate
	 *            The sample rate
	 * @param bitsPerSample
	 *            The bits per sample (usually 16)
	 * @param sampleCount
	 *            The total count of samples in the data
	 */
	public Sound(ByteBuffer data, int channels, int sampleRate, int bitsPerSample, int sampleCount) {
		this.data = data;
		this.channels = channels;
		this.sampleRate = sampleRate;
		this.bitsPerSample = bitsPerSample;
		this.sampleCount = sampleCount;
	}
	
	/**
	 * @return The duration in seconds
	 */
	public float getDuration() {
		return (float) this.sampleCount / this.sampleRate;
	}
	
	/**
	 * @return The number of channels in this sound
	 */
	public int getChannels() {
		return this.channels;
	}
	
	/**
	 * @return The sample rate
	 */
	public int getSampleRate() {
		return this.sampleRate;
	}
	
	/**
	 * @return The total count of samples in the sound
	 */
	public int getSampleCount() {
		return this.sampleCount;
	}
	
	/**
	 * @return The number of bits per sample (usually 16)
	 */
	public int getBitsPerSample() {
		return this.bitsPerSample;
	}
	
	/**
	 * @return The PCM data buffer
	 */
	public ByteBuffer getData() {
		return this.data;
	}
	
	/**
	 * Disposes the sound. It shall not be used after that.
	 */
	public void dispose() {
		this.triggerEvent("dispose");
	}
	
	public static Sound load(String filePath) throws IOException {
		return load(filePath, false);
	}
	
	/**
	 * Loads a sound from the specified file. It uses the loader associated with the file extension.<br>
	 * Calling this method is equivalent to:
	 * 
	 * <pre>
	 * {@link AudioDataLoader}.{@link AudioDataLoader#load load}(filePath, inJar);
	 * </pre>
	 * 
	 * @param filePath
	 *            The path to the file (with extension)
	 * @param inJar
	 *            When true, it'll try to load it from the class path (= in the jar).
	 * @return The loaded sound
	 * @throws IOException
	 */
	public static Sound load(String filePath, boolean inJar) throws IOException {
		return AudioDataLoader.load(filePath, inJar);
	}
}
