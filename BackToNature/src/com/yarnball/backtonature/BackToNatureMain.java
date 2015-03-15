package com.yarnball.backtonature;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.yarnball.backtonature.EngineState.GameState;
import com.yarnball.backtonature.FrogAnimator.PhaseType;

public class BackToNatureMain implements ApplicationListener {

	private static final String PREF_MUTE = "mute";
	private static final String PREFS_NAME = "backtonaturetribute";

	private static final int BUGS_TO_WIN = 64;
	private static final int BUGS_TO_END4 = 48;
	private static final int BUGS_TO_END3 = 32;
	private static final int BUGS_TO_END2 = 16;

	private static final int MAX_FLY_COUNT = 5;

	private static final int FLY_COUNT_AT_GAME_START_EASY = 5;
	private static final int FLY_COUNT_AT_GAME_START_NORMAL = 3;
	private static final int FLY_COUNT_AT_GAME_START_HARD = 1;

	private static final float DEFAULT_FLY_SPEED = 10;
	private static final float NEXT_FLY_FASTER_BY = 0.2f;
	private static final float MAX_FLY_SPEED = 1f;

	private static final float MAX_ENERGY = 100f;
	private static final float ENERGY_FOR_FLY = 15f;
	private static final float ENERGY_FOR_STANDUP = 5f;
	private static final float ENERGY_FOR_IDLE = 1f / 50;
	private static final float ENERGY_FOR_TOUNGE = 5f / 40;
	private static final float ENERGY_USAGE_INCREASE_PER_FLY = 001f / 100f;
	private static final float DEFAULT_ENERGY_USAGE_MULTIPLYER = 0.9f;
	private static final float LOW_ENERGY_LEVEL = MAX_ENERGY * 0.3f;
	private static final Color BORDER_COLOR = new Color(132f / 255f,
			197f / 255f, 204f / 255f, 1);

	private static final int END_OF_GAME_TOUCH_FREEZE_LENGTH = 25 * 3;
	private static final int INTRO_TOUCH_FREEZE_LENGTH = 25 * 1;
	private static final int GAME_TOUCH_FREEZE_LENGTH = 25 * 1;
	private static final int MUTE_TOGGLE_RATE = 25;

	private Stage stage;
	private BackgroundActor actorBackground;
	private FrogActor actorFrog;
	private ToungeActor actorTounge;
	private List<FlyActor> lstActorFly;
	private int tickCount;
	private FrogAnimator frogAnimator;
	private MuteActor actorMute;
	private IntroBackgroundActor actorIntroBackground;
	private FlyCounterActor actorFlyCounter;
	private EnergyActor actorEnergy;
	private IntroTextActor actorIntroText;
	private EndTextActor actorEndText;
	private GameOverTextActor actorGameOVer;
	private DifficultyLevelTextActor actorDifficultyLevel;

	private float nextFlySpeed;
	private int bugs;
	private float energy;
	private float energyUsageMultiplyer;
	private DifficultyLevel difficultyLevel;

	private boolean fingerDown;
	private int actualFingerPositionX;
	private int actualFingerPositionY;

	private int nextFrogVerticalPos;
	private int nextToungePos;
	private float tickDelta;
	private int frozeInputForTick;
	private int lastMuteAt = -1;

	protected Preferences getPrefs() {
		return Gdx.app.getPreferences(PREFS_NAME);
	}

	public boolean isMute() {
		return getPrefs().getBoolean(PREF_MUTE, false);
	}

	public void setMute(boolean mute) {
		EngineState.setMute(mute);
		actorMute.setMute(mute);
		getPrefs().putBoolean(PREF_MUTE, mute);
		getPrefs().flush();
	}

