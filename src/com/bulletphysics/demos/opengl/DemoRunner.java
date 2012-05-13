package com.bulletphysics.demos.opengl;

import static com.bulletphysics.demos.opengl.IGL.GL_MODELVIEW;
import static com.bulletphysics.demos.opengl.IGL.GL_PROJECTION;

import com.bulletphysics.demos.basic.BasicDemo;
import com.bulletphysics.demos.dynamiccontrol.DynamicControlDemo;
import com.bulletphysics.demos.genericjoint.GenericJointDemo;
import com.bulletphysics.demos.movingconcave.MovingConcaveDemo;
import com.bulletphysics.demos.vehicle.VehicleDemo;
import com.bulletphysics.demos.forklift.ForkLiftDemo;


public class DemoRunner {

  protected IGL gl;
  
  DemoApplication demoApplication;
  int screenWidth;
  int screenHeight;
  
  public DemoRunner(IGL gl) {
    this.gl = gl;
  }

  public void myinit() {
    gl.glClearColor(0.7f, 0.7f, 0.7f, 0f);
  }

  public void reshape(int width, int height) {
    this.screenWidth = width;
    this.screenHeight = height;
    if (demoApplication != null) {
      demoApplication.reshape(width, height);
    } else {
      gl.glViewport(0, 0, width, height);
    }
  }

  public void moveAndDisplay() {
    if (demoApplication != null) {
      demoApplication.moveAndDisplay();
    } else {
      gl.glClear(IGL.GL_COLOR_BUFFER_BIT | IGL.GL_DEPTH_BUFFER_BIT); 
      // switch to projection mode
      gl.glMatrixMode(GL_PROJECTION);
      // reset matrix
      gl.glLoadIdentity();
      // set a 2D orthographic projection
      gl.gluOrtho2D(0f, screenWidth, 0f, screenHeight);
      gl.glMatrixMode(GL_MODELVIEW);
      gl.glLoadIdentity();
      
      // invert the y axis, down is positive
      gl.glScalef(2f, -2f, 1f);
      // mover the origin from the bottom left corner
      // to the upper left corner
      gl.glTranslatef(0f, -screenHeight/2, 0f);
      gl.glColor3f(0, 0, 0);

      drawMenu();
    }
  }

  protected void drawMenu() {
    gl.drawString("GBullet Demo Runner", 10, 10, 0, 0, 0);
    gl.drawString("0) Basic Demo", 20, 30, 0, 0, 0);
    gl.drawString("1) Vehicle Demo", 20, 50, 0, 0, 0);
    gl.drawString("2) Forklift Demo", 20, 70, 0, 0, 0);
    gl.drawString("3) Generic Joint Demo", 20, 90, 0, 0, 0);
    gl.drawString("4) DynamicControl Demo", 20, 110, 0, 0, 0);
  }

  public void keyboardCallback(char eventCharacter, int x, int y, int modifiers) {
    if (eventCharacter == 'q') {
      demoApplication = null;
    } else if (demoApplication != null) {
      demoApplication.keyboardCallback(eventCharacter, x, y, modifiers);
    } else {
      DemoApplication app = createDemo(eventCharacter);
      if (app != null) {
        try {
          app.myinit();
          app.initPhysics();
          app.reshape(screenWidth, screenHeight);
          app.getDynamicsWorld().setDebugDrawer(new GLDebugDrawer(gl));
          demoApplication = app;
        } catch(Exception e) {
          throw new RuntimeException(e);
        }
      }
    } 
  }

  protected DemoApplication createDemo(char eventCharacter) {
    switch (eventCharacter) {
    case '0': 
      return new BasicDemo(gl);
    case '1':
      return new VehicleDemo(gl); 
    case '2':
      return new ForkLiftDemo(gl);
    case '3':
      return new DynamicControlDemo(gl); 
    case '4':
      return new GenericJointDemo(gl); 
    default:
      return null;
    }
  }

  public void specialKeyboard(int eventKey, int x, int y, int modifiers) {
    if (demoApplication != null) {
      demoApplication.specialKeyboard(eventKey, x, y, modifiers);
    }
  }

  public void specialKeyboardUp(int eventKey, int x, int y, int modifiers) {
    if (demoApplication != null) {
      demoApplication.specialKeyboardUp(eventKey, x, y, modifiers);
    }
  }

  public void mouseFunc(int btn, int state, int eventX, int eventY) {
    if (demoApplication != null) {
      demoApplication.mouseFunc(btn, state, eventX, eventY);
    }
  }

  public void mouseMotionFunc(int x, int y) {
    if (demoApplication != null) {
      demoApplication.mouseMotionFunc(x, y);
    }
  }
}
