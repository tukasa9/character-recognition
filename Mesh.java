// Mesh.java: Mesh�����ɂ�镶���F���N���X
import java.io.*;

class Mesh {
    static final int BLACK = 0x00;
    static final int WHITE = 0xFF;
    static final int LARGE_VALUE = 99999;/* ���ɑ傫�Ȓl:+���̑�p�l */
    static final int SMALL_VALUE = -99999;
    /* ���ɏ����Ȓl:-���̑�p�l */

    static final int N = 7; //���b�V���̉������̐�
    static final int M = 7; //���b�V���̏c�����̐�
    public static final int K = (N*M);

    static final int C = 10;
    /*** Feature1: ���b�V�������ʂ��v�����郁�\�b�g�J ***/
    

	//�O�ڎl�p�`���̃��b�V��������
	public static void Feature2(PgmImage pgm, double w[]) {
		// �����̊O�ڎl�p�`
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
		int wide = xmax-xmin+1;//��
		int hight = ymax-ymin+1;//�c
		
		//���b�V��������
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
		
		// *** ����(�n) ***
		int result = -1; // �F������: result
		double dmin = (double)LARGE_VALUE; // �����̍ŏ��ldmin (���@[1]�p)
		double dmax = (double)SMALL_VALUE;; // ���ʊ֐��̍ő�ldmax (���@[2]�p)
		double dx; // i�Ԗڂ̃J�e�S���Ƃ̋����܂��͊֐��l�ۑ��pdx
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