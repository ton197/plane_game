public class Bullet {

    public int xc,yc;
    public int X = 250,Y = 50;

    public Bullet(){
        xc = bgj(angle_x(5),angle_x(5));
        yc = bgj(angle_y(5),angle_y(5));

    }

    private int angle_x(int velocity){
        int r = (int) (Math.random()* velocity);
        return r;
    }

    private int angle_y(int velocity) {
        int r = (int) (Math.random() * velocity);
        return r;
    }

    private static int bgj(int x1,int x2){
        int z = x2 - x1;
        return z;
    }

}
