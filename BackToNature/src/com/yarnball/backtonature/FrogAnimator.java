package com.yarnball.backtonature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class FrogAnimator {

	enum PhaseType {
		IDLE, GOING_TO_POS, TOUNGE_OUT, TOUNGE_IN, RETURN_TO_IDLE
	}

	private final FrogActor actorFrog;
	private final ToungeActor actorTounge;

	private Sound sndTounge1;
	private Sound sndTounge2;
	private Sound sndTounge3;

	private int waitCounter;
	private PhaseType phase;
	private int targetFrogPos;
	private int targetToungePos;

	public FrogAnimator(FrogActor actorFrog, ToungeActor actorTounge) {
		this.actorFrog = actorFrog;
		this.actorTounge = actorTounge;

		sndTounge1 = Gdx.audio.newSound(Gdx.files.internal("data/frog1.mp3"));
		sndTounge2 = Gdx.audio.newSound(Gdx.files.internal("data/frog2.mp3"));
		sndTounge3 = Gdx.audio.newSound(Gdx.files.internal("data/frog3.mp3"));
	}

	public void dispose() {
		sndTounge1.dispose();
		sndTounge2.dispose();
		sndTounge3.dispose();
	}

	public void reset() {
		phase = PhaseType.IDLE;
	}

	public void setTargetFrogPos(int targetFrogPos) {
		this.targetFrogPos = targetFrogPos;
	}

	public void setTargetToungePos(int targetToungePos) {
		this.targetToungePos = targetToungePos;
	}

	public void startAnim() {
		waitCounter = 3;
		phase = PhaseType.GOING_TO_POS;
	}

	public synchronized boolean isIdle() {
		return phase == PhaseType.IDLE;
	}

	public boolean isToungeOutPhase() {
		return phase == PhaseType.TOUNGE_OUT;
	}

	public PhaseType getPhase() {
		return phase;
	}

	public void setPhase(PhaseType phase) {
		this.phase = phase;

		switch (phase) {
		case TOUNGE_IN:
			actorTounge.setToungeEndIsUp(true);
			break;
		}
	}

	public synchronized void tick() {

		actorFrog.setBlink(false);

		if (phase == PhaseType.IDLE) {
			return;
		}

		if (waitCounter > 0) {
			waitCounter--;
			return;
		}

		if (phase == PhaseType.GOING_TO_POS) {
			int vertPos = actorFrog.getVerticalPos();
			if (targetFrogPos == vertPos) {
				actorTounge.setFrogVerticalPos(vertPos);
				actorTounge.setToungeEndIsUp(false);

				if (!EngineState.isMute()) {
					if (targetToungePos > 220 / 8) {
						sndTounge3.play();
					} else if (targetToungePos > 150 / 8) {
						sndTounge2.play();
					} else {
						sndTounge1.play();
					}
				}

				phase = PhaseType.TOUNGE_OUT;
			} else {
				if (targetFrogPos > vertPos) {
					vertPos++;
				} else {
					vertPos--;
				}

				actorFrog.setVerticalPos(vertPos);
				waitCounter = 3;
			}
		} else if (phase == PhaseType.TOUNGE_OUT) {
			int tpos = actorTounge.getToungePos();
			if (targetToungePos > tpos) {
				tpos++;
				actorTounge.setToungePos(tpos);
			} else {
				setPhase(PhaseType.TOUNGE_IN);
			}
		} else if (phase == PhaseType.TOUNGE_IN) {
			int tpos = actorTounge.getToungePos();
			if (0 < tpos) {
				actorTounge.setToungePos(tpos - 1);
			} else {
				phase = PhaseType.RETURN_TO_IDLE;
			}
		} else if (phase == PhaseType.RETURN_TO_IDLE) {
			int vertPos = actorFrog.getVerticalPos();
			if (1 == vertPos) {
				phase = PhaseType.IDLE;
			} else {
				actorFrog.setVerticalPos(vertPos - 1);
				waitCounter = 2;
			}
		}

	}

}
