package ce326.hw2;


public class evalObject {
    private int selectedEval;
    private Pawn selectedMove;

    public evalObject() {
        selectedEval = 0;
        selectedMove = null;
    }


    public int getSelectedEval() {
        return selectedEval;
    }

    public Pawn getSelectedMove() {
        return selectedMove;
    }


    public void setSelectedEval(int selectedEval) {
        this.selectedEval = selectedEval;
    }

    public void setSelectedMove(Pawn selectedMove) {
        this.selectedMove = selectedMove;
    }

}