package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.utils.viewport.FitViewport;


import java.util.ArrayList;
import java.util.List;

import entities.Constans;
import entities.Obstaculo;
import entities.PlayerEntity;
import entities.SueloEntity;

public class GameScreen extends BaseScreen{
    private Stage stage;
    private World world;
    private PlayerEntity player;
    private Obstaculo obs;

    private SueloEntity suelo;
    private Boolean alive;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    private boolean colisionDetectada=false;

    private List<SueloEntity> listaSuelos = new ArrayList<SueloEntity>();
    private List<Obstaculo> listaObstaculos = new ArrayList<Obstaculo>();
    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }


    public GameScreen(MyGdxGame game) {
        super(game);
        stage= new Stage(new FitViewport(640,360));
        world= new World(new Vector2(0,-10),true);

    }

    @Override
    public void show() {

        renderer= new Box2DDebugRenderer();
        camera= new OrthographicCamera(32,18);
        camera.translate(0,1);

        Texture playerTexture= game.getManager().get("dino100.png");
        player= new PlayerEntity(world,playerTexture,new Vector2(2,0.9f));
        stage.addActor(player);

        final Texture obstaculo= game.getManager().get("roka.png");
        listaObstaculos.add(new Obstaculo(world,obstaculo,new Vector2(10,0.8f)));
        listaObstaculos.add(new Obstaculo(world,obstaculo,new Vector2(25,0.8f)));
        listaObstaculos.add(new Obstaculo(world,obstaculo,new Vector2(50,0.8f)));
        listaObstaculos.add(new Obstaculo(world,obstaculo,new Vector2(75,0.8f)));
        listaObstaculos.add(new Obstaculo(world,obstaculo,new Vector2(100,0.8f)));
        //obs= new Obstaculo(world,obstaculo,new Vector2(5,0.7f));
        for (Obstaculo obs : listaObstaculos){
            stage.addActor(obs);
        }


        Texture sueloTexture= game.getManager().get("suelo.png");
        listaSuelos.add(new SueloEntity(world,sueloTexture,new Vector2(0,0)));
        listaSuelos.add(new SueloEntity(world,sueloTexture,new Vector2(10,0)));
        listaSuelos.add(new SueloEntity(world,sueloTexture,new Vector2(20,0)));
        listaSuelos.add(new SueloEntity(world,sueloTexture,new Vector2(30,0)));
        listaSuelos.add(new SueloEntity(world,sueloTexture,new Vector2(40,0)));


        //suelo= new SueloEntity(world,sueloTexture,new Vector2(0,0));
        for (SueloEntity suelo : listaSuelos){
            stage.addActor(suelo);
        }

        world.setContactListener(new ContactListener() {
            private boolean colision(Contact contact, Object UserA, Object UserB){

                return (contact.getFixtureA().getUserData().equals(UserA))&&
                        contact.getFixtureB().getUserData().equals(UserB) ||
                        (contact.getFixtureA().getUserData().equals(UserB)&&
                                contact.getFixtureB().getUserData().equals(UserA));


            }
            @Override
            public void beginContact(Contact contact) {
                if (colision(contact,"player","obstaculo")){
                    colisionDetectada=true;
                }
                if (colision(contact,"obstaculo","player")){
                    colisionDetectada=true;
                    player.setAlive(false);
                }

                if (colision(contact,"player","suelo")){
                    colisionDetectada=false;
                    player.setAlive(true);
                }
                if (colision(contact,"suelo","player")){
                    colisionDetectada=false;
                    player.setAlive(true);
                }

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    @Override
    public void dispose() {

        stage.dispose();
        world.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (player.getX()>100 && player.getAlive()){
            stage.getCamera().translate(4f*delta* Constans.pixelInMiddle,0,0);
        }

        if (Gdx.input.justTouched() && colisionDetectada==false){
            saltar();
        }

        if (colisionDetectada==true){
            player.getBody().setLinearVelocity(0,0);
        }

        stage.act();
        world.step(delta,6,2);
        stage.draw();

    }

    private void saltar(){
        Vector2 position=player.getBody().getPosition();
        player.getBody().applyLinearImpulse(0,5, position.x, position.y, true);
        //player.getBody().applyAngularImpulse(20.5f,true);
    }

    @Override
    public void hide() {
        player.liberar();
        player.remove();

        for (Obstaculo obstaculo : listaObstaculos){
            obstaculo.liberar();
            obstaculo.remove();
        }

        for (SueloEntity suelo : listaSuelos){
            suelo.liberar();
            suelo.remove();
        }
    }
}