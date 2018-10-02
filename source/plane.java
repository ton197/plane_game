import java.io.IOException;

public class plane{

    int x = 250,y = 400;


    public int move(String s, int distance) throws IOException{
        if (s == "x"){
            x += distance;
            return x;
        } else if (s == "y"){
            y += distance;
            return y;
        } else {
            throw new IOException("No coordinate axis:" + s);
        }
    }

    public plane() {
    }

}
