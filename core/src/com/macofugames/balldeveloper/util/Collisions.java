package com.macofugames.balldeveloper.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.macofugames.balldeveloper.BallDeveloper;
import com.macofugames.balldeveloper.actors.Arcs;
import com.macofugames.balldeveloper.actors.Discs;
import com.macofugames.balldeveloper.actors.Planes;
import com.macofugames.balldeveloper.actors.Player;
import com.macofugames.balldeveloper.actors.Princess;
import com.macofugames.balldeveloper.maps.Level;


public class Collisions {

    private Player player;
    private Level level;
    private BallDeveloper game;
    private Princess princess;
    public float upperBoundary,lowerBoundary,leftBoundary,rightBoundary;
    private int upperBoundaryIndex,lowerBoundaryIndex;
    private float ouchTimer,checkedInTimer,soundTimer,collisionTimer;
    private boolean isSoundJustPlayed,isJustCollided;
    private int currentIndex,previousIndex,currentIndexDisc,previousIndexDisc;


    public Collisions(Player player,Princess princess, Level level,BallDeveloper game) {
        this.level = level;
        this.player = player;
        this.game = game;
        this.princess = princess;
        isSoundJustPlayed=false;
        isJustCollided=false;
        collisionTimer=0;
        soundTimer=0;
        ouchTimer=0;
        checkedInTimer=0;
        currentIndex=-2;
        previousIndex = -1;
        currentIndexDisc=-2;
        previousIndexDisc = -1;
    }

    public void determineBounds() {
        for (int i = 0; i < level.planeVertices.size(); i++) {
            Planes planes = level.planeVertices.get(i);
            if (planes.getNormal().get(0) == 0 && planes.getNormal().get(1) == 1) {
                upperBoundary = planes.getEndPoints().y;
                upperBoundaryIndex = i;
            }
            if (planes.getNormal().get(0) == 0 && planes.getNormal().get(1) == -1) {
                lowerBoundary = planes.getEndPoints().y;
                lowerBoundaryIndex = i;
            }
            if (planes.getNormal().get(1) == 0 && planes.getNormal().get(0) == 1) {
                leftBoundary = planes.getEndPoints().x;
            }
            if (planes.getNormal().get(1) == 0 && planes.getNormal().get(0) == -1) {
                rightBoundary = planes.getEndPoints().x;
            }
        }
    }

    public void update(float delta) {

        updateDiscs(delta);
        updatePlayer(delta);
    }

    private void updateDiscs(float delta){
        updateDiscLocation(delta);
        checkDiscToDiscCollision(delta);
        checkDiscToArcCollision(delta);
        checkDiscToSegmentCollision(delta);
    }

    private void updatePlayer(float delta){

        updatePlayerPosition(delta);
        checkPlayerToPrincess(delta);
        checkPlayerToRespawns(delta);
        checkPlayerToSegmentCollision(delta);
        checkPlayerToDiscCollision(delta);
        checkPlayerToArcCollision(delta);
        checkPlayerToPlaneCollision();


        if(isJustCollided) {
            collisionTimer += delta;

            if (collisionTimer > 0.5) {
                isJustCollided = false;
                collisionTimer = 0;
                previousIndexDisc = -1;
                previousIndex = -1;
            }
        }


        if(player.isCollided)
        {
            ouchTimer +=delta;
            if(ouchTimer > 1) {
                player.isCollided = false;
                ouchTimer = 0;
            }
        }

        if( player.isCheckedIn)
        {
            checkedInTimer +=delta;
            if(checkedInTimer > 1) {
                player.isCheckedIn = false;
                checkedInTimer = 0;
            }
        }

        if(isSoundJustPlayed){
            soundTimer+=delta;

            if(soundTimer>0.3){
                soundTimer=0;
                isSoundJustPlayed=false;
            }
        }

    }

