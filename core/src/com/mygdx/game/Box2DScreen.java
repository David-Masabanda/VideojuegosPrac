package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DScreen extends BaseScreen {

    private World world;

    private Box2DDebugRenderer renderer;

    private OrthographicCamera camera;

    private Body body, sueloBody, rocaBody;

    private Fixture fixture, sueloFixture, rocaFixture;

    private boolean colisionDetectada=false;

    public Box2DScreen(MyGdxGame game) {
        super(game);
    }


    private void saltar(){
        Vector2 posicion = body.getPosition();
        body.applyLinearImpulse(5,10, posicion.x , posicion.y, true);
        //body.applyAngularImpulse(20,true);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float velocidadY= body.getLinearVelocity().y;
        body.setLinearVelocity(10,velocidadY);

        if (Gdx.input.isTouched() && colisionDetectada==false){
            saltar();
        }
        if (colisionDetectada==true){
            body.setLinearVelocity(0,0);
//            colisionDetectada=false;
        }



        world.step(delta, 6, 2);
        camera.update();
        renderer.render(world, camera.combined);

    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -10), true);
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera( 32, 18);
        camera.translate(0,1);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Fixture fixtureA=contact.getFixtureA();
                Fixture fixtureB=contact.getFixtureB();
                if (fixtureA==fixture && fixtureB==rocaFixture){
                    colisionDetectada=true;
                }
                if (fixtureB==fixture && fixtureA==rocaFixture){
                    colisionDetectada=true;
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


        BodyDef bodyDef = createBodyDef();
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1, 1);
        fixture = body.createFixture(shape, 1);

        BodyDef sueBody = createBodyDef2();
        sueloBody = world.createBody(sueBody);
        PolygonShape shape2 = new PolygonShape();
        shape2.setAsBox(500, 1);
        sueloFixture = sueloBody.createFixture(shape2, 1);

        BodyDef rocBody = createBodyDef3();
        rocaBody = world.createBody(rocBody);
        Vector2 [] vertices = new Vector2[3];
        vertices[0] = new Vector2(-0.5f, -0.5f);
        vertices[1] = new Vector2(0.5f, -0.5f);
        vertices[2] = new Vector2(0, 0.5f);
        PolygonShape shape3 = new PolygonShape();
        shape3.set(vertices);
        rocaFixture = rocaBody.createFixture(shape3, 1);
    }

    private BodyDef createBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(-10, -8.5f);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        return bodyDef;
    }

    private BodyDef createBodyDef2() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0, -10f);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        return bodyDef;
    }

    private BodyDef createBodyDef3() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(10, -8.5f);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        return bodyDef;
    }

    @Override
    public void dispose() {
        body.destroyFixture(fixture);
        sueloBody.destroyFixture(sueloFixture);
        world.destroyBody(body);
        world.dispose();
        renderer.dispose();
    }
}