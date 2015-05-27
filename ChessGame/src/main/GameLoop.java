package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayDeque;
import java.util.Queue;

public abstract class GameLoop implements MouseListener, MouseMotionListener {
	private int UPDATES_PER_SECOND;
	private int FRAMES_PER_SECOND;
	private boolean running = true;
	private long targetDrawTime;
	private long targetUpdateTime;

	// ArrayDeque is supposed to be the fastest collection
	private final Queue<MouseEventWithType> mouseEvents = new ArrayDeque<>();

	private long runningFPS;

	protected GameLoop(final int fps, final int ups) {
		setTargetFPS(fps);
		setTargetUPS(ups);
	}

	private void setTargetUPS(final int ups) {
		UPDATES_PER_SECOND = ups;
		targetUpdateTime = 1000 / UPDATES_PER_SECOND;
	}

	public void setTargetFPS(final int fps) {
		FRAMES_PER_SECOND = fps;
		targetDrawTime = 1000 / FRAMES_PER_SECOND;
	}

	public void run() {
		int currentFPS = 0;
		long counterstart = System.nanoTime();
		long counterelapsed = 0;
		long start;
		long elapsed;
		long wait;
		long lastUpdateTime = System.currentTimeMillis();

		while (running) {
			start = System.nanoTime();

			synchronized (mouseEvents) {
				processInput(mouseEvents);
				mouseEvents.clear();

			}

			if (System.currentTimeMillis() > lastUpdateTime + targetUpdateTime) {
				lastUpdateTime = System.currentTimeMillis();
				update();
			}
			draw();
			// Take account for the time it took to draw
			elapsed = System.nanoTime() - start;
			wait = targetDrawTime - elapsed / 1000000;
			counterelapsed = System.nanoTime() - counterstart;
			currentFPS++;

			// at the end of every second
			if (counterelapsed >= 1000000000L) {
				// runningFPS is how many frames we processed last second
				runningFPS = currentFPS;
				currentFPS = 0;
				counterstart = System.nanoTime();
			}

			// dont wanna wait for negative time
			if (wait > 0) {
				try {
					Thread.sleep(wait);
				} catch (final InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public long getCurrentFPS() {
		return runningFPS;
	}

	public void stop() {
		running = false;
	}

	public abstract void processInput(
			final Queue<MouseEventWithType> mouseEvents);

	public abstract void update();

	public abstract void draw();

	@Override
	public void mouseClicked(final MouseEvent e) {
		synchronized (mouseEvents) {
			mouseEvents.add(new MouseEventWithType(e, MouseEventType.CLICK));
		}
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		synchronized (mouseEvents) {
			mouseEvents.add(new MouseEventWithType(e, MouseEventType.ENTER));
		}
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		synchronized (mouseEvents) {
			mouseEvents.add(new MouseEventWithType(e, MouseEventType.EXIT));
		}
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		synchronized (mouseEvents) {
			mouseEvents.add(new MouseEventWithType(e, MouseEventType.PRESS));
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		synchronized (mouseEvents) {
			mouseEvents.add(new MouseEventWithType(e, MouseEventType.RELEASE));
		}
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		synchronized (mouseEvents) {
			mouseEvents.add(new MouseEventWithType(e, MouseEventType.DRAG));
		}
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public static enum MouseEventType {
		PRESS, RELEASE, CLICK, EXIT, ENTER, DRAG;
	}

	public static class MouseEventWithType {
		public MouseEvent event;
		public MouseEventType type;

		public MouseEventWithType(final MouseEvent event,
				final MouseEventType type) {
			this.event = event;
			this.type = type;
		}

	}
}