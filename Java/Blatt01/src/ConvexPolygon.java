public class ConvexPolygon extends Polygon {
    @Override
    public double perimeter() {
        return 0;
    }

    @Override
    public double area() {
        return 0;
    }

    @Override
    public String ToString(){

        StringBuilder builder = new StringBuilder("ConvexPolygon([(");

        for (int i = 0; i < vertices.length; i++){

            builder.append(vertices[i].getX());
            builder.append(", ");
            builder.append(vertices[i].getY());
            builder.append(") ");

            if (i < vertices.length - 1){

                builder.append(", ");
                builder.append( "(");

            }


        }
        builder.append("])");
        return builder.toString();

    }



}


