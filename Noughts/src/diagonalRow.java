import java.util.ArrayList;
import java.util.List;

public class diagonalRow {

    private List<Integer> x;
    private List<Integer> y;
    private int amountdone;
    private int maxSize;
    private SquareEnum player;

    public diagonalRow(int maxSize_, SquareEnum player_)
    {
        x = new ArrayList<>();
        y = new ArrayList<>();

        maxSize = maxSize_;
        amountdone = 1;
        player_ = player_;
    }

    public void addXAndY (int xNew, int yNew)
    {
        if(amountdone == maxSize)
            return;

        x.add(xNew);
        y.add(yNew);

        amountdone++;
    }

    public int getX(int index) {
        return x.get(index);
    }
    public int getY(int index) {
        return y.get(index);
    }

    public List<Integer> getArrayListX ()
    {
        return x;
    }
    public List<Integer> getArrayListY ()
    {
        return y;
    }

    public boolean winner (SquareEnum[][] grid)
    {
        int wins = 0;

        for (int x = 0; x < maxSize; x++)
        {
            for (int y = 0; y < maxSize; y++)
            {
                if(!getArrayListX().contains(x) || !getArrayListY().contains(y))
                    continue;

                SquareEnum player__ = grid[x][y];
                if(y == x && player == player__)
                {
                    wins++;
                }


                if(player == player__)
                    wins++;
            }
        }

        if(wins == maxSize)
            return true;
        else
            wins = 0;

        for (int x = 0; x < maxSize; x++)
        {
            for (int y = 0; y < maxSize; y++)
            {
                if(!getArrayListX().contains(x) || !getArrayListY().contains(y))
                    continue;
                SquareEnum player__ = grid[x][y];
                if(player != player__)
                    continue;

                if(x == 0 && y == maxSize)
                    wins++;
                if(y == 0 && x == maxSize)
                    wins++;
                if(x == y)
                    wins++;

            }
        }

        if(wins == maxSize)
            return true;
        else
            return false;
    }
}
