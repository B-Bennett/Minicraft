package com.theironyard.minicraft;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Minicraft extends ApplicationAdapter {
    final int WIDTH = 100;
    final int HEIGHT = 100;
    FitViewport viewport;

    SpriteBatch batch;
    TextureRegion down, up, right, left;

    float x = 0;
    float y = 0;
    float xv = 0;
    float yv = 0;
    float time = 0;

    final float Max_VELOCITY = 300;
    final int DRAW_WIDTH = WIDTH ; // MAKING the image larger/bigger
    final int DRAW_HEIGHT = HEIGHT;


    @Override
    public void create () {
        batch = new SpriteBatch();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
;
        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        down = grid[6][0];
        up = grid[6][1];
        right = grid[6][3];
        left = new TextureRegion(right);
        left.flip(true, false);
    }
    void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            yv = Max_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            yv = Max_VELOCITY * -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = Max_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xv = Max_VELOCITY * -1;
        }

        float oldX = x; //
        float oldY = y;

        x += xv * Gdx.graphics.getDeltaTime();
        y += yv * Gdx.graphics.getDeltaTime();

        if (y < 0) { //where it does not go off screen up/down
            y = 0;
        }
        if (x < 0) {
            x = 0;
        }
        if (y > Gdx.graphics.getHeight() - HEIGHT) { //this will minus the height of player keep on screen
            y = oldY;
        }
        if (x > Gdx.graphics.getWidth() - WIDTH) {
            x = oldX;
        }

        xv *= .7; //this is acting as a damper slows the image down. reducing the fraction allows different slow down.
        yv *= .7;
    }

    @Override
    public void render () {
        move();
        draw();
    }

    void draw() {
        time += Gdx.graphics.getDeltaTime();
        TextureRegion img;
        if (Math.abs(yv) > Math.abs(xv)) { //getting the absolute value of yv
            if (yv > 0) {
                img = up;
            }
            else {
                img = down;
            }
        }//End of outer if
       else{
            if(xv > 0){
                img = right;
            }
            else{
                img = left;
            }
        }//Enf of outer else


        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, x, y, WIDTH, HEIGHT);
        batch.end();
    }
}
