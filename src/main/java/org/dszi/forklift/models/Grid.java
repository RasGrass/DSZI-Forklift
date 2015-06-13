package org.dszi.forklift.models;

/**
 *
 * @author Slawek
 */
public class Grid {

	GridItem[][] pools;

	int _width;
	int _height;
	int scaleWidth;
	int scaleHeight;

	public Grid() {
		_width = 15;
		_height = 15;
		scaleWidth = 1;
		scaleHeight = 1;
		initPool();
	}

	public void SetLayoutResolution(int screenWidth, int screenHeight) {
		scaleWidth = screenWidth / _width;
		scaleHeight = screenHeight / _height;
	}

	private void initPool() {
		pools = new GridItem[_width][];

		for (int x = 0; x < _width; x++) {
			pools[x] = new GridItem[_height];
			for (int y = 0; y < _height; y++) {
				pools[x][y] = null;
			}
		}
	}

	public void SetObject(GridItem item, int x, int y) {
		int scaledX = x / scaleWidth;
		int scaledY = y / scaleHeight;
		pools[scaledX][scaledY] = item;
	}

	public GridItem GetObject(int x, int y) {
		int scaledX = x / scaleWidth;
		int scaledY = y / scaleHeight;
		return pools[scaledX][scaledY];
	}

	public void DeleteObject(int x, int y) {
		int scaledX = x / scaleWidth;
		int scaledY = y / scaleHeight;
		pools[scaledX][scaledY] = null;
	}

	public int GetWidth() {
		return _width;
	}

	public int GetHeight() {
		return _height;
	}
}
