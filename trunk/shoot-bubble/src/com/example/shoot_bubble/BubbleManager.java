package com.example.shoot_bubble;

import java.util.Random;
import android.os.Bundle;

public class BubbleManager {
	int bubblesLeft;
	Bmp[] bubbles;
	int[] countBubbles;

	public BubbleManager(Bmp[] bubbles) {
		this.bubbles = bubbles;
		this.countBubbles = new int[bubbles.length];
		this.bubblesLeft = 0;
	}

	// public void saveState(Bundle map)
	// {
	// map.putInt("BubbleManager-bubblesLeft", bubblesLeft);
	// map.putIntArray("BubbleManager-countBubbles", countBubbles);
	// }
	//
	// public void restoreState(Bundle map)
	// {
	// bubblesLeft = map.getInt("BubbleManager-bubblesLeft");
	// countBubbles = map.getIntArray("BubbleManager-countBubbles");
	// }
	public void addBubble(Bmp bubble) {
		countBubbles[findBubble(bubble)]++;
		bubblesLeft++;
	}

	public void removeBubble(Bmp bubble) {
		countBubbles[findBubble(bubble)]--;
		bubblesLeft--;
	}

	private int findBubble(Bmp bubble) {
		for (int i = 0; i < bubbles.length; i++) {
			if (bubbles[i] == bubble) {
				return i;
			}
		}

		return -1;
	}

	public int countBubbles() {
		return bubblesLeft;
	}

	public int nextBubbleIndex(Random rand) {
		int select = rand.nextInt() % bubbles.length;

		if (select < 0) {
			select = -select;
		}

		int count = -1;
		int position = -1;

		while (count != select) {
			position++;

			if (position == bubbles.length) {
				position = 0;
			}

			if (countBubbles[position] != 0) {
				count++;
			}
		}

		return position;
	}

	public Bmp nextBubble(Random rand) {
		return bubbles[nextBubbleIndex(rand)];
	}

}
