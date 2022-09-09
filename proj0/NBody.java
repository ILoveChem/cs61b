public class NBody {
    /**
     * @param filename Name of 
     * @return
     */
    public static double readRadius(String filename) {
        In content = new In(filename);
        double number = content.readInt();
        double radius = content.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String filename) {
        In content = new In(filename);
        int number = content.readInt();
        double radius = content.readDouble();
        Planet[] ansArrary = new Planet[number];

        for (int i = 0; i < number; i++) {
            double xPos = content.readDouble();
            double yPos = content.readDouble();
            double xVel = content.readDouble();
            double yVel = content.readDouble();
            double mas = content.readDouble();
            String name = content.readString();
            Planet newPlanet = new Planet(xPos, yPos, xVel, yVel, mas, name);
            ansArrary[i] = newPlanet;
        }

        return ansArrary;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] body = readPlanets(filename);
        double radius = readRadius(filename);
        String backgroundFileName = "images/starfield.jpg";
    
        StdDraw.setScale(0 - radius, radius);
        StdDraw.picture(0, 0, backgroundFileName);
        
        for (Planet p: body) {
            p.draw();
        }
        
        StdDraw.enableDoubleBuffering();

        double loopTime = 0;
        while (loopTime <= T) {
            double[] xForce = new double[body.length];
            double[] yForce = new double[body.length];
            for (int i = 0; i < body.length; i ++) {
                xForce[i] = body[i].calcNetForceExertedByX(body);
                yForce[i] = body[i].calcNetForceExertedByY(body);
            }
            StdDraw.picture(0, 0, backgroundFileName);
            for (int i = 0; i < body.length; i++) {
                body[i].update(dt, xForce[i], yForce[i]);
                body[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            loopTime += dt;
        }
        StdOut.printf("%d\n", body.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < body.length; i++) {
        StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  body[i].xxPos, body[i].yyPos, body[i].xxVel,
                  body[i].yyVel, body[i].mass, body[i].imgFileName);   
        }
    }
}