	@Override
	public void create() {
		stage = new Stage(320, 200, true);

		actorIntroBackground = new IntroBackgroundActor();
		actorBackground = new BackgroundActor();
		actorFrog = new FrogActor();
		actorTounge = new ToungeActor();
		actorMute = new MuteActor();
		actorFlyCounter = new FlyCounterActor();
		actorEnergy = new EnergyActor();
		actorDifficultyLevel = new DifficultyLevelTextActor();
		actorIntroText = new IntroTextActor();
		actorEndText = new EndTextActor();
		actorGameOVer = new GameOverTextActor();

		stage.addActor(actorIntroBackground);
		stage.addActor(actorIntroText);

		stage.addActor(actorBackground);
		stage.addActor(actorTounge);
		stage.addActor(actorMute);
		stage.addActor(actorFlyCounter);
		stage.addActor(actorEnergy);
		stage.addActor(actorDifficultyLevel);

		stage.addActor(actorEndText);
		stage.addActor(actorGameOVer);

		EngineState.setMute(isMute());
		actorMute.setMute(isMute());

		lstActorFly = new ArrayList<FlyActor>();

		for (int i = 0; i < MAX_FLY_COUNT; i++) {
			FlyActor fly = new FlyActor();
			fly.setSpeed((float) (1 + Math.random() * 3));
			stage.addActor(fly);
			lstActorFly.add(fly);
		}

		stage.addActor(actorFrog);

		frogAnimator = new FrogAnimator(actorFrog, actorTounge);
		frogAnimator.reset();

		switchGameState(GameState.INTRO);

		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(new InputProc());

		tickCount = 0;
	}

	private void switchGameState(GameState gameState) {
		if (EngineState.getGameState() == gameState) {
			return;
		}
		fingerDown = false;
		nextFrogVerticalPos = -1;
		nextToungePos = -1;
		if (gameState == GameState.INTRO) {
			frozeInputForTick = INTRO_TOUCH_FREEZE_LENGTH;
			frogAnimator.setPhase(PhaseType.IDLE);
			actorBackground.setVisible(false);
			actorIntroBackground.setVisible(true);
			actorIntroText.setVisible(true);
			actorFrog.setVisible(false);
			actorTounge.setVisible(false);
			actorFlyCounter.setVisible(false);
			actorEnergy.setVisible(false);
			actorGameOVer.setVisible(false);
			actorEndText.setVisible(false);
			actorDifficultyLevel.setVisible(false);
			for (FlyActor fly : lstActorFly) {
				fly.setVisible(false);
			}
		} else if (gameState == GameState.GAME) {
			frozeInputForTick = GAME_TOUCH_FREEZE_LENGTH;
			actorBackground.setVisible(true);
			actorIntroBackground.setVisible(false);
			actorIntroText.setVisible(false);
			actorFrog.setVisible(true);
			actorFrog.setVerticalPos(3);
			actorTounge.setVisible(true);
			actorTounge.setToungePos(0);
			actorFlyCounter.setVisible(true);
			actorEnergy.setVisible(true);
			actorDifficultyLevel.setVisible(true);
			actorDifficultyLevel.setText(difficultyLevel);
			energyUsageMultiplyer = DEFAULT_ENERGY_USAGE_MULTIPLYER;
			setEnergy(MAX_ENERGY);
			frogAnimator.setPhase(PhaseType.RETURN_TO_IDLE);

			bugs = 0;
			actorFlyCounter.setNumber(bugs);
			nextFlySpeed = DEFAULT_FLY_SPEED;

			int flyCountAtGameStart = 5;
			switch (difficultyLevel) {
			case EASY:
				flyCountAtGameStart = FLY_COUNT_AT_GAME_START_EASY;
				break;
			case NORMAL:
				flyCountAtGameStart = FLY_COUNT_AT_GAME_START_NORMAL;
				break;
			case HARD:
				flyCountAtGameStart = FLY_COUNT_AT_GAME_START_HARD;
				break;
			}

			for (int i = 0; i < flyCountAtGameStart; i++) {
				bornFly();
			}

		} else if (gameState == GameState.END_GAME) {
			frozeInputForTick = END_OF_GAME_TOUCH_FREEZE_LENGTH;

			if (bugs == BUGS_TO_WIN) {
				actorEndText.setEndText(5);
			} else if (bugs > BUGS_TO_END4) {
				actorEndText.setEndText(4);
			} else if (bugs > BUGS_TO_END3) {
				actorEndText.setEndText(3);
			} else if (bugs > BUGS_TO_END2) {
				actorEndText.setEndText(2);
			} else {
				actorEndText.setEndText(1);
			}

			actorEnergy.setVisible(false);
			actorGameOVer.setVisible(true);
			actorEndText.setVisible(true);
			for (FlyActor fly : lstActorFly) {
				fly.setVisible(false);
			}
		}
		EngineState.setGameState(gameState);
	}

