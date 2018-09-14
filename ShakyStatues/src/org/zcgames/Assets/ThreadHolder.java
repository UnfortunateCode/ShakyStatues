package org.zcgames.Assets;

/**
 * ThreadHolder holds a pausing thread. It is up to
 * the Runnable provided at the constructor to gain
 * access to this ThreadHolder and to handle pausing.
 * 
 * Note: paused = true is not the same as the thread
 * being in wait mode. Ideally, the Runnable would try
 * to get the thread into wait mode as soon as possible
 * after paused is set to true.
 * 
 * @author Paul
 *
 */
public class ThreadHolder implements Runnable {
	private boolean paused = true;
	private Thread thread;
	
	public ThreadHolder(Runnable run) {
		thread = new Thread(run);
	}
	
	@Override
	public void run() {
		paused = false;
		thread.start();
	}
	
	public synchronized void pause() {
		paused = true;
	}
	
	public synchronized void resume() {
		paused = true;
		notifyAll();
	}
	
	public synchronized boolean isPaused() {
		return paused;
	}
	
	public boolean isDone() {
		return (thread.getState() == Thread.State.TERMINATED);
		
	}
	
	
}
