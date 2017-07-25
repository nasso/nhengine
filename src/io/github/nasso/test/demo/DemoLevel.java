package io.github.nasso.test.demo;

import java.util.LinkedList;
import java.util.Queue;

import io.github.nasso.nhengine.component.TiledSpriteComponent;
import io.github.nasso.nhengine.core.Game;
import io.github.nasso.nhengine.core.GameWindow;
import io.github.nasso.nhengine.core.Nhengine;
import io.github.nasso.nhengine.level.Level;
import io.github.nasso.nhengine.level.Node;

public class DemoLevel extends Level {
	private static final int ORI_RIGHT = 2;
	private static final int ORI_LEFT = 1;
	private static final int ORI_UP = 3;
	private static final int ORI_DOWN = 0;
	
	private DemoWorldScene worldScene = new DemoWorldScene();
	private DemoGameDialogScene gameHudScene = new DemoGameDialogScene();
	private DemoUIScene uiScene = new DemoUIScene();
	private DemoTestDialog sansDial;
	
	private float cameraSpeed = 4.0f / 1000.0f;
	
	private float playerSpeed = 3f / 1000.0f;
	private float playerAnimPos = 0.0f;
	private float playerAnimSpeed = 2.0f;
	
	private float playerPrecisePosX = 0, playerPrecisePosY = 0;
	private float pixelSize = 1f / 16f;
	private int playerOrientation = ORI_DOWN;
	private Queue<Integer> orientationCommands = new LinkedList<Integer>();
	
	private Node player;
	private TiledSpriteComponent characterTilesComp;
	
	public DemoLevel() {
		this.addOverlayScene(this.worldScene);
		this.addOverlayScene(this.gameHudScene);
		this.addOverlayScene(this.uiScene);
		
		this.player = this.worldScene.getPlayer();
		this.characterTilesComp = this.worldScene.getPlayerTiledSprite();
		
		this.sansDial = new DemoTestDialog();
	}
	
	public void update(float delta) {
		GameWindow win = Game.instance().window();
		
		if(win.isKeyPressed(DemoMain.GAME_KEY_LEFT)) {
			this.playerAnimPos = 0;
			if(!this.orientationCommands.contains(ORI_LEFT)) this.orientationCommands.add(ORI_LEFT);
		} else if(win.isKeyPressed(DemoMain.GAME_KEY_RIGHT)) {
			this.playerAnimPos = 0;
			if(!this.orientationCommands.contains(ORI_RIGHT)) this.orientationCommands.add(ORI_RIGHT);
		} else if(win.isKeyPressed(DemoMain.GAME_KEY_UP)) {
			this.playerAnimPos = 0;
			if(!this.orientationCommands.contains(ORI_UP)) this.orientationCommands.add(ORI_UP);
		} else if(win.isKeyPressed(DemoMain.GAME_KEY_DOWN)) {
			this.playerAnimPos = 0;
			if(!this.orientationCommands.contains(ORI_DOWN)) this.orientationCommands.add(ORI_DOWN);
		}
		
		if(win.isKeyReleased(DemoMain.GAME_KEY_LEFT)) {
			this.characterTilesComp.setActiveCell(0, this.playerOrientation);
			if(this.orientationCommands.contains(ORI_LEFT)) this.orientationCommands.remove(ORI_LEFT);
		} else if(win.isKeyReleased(DemoMain.GAME_KEY_RIGHT)) {
			this.characterTilesComp.setActiveCell(0, this.playerOrientation);
			if(this.orientationCommands.contains(ORI_RIGHT)) this.orientationCommands.remove(ORI_RIGHT);
		} else if(win.isKeyReleased(DemoMain.GAME_KEY_UP)) {
			this.characterTilesComp.setActiveCell(0, this.playerOrientation);
			if(this.orientationCommands.contains(ORI_UP)) this.orientationCommands.remove(ORI_UP);
		} else if(win.isKeyReleased(DemoMain.GAME_KEY_DOWN)) {
			this.characterTilesComp.setActiveCell(0, this.playerOrientation);
			if(this.orientationCommands.contains(ORI_DOWN)) this.orientationCommands.remove(ORI_DOWN);
		}
		
		if(!this.orientationCommands.isEmpty()) {
			this.playerOrientation = this.orientationCommands.peek();
		}
		
		int animFrame = (int) (1.0f / this.playerSpeed + this.playerAnimPos) % 4;
		if(animFrame == 4) animFrame = 0;
		if(win.isKeyDown(DemoMain.GAME_KEY_LEFT)) {
			this.playerPrecisePosX -= this.playerSpeed * delta;
			this.characterTilesComp.setActiveCell(animFrame, this.playerOrientation);
		} else if(win.isKeyDown(DemoMain.GAME_KEY_RIGHT)) {
			this.playerPrecisePosX += this.playerSpeed * delta;
			this.characterTilesComp.setActiveCell(animFrame, this.playerOrientation);
		}
		
		if(win.isKeyDown(DemoMain.GAME_KEY_UP)) {
			this.playerPrecisePosY -= this.playerSpeed * delta;
			this.characterTilesComp.setActiveCell(animFrame, this.playerOrientation);
		} else if(win.isKeyDown(DemoMain.GAME_KEY_DOWN)) {
			this.playerPrecisePosY += this.playerSpeed * delta;
			this.characterTilesComp.setActiveCell(animFrame, this.playerOrientation);
		}
		
		this.player.setPosition((float) (Math.floor(this.playerPrecisePosX / this.pixelSize)) * this.pixelSize, (float) (Math.floor(this.playerPrecisePosY / this.pixelSize)) * this.pixelSize);
		
		this.playerAnimPos += this.playerSpeed * this.playerAnimSpeed * delta;
		
		if(win.isKeyDown(Nhengine.KEY_D)) {
			this.worldScene.getCamera().translateX(this.cameraSpeed * delta);
		}
		
		if(win.isKeyDown(Nhengine.KEY_A)) {
			this.worldScene.getCamera().translateX(-this.cameraSpeed * delta);
		}
		
		if(win.isKeyDown(Nhengine.KEY_W)) {
			this.worldScene.getCamera().translateY(-this.cameraSpeed * delta);
		}
		
		if(win.isKeyDown(Nhengine.KEY_S)) {
			this.worldScene.getCamera().translateY(this.cameraSpeed * delta);
		}
		
		if(win.isKeyPressed(Nhengine.KEY_F5)) {
			this.gameHudScene.startDialog(this.sansDial.getPage());
		}
	}
	
	public DemoWorldScene getWorldScene() {
		return this.worldScene;
	}
	
	public void dispose() {
		super.dispose();
		
		this.worldScene.dispose();
	}
}
