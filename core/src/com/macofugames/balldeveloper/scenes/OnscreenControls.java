package com.macofugames.balldeveloper.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.macofugames.balldeveloper.actors.Player;
import com.macofugames.balldeveloper.screens.PlayScreenWithStage;
import com.macofugames.balldeveloper.util.Assets;
import com.macofugames.balldeveloper.util.Constants;
import com.macofugames.balldeveloper.util.Helpers;


public class OnscreenControls extends InputAdapter {

    public Viewport onscreenViewport;
    private Player player;
    private Vector2 moveLeftCenter;
    private Vector2 moveRightCenter;
    private Vector2 moveUpCenter;
    private Vector2 moveDownCenter;
    private int moveLeftPointer;
    private int moveRightPointer;
    private int moveUpPointer;
    private int moveDownPointer;
    private Vector2 pauseButtonCenter;
    private ImageButton pauseButton;
    private PlayScreenWithStage ps;

    private Vector2 touching = new Vector2(0,0);
    private Vector2 touching2 = new Vector2(0,0);


    public OnscreenControls(Player player,PlayScreenWithStage ps) {
        this.player = player;
        this.onscreenViewport = new ExtendViewport(Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE, Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE);
        this.ps = ps;
        moveDownCenter = new Vector2();
        moveLeftCenter = new Vector2();
        moveRightCenter = new Vector2();
        moveUpCenter = new Vector2();
        pauseButtonCenter = new Vector2();
        pauseButton = new ImageButton(new TextureRegionDrawable(Assets.instance.onScreenControlAssets.pauseButtonImage),
                new TextureRegionDrawable(Assets.instance.onScreenControlAssets.pauseButtonImage));
        pauseButton.setOrigin(pauseButton.getWidth()/2,pauseButton.getHeight()/2);


        recalculateButtonPositions();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 viewportPosition = onscreenViewport.unproject(new Vector2(screenX, screenY));
        touching = viewportPosition;
        if (viewportPosition.dst(moveUpCenter) < Constants.BUTTON_RADIUS) {
                player.upButtonPressed = true;
                moveUpPointer = pointer;
        }
        if (viewportPosition.dst(moveDownCenter) < Constants.BUTTON_RADIUS) {
                player.downButtonPressed = true;
                moveDownPointer = pointer;
        }
        if (viewportPosition.dst(moveLeftCenter) < Constants.BUTTON_RADIUS) {
                player.leftButtonPressed = true;
                moveLeftPointer = pointer;
        }
        if (viewportPosition.dst(moveRightCenter) < Constants.BUTTON_RADIUS) {
                player.rightButtonPressed = true;
                moveRightPointer = pointer;
        }
        if (viewportPosition.dst(pauseButtonCenter) < Constants.PAUSE_RADIUS) {
            ps.pause = !(ps.pause);

        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 viewportPosition = onscreenViewport.unproject(new Vector2(screenX, screenY));

        if (viewportPosition.dst(moveUpCenter) < Constants.BUTTON_RADIUS) {
            player.upButtonPressed = false;
            moveUpPointer = 0;
        }
        if (viewportPosition.dst(moveDownCenter) < Constants.BUTTON_RADIUS) {
            player.downButtonPressed = false;
            moveDownPointer = 0;
        }
        if (viewportPosition.dst(moveLeftCenter) < Constants.BUTTON_RADIUS) {
            player.leftButtonPressed = false;
            moveLeftPointer = 0;
        }
        if (viewportPosition.dst(moveRightCenter) < Constants.BUTTON_RADIUS) {
            player.rightButtonPressed = false;
            moveRightPointer = 0;
        }


        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 viewportPosition = onscreenViewport.unproject(new Vector2(screenX, screenY));
        touching2 = viewportPosition;
        Gdx.app.log("" , " " + pointer);
        if (pointer == moveLeftPointer && viewportPosition.dst(moveRightCenter) < Constants.BUTTON_RADIUS) {
            player.leftButtonPressed = false;
            moveLeftPointer = 0;
            moveRightPointer = pointer;
            player.rightButtonPressed = true;
        }

        if (pointer == moveRightPointer && viewportPosition.dst(moveLeftCenter) < Constants.BUTTON_RADIUS) {
            player.rightButtonPressed = false;
            moveRightPointer = 0;
            moveLeftPointer = pointer;
            player.leftButtonPressed = true;
        }

        if (pointer == moveUpPointer && viewportPosition.dst(moveDownCenter) < Constants.BUTTON_RADIUS) {
            player.upButtonPressed = false;
            moveUpPointer = 0;
            moveDownPointer = pointer;
            player.downButtonPressed = true;
        }

        if (pointer == moveDownPointer && viewportPosition.dst(moveUpCenter) < Constants.BUTTON_RADIUS) {
            player.downButtonPressed = false;
            moveDownPointer = 0;
            moveUpPointer = pointer;
            player.upButtonPressed = true;
        }

        if (pointer == moveUpPointer && viewportPosition.dst(moveUpCenter) > Constants.BUTTON_RADIUS) {
            player.upButtonPressed = false;
            moveUpPointer = 0;
        }
        if (pointer == moveDownPointer && viewportPosition.dst(moveDownCenter) > Constants.BUTTON_RADIUS) {
            player.downButtonPressed = false;
            moveDownPointer = 0;
        }
        if (pointer == moveLeftPointer && viewportPosition.dst(moveLeftCenter) > Constants.BUTTON_RADIUS) {
            player.leftButtonPressed = false;
            moveLeftPointer = 0;
        }
        if (pointer == moveRightPointer &&viewportPosition.dst(moveRightCenter) > Constants.BUTTON_RADIUS) {
            player.rightButtonPressed = false;
            moveRightPointer = 0;
        }

        return super.touchDragged(screenX, screenY, pointer);
    }

    public void render(SpriteBatch batch) {
     //   Gdx.app.log(""," " + player.leftButtonPressed + " " + " " + player.rightButtonPressed +" " +moveLeftPointer + "  " + moveLeftCenter + " " + moveRightCenter +
       //         " " + touching + " " + " "+ touching2+ " " + touching.dst(moveRightCenter) + " " + touching.dst(moveLeftCenter));
        onscreenViewport.apply();
        batch.setProjectionMatrix(onscreenViewport.getCamera().combined);
        batch.begin();

        if (!Gdx.input.isTouched(moveUpPointer)) {
            player.upButtonPressed = false;
            moveUpPointer = 0;
        }

        if (!Gdx.input.isTouched(moveLeftPointer)) {
            player.leftButtonPressed = false;
            moveLeftPointer = 0;
        }

        if (!Gdx.input.isTouched(moveRightPointer)) {
            player.rightButtonPressed = false;
            moveRightPointer = 0;
        }

        if (!Gdx.input.isTouched(moveDownPointer)) {
            player.downButtonPressed = false;
            moveDownPointer = 0;
        }

        Helpers.drawTextureRegion(
                batch,
                Assets.instance.onScreenControlAssets.moveLeft,
                moveLeftCenter,
                Constants.BUTTON_CENTER
        );

        Helpers.drawTextureRegion(
                batch,
                Assets.instance.onScreenControlAssets.moveRight,
                moveRightCenter,
                Constants.BUTTON_CENTER
        );

        Helpers.drawTextureRegion(
                batch,
                Assets.instance.onScreenControlAssets.moveUp,
                moveUpCenter,
                Constants.BUTTON_CENTER
        );

        Helpers.drawTextureRegion(
                batch,
                Assets.instance.onScreenControlAssets.moveDown,
                moveDownCenter,
                Constants.BUTTON_CENTER
        );

        Helpers.drawTextureRegion(
                batch,
                Assets.instance.onScreenControlAssets.pauseButtonImage,
                pauseButtonCenter,
                Constants.PAUSE_CENTER
        );

        batch.end();

    }


    public void recalculateButtonPositions() {

        moveLeftCenter.set(1.6f*Constants.BUTTON_RADIUS , 1.6f*Constants.BUTTON_RADIUS);
        moveRightCenter.set(1.6f*Constants.BUTTON_RADIUS * 9/4, 1.6f*Constants.BUTTON_RADIUS);

        moveDownCenter.set(
                onscreenViewport.getWorldWidth() - 1.6f*Constants.BUTTON_RADIUS,
                1.6f*Constants.BUTTON_RADIUS
        );

        moveUpCenter.set(
                onscreenViewport.getWorldWidth() - 1.6f*Constants.BUTTON_RADIUS,
                1.6f*Constants.BUTTON_RADIUS*9/4
        );

        pauseButtonCenter.set(onscreenViewport.getWorldWidth() - 0.75f * Constants.PAUSE_RADIUS,
                onscreenViewport.getWorldHeight() - 0.75f * Constants.PAUSE_RADIUS);

    }


}
