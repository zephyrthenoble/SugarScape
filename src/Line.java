import java.awt.geom.Line2D.Double;
import java.awt.geom.Line2D;
public class Line extends Line2D.Double
{
   public Line(double x, double y, double z, double w)
   {
      super(x,y,z,w);
   }
   public void scale(double d)
   {
      double x1 = getX1()*d;
      double y1 = getY1()*d;
      double x2 = getX2()*d;
      double y2 = getY2()*d;
      setLine(x1, y1, x2, y2);
   }
   public void scaleX(double d)
   {
      double x1 = getX1()*d;
      double x2 = getX2()*d;
      setLine(x1, getY1(), x2, getY2());
   }
   public void scaleY(double d)
   {
      double y1 = getY1()*d;
      double y2 = getY2()*d;
      setLine(getX1(), y1, getX2(), y2);
   }
   public Line copy()
   {
      return new Line(getX1(), getY1(), getX2(), getY2());
   }
}