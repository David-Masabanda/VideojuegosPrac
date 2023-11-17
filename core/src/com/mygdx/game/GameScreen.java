package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.utils.viewport.FitViewport;


import entities.Obstaculo;
import entities.PlayerEntity;
import entities.SueloEntity;

public class GameScreen extends BaseScreen{
    private Stage stage;
    private World world;
    private PlayerEntity player;
    private Obstaculo obs;

    private SueloEntity suelo;

    public GameScreen(MyGdxGame game) {
        super(game);
        stage= new Stage(new FitViewport(640,360));
        world= new World(new Vector2(0,-10),true);

    }

    @Override
    public void show() {
        Texture playerTexture= game.getManager().get("dino100.png");
        player= new PlayerEntity(world,playerTexture,new Vector2(2,0.8f));
        stage.addActor(player);

        Texture obstaculo= game.getManager().get("roka.png");
        obs= new Obstaculo(world,obstaculo,new Vector2(5,0.7f));
        stage.addActor(obs);

        Texture sueloTexture= game.getManager().get("suelo.png");
        suelo= new SueloEntity(world,sueloTexture,new Vector2(0,0));
        stage.addActor(suelo);
    }

    @Override
    public void dispose() {

        stage.dispose();
        world.dispose();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        world.step(delta,6,2);
        stage.draw();

    }
}