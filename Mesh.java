// Mesh.java: Mesh特徴による文字認識クラス
import java.io.*;

class Mesh {
    static final int BLACK = 0x00;
    static final int WHITE = 0xFF;
    static final int LARGE_VALUE = 99999;/* 非常に大きな値:+∞の代用値 */
    static final int SMALL_VALUE = -99999;
    /* 非常に小さな値:-∞の代用値 */

    static final int N = 7; //メッシュの横方向の数
    static final int M = 7; //メッシュの縦方向の数
    public static final int K = (N*M);

    static final int C = 10;
    /*** Feature1: メッシュ特徴量を計測するメソット゛ ***/
    

	//外接四角形内のメッシュ特徴量
	public static void Feature2(PgmImage pgm, double w[]) {
		// 文字の外接四角形
		int xmin = 1000000;
		int xmax = -1;
		int ymin = 1000000;
		int ymax = -1;
		int num = 0;
		for(int y=0; y<pgm.ys; y++){
			for(int x=0; x<pgm.xs; x++){
				if(pgm.img[x][y] == BLACK){
					if(x < xmin) xmin = x;
					if(y < ymin) ymin = y;
					if(x > xmax) xmax = x;
					if(y > ymax) ymax = y;
				}
			}
		}
		int wide = xmax-xmin+1;//横
		int hight = ymax-ymin+1;//縦
		
		//メッシュ特徴量
        int[] b = new int[K];
        int[] s = new int[K];
        double x_len = (double)wide/N;
        double y_len = (double)hight/M;
        int k;
        
        for(int y=ymin; y<=ymax; y++){
            for(int x=xmin; x<=xmax; x++){
				k = (int)((y-ymin)/y_len)*N + (int)((x-xmin)/x_len);
				//System.out.println(k);
                s[k] ++;
                if(pgm.img[x][y]==BLACK){
                    b[k] ++;  
                }
            }
        }

        for(k=0; k<K; k++){
			if(s[k]>0)
				w[k] = (double)b[k]/s[k];
            else
			
				w[k] = 0.0;
        }
    }
	

	
	public static int Reco2(PgmImage pgm){
        double[] w = new double[K];
		int i, j;
		
		double pv[][] = {
			{0.16, 0.14, 0.18, 0.17, 0.00, 0.18, 0.21, 0.12, 0.18},
			{0.11, 0.27, 0.42, 0.08, 0.38, 0.19, 0.27, 0.24, 0.19},
			{0.15, 0.14, 0.20, 0.04, 0.17, 0.06, 0.28, 0.16, 0.13},
			{0.12, 0.12, 0.22, 0.06, 0.18, 0.24, 0.10, 0.12, 0.21},
			{0.05, 0.22, 0.01, 0.18, 0.20, 0.01, 0.17, 0.27, 0.11},
			{0.16, 0.19, 0.11, 0.15, 0.14, 0.12, 0.14, 0.15, 0.22},
			{0.14, 0.16, 0.09, 0.32, 0.15, 0.12, 0.25, 0.14, 0.26},
			{0.20, 0.14, 0.29, 0.01, 0.10, 0.10, 0.05, 0.10, 0.05},
			{0.19, 0.18, 0.23, 0.17, 0.24, 0.13, 0.25, 0.13, 0.23},
			{0.25, 0.12, 0.26, 0.09, 0.19, 0.16, 0.12, 0.13, 0.04}
		};
		
		Feature2(pgm, w);
		
		// *** 識別(始) ***
		int result = -1; // 認識結果: result
		double dmin = (double)LARGE_VALUE; // 距離の最小値dmin (方法[1]用)
		double dmax = (double)SMALL_VALUE;; // 識別関数の最大値dmax (方法[2]用)
		double dx; // i番目のカテゴリとの距離または関数値保存用dx
		double sum = 0;
		double[] array = new double[C];
		
		for(i=0; i<C; i++){
			sum=0.0;
			for(j=0; j<K; j++){
				sum += (w[j]-pv[i][j]) * (w[j]-pv[i][j]);
			}
			
			dx = Math.sqrt(sum);
			array[i] = dx;
		}
		
		for(int k=0; k<C; k++){
			if(array[k] < dmin){
				result = k;
				dmin = array[k];
			}
		}
		
		return result;
    }
}