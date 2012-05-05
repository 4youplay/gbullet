package com.bulletphysics.demos.lwjgl;

import com.bulletphysics.demos.bsp.BspDemo;
import com.bulletphysics.demos.character.CharacterDemo;
import com.bulletphysics.demos.concave.ConcaveDemo;
import com.bulletphysics.demos.concaveconvexcast.ConcaveConvexcastDemo;
import com.bulletphysics.demos.opengl.DemoApplication;
import com.bulletphysics.demos.opengl.DemoRunner;
import com.bulletphysics.demos.opengl.IGL;

public class FullDemoRunner extends DemoRunner {

  public FullDemoRunner(IGL gl) {
    super(gl);
  }

  protected DemoApplication createDemo(char c) {
    switch(c) {
    case '6':
      return new BspDemo(gl);
    case '7':
      return new CharacterDemo(gl); 
    case '8':
      return new ConcaveDemo(gl); 
    case '9':
      return new ConcaveConvexcastDemo(gl); 
    default:
      return super.createDemo(c);
    }
  }
    
  protected void drawMenu() {
    super.drawMenu();
    gl.drawString("6) Bsp Demo", 20, 150, 0, 0, 0);
    gl.drawString("7) Character Demo", 20, 170, 0, 0, 0);
    gl.drawString("8) Concave Demo", 20, 190, 0, 0, 0);
    gl.drawString("9) Concave Convexcast Demo", 20, 210, 0, 0, 0);

  }
}