	private void changeEnergy(float change) {
		if (change < 0) {
			change *= energyUsageMultiplyer;
		}
		float newEnergy = energy + change;
		if (newEnergy < 0) {
			newEnergy = 0;
		}
		if (newEnergy > MAX_ENERGY) {
			newEnergy = MAX_ENERGY;
		}
		setEnergy(newEnergy);
	}

	private void setEnergy(float energy) {
		this.energy = energy;
		actorEnergy.setEnergy(energy / MAX_ENERGY);
		if (energy >= LOW_ENERGY_LEVEL) {
			actorEnergy.setBlink(false);
		}
		if (energy < MAX_ENERGY / 200) {
			switchGameState(GameState.END_GAME);
		}
	}

	private void bornFly() {
		for (FlyActor fly : lstActorFly) {
			if (!fly.isVisible()) {
				fly.born();
				fly.setSpeed(nextFlySpeed);
				break;
			}
		}
	}

	private void tick() {
		if (frozeInputForTick > 0) {
			frozeInputForTick--;
		}
		tickCount++;

		if (EngineState.getGameState() == GameState.INTRO) {
			actorIntroText.tick();
		} else if (EngineState.getGameState() == GameState.GAME
				|| EngineState.getGameState() == GameState.END_GAME) {

			if (EngineState.getGameState() == GameState.GAME) {
				changeEnergy(-ENERGY_FOR_IDLE);

				if (energy < LOW_ENERGY_LEVEL) {
					actorEnergy.setBlink((tickCount / 10) % 2 == 0);
				}

				PhaseType animPhase = frogAnimator.getPhase();
				if (fingerDown
						&& (animPhase == PhaseType.IDLE || animPhase == PhaseType.RETURN_TO_IDLE)) {

					if (actualFingerPositionX > 87
							&& actualFingerPositionY < 320) {
						int toungeTargetX = ((actualFingerPositionX - 88) / 8) + 2;
						nextToungePos = toungeTargetX;
						if (actualFingerPositionY < 95) {
							nextFrogVerticalPos = 1;
						} else if (actualFingerPositionY < 140) {
							nextFrogVerticalPos = 2;
						} else {
							nextFrogVerticalPos = 3;
						}
					}
				}
			}

			if (EngineState.getGameState() == GameState.END_GAME
					&& tickCount % (25 * 15) == 0) {
				nextFrogVerticalPos = 3;
				nextToungePos = 27;
			}

			if (frogAnimator.getPhase() == PhaseType.IDLE) {
				if (nextFrogVerticalPos != -1 && nextToungePos != -1) {

					changeEnergy((nextFrogVerticalPos > 1 ? -ENERGY_FOR_STANDUP
							: 0) - ENERGY_FOR_TOUNGE * nextToungePos);

					frogAnimator.setTargetFrogPos(nextFrogVerticalPos);
					frogAnimator.setTargetToungePos(nextToungePos);
					frogAnimator.startAnim();
					nextFrogVerticalPos = -1;
					nextToungePos = -1;
				}
			}

			frogAnimator.tick();
			for (FlyActor fly : lstActorFly) {
				if (fly.isVisible()) {
					fly.tick();
				}
			}

			if (frogAnimator.getPhase() == PhaseType.TOUNGE_OUT) {

				int tpos = actorTounge.getToungePos();

				int tx1 = (int) (actorTounge.getX() + tpos * 8);
				int ty1 = (int) actorTounge.getY();
				int tx2 = tx1 + 8;
				int ty2 = ty1 + 8;

				for (FlyActor fly : lstActorFly) {
					if (fly.isVisible() && !fly.isHit()) {

						int fx1 = (int) fly.getX();
						int fy1 = (int) fly.getY();
						int fx2 = fx1 + 8;
						int fy2 = fy1 + 8;

						if (((fx1 >= tx1 && fx1 <= tx2) || (fx2 >= tx1 && fx2 <= tx2))
								&& ((fy1 >= ty1 && fy1 <= ty2) || (fy2 >= ty1 && fy2 <= ty2))) {
							fly.setHit(true);
							frogAnimator.setPhase(PhaseType.TOUNGE_IN);
							if (nextFlySpeed > MAX_FLY_SPEED) {
								nextFlySpeed -= NEXT_FLY_FASTER_BY;
							}
							actorFrog.setBlink(true);
							bugs++;
							actorFlyCounter.setNumber(bugs);
							energyUsageMultiplyer += ENERGY_USAGE_INCREASE_PER_FLY;
							changeEnergy(ENERGY_FOR_FLY);
							if (bugs == BUGS_TO_WIN) {
								switchGameState(GameState.END_GAME);
							}
							break;
						}
					}
				}
			} else if (frogAnimator.getPhase() == PhaseType.TOUNGE_IN) {
				for (FlyActor fly : lstActorFly) {
					if (fly.isVisible() && fly.isHit()) {
						int tpos = actorTounge.getToungePos();
						int tx1 = (int) (actorTounge.getX() + tpos * 8);
						int ty1 = (int) actorTounge.getY();
						fly.setPosition(tx1, ty1);
					}
				}
			} else if (frogAnimator.getPhase() == PhaseType.RETURN_TO_IDLE) {
				for (FlyActor fly : lstActorFly) {
					if (fly.isVisible() && fly.isHit()) {
						fly.setVisible(false);
						bornFly();
					}
				}
			}
		}
	}

