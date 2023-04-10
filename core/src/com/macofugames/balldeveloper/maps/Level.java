package com.macofugames.balldeveloper.maps;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.macofugames.balldeveloper.actors.Arcs;
import com.macofugames.balldeveloper.actors.Discs;
import com.macofugames.balldeveloper.actors.Planes;
import com.macofugames.balldeveloper.actors.Respawns;
import com.macofugames.balldeveloper.actors.Segments;
import com.macofugames.balldeveloper.actors.Vertexes;
import com.macofugames.balldeveloper.levelreader.Reader;
import com.macofugames.balldeveloper.util.Assets;
import com.macofugames.balldeveloper.util.Constants;
import com.macofugames.balldeveloper.util.Helpers;

import java.util.ArrayList;

public class Level {


    public  ArrayList<Segments> segmentVertices;
    public  ArrayList<Discs> discVertices;
    public  ArrayList<Planes> planeVertices;
    public  ArrayList<Segments> arcVertices;
    public ArrayList<Respawns> respawns;
    public ArrayList<Vertexes> vertexes;
    private int currentLevel;

    private TextureRegion themeLeft,themeRight,themeMid;

    public Level(){
    }

    public  void load(int currentLevel){
        this.currentLevel = currentLevel;

        switch (currentLevel % 4){
            case 0:
                themeLeft = Assets.instance.playScreenStageAssets.themeFourleft;
                themeMid = Assets.instance.playScreenStageAssets.themeFourMiddle;
                themeRight = new TextureRegion(Assets.instance.playScreenStageAssets.themeFourleft);
                themeRight.flip(true,false);
                break;
            case 1:
                themeLeft = Assets.instance.playScreenStageAssets.themeOneleft;
                themeMid = Assets.instance.playScreenStageAssets.themeOneMiddle;
                themeRight = new TextureRegion(Assets.instance.playScreenStageAssets.themeOneleft);
                themeRight.flip(true,false);
                break;
            case 2:
                themeLeft = Assets.instance.playScreenStageAssets.themeTwoleft;
                themeMid = Assets.instance.playScreenStageAssets.themeTwoMiddle;
                themeRight = new TextureRegion(Assets.instance.playScreenStageAssets.themeTwoleft);
                themeRight.flip(true,false);
                break;
            default:
                themeLeft = Assets.instance.playScreenStageAssets.themeThreeleft;
                themeMid = Assets.instance.playScreenStageAssets.themeThreeMiddle;
                themeRight = new TextureRegion(Assets.instance.playScreenStageAssets.themeThreeleft);
                themeRight.flip(true,false);
                break;

        }

        segmentVertices = new ArrayList<Segments>();
        discVertices = new ArrayList<Discs>();
        planeVertices = new ArrayList<Planes>();
        arcVertices = new ArrayList<Segments>();
        respawns = new ArrayList<Respawns>();
        vertexes = new ArrayList<Vertexes>();

        for(int i=0;i<Reader.levelMaps.vertexes.size();i++){
            vertexes.add(new Vertexes(Reader.levelMaps.vertexes.get(i)));
        }

        for (int i = 0; i < Reader.levelMaps.discs.size(); i++) {
            discVertices.add(new Discs(Reader.levelMaps.discs.get(i)));
        }

        for (int i = 0; i < Reader.levelMaps.segments.size(); i++) {
            if(Reader.levelMaps.segments.get(i).curve ==0)
                segmentVertices.add(new Segments(Reader.levelMaps.segments.get(i)));
            else
                arcVertices.add(new Segments(Reader.levelMaps.segments.get(i)));

        }

        for (int i = 0; i < Reader.levelMaps.planes.size(); i++) {
            planeVertices.add(new Planes(Reader.levelMaps.planes.get(i)));
        }

        for (int i = 0; i < Reader.levelMaps.respawns.size(); i++) {
            respawns.add(new Respawns(Reader.levelMaps.respawns.get(i).x,Reader.levelMaps.respawns.get(i).y,Constants.PLAYER_RADIUS));
        }

    }


