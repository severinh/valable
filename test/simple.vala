/**
 * Comments should be ignored.
 */

using Gee;

public class Simple : Object {
  private int age;
  string name;
  private static int count = 0;

  public static int main(string[] args) {
    // Comment
    return 0;
  }
  
  
  construct {
    // Comment
  }
  
  
  public void doThing() {
    // Comment
  }
  
  
  public Simple getParent() {
    return this;
  }
}


public class Foo : Simple {
  public Simple getParent() {
    return null;
  }
  
  public void removeParent() {
    // Comment
  }
}
