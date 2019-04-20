import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI2 {

    //Obvious (should be)
    private JFrame selectFrame;
    private GridLayout selectLay;
    private Dimension frameDimension;
    private int closeOp, sizeMultiplier, size;

    //Buttons for X and O
    private JButton[][] selectButtons;

    private SquareEnum[][] grid;

    private Font font;

    private String fontName = "Avenir";

    private boolean isNoughts;

    private diagonalRow NoughtsDiagonal, CrossesDiagonal;

    public GUI2(int size, boolean noughtStarts)
    {
        //region General Init
        this.size = size;

        if(noughtStarts)
            isNoughts = true;
        else
            isNoughts = false;

        selectFrame = new JFrame("Noughts And Crosses - " + helper.getTextPlaying(helper.getPlayer(isNoughts)) + " is playing.");

        sizeMultiplier = 150;
        closeOp = WindowConstants.EXIT_ON_CLOSE;
        font = new Font(fontName, Font.PLAIN, 18);

        frameDimension = new Dimension(size * sizeMultiplier, size * sizeMultiplier);
        selectLay = new GridLayout(size, size);

        selectFrame.setResizable(false);
        selectFrame.setPreferredSize(frameDimension);
        selectFrame.setLayout(selectLay);
        selectFrame.setDefaultCloseOperation(closeOp);

        NoughtsDiagonal = new diagonalRow(size, SquareEnum.nought);
        CrossesDiagonal = new diagonalRow(size, SquareEnum.cross);

        selectButtons = new JButton[size][size];
        grid = new SquareEnum[size][size];
        //endregion

        //region 2D Array Init
        for(int x = 0; x < size; x++)
        {
            for(int y = 0; y < size; y++)
            {
                int x2 = x + 1;
                int y2 = y + 1;


                selectButtons[x][y] = new JButton(" ");
                selectButtons[x][y].setFont(font);

                selectFrame.add(selectButtons[x][y]);

                grid[x][y] = SquareEnum.unactive;

            }
        }

        //endregion

        //region Button Action Listeners
        for(int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                final int x2 = x;
                final int y2 = y;

                selectButtons[x][y].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SquareEnum player = helper.getSquareEnumFromPlayer(isNoughts);

                        changeSquare(player, x2, y2);
                        didTheyWin();
                    }
                });
            }
        }
        //endregion

        //region JFrame end stuff
        selectFrame.pack();
        selectFrame.setLocationRelativeTo(null);
        selectFrame.setVisible(true);
        //endregion
    }

    private void changeSquare (SquareEnum sq, int x, int y)
    {
        grid[x][y] = sq;

        selectButtons[x][y].setEnabled(false);

        switch (sq)
        {
            case cross:
                selectButtons[x][y].setText(helper.getTextPlaying(SquareEnum.cross));
                System.out.println(helper.getXY(x, y) + " as " + SquareEnum.cross);
                CrossesDiagonal.addXAndY(x, y);
                break;
            case nought:
                selectButtons[x][y].setText(helper.getTextPlaying(SquareEnum.nought));
                System.out.println(helper.getXY(x, y) + " as " + SquareEnum.nought);
                NoughtsDiagonal.addXAndY(x, y);

                if(NoughtsDiagonal.winner(grid))
                {
                    win(SquareEnum.nought);
                    disableButtonsAndTxt(0, rowOrColumn.diagonal, SquareEnum.nought, NoughtsDiagonal);
                }
                break;
        }





        //Setting new player
        isNoughts = !isNoughts;


        selectFrame.setTitle("Noughts And Crosses - " + helper.getTextPlaying(helper.getPlayer(isNoughts)) + " is playing.");

    }

    private SquareEnum[][] getGrid ()
    {
        return grid;
    }

    private SquareEnum[] getRow (int row)
    {
        return grid[row];
    }

    private SquareEnum[] getCol (int col)
    {
        SquareEnum[] returner = new SquareEnum[size];

        for (int x = 0; x < size; x++)
        {
            returner[x] = grid[x][col];
        }

        return returner;
    }

    private SquareEnum rowWon (SquareEnum[] row)
    {
        int crosses = 0;
        int noughts = 0;

        for(SquareEnum sq : row)
        {
            if(sq == SquareEnum.unactive)
            {
                return SquareEnum.unactive;
            }

            if(sq == SquareEnum.cross)
                crosses++;
            if(sq == SquareEnum.nought)
                noughts++;
        }

        if(noughts == 0 && crosses == size)
            return SquareEnum.cross;

        if(crosses == 0 && noughts == size)
            return SquareEnum.nought;

        return SquareEnum.unactive;
    }
    private SquareEnum colWon (SquareEnum[] col)
    {
        int crosses = 0;
        int noughts = 0;

        for(SquareEnum sq : col)
        {
            if(sq == SquareEnum.unactive)
            {
                return SquareEnum.unactive;
            }

            if(sq == SquareEnum.cross)
                crosses++;
            if(sq == SquareEnum.nought)
                noughts++;
        }

        if(noughts == 0 && crosses == size)
            return SquareEnum.cross;

        if(crosses == 0 && noughts == size)
            return SquareEnum.nought;

        return SquareEnum.unactive;
    }

    private void didTheyWin ()
    {
        boolean foundOne = false;

        for (int x = 0; x < size; x++) {
            if(!foundOne) {
                SquareEnum[] row = getRow(x);

                switch (rowWon(row)) {
                    case nought:
                        win(SquareEnum.nought);
                        foundOne = true;
                        disableButtonsAndTxt(x, rowOrColumn.row, SquareEnum.nought, null );
                        break;
                    case cross:
                        win(SquareEnum.cross);
                        foundOne = true;
                        disableButtonsAndTxt(x, rowOrColumn.row, SquareEnum.cross, null);
                        break;
                }


            }

            for (int y = 0; y < size; y++)
            {
                SquareEnum[] col = getCol(y);

                switch (colWon(col))
                {
                    case nought:
                        win(SquareEnum.nought);
                        foundOne = true;
                        disableButtonsAndTxt(y, rowOrColumn.column, SquareEnum.nought, null);
                        break;
                    case cross:
                        win(SquareEnum.cross);
                        foundOne = true;
                        disableButtonsAndTxt(y, rowOrColumn.column, SquareEnum.nought, null);
                        break;
                }
            }

        }
    }

    private void win (SquareEnum type)
    {
        switch (type)
        {
            case cross:
                System.out.println("Crosses Won!!");
                break;
            case nought:
                System.out.println("Noughts Won!!");
                break;
        }
    }

    private void disableButtonsAndTxt (int xOrY, rowOrColumn RC, SquareEnum whoWon, diagonalRow justInCase)
    {
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                selectButtons[x][y].setEnabled(false);
            }
        }

        switch (RC)
        {
            case row:
                disableX(xOrY, whoWon);
                break;
            case column:
                disableY(xOrY, whoWon);
            case diagonal:
                disableDiagonal(justInCase, whoWon);
        }
    }

    private void disableX (int row, SquareEnum whoWon)
    {
        for (int y = 0; y < size; y++)
        {
            selectButtons[row][y].setText("WON!! as " + helper.getTextPlaying(whoWon));
        }

        for (int x = 0; x < size; x++)
        {
            if(x == row)
                continue;

            for (int y = 0; y < size; y++)
            {
                selectButtons[x][y].setText("Was " + helper.getTextPlaying(getGrid()[x][y]));
            }
        }
    }

    private void disableY (int col, SquareEnum whoWon)
    {
        for (int x = 0; x < size; x++)
        {
            selectButtons[x][col].setText("WON!! as " + helper.getTextPlaying(whoWon));
        }

        for (int y = 0; y < size; y++)
        {
            if(y == col)
                continue;

            for (int x = 0; x < size; x++)
            {
                selectButtons[x][y].setText("Was " + helper.getTextPlaying(getGrid()[x][y]));
            }
        }
    }

    private void disableDiagonal(diagonalRow diags, SquareEnum who)
    {
        for(int x : diags.getArrayListX())
        {
            for(int y : diags.getArrayListY())
            {
                selectButtons[x][y].setText("WON!! as " + helper.getTextPlaying(who));
            }
        }

        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                if(diags.getArrayListX().contains(x) || diags.getArrayListY().contains(y))
                    continue;

                selectButtons[x][y].setText("Was " + helper.getTextPlaying(getGrid()[x][y]));
            }
        }
    }
}
