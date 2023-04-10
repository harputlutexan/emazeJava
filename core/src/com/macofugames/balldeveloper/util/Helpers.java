package com.macofugames.balldeveloper.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.macofugames.balldeveloper.actors.Arcs;

import space.earlygrey.shapedrawer.ShapeDrawer;


public class Helpers {


    public static void drawTextureRegion(SpriteBatch batch, TextureRegion region, Vector2 position) {
        drawTextureRegion(batch, region, position.x, position.y);
    }

    public static void drawTextureRegion(SpriteBatch batch, TextureRegion region, Vector2 position, Vector2 offset) {
        drawTextureRegion(batch, region, position.x - offset.x, position.y - offset.y);
    }

    public static void drawTextureRegion(SpriteBatch batch, TextureRegion region, float x, float y) {
        batch.draw(
                region.getTexture(),
                x,
                y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }

    public static void drawLine(SpriteBatch batch, TextureRegion rect, float x1, float y1, float x2, float y2, int thickness,Color color) {
        ShapeDrawer drawer1 = new ShapeDrawer(batch, Assets.instance.lineAssets.rectPixmap);

        //batch.begin();
        batch.setColor(color);
        drawer1.line(x1, y1, x2, y2,color,thickness);
        batch.setColor(Color.WHITE);
        //batch.end();
    }

    public static void drawLine2(SpriteBatch batch, TextureRegion rect, float x1, float y1,Color color) {

        batch.begin();
        batch.setColor(color);
        batch.draw(rect,x1-2.5f,y1-2.5f,5,5);
        batch.setColor(Color.WHITE);
        batch.end();
    }

    // Beginning of ARC drawing Helpers

    public static Arcs calculateArc(Vector2 a, Vector2 b, double curveDegree){

        Arcs arcs = new Arcs();
        float radius;
        float centerX;
        float centerY;
        float start;
        float degree;

        if (curveDegree < 0) {
            curveDegree = - curveDegree;
            Vector2 c = a;
            a = b;
            b = c;
        }

        Vector2 c = new Vector2(b.x-a.x,b.y - a.y);
        Vector2 d = new Vector2(a.x+c.x/2,a.y+c.y/2);

        float nc = norm(c);

        if(curveDegree == 180)
        {
            arcs.radius = nc/2;
            arcs.centerX = d.x;
            arcs.centerY = d.y;
            arcs.start = (float) Math.toDegrees(angle_to(d,b));
            arcs.endDegree = (float) Math.toDegrees(angle_to(d,a));
            arcs.degree = (float) curveDegree;
            return arcs;
        }

        double angle = curveDegree * Math.PI / 180;
        double spa2 = Math.sin(Math.PI/2-angle/2);
        radius = (float) Math.abs(nc * spa2 / Math.sin(angle));
        Vector2 cp = normalize(new Vector2(c.y,-1*c.x));
        double l = Math.sqrt((nc*nc/4)+radius*radius - nc*radius*Math.cos(Math.PI/2 - angle/2));


        if (curveDegree > 180)
            l = -l;

        arcs.radius = radius;
        centerX = (float)(d.x + cp.x * l);
        centerY = (float)(d.y + cp.y * l);
        arcs.centerX = centerX;
        arcs.centerY = centerY;

        start = (float) Math.toDegrees(angle_to(new Vector2(centerX,centerY),b));

        arcs.start = start;
        arcs.degree = (float) curveDegree;
        arcs.endDegree = (float) Math.toDegrees(angle_to(new Vector2(centerX,centerY),a));
        return arcs;
    }

    private static float norm(Vector2 v){
        return (float) (Math.sqrt((double)(v.x*v.x+v.y*v.y)));
    }

    private static float angle_to(Vector2 o, Vector2 p){
        return (float) Math.atan2((double) (p.y-o.y),(double) (p.x - o.x));
    }

    private static Vector2 normalize(Vector2 v){
        float k = norm(v);
        float x = v.x/k;
        float y = v.y/k;
        return new Vector2(x,y);
    }

    public static void drawArc(SpriteBatch batch,float centerX, float centerY, float radius, float start, float degrees, Color color) {

        TextureRegion region;

        if(color.toString().equals("ffff00ff"))
               region=Assets.instance.lineAssets.rectPixmapYellow;
        else if (color.toString().equals("00ff00ff"))
            region=Assets.instance.lineAssets.rectPixmapGreen;
        else
            region=Assets.instance.lineAssets.rectPixmapBlack;


        ShapeDrawer drawer2 = new ShapeDrawer(batch, region);

        //batch.begin();
        drawer2.arc(centerX,centerY,radius, (float) Math.toRadians((double) start),(float) Math.toRadians((double) degrees),5);
        batch.setColor(Color.WHITE);
        //batch.end();
    }

    public static void drawArc2(SpriteBatch batch,float centerX, float centerY, float radius, float start, float degrees, Color color) {
        ShapeDrawer drawer3 = new ShapeDrawer(batch, Assets.instance.lineAssets.rectPixmapBlack);

        batch.begin();
        drawer3.arc(centerX,centerY,radius, (float) Math.toRadians((double) start),(float) Math.toRadians((double) degrees),15);
        batch.setColor(Color.WHITE);
        batch.end();
    }
    // End of Arcs Drawing Helpers

}