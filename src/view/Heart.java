package view;


import java.io.File;
import java.net.MalformedURLException;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Heart extends ImageView {
	private Image greyHeartImage;
	private Image redHeartImage;
	private static final int MAX_TTL = 500;
	private int ttl;
	private AnimationTimer timer;
	/*
	 * 每次 Timer 被触发，TTL 就会减少，当 TTL 减少到 0 以下时，灯就会灭掉
	 * 不过，通过 beat 方法，可以把 TTL 重置为最大值，也就是说，每次收到心跳包文，这个东西就会亮起来
	 * 当 MAX_TTL 设置的比较小时，这个东西会处于一种闪烁的模式，因为收到心跳包文后，TTL 很快又衰减为 0 了
	 * 当 MAX_TTL 设置得比较大时，这个东西会处于常亮的状态，因为这个东西还没来得及熄灭时，又一个心跳报文把这个东西状态拉满了
	 * 
	 */
	public Heart() {
		
		try {
			greyHeartImage = new Image(new File("res/grey heart.png").toURI().toURL().toString());
			redHeartImage = new Image(new File("res/red heart.png").toURI().toURL().toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setImage(greyHeartImage);
		ttl = 0;
		timer = new AnimationTimer() {	
			public void handle(long now) {
				if(ttl <= 0) {
					Heart.this.setImage(greyHeartImage);
					return;
				}
				ttl -= 1000.0/60.0;
			}
		};
		timer.start();
	}
	public void beat() {
		this.setImage(redHeartImage);
		ttl = MAX_TTL;
	}
}
