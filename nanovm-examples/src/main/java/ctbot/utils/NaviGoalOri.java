package ctbot.utils;


public class NaviGoalOri extends NaviGoal {
  private float targetOri;
  private float targetDelta;

  public NaviGoalOri(float ori, float delta) {
    targetOri = ori;
    targetDelta = delta;
    this.subGoal = null;
    this.nextGoal = null;
  }

  public boolean work(){
    float dori = Odometry.normalizeOri(targetOri-Odometry.ori);

    if (nanovm.lang.Math.abs(dori)<targetDelta)
      return false;

    if (dori<0.0f)
      Navigator.goRotate(Navigator.checkTurnSpeed(-Navigator.MAX_SPEED, dori));
    if (dori>0.0f)
      Navigator.goRotate(Navigator.checkTurnSpeed(Navigator.MAX_SPEED, dori));
    
    return true; // Goal needs additional work...
  }

}
