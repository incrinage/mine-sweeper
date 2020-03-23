public class Block {
    private boolean marked;
    private boolean mine;
    private int sum;
    private boolean revealed;
    private Type type;

    public Block() {
        this.marked = false;
        this.mine = false;
        this.sum = 0;
        this.revealed = false;
        this.type = Type.HIDDEN;
    }

    public void toggleMine() {
        this.mine = !this.mine;
    }

    public boolean isMine() {
        return this.mine;
    }

    public void increment() {
        this.sum++;
    }

    public boolean reveal() {
        this.revealed = true;
        if (this.mine && !this.marked) {
            this.type = Type.MINE;
            return false;
        }  else if (sum > 0) {
            this.type = Type.NUM;
        } else {
            this.type = Type.CLEARED;
        }
        return true;
    }

    public boolean isRevealed(){
        return this.revealed;
    }

    public int getSum() {
        return this.sum;
    }

    public void toggleMark() {
        if(getSum() > 0 && isRevealed()) return;

        this.marked = !this.marked;

        if(this.marked){
            this.type = Type.MARKED;
        } else {
            this.type = Type.HIDDEN;
        }
    }

    public void draw() {
        if(this.type == Type.NUM ){
            System.out.print(" " + this.sum + " ");
        } else{
            System.out.print(" "  + this.type.sym + " ");
        }
    }


    enum Type {
        NUM('#'), CLEARED('C'), HIDDEN('?'), MINE('*'), MARKED('F');
        final char sym;
        Type(char c) {
            this.sym = c;
        }
    }
}
