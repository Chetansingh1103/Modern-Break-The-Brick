import java.awt.*;

public class MapGenerator2 {
    public int map2[][];
    public int brickWidth;
    public int brickHeight;
    public MapGenerator2(int row,int col){
        map2=new int[row][col];
        for(int i=0;i<map2.length;i++){
            if(i!=map2.length-1) {
                for (int j = 0; j < map2[0].length; j++) {
                    map2[i][j] = 1;
                }
            }
            else{
                for (int j = 0; j < map2[0].length; j++) {
                    map2[i][j] = 2;
                }
            }
        }
        brickWidth = 540/col;
        brickHeight = 150/row;
    }
    public void drawM(Graphics2D g){
        for(int i=0;i<map2.length;i++){
            for(int j=0;j<map2[0].length;j++) {
                if(map2[i][j]==1){
                    g.setColor(Color.white);
                    g.fillRect(j*brickWidth+80,i*brickHeight+50,brickWidth,brickHeight);

                    g.setStroke(new BasicStroke(20));
                    g.setColor(Color.black);
                    g.drawRect(j*brickWidth+80,i*brickHeight+50,brickWidth,brickHeight);
                }
                if(map2[i][j]==2){
                    g.setColor(Color.red);
                    g.fillRect(j*brickWidth+80,i*brickHeight+50,brickWidth,brickHeight);

                    g.setStroke(new BasicStroke(8));
                    g.setColor(Color.black);
                    g.drawRect(j*brickWidth+80,i*brickHeight+50,brickWidth,brickHeight);
                }
            }
        }
    }
    public void setBrickValueM(int value,int row,int col){
        map2[row][col]=value;
    }
}