    public void render(SpriteBatch spriteBatch){

    //    spriteBatch.begin();
        spriteBatch.draw(themeMid,1600,-400,1605,1200);
        spriteBatch.draw(themeLeft,-400,-400,2015,1200);
        spriteBatch.draw(themeRight,3190,-400,2015,1200);

        for (int i = 0; i < planeVertices.size();i++){

            Helpers.drawLine(spriteBatch, Assets.instance.lineAssets.rectPixmap,planeVertices.get(i).getStartPoints().x
                    ,planeVertices.get(i).getStartPoints().y,
                    planeVertices.get(i).getEndPoints().x,planeVertices.get(i).getEndPoints().y,6,Color.DARK_GRAY);
        }


        for (int i = 0; i < segmentVertices.size(); i++) {
            if(segmentVertices.get(i).visibility != false) {
                float xStart = segmentVertices.get(i).getStartPoints().x;
                float yStart = segmentVertices.get(i).getStartPoints().y;
                float xEnd = segmentVertices.get(i).getEndPoints().x;
                float yEnd = segmentVertices.get(i).getEndPoints().y;
                Vector2 starts = new Vector2(xStart,yStart);
                Vector2 ends = new Vector2(xEnd,yEnd);
                Color color = segmentVertices.get(i).getColor();


                Helpers.drawLine(spriteBatch, Assets.instance.lineAssets.rectPixmap,starts.x,starts.y,ends.x,ends.y,6,color);
            //    Helpers.drawLine2(spriteBatch, Assets.instance.lineAssets.rectPixmap,starts.x,starts.y,color);
            //    Helpers.drawLine2(spriteBatch, Assets.instance.lineAssets.rectPixmap,ends.x,ends.y,color);

            }
        }

        for (int i = 0; i < arcVertices.size(); i++) {
            if(arcVertices.get(i).visibility != false) {
                float xStart = arcVertices.get(i).getStartPoints().x;
                float yStart = arcVertices.get(i).getStartPoints().y;
                float xEnd = arcVertices.get(i).getEndPoints().x;
                float yEnd = arcVertices.get(i).getEndPoints().y;

                Vector2 starts = new Vector2(xStart,yStart);
                Vector2 ends = new Vector2(xEnd,yEnd);


                Arcs arcs = Helpers.calculateArc(starts,ends,arcVertices.get(i).getCurveDegree());

               Helpers.drawArc(spriteBatch, arcs.centerX, arcs.centerY, arcs.radius, arcs.start,arcs.degree, arcVertices.get(i).getColor());

            }
        }


        for (int i = 0; i < discVertices.size(); i++) {


            if(discVertices.get(i).getColor().toString().equals("ffff00ff"))
                spriteBatch.draw(Assets.instance.circleAssets.yellowCircle,discVertices.get(i).getCenterPosition().x-discVertices.get(i).getRadius(),
                        discVertices.get(i).getCenterPosition().y-discVertices.get(i).getRadius(),2* discVertices.get(i).getRadius(),
                        2* discVertices.get(i).getRadius());
            else if (discVertices.get(i).getColor().toString().equals("ff0000ff"))
                spriteBatch.draw(Assets.instance.circleAssets.redCircle,discVertices.get(i).getCenterPosition().x-discVertices.get(i).getRadius(),
                        discVertices.get(i).getCenterPosition().y-discVertices.get(i).getRadius(),2* discVertices.get(i).getRadius(),
                        2* discVertices.get(i).getRadius());
            else if (discVertices.get(i).getColor().toString().equals("00ff00ff"))
                spriteBatch.draw(Assets.instance.circleAssets.greenCircle,discVertices.get(i).getCenterPosition().x-discVertices.get(i).getRadius(),
                        discVertices.get(i).getCenterPosition().y-discVertices.get(i).getRadius(),2* discVertices.get(i).getRadius(),
                        2* discVertices.get(i).getRadius());
            else
                spriteBatch.draw(Assets.instance.circleAssets.blackCircle,discVertices.get(i).getCenterPosition().x-discVertices.get(i).getRadius(),
                        discVertices.get(i).getCenterPosition().y-discVertices.get(i).getRadius(),2* discVertices.get(i).getRadius(),
                        2* discVertices.get(i).getRadius());


        }

        for (int i = 0; i < respawns.size(); i++) {

                spriteBatch.draw(Assets.instance.circleAssets.checkPoint,respawns.get(i).x-respawns.get(i).radius,
                        respawns.get(i).y-respawns.get(i).radius,3f* respawns.get(i).radius,
                        4f* respawns.get(i).radius);


        }
      //  spriteBatch.end();
   }



}