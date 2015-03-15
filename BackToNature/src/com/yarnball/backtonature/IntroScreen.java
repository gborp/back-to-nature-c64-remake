package com.yarnball.backtonature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.yarnball.backtonature.EngineState.GameState;

public class IntroScreen implements Screen, InputProcessor {

	private Stage stage;
	private IntroBackgroundActor actorIntroBackground;
	private MuteActor actorMute;
	private IntroTextActor actorIntroText;

	public IntroScreen() {

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(320, 200, true);
		stage.getCamera().position.set(320 / 2, 200 / 2, 0);
	}

	@Override
	public void show() {
		EngineState.setGameState(GameState.INTRO);
		stage = new Stage(320, 200, true);

		actorIntroBackground = new IntroBackgroundActor();
		actorMute = new MuteActor();
		actorIntroText = new IntroTextActor();

		stage.addActor(actorIntroBackground);
		stage.addActor(actorIntroText);
		stage.addActor(actorMute);

		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
