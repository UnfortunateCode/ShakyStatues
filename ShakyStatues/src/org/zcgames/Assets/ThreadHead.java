package org.zcgames.Assets;

public class ThreadHead {
	private ThreadHolder threadHolder;
	
	public void pause() {
		if (threadHolder != null) {
			threadHolder.pause();
		}
	}
	
	public void resume() {
		if (threadHolder != null) {
			threadHolder.resume();
		}
	}
	
	public ThreadHead attach(ThreadHolder th) {
		if (threadHolder != null) {
			ThreadHead tHead = new ThreadHead();
			tHead.attach(th);
			return tHead;
		}
		threadHolder = th;
		return this;
	}
	
	public void begin() {
		if (threadHolder != null) {
			threadHolder.run();
		}
	}
	
	public boolean isDone() {
		return threadHolder.isDone();
	}
	
	/**
	 * waiting is the method called when the runnable is 
	 * in a position to pause. waiting waits until the
	 * thread is no longer paused, and then returns to 
	 * the runnable that called it.
	 */
	public void waiting() {
		while (threadHolder.isPaused()) {
			try {
				threadHolder.wait();
			} catch (InterruptedException e) {
				
			}
		}
	}
	
}
