package com.yarnball.backtonature;

public class EngineState {

	public enum GameState {
		INTRO, GAME, END_GAME
	}

	private static boolean mute;
	private static GameState gameState;

	public static boolean isMute() {
		return mute;
	}

	public static void setMute(boolean mute) {
		EngineState.mute = mute;
	}

	public static GameState getGameState() {
		return gameState;
	}

	public static void setGameState(GameState gameState) {
		EngineState.gameState = gameState;
	}

}
