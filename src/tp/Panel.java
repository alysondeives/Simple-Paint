/*
 * Copyright(C) 2013 Alyson D. Pereira <alyson.deives@outlook.com>
 */
 
package tp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;

/**
 *
 * @author Alyson Deives Pereira
 */
public class Panel extends javax.swing.JPanel {
    private int X1 = -1;
    private int Y1 = -1;
    private int X2 = -1;
    private int Y2 = -1;
    private int X3 = -1;
    private int Y3 = -1;
    private int X4 = -1;
    private int Y4 = -1;
    public int X;
    public int Y;
    public int OP = 0;
    boolean [][] visitado;

    /**
     * Creates new form Panel
     */
    public Panel() {
        initComponents();
        this.setBackground(Color.white);
        setVisitados();
        
        setBorder(BorderFactory.createLineBorder(Color.black));
        
        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                Janela.jTextField1.setText(e.getX()+","+e.getY());
            }
        });
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                switch (OP){
                    case 0:
                         drawPoint(e.getX(),e.getY());
                         break;
                    case 1:
                        if(X1 == -1){
                            X1 = e.getX(); Y1 = e.getY();
                        }
                        else{
                            X2 = e.getX(); Y2 = e.getY();
                        }
                        if(X1 != -1 && X2 != -1){
                            bresenham(X1,Y1,X2,Y2);
                            X1 = -1; X2 = -1;
                        }
                        break;
                    case 2:
                        if(X1 == -1){
                            X1 = e.getX(); Y1 = e.getY();
                        }
                        else{
                            X2 = e.getX(); Y2 = e.getY();
                        }
                        if(X1 != -1 && X2 != -1){
                            int r = (int) Math.sqrt(Math.pow(X1-X2, 2)+Math.pow(Y1-Y2, 2));
                            circBresenham(X1,Y1,r);
                            X1 = -1; X2 = -1;
                        }
                        break;
                    case 3:
                         if(X1 == -1){
                                X1 = e.getX(); Y1 = e.getY();
                        }
                        else if(X2 == -1){
                                X2 = e.getX(); Y2 = e.getY();
                        }
                        else{
                            X3 = e.getX(); Y3 = e.getY();
                            int width = (int) Math.sqrt(Math.pow(X1-X2, 2)+Math.pow(Y1-Y2, 2));
                            int height = (int) Math.sqrt(Math.pow(X1-X3, 2)+Math.pow(Y1-Y3, 2));
                            ellipseBresenham(X1, Y1, width, height);
                            X1 = -1; X2 = -1;X3 = -1;
                        }
                        break;
                    case 4:
                        if(X1 == -1){
                                X1 = e.getX(); Y1 = e.getY();
                        }
                        else if(X2 == -1){
                                X2 = e.getX(); Y2 = e.getY();
                        }
                        else if(X3 == -1){
                                X3 = e.getX(); Y3 = e.getY();
                        }
                        else{
                            X4 = e.getX(); Y4 = e.getY();
                            bezier();
                            X1 = -1; X2 = -1;X3 = -1; X4 = -1;
                        }
                        break;
                    case 5:
                        if(X1 == -1){
                            X1 = e.getX(); Y1 = e.getY();
                        }
                        else if(X2 == -1){
                            X2 = e.getX(); Y2 = e.getY();
                        }
                        else if(X3 == -1){
                            X3 = e.getX(); Y3 = e.getY();
                        }
                        else{
                            X4 = e.getX(); Y4 = e.getY();
                            hermite();
                            X1 = -1; X2 = -1;X3 = -1; X4 = -1;
                        }
                        break;
                    case 6:
                        flood4(e.getX(), e.getY(), Color.BLACK, getColor(e.getX(), e.getY()));
                        break;
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                //moveSquare(e.getX(),e.getY());
                X = e.getX();
                Y = e.getY();
                switch (OP){
                    case 0:
                        drawPoint(e.getX(),e.getY());
                        break;
                }        
            }
        });
    }
    
    private void drawPoint(int x, int y){
        Graphics g = this.getGraphics();
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 1, 1);
    }
    
    public void drawPoint(int x, int y, Color color){
        Graphics g = this.getGraphics();
        g.setColor(color);
        g.drawRect(x, y, 1, 1);
    }
    
    public void bresenham(int x1, int y1, int x2, int y2){
        int dx,dy,x,y,const1,const2,p,incrx,incry;
        dx=x2-x1;
        dy=y2-y1;
        if(dx>=0){
            incrx = 1;
        }
        else{
            incrx=-1;
            dx=-dx;
        }
        if(dy>=0){
            incry=1;
        }
        else{
            incry=-1;
            dy=-dy;
        }
        x = x1;
        y = y1;
        drawPoint(x, y);
        if(dy<dx){
            p = 2*dy-dx;
            const1=2*dy;
            const2=2*(dy-dx);
            for(int i=0;i<dx;i++){
                x+=incrx;
                if(p<0){
                    p+=const1;
                }
                else{
                    y+=incry;
                    p+=const2;
                }
                drawPoint(x, y);
            }
        }
        else{
            p = 2*dx-dy;
            const1=2*dx;
            const2=2*(dx-dy);
            for(int i=0;i<dy;i++){
                y+=incry;
                if(p<0){
                    p+=const1;
                }
                else{
                    x+=incrx;
                    p+=const2;
                }
                drawPoint(x, y);
            }
        }
    }
    
    void drawCirclePoints(int xc, int yc, int x, int y){
            drawPoint(xc+x, yc+y);
            drawPoint(xc-x, yc+y);
            drawPoint(xc+x, yc-y);
            drawPoint(xc-x, yc-y);
            drawPoint(xc+y, yc+x);
            drawPoint(xc-y, yc+x);
            drawPoint(xc+y, yc-x);
            drawPoint(xc-y, yc-x);
    }
    
    void circBresenham(int xc, int yc, int r){
        int x=0,y=r,p=3-2*r;
        drawCirclePoints(xc, yc, x, y);
        while(x<y){
            if(p<0){
                p = p+4*x+6;
            }
            else{
                p = p+4*(x-y)+10;
                y--;
            }
            x++;
            drawCirclePoints(xc, yc, x, y);
        } 
    }
    
    void ellipseBresenham (int xc, int yc, int width, int height){
        int a2 = width * width;
        int b2 = height * height;
        int fa2 = 4 * a2, fb2 = 4 * b2;
        int x, y, sigma;

        for (x = 0, y = height, sigma = 2*b2+a2*(1-2*height); b2*x <= a2*y; x++)
        {
            drawPoint (xc + x, yc + y);
            drawPoint (xc - x, yc + y);
            drawPoint (xc + x, yc - y);
            drawPoint (xc - x, yc - y);
            if (sigma >= 0){
                sigma += fa2 * (1 - y);
                y--;
            }
            sigma += b2 * ((4 * x) + 6);
        }

        for (x = width, y = 0, sigma = 2*a2+b2*(1-2*width); a2*y <= b2*x; y++){
            drawPoint (xc + x, yc + y);
            drawPoint (xc - x, yc + y);
            drawPoint (xc + x, yc - y);
            drawPoint (xc - x, yc - y);
            if (sigma >= 0){
                sigma += fb2 * (1 - x);
                x--;
            }
            sigma += a2 * ((4 * y) + 6);
        }
    }
    
    void hermite(){
        int i=0, totalPts=4;
        int [] x = {X1,X2,X3,X4};
        int [] y = {Y1,Y2,Y3,Y4};
        while(i+1 < totalPts){
            double RangeX = ((x[i+1]-x[i])<0)?(-(x[i+1]-x[i])):(x[i+1]-x[i]);
            double RangeY = ((y[i+1]-y[i])<0)?(-(y[i+1]-y[i])):(y[i+1]-y[i]);
            double Step = (RangeX > RangeY)?(Step=(1.0/RangeX)):(Step=(1.0/RangeY));
            //Determinacao automatica das tangentes
            double t1x=x[0],t2x=x[1],t1y=y[0]-30,t2y=y[1]-30;
            if(i==0){
                t1x = x[i+1]-x[i];
                t2x = x[i+2]-x[i];
                t1y = y[i+1]-y[i];
                t2y = y[i+2]-y[i];
            }else{
                if((i!=0) && (i!=(totalPts-2))){
                    t1x = x[i+1]-x[i-1];
                    t2x = x[i+2]-x[i];
                    t1y = y[i+1]-y[i-1];
                    t2y = y[i+2]-y[i];
                }else{
                    t1x = x[i+1]-x[i-1];
                    t2x = x[i+1]-x[i];
                    t1y = y[i+1]-y[i-1];
                    t2y = y[i+1]-y[i];
                }
            }
            double WG =0.5,X=0,Y=0,Xant=0,Yant=0;
            for(double t=0;t<=1;t+=Step){
                X = 0.5 + ((2*Math.pow(t,3) -3*Math.pow(t,2) +1)* x[i] + (-2*Math.pow(t,3)+3*Math.pow(t,2))*x[i+1] + (1*Math.pow(t,3)-2*Math.pow(t,2)+t)*WG*t1x + (1*Math.pow(t,3)-1*Math.pow(t,2))*WG*t2x);
                Y = 0.5 + ((2*Math.pow(t,3) -3*Math.pow(t,2) +1)* y[i] + (-2*Math.pow(t,3)+3*Math.pow(t,2))*y[i+1] + (1*Math.pow(t,3)-2*Math.pow(t,2)+t)*WG*t1y + (1*Math.pow(t,3)-1*Math.pow(t,2))*WG*t2y);
                if(t == 0){
                    Xant=X;
                    Yant=Y;
                }else{
                    bresenham((int)Xant,(int)Yant,(int)X,(int)Y);
                    Xant=X;
                    Yant=Y;
                }
            }
            i++;
        }
    }
    
    Color getColor(int x, int y){
        BufferedImage image = (BufferedImage) this.createImage(this.getWidth(), this.getHeight());
        BufferedImage image2 =  new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        image2.setData(image.getRaster());
        int rgb = image2.getRGB(x, y);
        Color color = new Color(rgb);
        return color;
    }
    
    void setVisitados(){
        visitado = new boolean[this.getWidth()][this.getHeight()];
        for(int i = 0;i<this.getWidth();i++){
             for(int j = 0;j<this.getHeight();j++){
                 visitado[i][j] = false;
             }
        }
    }
    
    void flood4(int x, int y, Color nova, Color antiga){      
        if (x < 0) return;
        if (y < 0) return;
        if (x >= this.getWidth()) return;
        if (y >= this.getHeight()) return;
        if (visitado[x][y]) return;
        Color cor = getColor(x, y);
        if(!cor.toString().equals(antiga.toString())) return;
        System.out.println(x+" "+y+" "+cor.toString()+" "+antiga.toString()+" "+nova.toString());
        drawPoint(x, y, nova);
        visitado[x][y] = true;
        flood4(x+1,y,nova,antiga);
        flood4(x-1,y,nova,antiga);
        flood4(x,y+1,nova,antiga);
        flood4(x,y-1,nova,antiga);
    }
    
    void bezier(){
        double x1,x2,y1,y2;
        double t,k=0.025;
        x1 = X1;
        y1 = Y1;
        for(t=k;t<=1+k;t+=k){
            x2=(X1+t*(-1*X1*3+t*(3*X1-X1*t)))+t*(3*X2+t*(-6*X2+X2*3*t))+t*t*(X3*3-X3*3*t)+X4*t*t*t;
            y2=(Y1+t*(-1*Y1*3+t*(3*Y1-Y1*t)))+t*(3*Y2+t*(-6*Y2+Y2*3*t))+t*t*(Y3*3-Y3*3*t)+Y4*t*t*t;
            bresenham((int)x1,(int)y1,(int)x2,(int)y2);
            x1 = x2;
            y1 = y2;
        }
    }
    

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setVisitados();
    }  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jOptionPane1 = new javax.swing.JOptionPane();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JOptionPane jOptionPane1;
    // End of variables declaration//GEN-END:variables
}
