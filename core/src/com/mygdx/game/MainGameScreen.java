package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import actores.ActorJugador;
import actores.ActorRoca;

public class MainGameScreen extends BaseScreen{

    private Texture texturaJugador;
    private Texture texturaRoca;
    private Stage stage;
    private ActorJugador jugador;
    private ActorRoca roca;

    public MainGameScreen(MyGdxGame game) {
        super(game);
        texturaJugador=new Texture("dino100.png");
        texturaRoca=new Texture("roka.png");
    }

    @Override
    public void show() {
        this.stage=new Stage();
        this.stage.setDebugAll(true);
        this.jugador=new ActorJugador(texturaJugador);
        this.roca=new ActorRoca(texturaRoca);
        this.stage.addActor(jugador);
        this.stage.addActor(roca);

        this.jugador.setPosition(20,100);
        this.roca.setPosition(400,100);
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9f,0,0.9f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        colisiones();
    }

    @Override
    public void dispose() {
        super.dispose();
        texturaJugador.dispose();
        texturaRoca.dispose();
    }

    private void colisiones(){
        if (jugador.isAlive() && jugador.getX()+jugador.getWidth()>roca.getX()){
            System.out.println("Colision detectada");

            this.jugador.setAlive(false);
            this.roca.setAlive(false);
        }

    }

}
