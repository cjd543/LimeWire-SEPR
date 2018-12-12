package com.limewire.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture grid, greenSquare,minimap,toolbar;
	int gridWidth = 32;
	int gridHeight = 32;
	int gridLines = 3;
	boolean[][] activeGrid;
	private OrthographicCamera cam;

	Stage stage;
	Button buttonmap;
	int buttonmap_counter = 0;
	Button buttontool;
	int buttontool_counter = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		grid = new Texture("32Grid.png");
		greenSquare = new Texture("32greenSquare.png");
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		cam = new OrthographicCamera(100, 100 * (h / w));
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();

		activeGrid = new boolean[32][32];

		/* button */
		minimap = new Texture("minimap.png");
		toolbar = new Texture("GUI-1.png");
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		buttonmap = new ButtonSetup().setup("buttonmap");
		buttonmap.setSize(50,50);
		buttonmap.setPosition(10, 60);
		buttonmap.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("click map");
				buttonmap_counter +=1;
			}
		});
		buttontool = new ButtonSetup().setup("buttontool");
		buttontool.setSize(50,50);
		buttontool.setPosition(10,5);
		buttontool.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				System.out.println("click tool");
				buttontool_counter += 1;
			}
		});
		stage.addActor(buttonmap);
		stage.addActor(buttontool);


		/* button end */

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		CameraMove();
		cam.update();
		batch.setProjectionMatrix(cam.combined);

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
			activeGrid[coordinates[1]/4+(int)Math.round(((cam.position.y-(gridWidth*2+gridLines*2.5))/(gridLines+gridWidth)))][coordinates[0]/4+(int)Math.round(((cam.position.x-(gridWidth*2+gridLines*2.5))/(gridLines+gridWidth)))] = true;
		}

		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
			int[] coordinates = getMouseLocation();
			activeGrid[coordinates[1]/4+(int)Math.round(((cam.position.y-(gridWidth*2+gridLines*2.5))/(gridLines+gridWidth)))][coordinates[0]/4+(int)Math.round(((cam.position.x-(gridWidth*2+gridLines*2.5))/(gridLines+gridWidth)))] = false;
		}


		batch.draw(grid, 0, 0);
		if(buttonmap_counter%2 == 1){
			batch.draw(minimap,20+cam.position.x, 20+cam.position.y,50,50);
		}
		if(buttontool_counter%2 ==1){
			batch.draw(toolbar,(float) (15+cam.position.x-(gridWidth*2+gridLines*2.5)),(float)(cam.position.y-(gridWidth*2+gridLines*2.5)),120,33);
		}

		batch.end();

		/*button*/
		stage.act();
		stage.draw();

		/*button end*/
	}
	private void CameraMove() {
		//System.out.println(cam.position.y);
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			cam.translate(-3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			cam.translate(3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			cam.translate(0, -3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			cam.translate(0, 3, 0);
		}
		//control by mouse move
		if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)||Gdx.input.isButtonPressed(Input.Buttons.LEFT)){

			if(Gdx.input.getX()<=50){
				cam.translate(-3, 0, 0);
			}
			if(Gdx.input.getX()>=Gdx.graphics.getWidth()-20){
				cam.translate(3, 0, 0);
			}
			if(Gdx.input.getY()>=Gdx.graphics.getHeight()-20){
				cam.translate(0, -3, 0);
			}
			if(Gdx.input.getY()<=50){
				cam.translate(0, 3, 0);
			}
		}
		cam.zoom = 1.43f;

		float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
		float effectiveViewportHeight = cam.viewportHeight * cam.zoom;
		cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, (float)(gridWidth*16-gridLines*1.5-16));
		cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, (float)(gridWidth*16-gridLines*1.5-16));
	}

	public int[] getMouseLocation(){ // Returns the coordinates of the mouse in the grid
		return new int[] {((Gdx.input.getX() / (gridWidth + gridLines))),
				(((Gdx.graphics.getHeight() - Gdx.input.getY()) / (gridHeight + gridLines)))};
	}

}


