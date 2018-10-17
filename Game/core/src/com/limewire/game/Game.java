package com.limewire.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture grid, greenSquare;
	int gridWidth = 32;
	int gridHeight = 32;
	int gridLines = 3;
	boolean[][] activeGrid;

	@Override
	public void create () {
		batch = new SpriteBatch();
		grid = new Texture("32Grid.png");
		greenSquare = new Texture("32greenSquare.png");

		activeGrid = new boolean[32][32];
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		batch.begin();
		for (int i = 0; i < gridHeight; i++){
			for (int j = 0; j < gridWidth; j++){
				if (activeGrid[j][i]){
					batch.draw(greenSquare, gridLines + i * (gridWidth + gridLines),
							gridLines + j * (gridHeight + gridLines));
				}
			}
		}

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			int[] coordinates = getMouseLocation();
			activeGrid[coordinates[1]][coordinates[0]] = true;
		}

		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
			int[] coordinates = getMouseLocation();
			activeGrid[coordinates[1]][coordinates[0]] = false;
		}

		batch.draw(grid, 0, 0);
		batch.end();
	}

	public int[] getMouseLocation(){ // Returns the coordinates of the mouse in the grid
		return new int[] {(Gdx.input.getX() / (gridWidth + gridLines)),
				((Gdx.graphics.getHeight() - Gdx.input.getY()) / (gridHeight + gridLines))};
	}
}
