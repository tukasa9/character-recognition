import java.io.*;

class Cross {
    static final int BLACK = 0x00;
    static final int WHITE = 0xFF;
    static final int LARGE_VALUE =99999;
    static final int SMALL_VALUE =-99999;
    public static final int K = 6; 

    // Feature1
    public static void Feature1(PgmImage pgm, int w[]) {
        int x, y;
        int xmin, xmax, ymin, ymax;
        int width, height;
        int stat;
        int k = 0;
        int Ny = 5;
        int Nx = 3;

        
        xmin = 1000000;
		xmax = -1;
		ymin = 1000000;
		ymax = -1;
		for(y=0; y<pgm.ys; y++){
			for(x=0; x<pgm.xs; x++){
				if(pgm.img[x][y] == BLACK){
					if(x < xmin) xmin = x;
					if(y < ymin) ymin = y;
					if(x > xmax) xmax = x;
					if(y > ymax) ymax = y;
				}
			}
		}
		width = xmax-xmin+1;
		height = ymax-ymin+1;
        

        //êÖïΩï˚å¸ÇÃåç∑ì¡í•ó 
        for(int i=1; i<Ny; i++){
            y= ymin + i*height/Ny;
            w[k] = 0;
            stat = WHITE;
            for(x=xmin; x<=xmax; x++){
                if((stat==WHITE) && (pgm.img[x][y]==BLACK)){
                    stat = BLACK;
                    w[k]++;
                }
                else if(pgm.img[x][y]==WHITE){
                stat = WHITE;
                }
            }
            k++;
        }
		
		//êÇíºï˚å¸ÇÃåç∑ì¡í•ó 
        for(int j=1; j<Nx; j++){
            x= xmin + j*width/Nx;
            w[k] = 0;
            stat = WHITE;
            for(y=ymin; y<=ymax; y++){
                if((stat==WHITE) && (pgm.img[x][y]==BLACK)){
                    stat = BLACK;
                    w[k]++;
                }
                else if(pgm.img[x][y]==WHITE){
                stat = WHITE;
                }
            }
            k++;
        }
    }
 
    public static int Reco1(PgmImage pgm){
        int[] w = new int[K]; 
        int pv[][] = { 
            {0, 2, 2, 2, 2, 2, 2}, 
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 2, 1},
            {2, 2, 1, 1, 1, 3, 3},
            {3, 2, 1, 1, 1, 3, 4},
            {3, 1, 1, 1, 1, 3, 4},
            {4, 2, 2, 2, 1, 2, 1},
            {4, 2, 2, 2, 1, 1, 1},
            {5, 1, 1, 1, 1, 3, 3},
            {6, 1, 1, 2, 2, 3, 3},
            {6, 1, 2, 2, 2, 3, 3},
            {7, 2, 1, 1, 1, 1, 1},
            {7, 2, 1, 1, 1, 2, 2},
            {8, 2, 2, 2, 2, 3, 4},
            {8, 2, 1, 2, 2, 4, 4},
            {9, 2, 3, 1, 1, 3, 3},
            {9, 2, 2, 1, 1, 2, 3},
            {-1, 0, 0, 0, 0, 0, 0} 
        };

        Feature1(pgm, w);
        
        int result = -1;
        int i =0;
        while(pv[i][0] >= 0) {
            boolean flag = true;
            for(int j=0; j<K; j++){
                if(pv[i][j+1] != w[j]) { 
                    flag = false; 
                    break;
                }
            }
            if(flag == true) { 
                result = pv[i][0]; 
                break;
            }
            i++;
        }
		
		
        

        
        return result;
    }
}
