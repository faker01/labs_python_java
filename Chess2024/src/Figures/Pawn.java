package Figures;

public class Pawn extends Figure {
    private  boolean isFirstStep = true;
    public Pawn(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
//        if (!super.canMove(row, col, row1, col1)){
//            return false;
//        }
        if(this.isFirstStep){
            if (((((row+2==row1)||(row+1==row1)) && this.getColor()=='w') ||
                    (((row-2==row1)||(row-1==row1)) && this.getColor()=='b') ) && (col==col1)){
                this.isFirstStep = false;
                return true;
            }
        }else{
            if ((((row+1==row1)) && this.getColor()=='w') || (((row-1==row1)) && this.getColor()=='b')&& (col==col1)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1) {
        if (Math.abs(row - row1) ==1 && Math.abs(col-col1)==1){
            return true;
        }
        return false;
    }

    @Override
    public int[] getPathCells(int row, int col, int row1, int col1) {
        if ( Math.abs(row1 - row) == 2) {
            int[] cells = new int[4];
            cells[0] = row + ((row1 - row)/2);
            cells[1] = col;
            cells[2] = row1;
            cells[3] = col1;
            return cells;
        }
        else {
            int[] cells = new int[2];
            cells[0] = row1;
            cells[1] = col1;
            return cells;
        }
    }
}
