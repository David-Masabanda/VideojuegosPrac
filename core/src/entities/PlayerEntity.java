package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlayerEntity extends Actor {

    private Texture texture;

    private World world;
    private Body body;
    private Fixture fixture;
    private Boolean alive=true;


    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }


    public PlayerEntity(World world, Texture texture, Vector2 position) {
        this.world=world;
        this.texture=texture;

        BodyDef def= new BodyDef();
        def.position.set(position);
        def.type=BodyDef.BodyType.DynamicBody;

        body= world.createBody(def);

        PolygonShape polygonShape= new PolygonShape();
        polygonShape.setAsBox(0.4f,0.4f);
        fixture= body.createFixture(polygonShape,1);
        fixture.setUserData("player");
        polygonShape.dispose();
        setSize(Constans.pixelInMiddle,Constans.pixelInMiddle);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float velocidadY= body.getLinearVelocity().y;
        body.setLinearVelocity(4,velocidadY);

        setPosition((body.getPosition().x-1)*Constans.pixelInMiddle,body.getPosition().y*Constans.pixelInMiddle);
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());
    }

    public void liberar(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