	@Override
	public void dispose() {
		stage.dispose();
		frogAnimator.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(BORDER_COLOR.r, BORDER_COLOR.g, BORDER_COLOR.b,
				BORDER_COLOR.a);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		float delta = Gdx.graphics.getDeltaTime();
		stage.act(delta);
		stage.draw();

		tickDelta += delta * 25;

		while (tickDelta > 1) {
			tick();
			tickDelta--;
		}

	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(320, 200, true);
		stage.getCamera().position.set(320 / 2, 200 / 2, 0);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		fingerDown = false;
		tickDelta = 0;
	}

	private boolean handleTouch(int screenX, int screenY) {

		if (frozeInputForTick > 0) {
			return false;
		}

		if (EngineState.getGameState() == GameState.END_GAME) {
			switchGameState(GameState.INTRO);
			return true;
		}

		Vector2 stagePos = stage.screenToStageCoordinates(new Vector2(screenX,
				screenY));

		if (stagePos.x > 320 - 48 && stagePos.y > 200 - 48) {
			if (lastMuteAt == -1 || (tickCount - lastMuteAt) > MUTE_TOGGLE_RATE) {
				lastMuteAt = tickCount;
				setMute(!EngineState.isMute());
			}
			return true;
		}

		if (EngineState.getGameState() == GameState.INTRO) {
			if (stagePos.y < 200 - 76) {
				if (stagePos.x < 99) {
					difficultyLevel = DifficultyLevel.EASY;
				} else if (stagePos.x < 223) {
					difficultyLevel = DifficultyLevel.NORMAL;
				} else {
					difficultyLevel = DifficultyLevel.HARD;
				}
				switchGameState(GameState.GAME);
			}
			return true;
		}

		actualFingerPositionX = (int) stagePos.x;
		actualFingerPositionY = (int) stagePos.y;

		return false;
	}

	private class InputProc implements InputProcessor {

		public boolean keyDown(int keycode) {
			return false;
		}

		public boolean keyUp(int keycode) {
			if (keycode == Keys.BACK) {
				if (EngineState.getGameState() == GameState.INTRO) {
					Gdx.app.exit();
				} else {
					if (EngineState.getGameState() == GameState.GAME) {
						switchGameState(GameState.INTRO);
					}
				}
				return true;
			}
			return false;
		}

		public boolean keyTyped(char character) {
			return false;
		}

		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			fingerDown = true;
			return handleTouch(screenX, screenY);
		}

		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			fingerDown = false;
			return false;
		}

		public boolean touchDragged(int screenX, int screenY, int pointer) {
			return handleTouch(screenX, screenY);
		}

		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		public boolean scrolled(int amount) {
			return false;
		}

	}
}