    private void updatePlayerPosition(float delta){

        if(player.leftButtonPressed || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.getSpeed().x -= Constants.VELOCITY_INCREMENT;
            player.isFacingRight = false;
        }
        if(player.rightButtonPressed || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.getSpeed().x += Constants.VELOCITY_INCREMENT;
            player.isFacingRight = true;
        }
        if(player.upButtonPressed || Gdx.input.isKeyPressed(Input.Keys.UP))
            player.getSpeed().y += Constants.VELOCITY_INCREMENT;
        if(player.downButtonPressed || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            player.getSpeed().y -= Constants.VELOCITY_INCREMENT;

        player.getSpeed().x = player.getSpeed().x * Constants.PLAYER_DAMPING;
        player.getSpeed().y = player.getSpeed().y * Constants.PLAYER_DAMPING;

        if (Math.abs(player.getSpeed().x) > Constants.PLAYER_MAXVELOCITY)
            player.getSpeed().x = Math.signum(player.getSpeed().x)*Constants.PLAYER_MAXVELOCITY;
        else if (Math.abs(player.getSpeed().x) < Constants.PLAYER_MINVELOCITY)
            player.getSpeed().x = Math.signum(player.getSpeed().x)*Constants.PLAYER_MINVELOCITY;
        if (Math.abs(player.getSpeed().y) > Constants.PLAYER_MAXVELOCITY)
            player.getSpeed().y = Math.signum(player.getSpeed().y)*Constants.PLAYER_MAXVELOCITY;
        else if (Math.abs(player.getSpeed().y) < Constants.PLAYER_MINVELOCITY)
            player.getSpeed().y = Math.signum(player.getSpeed().y)*Constants.PLAYER_MINVELOCITY;

        player.x = player.x + player.getSpeed().x * delta;
        player.y = player.y + player.getSpeed().y * delta;
        player.setCenterPosition(player.x,player.y);

    }

    private void checkPlayerToPrincess(float delta) {

        if (player.overlaps(princess)) {
            princess.isSaved = true;
            if(game.prefs.hasSound())
                Assets.instance.soundAssets.levelUp.play(0.5f);
        }
    }

    private void checkPlayerToRespawns(float delta){

        for(int i=0;i<level.respawns.size();i++){
            if(player.overlaps(level.respawns.get(i))) {
                if(game.prefs.hasSound())
                    Assets.instance.soundAssets.checkIn.play(0.5f);

                player.isCheckedIn = true;
                player.spawnLocation.set(level.respawns.get(i).x,level.respawns.get(i).y);
                game.prefs.setSpawns(level.respawns.get(i).x,level.respawns.get(i).y);
                level.respawns.remove(i);
            }
        }

    }

    private void checkPlayerToDiscCollision(float delta){
        for (int i = 0; i < level.discVertices.size(); i++) {

            Discs currentDisc = level.discVertices.get(i);
            Circle currentCircle = new Circle(currentDisc.getCenterPosition(),currentDisc.getRadius());

            boolean collided = player.overlaps(currentCircle) && currentDisc.getcMask().contains("red");
            boolean moveableDisc=false;

            if(collided) {

                if (currentDisc.getInvMass() > 0.1)
                    moveableDisc = true;

                currentIndexDisc = i;
                isJustCollided=true;

                float discbCoef = currentDisc.getbCoef();

                boolean black = level.discVertices.get(i).getColor().toString().equals("000000ff");

                if(currentIndexDisc != previousIndexDisc && !black) {
                    player.collisionNumber += 1;
                    player.isCollided = true;
                }

                if(!isSoundJustPlayed) {
                    if (game.prefs.hasSound() && (discbCoef > 2 || discbCoef < -2))
                        Assets.instance.soundAssets.ballCoefBig.play(0.5f);
                    if (game.prefs.hasSound() && (discbCoef >= -2 && discbCoef <= 2))
                        Assets.instance.soundAssets.ballCoef1.play(0.5f);
                    isSoundJustPlayed=true;
                }

                if(discbCoef > 5 || discbCoef < -5){
                    player.respawn();
                    previousIndexDisc = currentIndexDisc;
                    return;
                }

                Vector2 radiusDifference = new Vector2(player.getCenter().x-currentDisc.getCenterPosition().x  ,
                        player.getCenter().y-currentDisc.getCenterPosition().y  );
                float movePlayerByDisc = (currentDisc.getRadius()+player.radius)-radiusDifference.len();
                double centerAngles = Math.atan2(radiusDifference.y,radiusDifference.x);


                player.x = player.getCenter().x  + (movePlayerByDisc)*(float) Math.cos(centerAngles);
                player.y = player.getCenter().y  + (movePlayerByDisc)*(float) Math.sin(centerAngles);
                player.setCenterPosition(player.x,player.y);


                Vector2 temp = new Vector2(radiusDifference.y,-radiusDifference.x);
                Vector2 tempNor = temp.nor();
                Vector2 relSpeed = new Vector2(player.getSpeed().x - currentDisc.getSpeed().x/delta,
                        player.getSpeed().y - currentDisc.getSpeed().y/delta );
                float length = relSpeed.dot(tempNor);
                Vector2 xSpeed = tempNor.scl(length);
                Vector2 ySpeed = relSpeed.sub(xSpeed);

                if(moveableDisc)
                {
                    player.setSpeed(player.getSpeed().x - ySpeed.x,player.getSpeed().y - ySpeed.y);
                    currentDisc.setSpeed(currentDisc.getSpeed().x + ySpeed.x*delta, currentDisc.getSpeed().y + ySpeed.y*delta);
                }
                else
                {
                    player.setSpeed(player.getSpeed().x - 2*ySpeed.x,player.getSpeed().y - 2*ySpeed.y);
                }

                previousIndexDisc = currentIndexDisc;
                break;


            }
        }
    }

    private void checkPlayerToArcCollision(float delta) {
        for (int i = 0; i < level.arcVertices.size(); i++) {
            float xStart = level.arcVertices.get(i).getStartPoints().x;
            float yStart = level.arcVertices.get(i).getStartPoints().y;
            float xEnd = level.arcVertices.get(i).getEndPoints().x;
            float yEnd = level.arcVertices.get(i).getEndPoints().y;

            Vector2 starts = new Vector2(xStart,yStart);
            Vector2 ends = new Vector2(xEnd,yEnd);

            Arcs arcs = Helpers.calculateArc(starts,ends,level.arcVertices.get(i).getCurveDegree());
            boolean collided = getArcCollision(player, arcs.radius, new Vector2((float) Math.toRadians((double)arcs.start),
                    (float) Math.toRadians((double)(arcs.degree + arcs.start))), new Vector2(arcs.centerX,arcs.centerY)) && level.arcVertices.get(i).getcMask().contains("red");



            if (collided) {
                currentIndex = i;
                float arcBcoef = level.arcVertices.get(i).getbCoef();

                isJustCollided=true;

                boolean green = level.arcVertices.get(i).getColor().toString().equals("00ff00ff");

                if(arcBcoef != 1 && currentIndex != previousIndex && !green) {
                    player.collisionNumber += 1;
                    player.isCollided = true;
                }

                if(!isSoundJustPlayed) {
                    if (game.prefs.hasSound() && arcBcoef !=1 && !green)
                        Assets.instance.soundAssets.wallCoefBig.play(0.5f);
                    if (game.prefs.hasSound() && green)
                        Assets.instance.soundAssets.greenWalls.play(0.5f);
                    isSoundJustPlayed=true;
                }

                if((arcBcoef > 5 || arcBcoef < -5) && !green){
                    player.respawn();
                    previousIndex = currentIndex;
                    return;
                }



                    Vector2 radiusDifference = new Vector2(player.x - arcs.centerX, player.y - arcs.centerY);
                    double centerAngles = Math.atan2(radiusDifference.y, radiusDifference.x);
                    float dist = radiusDifference.len();
                    float xmoveby;
                    boolean outside;

                    if (arcs.radius > dist) {
                        outside = false;
                        xmoveby = (float) (player.getRadius() + dist - arcs.radius);

                    } else {
                        outside = true;
                        xmoveby = (float) (arcs.radius + player.getRadius() - dist);

                    }

                    if (outside) {
                        player.x = player.x + xmoveby * (float) Math.cos(centerAngles);
                        player.y = player.y + xmoveby * (float) Math.sin(centerAngles);
                    } else {
                        player.x = player.x - xmoveby * (float) Math.cos(centerAngles);
                        player.y = player.y - xmoveby * (float) Math.sin(centerAngles);
                    }

                    player.setCenterPosition(player.x, player.y);

                    previousIndex = currentIndex;

                Vector2 temp = new Vector2(radiusDifference.y,-radiusDifference.x);
                Vector2 tempNor = temp.nor();
                Vector2 playerSpeedTemp = new Vector2(player.getSpeed().x,player.getSpeed().y);
                Vector2 newSpeed = playerSpeedTemp.sub(tempNor.scl(2 * playerSpeedTemp.dot(tempNor)));

                player.setSpeed(-newSpeed.x*arcBcoef*Constants.PLAYER_BCOEF,-newSpeed.y*arcBcoef*Constants.PLAYER_BCOEF);

                break;

            }
        }
    }

    private void checkPlayerToSegmentCollision(float delta) {

        for (int j = 0; j < level.segmentVertices.size(); j++) {
            Vector2 startPoints = level.segmentVertices.get(j).getStartPoints();
            Vector2 endPoints = level.segmentVertices.get(j).getEndPoints();

            Vector2 displacement = new Vector2();
            Vector2 segmentVector = new Vector2(endPoints.x - startPoints.x, endPoints.y - startPoints.y);

            float d = Intersector.intersectSegmentCircleDisplace(startPoints, endPoints, player.getCenter(), player.getRadius(), displacement);
            boolean collided = d != Float.POSITIVE_INFINITY && level.segmentVertices.get(j).getcMask().contains("red");

            if (collided) {

                Vector2 nearestSegmentPoint = new Vector2();
                nearestSegmentPoint = Intersector.nearestSegmentPoint(startPoints, endPoints, player.getCenter(), nearestSegmentPoint);
                float segmentBcoef = level.segmentVertices.get(j).getbCoef();

                boolean green = level.segmentVertices.get(j).getColor().toString().equals("00ff00ff");
                boolean yellow = level.segmentVertices.get(j).getColor().toString().equals("ffff00ff");

                if(!isSoundJustPlayed) {
                    if (game.prefs.hasSound() && (segmentBcoef >= -1 && segmentBcoef <= 1))
                        Assets.instance.soundAssets.wallCoef1.play(0.5f);

                    if (game.prefs.hasSound() && (segmentBcoef < -1 || segmentBcoef > 1) && yellow)
                        Assets.instance.soundAssets.wallCoefBig.play(0.5f);

                    if (game.prefs.hasSound() && green)
                        Assets.instance.soundAssets.greenWalls.play(0.5f);

                    isSoundJustPlayed=true;
                }
                if(yellow) {
                    player.collisionNumber += 1;
                    player.isCollided = true;
                }


                float movePlayerBySegment = d;
                double segmentToSpeedAngle = Math.atan2(player.y - nearestSegmentPoint.y,player.x - nearestSegmentPoint.x);


                if(segmentBcoef > 5 || segmentBcoef < -5){
                    player.respawn();
                    return;
                }

                Vector2 segmentTemp = new Vector2(segmentVector.x,segmentVector.y);
                Vector2 segmentNormed = segmentTemp.nor();
                Vector2 playerSpeedTemp = new Vector2(player.getSpeed().x,player.getSpeed().y);
                Vector2 newSpeed = playerSpeedTemp.sub(segmentNormed.scl(2 * playerSpeedTemp.dot(segmentNormed)));
                player.setSpeed(-newSpeed.x *segmentBcoef*Constants.PLAYER_BCOEF ,-newSpeed.y*segmentBcoef*Constants.PLAYER_BCOEF);
                player.x = player.getCenter().x + (player.getRadius() - movePlayerBySegment) * (float) Math.cos(segmentToSpeedAngle);
                player.y = player.getCenter().y  + (player.getRadius() - movePlayerBySegment) * (float) Math.sin(segmentToSpeedAngle);
                player.setCenterPosition(player.x,player.y);

                break;
            }

        }

    }

    private void checkPlayerToPlaneCollision() {
        float segmentBcoef;
        if (player.y + player.radius >= upperBoundary) {
            segmentBcoef = level.planeVertices.get(upperBoundaryIndex).getbCoef();
            player.y = upperBoundary - player.radius;
            player.speed.set(segmentBcoef * player.speed.x * Constants.PLAYER_BCOEF, -segmentBcoef * player.speed.y* Constants.PLAYER_BCOEF);

            if(game.prefs.hasSound())
                Assets.instance.soundAssets.planeHit.play(0.5f);

        } else if (player.y - player.radius < lowerBoundary) {
            segmentBcoef = level.planeVertices.get(lowerBoundaryIndex).getbCoef();
            player.y = lowerBoundary + player.radius;
            player.speed.set(segmentBcoef * player.speed.x* Constants.PLAYER_BCOEF, -segmentBcoef * player.speed.y* Constants.PLAYER_BCOEF);

            if(game.prefs.hasSound())
                Assets.instance.soundAssets.planeHit.play(0.5f);

        } else if (player.x - player.radius < leftBoundary) {
            player.x = 30;
            player.y = Constants.VIEWPORT_HEIGHT / 2;
            player.speed.set(0, 0);

            if(game.prefs.hasSound())
                Assets.instance.soundAssets.planeHit.play(0.5f);

        } else if (player.x + player.radius > rightBoundary) {
            player.x = 30;
            player.y = Constants.VIEWPORT_HEIGHT / 2;
            player.speed.set(0, 0);

            if(game.prefs.hasSound())
                Assets.instance.soundAssets.planeHit.play(0.5f);
        }

    }

    private void updateDiscLocation(float delta) {

        for (int i = 0; i < level.discVertices.size(); i++) {
            Discs currentDisc = level.discVertices.get(i);
            currentDisc.setSpeed(currentDisc.getSpeed().x * currentDisc.getDamping(),currentDisc.getSpeed().y * currentDisc.getDamping());
            currentDisc.setCenterPosition(currentDisc.getCenterPosition().x + currentDisc.getSpeed().x,
                    currentDisc.getCenterPosition().y + currentDisc.getSpeed().y );
        }
    }

    private void checkDiscToSegmentCollision(float delta) {

        for (int i = 0; i < level.discVertices.size(); i++) {
            Discs currentDisc = level.discVertices.get(i);

            boolean constantDisc = currentDisc.getSpeed().len() == 0 && (currentDisc.getbCoef() > 1 || currentDisc.getbCoef() < -1);

            if(constantDisc)
                continue;

            float moveDiscBy = 0f;
            Vector2 nearestPoint = new Vector2();
            Vector2 segmentVector = new Vector2();
            double segmentToSpeedAngle = 0;

            for (int j = 0; j < level.segmentVertices.size(); j++) {
                Vector2 startPoints = level.segmentVertices.get(j).getStartPoints();
                Vector2 endPoints = level.segmentVertices.get(j).getEndPoints();
                Vector2 displacement = new Vector2();
                segmentVector = segmentVector.set(endPoints.x - startPoints.x, endPoints.y - startPoints.y);

                float d = Intersector.intersectSegmentCircleDisplace(startPoints, endPoints, currentDisc.getCenterPosition(), currentDisc.getRadius(), displacement);
                boolean collided = d != Float.POSITIVE_INFINITY && level.segmentVertices.get(j).getcMask().contains("wall") && (currentDisc.getRadius() - d) > 0.2;

                if (collided) {
                    moveDiscBy = d;
                    nearestPoint = Intersector.nearestSegmentPoint(startPoints, endPoints, currentDisc.getCenterPosition(), nearestPoint);

                    segmentToSpeedAngle = Math.atan2(currentDisc.getCenterPosition().y-nearestPoint.y,currentDisc.getCenterPosition().x-nearestPoint.x);
                    currentDisc.setCenterPosition(currentDisc.getCenterPosition().x + (currentDisc.getRadius() - moveDiscBy) * (float) Math.cos(segmentToSpeedAngle),
                            currentDisc.getCenterPosition().y + (currentDisc.getRadius() - moveDiscBy) * (float) Math.sin(segmentToSpeedAngle));

                    Vector2 segmentNormed = segmentVector.nor();
                    Vector2 newSpeed = currentDisc.getSpeed().sub(segmentNormed.scl(2 * currentDisc.getSpeed().dot(segmentNormed)));
                    currentDisc.setSpeed(-newSpeed.x, -newSpeed.y);
                    break;
                }

            }
        }

    }

    private void checkDiscToDiscCollision(float delta){

        for (int i = 0; i < level.discVertices.size()-1; i++) {
            Discs currentDics = level.discVertices.get(i);

            if(!currentDics.getcMask().contains("wall"))
                 continue;

            for (int j = i+1; j < level.discVertices.size(); j++){

                Discs checkingDiscs = level.discVertices.get(j);

                if(!checkingDiscs.getcMask().contains("wall"))
                    continue;

                Vector2 radiusDifference = new Vector2(currentDics.getCenterPosition().x - checkingDiscs.getCenterPosition().x,
                        currentDics.getCenterPosition().y - checkingDiscs.getCenterPosition().y);

                float movediscby = (currentDics.getRadius()+checkingDiscs.getRadius())-radiusDifference.len();

                if(movediscby>0.4){

                    double centerAngles = Math.atan2(-radiusDifference.y,-radiusDifference.x);
                    movediscby/=2;

                    checkingDiscs.setCenterPosition(checkingDiscs.getCenterPosition().x + movediscby * (float) Math.cos(centerAngles),
                            checkingDiscs.getCenterPosition().y + movediscby * (float) Math.sin(centerAngles));
                    currentDics.setCenterPosition(currentDics.getCenterPosition().x - movediscby * (float) Math.cos(centerAngles),
                            currentDics.getCenterPosition().y - movediscby * (float) Math.sin(centerAngles));

                    Vector2 temp = new Vector2(radiusDifference.y,-radiusDifference.x);
                    Vector2 tempNor = temp.nor();
                    Vector2 relSpeed = new Vector2(currentDics.getSpeed().x-checkingDiscs.getSpeed().x,
                            currentDics.getSpeed().y - checkingDiscs.getSpeed().y);

                    float length = relSpeed.dot(tempNor);
                    Vector2 xSpeed = tempNor.scl(length);
                    Vector2 ySpeed = relSpeed.sub(xSpeed);

                    currentDics.setSpeed(currentDics.getSpeed().x - ySpeed.x,currentDics.getSpeed().y - ySpeed.y);
                    checkingDiscs.setSpeed(checkingDiscs.getSpeed().x + ySpeed.x, checkingDiscs.getSpeed().y + ySpeed.y);

                    break;
                }

            }

        }
    }

    private void checkDiscToArcCollision(float delta) {

        for (int i = 0; i < level.discVertices.size(); i++) {
            Discs currentDisc = level.discVertices.get(i);
            Circle currentCircle = new Circle(currentDisc.getCenterPosition(), currentDisc.getRadius());

            boolean constantDisc = currentDisc.getSpeed().len() == 0 && (currentDisc.getbCoef() > 1 || currentDisc.getbCoef() < -1);
            if (constantDisc)
                continue;

            for (int j = 0; j < level.arcVertices.size(); j++) {
                float xStart = level.arcVertices.get(j).getStartPoints().x;
                float yStart = level.arcVertices.get(j).getStartPoints().y;
                float xEnd = level.arcVertices.get(j).getEndPoints().x;
                float yEnd = level.arcVertices.get(j).getEndPoints().y;

                Vector2 starts = new Vector2(xStart, yStart);
                Vector2 ends = new Vector2(xEnd, yEnd);

                Arcs arcs = Helpers.calculateArc(starts
                        , ends, level.arcVertices.get(j).getCurveDegree());


                boolean collided = getArcCollision(currentCircle, arcs.radius, new Vector2((float) Math.toRadians((double) arcs.start),
                        (float) Math.toRadians((double) (arcs.degree + arcs.start))), new Vector2(arcs.centerX, arcs.centerY)) && level.arcVertices.get(j).getcMask().contains("wall");

              if(collided) {
                  Vector2 radiusDifference = new Vector2(currentDisc.getCenterPosition().x - arcs.centerX,currentDisc.getCenterPosition().y - arcs.centerY);
                  double centerAngles = Math.atan2(radiusDifference.y,radiusDifference.x);
                  float dist = radiusDifference.len();
                  float xmoveby;
                  boolean outside;

                  if(arcs.radius>dist) {
                       outside= false;
                      xmoveby = (float) (currentDisc.getRadius() + dist-arcs.radius);

                  }
                  else {
                      outside = true;
                      xmoveby = (float)(arcs.radius+currentDisc.getRadius() - dist);

                  }

                  if (outside) {

                      currentDisc.setCenterPosition(currentDisc.getCenterPosition().x + xmoveby * (float) Math.cos(centerAngles),
                              currentDisc.getCenterPosition().y + xmoveby * (float) Math.sin(centerAngles));

                  } else {

                      currentDisc.setCenterPosition(currentDisc.getCenterPosition().x - xmoveby * (float) Math.cos(centerAngles),
                              currentDisc.getCenterPosition().y - xmoveby * (float) Math.sin(centerAngles));
                  }

                  Vector2 temp = new Vector2(radiusDifference.y,-radiusDifference.x);
                  Vector2 tempNor = temp.nor();
                  Vector2 relSpeed = new Vector2(currentDisc.getSpeed().x,currentDisc.getSpeed().y);


                  float length = relSpeed.dot(tempNor);
                  Vector2 xSpeed = tempNor.scl(length);
                  Vector2 ySpeed = relSpeed.sub(xSpeed);

                  currentDisc.setSpeed(currentDisc.getSpeed().x - 2*ySpeed.x,currentDisc.getSpeed().y - 2*ySpeed.y);
              }
              }

        }

    }

    private boolean getArcCollision(Circle ourBallCircle, double radius, Vector2 startEnd
            , Vector2 centerArc) {

        float ball_radius = ourBallCircle.radius;
        Vector2 ballPosition = new Vector2(ourBallCircle.x, ourBallCircle.y);
        double start = startEnd.x;
        double end = startEnd.y;

        double outerRadius = radius;
        boolean collides = false;
        double cx = centerArc.x;
        double cy = centerArc.y;
        double bx = ballPosition.x;
        double by = ballPosition.y;

        double dx = bx - cx;
        double dy = by - cy;

        double dist = Math.sqrt(dx * dx + dy * dy);
        double dir = Math.atan2(dy, dx);

        double tangent_angle = Math.asin(ball_radius / dist);
        double dir0 = dir + tangent_angle;
        double dir1 = dir - tangent_angle;

        float an = (float) ((Math.toDegrees(dir) + 360) %360);
        float s = (float) ((Math.toDegrees(start) + 360) %360);
        float e = (float) ((Math.toDegrees(end) + 360) %360);

        if (dist + ball_radius > radius && dist - ball_radius < outerRadius) {

            boolean d;

            if(s<e)
                d = s<=an && an<=e;
            else
                d = s<=an || an<=e;

            if (d) {
                collides = true;
            }
        }
        return collides;
    }

}
