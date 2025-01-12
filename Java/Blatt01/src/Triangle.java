import java.util.Arrays;

public class Triangle extends ConvexPolygon {

    public Triangle(Vector2D a, Vector2D b, Vector2D c) {

        this.vertices = new Vector2D[] { a, b, c };


    }

    public Triangle(Triangle triangle) {

        this.vertices = triangle.vertices;

    }

    @Override
    public double area() {

        // Heronsche Formel: gefunden über chatgpt danach slebst gegoogelt ob es die überhaupt gibt
        // Ich wusste nicht wie ich die Grundseite bestimmen sollte für 1/2 gh

        Vector2D A = vertices[0];
        Vector2D B = vertices[1];                      //danke Inteli Code vervollständger
        Vector2D C = vertices[2];

        Vector2D c = new Vector2D(B.getX() - A.getX(), B.getY() - A.getY());
        Vector2D b = new Vector2D(C.getX() - A.getX(), C.getY() - A.getY());
        Vector2D a = new Vector2D(C.getX() - B.getX(), C.getY() - B.getY());


        double lengtha = Math.sqrt(a.getX()*a.getX() + a.getY()*a.getY());
        double lengthb = Math.sqrt(b.getX()*b.getX() + b.getY()*b.getY());
        double lengthc = Math.sqrt(c.getX()*c.getX() + c.getY()*c.getY());

        double Umfang = (lengtha + lengthb + lengthc) /2;

        double area = Math.sqrt(Umfang * (Umfang - lengthc) * (Umfang - lengtha) * (Umfang - lengthb));
        return area;
    }

    //@Override
   // public String toString() {
         //TODO
      //  }

    public static void main(String[] args) {
        Vector2D a = new Vector2D(0, 0);
        Vector2D b = new Vector2D(10, 0);
        Vector2D c =  new Vector2D(5, 5);
        Triangle triangle = new Triangle(a, b, c);
        double area = triangle.area();
        System.out.printf("Die Fläche des Dreiecks 'triangle' {%s, %s, %s} beträgt %.1f LE^2.\n", a, b, c, area);

        Triangle triangle2 = new Triangle(triangle);
        System.out.println("triangle2 ist eine Kopie per Copy-Konstruktor von 'triangle': " + triangle2);
        a.setX(-5);
        System.out.println("Eckpunkt 'a', der zur Definition von 'triangle' verwendet wurde, wird geändert.");
        System.out.println("Nun ist der Wert von 'triangle2': " + triangle2);
        /*
        Die erwartete Ausgabe ist:
Die Fläche des Dreiecks 'triangle' {(0.0, 0.0), (10.0, 0.0), (5.0, 5.0)} beträgt 25,0 LE^2.
triangle2 ist eine Kopie per Copy-Konstruktor von 'triangle': Triangle{[(0.0, 0.0), (10.0, 0.0), (5.0, 5.0)]}
Eckpunkt 'a', der zur Definition von 'triangle' verwendet wurde, wird geändert.
Nun ist der Wert von 'triangle2': Triangle{[(-5.0, 0.0), (10.0, 0.0), (5.0, 5.0)]}
         */
    }
}

