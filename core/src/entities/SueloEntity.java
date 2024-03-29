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

public class SueloEntity extends Actor {
    private Texture texture;

    private World world;
    private Body body;
    private Fixture fixture;




    public SueloEntity(World world, Texture texture, Vector2 position) {
        this.world=world;
        this.texture=texture;

        BodyDef def= new BodyDef();
        def.position.set(position);
        def.type=BodyDef.BodyType.StaticBody;

        body= world.createBody(def);

        PolygonShape polygonShape= new PolygonShape();
        polygonShape.setAsBox(30,1);
        fixture= body.createFixture(polygonShape,1);
        fixture.setUserData("suelo");
        polygonShape.dispose();
        setSize(Constans.pixelInMiddle,Constans.pixelInMiddle);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x)*Constans.pixelInMiddle,body.getPosition().y+0.5f*Constans.pixelInMiddle);
        batch.draw(texture,getX(),getY(),getWidth()*10,getHeight());
    }

    public void liberar(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
