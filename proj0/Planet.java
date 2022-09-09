public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    /**
     * calculate the distance between two planets.
     * @param anotherPlanet another planet.
     * @return return distance between two planets.
     */
    public double calcDistance(Planet anotherPlanet) {
        double xDistance = Math.pow((this.xxPos - anotherPlanet.xxPos), 2);
        double yDistance = Math.pow((this.yyPos - anotherPlanet.yyPos), 2);
        
        return Math.sqrt(xDistance + yDistance);
    }

    /**
     * @param anotherPlanet another planet.
     * @return  return the force between two planets, always positive.
     */
    public double calcForceExertedBy(Planet anotherPlanet) {
        double gravityConstant = 6.67e-11;
        double distance = this.calcDistance(anotherPlanet);
        double force = gravityConstant * this.mass * anotherPlanet.mass / (Math.pow(distance, 2));
        return force;
    }

    /**
     * @param anotherPlanet another planet.
     * @return return the force between two planets on x-axis, which can be either positive or negative.
     */
    public double calcForceExertedByX(Planet anotherPlanet) {
        double force = this.calcForceExertedBy(anotherPlanet);
        double dX = anotherPlanet.xxPos- this.xxPos;
        double distance = this.calcDistance(anotherPlanet);
        double ans = force * dX / distance;
        return ans;
    }

    /**
     * @param anotherPlanet another planet.
     * @return return the force between two planets on y-axis, which can be either positive or negative.
     */
    public double calcForceExertedByY(Planet anotherPlanet) {
        double force = this.calcForceExertedBy(anotherPlanet);
        double dY = anotherPlanet.yyPos - this.yyPos;
        double distance = this.calcDistance(anotherPlanet);
        return force * dY / distance;
    }

    /**
     * @param allPlanet arrary of Planet object.
     * @return return the net force on x-axis, noticed that force will be calculated if Planet object in allPlanet is itself.
     */
    public double calcNetForceExertedByX(Planet[] allPlanet) {
        double ans = 0;
        for (int i = 0; i < allPlanet.length; i++) {
            if (this.equals(allPlanet[i])) {
                continue;
            }
            double currentForceX = this.calcForceExertedByX(allPlanet[i]);
            ans += currentForceX;
        }
        return ans;
    }

    /**
     * @param allPlanet arrary of Planet object.
     * @return return the net force on y-axis, noticed that force will be calculated if Planet object in allPlanet is itself.
     */
    public double calcNetForceExertedByY(Planet[] allPlanet) {
        double ans = 0;
        for (int i = 0; i< allPlanet.length; i++) {
            if (this.equals(allPlanet[i])) {
                continue;
            }
            double currentForce = this.calcForceExertedByY(allPlanet[i]);
            ans += currentForce;
        }
        return ans;
    }

    /**
     * update dynamic information of Planet
     * @param dt time interval.
     * @param fX force applied on object on x-axis direction.
     * @param fY force applied on object on y-axis direction.
     */
    public void update(double dt, double fX, double fY) {
        double xAce = fX / this.mass;
        this.xxVel += xAce * dt;
        double yAce = fY / this.mass;
        this.yyVel += yAce * dt;

        this.xxPos += this.xxVel * dt;
        this.yyPos += this.yyVel * dt;
    }

    public void draw() {
        String filename = "images/" + this.imgFileName;
        StdDraw.picture(this.xxPos, this.yyPos, filename);
    }
}
